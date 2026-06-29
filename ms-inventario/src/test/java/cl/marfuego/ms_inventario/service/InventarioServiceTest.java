package cl.marfuego.ms_inventario.service;

import cl.marfuego.ms_inventario.DTO.InventarioDTO;
import cl.marfuego.ms_inventario.DTO.LocalDTO;
import cl.marfuego.ms_inventario.client.LocalClient;
import cl.marfuego.ms_inventario.model.Inventario;
import cl.marfuego.ms_inventario.repository.InventarioRepository;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private LocalClient localClient;

    @InjectMocks
    private InventarioService inventarioService;

    // ==========================================
    // TESTS PARA listarInventario()
    // ==========================================

    @Test
    @DisplayName("Listar Inventario - Caso Exitoso")
    void listarInventario_Exitoso() {
        Inventario inventario = new Inventario(1L, "Corvina De Oro", 1L);
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        List<Inventario> resultado = inventarioService.listarInventario();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Corvina De Oro", resultado.get(0).getNombre());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar Inventario - Lista Vacía")
    void listarInventario_ListaVacia() {
        when(inventarioRepository.findAll()).thenReturn(List.of());

        List<Inventario> resultado = inventarioService.listarInventario();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(inventarioRepository, times(1)).findAll();
    }

    // ==========================================
    // TESTS PARA buscarPorId()
    // ==========================================

    @Test
    @DisplayName("Buscar Inventario por ID - Caso Exitoso")
    void buscarPorId_Exitoso() {
        Inventario inventario = new Inventario(1L, "Corvina De Oro", 1L);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

        Inventario resultado = inventarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Corvina De Oro", resultado.getNombre());
        assertEquals(1L, resultado.getLocal_id());
        verify(inventarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar Inventario por ID - Error No Encontrado")
    void buscarPorId_ErrorNoEncontrado() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> inventarioService.buscarPorId(99L));

        assertNotNull(ex.getMessage());
        verify(inventarioRepository, times(1)).findById(99L);
    }

    // ==========================================
    // TESTS PARA listarPorLocal()
    // ==========================================

    @Test
    @DisplayName("Listar Inventario por Local - Caso Exitoso")
    void listarPorLocal_Exitoso() {
        Inventario inventario = new Inventario(1L, "Corvina De Oro", 1L);
        when(inventarioRepository.findByLocal_id(1L)).thenReturn(List.of(inventario));

        List<Inventario> resultado = inventarioService.listarPorLocal(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getLocal_id());
        verify(inventarioRepository, times(1)).findByLocal_id(1L);
    }

    @Test
    @DisplayName("Listar Inventario por Local - Lista Vacía")
    void listarPorLocal_ListaVacia() {
        when(inventarioRepository.findByLocal_id(99L)).thenReturn(List.of());

        List<Inventario> resultado = inventarioService.listarPorLocal(99L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(inventarioRepository, times(1)).findByLocal_id(99L);
    }

    // ==========================================
    // TESTS PARA guardarInventario()
    // ==========================================

    @Test
    @DisplayName("Guardar Inventario - Caso Exitoso")
    void guardarInventario_Exitoso() {
        InventarioDTO dto = new InventarioDTO("Salmón fresco", 1L);
        Inventario inventarioGuardado = new Inventario(1L, "Salmón fresco", 1L);
        LocalDTO localDto = new LocalDTO();

        when(localClient.obtenerLocalPorId(1L)).thenReturn(localDto);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioGuardado);

        Inventario resultado = inventarioService.guardarInventario(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Salmón fresco", resultado.getNombre());
        assertEquals(1L, resultado.getLocal_id());
        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Guardar Inventario - localClient devuelve null (falla validación)")
    void guardarInventario_LocalDevuelveNull() {
        InventarioDTO dto = new InventarioDTO("Salmón fresco", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> inventarioService.guardarInventario(dto));

        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Guardar Inventario - Error Local No Existe (Feign 404)")
    void guardarInventario_ErrorLocalNoExiste() {
        InventarioDTO dto = new InventarioDTO("Salmón fresco", 99L);

        when(localClient.obtenerLocalPorId(99L)).thenThrow(FeignException.NotFound.class);

        assertThrows(RuntimeException.class,
                () -> inventarioService.guardarInventario(dto));

        verify(localClient, times(1)).obtenerLocalPorId(99L);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Guardar Inventario - Error de comunicación Feign genérico")
    void guardarInventario_ErrorComunicacionFeign() {
        InventarioDTO dto = new InventarioDTO("Salmón fresco", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class,
                () -> inventarioService.guardarInventario(dto));

        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    // ==========================================
    // TESTS PARA actualizarInventario()
    // ==========================================

    @Test
    @DisplayName("Actualizar Inventario - Caso Exitoso")
    void actualizarInventario_Exitoso() {
        InventarioDTO dto = new InventarioDTO("Mero chileno", 1L);
        Inventario inventarioExistente = new Inventario(1L, "Salmón fresco", 1L);
        Inventario inventarioActualizado = new Inventario(1L, "Mero chileno", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenReturn(mock(LocalDTO.class));
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioExistente));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioActualizado);

        Inventario resultado = inventarioService.actualizarInventario(1L, dto);

        assertNotNull(resultado);
        assertEquals("Mero chileno", resultado.getNombre());
        assertEquals(1L, resultado.getLocal_id());
        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, times(1)).findById(1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Actualizar Inventario - localClient devuelve null (falla validación)")
    void actualizarInventario_LocalDevuelveNull() {
        InventarioDTO dto = new InventarioDTO("Mero chileno", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(1L, dto));

        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, never()).findById(anyLong());
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Actualizar Inventario - Error Local No Existe (Feign 404)")
    void actualizarInventario_ErrorLocalNoExiste() {
        InventarioDTO dto = new InventarioDTO("Mero chileno", 99L);

        when(localClient.obtenerLocalPorId(99L)).thenThrow(FeignException.NotFound.class);

        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(1L, dto));

        verify(localClient, times(1)).obtenerLocalPorId(99L);
        verify(inventarioRepository, never()).findById(anyLong());
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Actualizar Inventario - Error de comunicación Feign genérico")
    void actualizarInventario_ErrorComunicacionFeign() {
        InventarioDTO dto = new InventarioDTO("Mero chileno", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(1L, dto));

        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, never()).findById(anyLong());
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Actualizar Inventario - Error Inventario No Existe")
    void actualizarInventario_ErrorInventarioNoExiste() {
        InventarioDTO dto = new InventarioDTO("Mero chileno", 1L);

        when(localClient.obtenerLocalPorId(1L)).thenReturn(mock(LocalDTO.class));
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(99L, dto));

        verify(localClient, times(1)).obtenerLocalPorId(1L);
        verify(inventarioRepository, times(1)).findById(99L);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    // ==========================================
    // TESTS PARA eliminarInventario()
    // ==========================================

    @Test
    @DisplayName("Eliminar Inventario - Caso Exitoso")
    void eliminarInventario_Exitoso() {
        Inventario inventario = new Inventario(1L, "Corvina De Oro", 1L);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));
        doNothing().when(inventarioRepository).delete(any(Inventario.class));

        assertDoesNotThrow(() -> inventarioService.eliminarInventario(1L));

        verify(inventarioRepository, times(1)).findById(1L);
        verify(inventarioRepository, times(1)).delete(inventario);
    }

    @Test
    @DisplayName("Eliminar Inventario - Error No Existe")
    void eliminarInventario_ErrorNoExiste() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> inventarioService.eliminarInventario(99L));

        verify(inventarioRepository, times(1)).findById(99L);
        verify(inventarioRepository, never()).delete(any(Inventario.class));
    }
}