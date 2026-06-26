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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final LocalClient localClient;
    private final EmpleadoClient empleadoClient;
    private final PlatoClient platoClient;

    public PedidoService(PedidoRepository pedidoRepository, LocalClient localClient, EmpleadoClient empleadoClient, PlatoClient platoClient) {
        this.pedidoRepository = pedidoRepository;
        this.localClient = localClient;
        this.empleadoClient = empleadoClient;
        this.platoClient = platoClient;
    }

    public List<Pedido> listarPedidos() {
        log.info("[Service] Solicitando todos los pedidos a la base de datos");
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[Service] ⚠️ Fallo: No se encontró el pedido con ID: {}", id);
                    return new ErrorNoEncontrado("No se encontró el pedido con ID: " + id);
                });
    }

    @Transactional
    public Pedido guardarPedido(PedidoDTO dto) {
        log.info("[Service] Iniciando registro de nuevo pedido. Tipo: {} con {} detalles: ", dto.getTipoPedido(), dto.getDetalles() != null ? dto.getDetalles().size() : 0);
        validarPedido(dto);
        validarExistenciaPlatos(dto.getDetalles());

        if (dto.getTipoPedido() == TipoPedido.LOCAL) {
            log.info("[Service] Pedido Local: Validando Local Id: {} y mesa con Id: {}",dto.getLocal_id(), dto.getMesa_id());
            validarLocalYMesa(dto.getLocal_id(), dto.getMesa_id());
        } else if (dto.getTipoPedido() == TipoPedido.DELIVERY) {
            log.info("[Service] Pedido Delivery: validando empleado/repartidor con Id: {}",dto.getEmpleado_id());
            validarRepartidor(dto.getEmpleado_id());
        }
        Pedido pedido = new Pedido();
        pedido.setTipopedido(dto.getTipoPedido());
        pedido.setLocal_id(dto.getLocal_id());
        pedido.setMesa_id(dto.getMesa_id());
        pedido.setEmpleado_id(dto.getEmpleado_id());
        pedido.setDetalles(convertirDetalles(dto.getDetalles()));

        Pedido guardado = pedidoRepository.save(pedido);
        log.info("[Service] Pedido guardado exitosamente con Id: {}", guardado.getId());
        return guardado;
    }

    @Transactional
    public Pedido actualizarPedido(Long id, PedidoDTO dto) {
        log.info("[Service] Iniciando actualización del pedido con Id: {}",id);
        Pedido pedidoExistente = buscarPedidoPorId(id);
        validarPedido(dto);
        validarExistenciaPlatos(dto.getDetalles());

        if (dto.getTipoPedido() == TipoPedido.LOCAL) {
            log.info("[Service] Actualización Local: Validando Local Id: {} y mesa con Id: {}",dto.getLocal_id(), dto.getMesa_id());
            validarLocalYMesa(dto.getLocal_id(), dto.getMesa_id());
        } else if (dto.getTipoPedido() == TipoPedido.DELIVERY) {
            log.info("[Service] Actualización Delivery: Validando empleado/repartidor con Id: {}",dto.getEmpleado_id());
            validarRepartidor(dto.getEmpleado_id());
        }
        pedidoExistente.setTipopedido(dto.getTipoPedido());
        pedidoExistente.setLocal_id(dto.getLocal_id());
        pedidoExistente.setMesa_id(dto.getMesa_id());
        pedidoExistente.setEmpleado_id(dto.getEmpleado_id());
        pedidoExistente.getDetalles().clear();
        pedidoExistente.getDetalles().addAll(convertirDetalles(dto.getDetalles()));

        Pedido actualizado = pedidoRepository.save(pedidoExistente);
        log.info("[Service] Pedido con Id: {} Actualizado correctamente",id);
        return actualizado;
    }

    public void eliminarPedido(Long id) {
        log.info("[Service] Intentando eliminar pedido con Id: {}",id);
        if (!pedidoRepository.existsById(id)) {
            log.warn("[Service] ⚠️ Fallo al eliminar: El pedido {} no existe en los registros",id);
            throw new ErrorNoEncontrado("No se puede eliminar: El pedido " + id + " no existe.");
        }
        pedidoRepository.deleteById(id);
        log.info("[Service] Pedido con Id: {} eliminado correctamente",id);
    }

    private void validarPedido(PedidoDTO dto) {
        log.debug("[Service] Validando estructura básica del pedido");
        if (dto == null) {
            log.warn("[Service] ⚠️ Error de validación: el pedido es nulo");
            throw new IllegalArgumentException("El pedido no puede venir vacío.");
        }
        if (dto.getTipoPedido() == null) {
            log.warn("[Service] ⚠️ Error de validación: Tipo de pedido no especificados");
            throw new IllegalArgumentException("El tipo de pedido es obligatorio: LOCAL o DELIVERY.");
        }
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            log.warn("[Service] ⚠️ Error de validación: El pedido no tiene platos");
            throw new IllegalArgumentException("El pedido debe contener al menos un plato.");
        }
        log.debug("[Service] Validación básica del pedido superado");
    }

    private List<DetallePedido> convertirDetalles(List<DetallePedidoDTO> detallesDto) {
        log.debug("[Service] Convirtiendo {} detalles DTO a entidades", detallesDto.size());
        return detallesDto.stream().map(detalleDto -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setPlato_id(detalleDto.getPlato_id());
            detalle.setCantidad(detalleDto.getCantidad());
            return detalle;
        }).collect(Collectors.toList());
    }

    private void validarExistenciaPlatos(List<DetallePedidoDTO> detallesDto) {
        log.info("[Service] Validando existencia de {} platos en el catálogo", detallesDto.size());
        for (DetallePedidoDTO detalle : detallesDto) {
            if (detalle.getPlato_id() == null) {
                log.warn("[Service] ⚠️ Error: Plato sin Id en el detalle del pedido");
                throw new IllegalArgumentException("El ID del plato es obligatorio.");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                log.warn("[Service] ⚠️ Error: Cantidad inválida ({}) para el plato Id: {}",detalle.getCantidad(), detalle.getPlato_id());
                throw new IllegalArgumentException("La cantidad del plato debe ser mayor a 0.");
            }
            try {
                log.debug("[Service] Consultado plato Id: {}", detalle.getPlato_id());
                platoClient.obtenerPlatoPorId(detalle.getPlato_id());
                log.debug("[Service] Plato Id: {} existe en el catálogo", detalle.getPlato_id());
            } catch (FeignException.NotFound e) {
                log.warn("[Service] ⚠️ Plato Id: {} no se encontró en el catálogo (feign 404).",detalle.getPlato_id());
                throw new ErrorNoEncontrado("El plato con ID " + detalle.getPlato_id() + " no existe.");
            } catch (FeignException e) {
                log.error("[Service] Error de comunicación con el servicio de platos: "+e.getMessage(),e);
                throw new RuntimeException("Error de comunicación con el servicio de platos: " + e.getMessage());
            }
        }
        log.info("[Service] Validaciones de platos completa: Todos existen");
    }

    private void validarLocalYMesa(Long localId, Long mesaId) {
        log.info("[Service] Validando la existencia del local Id: {} y mesa {}", localId, mesaId);
        if (localId == null || mesaId == null) {
            log.warn("[Service] ⚠️ Error: local_id o mesa_id nulos para pedido Local");
            throw new IllegalArgumentException("Para pedidos LOCAL, local_id y mesa_id son obligatorios.");
        }
        try {
            log.debug("[Service] Consultado local Id: {}", localId);
            localClient.obtenerLocalPorId(localId);
            log.debug("[Service] Local id: {} existe", localId);

            log.debug("[Service] Consultando mesa Id: {}", mesaId);
            localClient.obtenerMesaPorId(mesaId);
            log.debug("[Service] Mesa Id: {} existe", mesaId);

            log.info("[Service] Validación exitosa: local y mesa existen");
        } catch (FeignException.NotFound e) {
            log.info("[Service] ⚠️ Error Local o mesa no encontrado (feign 404). LocalId: {}, MesaId: {}", localId, mesaId);
            throw new ErrorNoEncontrado("Local o mesa no encontrados en el microservicio de locales.");
        } catch (FeignException e) {
            log.error("[Service] Error de comunicación con el servidor de locales: {}",e.getMessage(),e);
            throw new RuntimeException("Error de comunicación con el servicio de locales: " + e.getMessage());
        }
    }

    private void validarRepartidor(Long empleadoId) {
        log.info("[Service] Validando la existencia de empleado/repartidor Id: {}", empleadoId);
        if (empleadoId == null) {
            log.warn("[Service] ⚠️ Error: empleado_id nulo para delivery");
            throw new IllegalArgumentException("Para pedidos DELIVERY, empleado_id es obligatorio.");
        }
        try {
            log.debug("[Service] Consultando empleado Id: {}", empleadoId);
            empleadoClient.obtenerEmpleadoPorId(empleadoId);
            log.debug("[Service] Validacion exitosa: empleado: {} existe",empleadoId);
        } catch (FeignException.NotFound e) {
            log.warn("[Service] ⚠️ Empleado Id: {} no encontrado (Feign 404)",empleadoId);
            throw new ErrorNoEncontrado("El empleado/repartidor con ID " + empleadoId + " no existe.");
        } catch (FeignException e) {
            log.error("[Service] Error de comunicación con el servicio de empleado: {}",e.getMessage(),e);
            throw new RuntimeException("Error de comunicación con el servicio de empleados: " + e.getMessage());
        }
    }
}