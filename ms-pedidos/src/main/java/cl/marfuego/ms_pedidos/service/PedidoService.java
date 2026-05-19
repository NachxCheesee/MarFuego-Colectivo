package cl.marfuego.ms_pedidos.service;

import cl.marfuego.ms_pedidos.clients.EmpleadoClient;
import cl.marfuego.ms_pedidos.clients.LocalClient;
import cl.marfuego.ms_pedidos.clients.PlatoClient;
import cl.marfuego.ms_pedidos.dto.DetallePedidoDTO;
import cl.marfuego.ms_pedidos.dto.PedidoDTO;
import cl.marfuego.ms_pedidos.enums.TipoPedido;
import cl.marfuego.ms_pedidos.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_pedidos.model.DetallePedido;
import cl.marfuego.ms_pedidos.model.Pedido;
import cl.marfuego.ms_pedidos.repository.PedidoRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final LocalClient localClient;
    private final EmpleadoClient empleadoClient;
    private final PlatoClient platoClient;

    public PedidoService(PedidoRepository pedidoRepository,
                         LocalClient localClient,
                         EmpleadoClient empleadoClient,
                         PlatoClient platoClient) {
        this.pedidoRepository = pedidoRepository;
        this.localClient = localClient;
        this.empleadoClient = empleadoClient;
        this.platoClient = platoClient;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ErrorNoEncontrado("No se encontró el pedido con ID: " + id));
    }

    @Transactional
    public Pedido guardarPedido(PedidoDTO dto) {
        validarPedido(dto);
        validarExistenciaPlatos(dto.getDetalles());

        if (dto.getTipoPedido() == TipoPedido.LOCAL) {
            validarLocalYMesa(dto.getLocal_id(), dto.getMesa_id());
        } else if (dto.getTipoPedido() == TipoPedido.DELIVERY) {
            validarRepartidor(dto.getEmpleado_id());
        }

        Pedido pedido = new Pedido();
        pedido.setTipopedido(dto.getTipoPedido());
        pedido.setLocal_id(dto.getLocal_id());
        pedido.setMesa_id(dto.getMesa_id());
        pedido.setEmpleado_id(dto.getEmpleado_id());
        pedido.setDetalles(convertirDetalles(dto.getDetalles()));

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido actualizarPedido(Long id, PedidoDTO dto) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        validarPedido(dto);
        validarExistenciaPlatos(dto.getDetalles());

        if (dto.getTipoPedido() == TipoPedido.LOCAL) {
            validarLocalYMesa(dto.getLocal_id(), dto.getMesa_id());
        } else if (dto.getTipoPedido() == TipoPedido.DELIVERY) {
            validarRepartidor(dto.getEmpleado_id());
        }

        pedidoExistente.setTipopedido(dto.getTipoPedido());
        pedidoExistente.setLocal_id(dto.getLocal_id());
        pedidoExistente.setMesa_id(dto.getMesa_id());
        pedidoExistente.setEmpleado_id(dto.getEmpleado_id());
        pedidoExistente.getDetalles().clear();
        pedidoExistente.getDetalles().addAll(convertirDetalles(dto.getDetalles()));

        return pedidoRepository.save(pedidoExistente);
    }

    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: El pedido " + id + " no existe.");
        }
        pedidoRepository.deleteById(id);
    }

    private void validarPedido(PedidoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El pedido no puede venir vacío.");
        }

        if (dto.getTipoPedido() == null) {
            throw new IllegalArgumentException("El tipo de pedido es obligatorio: LOCAL o DELIVERY.");
        }

        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un plato.");
        }
    }

    private List<DetallePedido> convertirDetalles(List<DetallePedidoDTO> detallesDto) {
        return detallesDto.stream().map(detalleDto -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setPlato_id(detalleDto.getPlato_id());
            detalle.setCantidad(detalleDto.getCantidad());
            return detalle;
        }).collect(Collectors.toList());
    }

    private void validarExistenciaPlatos(List<DetallePedidoDTO> detallesDto) {
        for (DetallePedidoDTO detalle : detallesDto) {
            if (detalle.getPlato_id() == null) {
                throw new IllegalArgumentException("El ID del plato es obligatorio.");
            }

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del plato debe ser mayor a 0.");
            }

            try {
                platoClient.obtenerPlatoPorId(detalle.getPlato_id());
            } catch (FeignException.NotFound e) {
                throw new ErrorNoEncontrado("El plato con ID " + detalle.getPlato_id() + " no existe.");
            } catch (FeignException e) {
                throw new RuntimeException("Error de comunicación con el servicio de platos: " + e.getMessage());
            }
        }
    }

    private void validarLocalYMesa(Long localId, Long mesaId) {
        if (localId == null || mesaId == null) {
            throw new IllegalArgumentException("Para pedidos LOCAL, local_id y mesa_id son obligatorios.");
        }

        try {
            localClient.obtenerLocalPorId(localId);
            localClient.obtenerMesaPorId(mesaId);
        } catch (FeignException.NotFound e) {
            throw new ErrorNoEncontrado("Local o mesa no encontrados en el microservicio de locales.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de locales: " + e.getMessage());
        }
    }

    private void validarRepartidor(Long empleadoId) {
        if (empleadoId == null) {
            throw new IllegalArgumentException("Para pedidos DELIVERY, empleado_id es obligatorio.");
        }

        try {
            empleadoClient.obtenerEmpleadoPorId(empleadoId);
        } catch (FeignException.NotFound e) {
            throw new ErrorNoEncontrado("El empleado/repartidor con ID " + empleadoId + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de empleados: " + e.getMessage());
        }
    }
}