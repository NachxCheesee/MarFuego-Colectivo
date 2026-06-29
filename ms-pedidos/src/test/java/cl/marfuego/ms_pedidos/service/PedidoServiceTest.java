package cl.marfuego.ms_pedidos.service;

import cl.marfuego.ms_pedidos.clients.EmpleadoClient;
import cl.marfuego.ms_pedidos.clients.LocalClient;
import cl.marfuego.ms_pedidos.clients.PlatoClient;
import cl.marfuego.ms_pedidos.dto.DetallePedidoDTO;
import cl.marfuego.ms_pedidos.dto.PedidoDTO;
import cl.marfuego.ms_pedidos.dto.*;
import cl.marfuego.ms_pedidos.enums.TipoPedido;
import cl.marfuego.ms_pedidos.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_pedidos.model.DetallePedido;
import cl.marfuego.ms_pedidos.model.Pedido;
import cl.marfuego.ms_pedidos.repository.PedidoRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private LocalClient localClient;

    @Mock
    private EmpleadoClient empleadoClient;

    @Mock
    private PlatoClient platoClient;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoDTO pedidoDtoLocal;
    private PedidoDTO pedidoDtoDelivery;
    private Pedido pedido;
    private DetallePedidoDTO detalleDto;

    @BeforeEach
    void setUp() {
        detalleDto = new DetallePedidoDTO();
        detalleDto.setPlato_id(1L);
        detalleDto.setCantidad(2);

        pedidoDtoLocal = new PedidoDTO();
        pedidoDtoLocal.setTipoPedido(TipoPedido.LOCAL);
        pedidoDtoLocal.setLocal_id(1L);
        pedidoDtoLocal.setMesa_id(1L);
        pedidoDtoLocal.setDetalles(List.of(detalleDto));

        pedidoDtoDelivery = new PedidoDTO();
        pedidoDtoDelivery.setTipoPedido(TipoPedido.DELIVERY);
        pedidoDtoDelivery.setEmpleado_id(1L);
        pedidoDtoDelivery.setDetalles(List.of(detalleDto));

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setTipopedido(TipoPedido.LOCAL);
        pedido.setLocal_id(1L);
        pedido.setMesa_id(1L);
        pedido.setEmpleado_id(null);
    }

    // ==========================================
    // TESTS PARA listarPedidos()
    // ==========================================

    @Test
    @DisplayName("Listar Pedidos - Caso Exitoso")
    void listarPedidos_Exitoso() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar Pedidos - Lista Vacía")
    void listarPedidos_ListaVacia() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pedidoRepository, times(1)).findAll();
    }

    // ==========================================
    // TESTS PARA buscarPedidoPorId()
    // ==========================================

    @Test
    @DisplayName("Buscar Pedido por ID - Caso Exitoso")
    void buscarPedidoPorId_Exitoso() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.buscarPedidoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar Pedido por ID - Error No Encontrado")
    void buscarPedidoPorId_ErrorNoEncontrado() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.buscarPedidoPorId(99L));
        verify(pedidoRepository, times(1)).findById(99L);
    }

    // ==========================================
    // TESTS PARA guardarPedido() - LOCAL
    // ==========================================

    @Test
    @DisplayName("Guardar Pedido LOCAL - Caso Exitoso")
    void guardarPedido_Local_Exitoso() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(localClient.obtenerLocalPorId(1L)).thenReturn(mock(LocalDTO.class));
        when(localClient.obtenerMesaPorId(1L)).thenReturn(mock(MesaDTO.class));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.guardarPedido(pedidoDtoLocal);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(platoClient, times(1)).obtenerPlatoPorId(1L);
        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(localClient, times(1)).obtenerMesaPorId(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Pedido nulo")
    void guardarPedido_ErrorPedidoNulo() {
        // El servicio lanza NullPointerException porque intenta acceder a dto.getTipoPedido() antes de validar
        assertThrows(NullPointerException.class, () -> pedidoService.guardarPedido(null));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: TipoPedido nulo")
    void guardarPedido_ErrorTipoPedidoNulo() {
        pedidoDtoLocal.setTipoPedido(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Detalles nulo o vacío")
    void guardarPedido_ErrorDetallesVacio() {
        pedidoDtoLocal.setDetalles(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: plato_id nulo")
    void guardarPedido_ErrorPlatoIdNulo() {
        detalleDto.setPlato_id(null);
        pedidoDtoLocal.setDetalles(List.of(detalleDto));
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Cantidad inválida (0 o null)")
    void guardarPedido_ErrorCantidadInvalida() {
        detalleDto.setCantidad(null);
        pedidoDtoLocal.setDetalles(List.of(detalleDto));
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Plato no existe (Feign 404)")
    void guardarPedido_Local_ErrorPlatoNoExiste() {
        when(platoClient.obtenerPlatoPorId(1L)).thenThrow(FeignException.NotFound.class);

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
        verify(localClient, never()).obtenerLocalPorId(any());
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Local no existe (Feign 404)")
    void guardarPedido_Local_ErrorLocalNoExiste() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(localClient.obtenerLocalPorId(1L)).thenThrow(FeignException.NotFound.class);

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(localClient, never()).obtenerMesaPorId(any());
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: Mesa no existe (Feign 404)")
    void guardarPedido_Local_ErrorMesaNoExiste() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(localClient.obtenerLocalPorId(1L)).thenReturn(mock(LocalDTO.class));
        when(localClient.obtenerMesaPorId(1L)).thenThrow(FeignException.NotFound.class);

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(localClient, times(1)).obtenerMesaPorId(1L);
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error: local o mesa con null en campos obligatorios")
    void guardarPedido_Local_ErrorLocalMesaNull() {
        pedidoDtoLocal.setLocal_id(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error de comunicación genérico con platos")
    void guardarPedido_Local_ErrorComunicacionPlatos() {
        when(platoClient.obtenerPlatoPorId(1L)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    @Test
    @DisplayName("Guardar Pedido LOCAL - Error de comunicación genérico con locales")
    void guardarPedido_Local_ErrorComunicacionLocales() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(localClient.obtenerLocalPorId(1L)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () -> pedidoService.guardarPedido(pedidoDtoLocal));
    }

    // ==========================================
    // TESTS PARA guardarPedido() - DELIVERY
    // ==========================================

    @Test
    @DisplayName("Guardar Pedido DELIVERY - Caso Exitoso")
    void guardarPedido_Delivery_Exitoso() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(empleadoClient.obtenerEmpleadoPorId(1L)).thenReturn(mock(EmpleadoDTO.class));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.guardarPedido(pedidoDtoDelivery);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(platoClient, times(1)).obtenerPlatoPorId(1L);
        verify(empleadoClient, times(1)).obtenerEmpleadoPorId(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Guardar Pedido DELIVERY - Error: Empleado no existe (Feign 404)")
    void guardarPedido_Delivery_ErrorEmpleadoNoExiste() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(empleadoClient.obtenerEmpleadoPorId(1L)).thenThrow(FeignException.NotFound.class);

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.guardarPedido(pedidoDtoDelivery));
        verify(empleadoClient, times(1)).obtenerEmpleadoPorId(1L);
    }

    @Test
    @DisplayName("Guardar Pedido DELIVERY - Error: empleado_id nulo")
    void guardarPedido_Delivery_ErrorEmpleadoIdNulo() {
        pedidoDtoDelivery.setEmpleado_id(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.guardarPedido(pedidoDtoDelivery));
    }

    @Test
    @DisplayName("Guardar Pedido DELIVERY - Error de comunicación genérico con empleados")
    void guardarPedido_Delivery_ErrorComunicacionEmpleados() {
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(empleadoClient.obtenerEmpleadoPorId(1L)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () -> pedidoService.guardarPedido(pedidoDtoDelivery));
    }

    // ==========================================
    // TESTS PARA actualizarPedido()
    // ==========================================

    @Test
    @DisplayName("Actualizar Pedido - Caso Exitoso")
    void actualizarPedido_Exitoso() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(localClient.obtenerLocalPorId(1L)).thenReturn(mock(LocalDTO.class));
        when(localClient.obtenerMesaPorId(1L)).thenReturn(mock(MesaDTO.class));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.actualizarPedido(1L, pedidoDtoLocal);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Actualizar Pedido - Error: Pedido no existe")
    void actualizarPedido_ErrorPedidoNoExiste() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.actualizarPedido(99L, pedidoDtoLocal));
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Actualizar Pedido DELIVERY - Caso Exitoso")
    void actualizarPedido_Delivery_Exitoso() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(platoClient.obtenerPlatoPorId(1L)).thenReturn(mock(PlatoDTO.class));
        when(empleadoClient.obtenerEmpleadoPorId(1L)).thenReturn(mock(EmpleadoDTO.class));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.actualizarPedido(1L, pedidoDtoDelivery);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    // ==========================================
    // TESTS PARA eliminarPedido()
    // ==========================================

    @Test
    @DisplayName("Eliminar Pedido - Caso Exitoso")
    void eliminarPedido_Exitoso() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1L);

        assertDoesNotThrow(() -> pedidoService.eliminarPedido(1L));
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar Pedido - Error: Pedido no existe")
    void eliminarPedido_ErrorNoExiste() {
        when(pedidoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> pedidoService.eliminarPedido(99L));
        verify(pedidoRepository, never()).deleteById(any());
    }
}