package cl.marfuego.ms_locales.service;

import cl.marfuego.ms_locales.dto.LocalDto;
import cl.marfuego.ms_locales.dto.MesaDto;
import cl.marfuego.ms_locales.enums.EstadoMesa;
import cl.marfuego.ms_locales.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_locales.model.Local;
import cl.marfuego.ms_locales.model.Mesa;
import cl.marfuego.ms_locales.repository.LocalRepository;
import cl.marfuego.ms_locales.repository.MesaRepository;
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
class LocalServiceTest {

    @Mock
    private LocalRepository localRepository;

    @Mock
    private MesaRepository mesaRepository;

    @InjectMocks
    private LocalService localService;

    // ==========================================
    // TESTS PARA LOCALES
    // ==========================================

    @Test
    @DisplayName("Listar Locales - Caso Exitoso")
    void listarLocales_Exitoso() {
        // Arrange (Preparar datos fakes)
        Local local = new Local();
        local.setId(1L);
        local.setNombre("MarFuego Central");
        when(localRepository.findAll()).thenReturn(List.of(local));

        // Act (Ejecutar el método real)
        List<Local> resultado = localService.listarLocales();

        // Assert (Verificar el resultado)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MarFuego Central", resultado.get(0).getNombre());
        verify(localRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar Local por ID - Caso Exitoso")
    void buscarLocalPorId_Exitoso() {
        Local local = new Local();
        local.setId(1L);
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));

        Local resultado = localService.buscarLocalPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Buscar Local por ID - Error No Encontrado")
    void buscarLocalPorId_ErrorNoEncontrado() {
        when(localRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> localService.buscarLocalPorId(1L));
    }

    @Test
    @DisplayName("Guardar Local - Caso Exitoso")
    void guardarLocal_Exitoso() {
        // En vez de hacer 'new LocalDto()', mockeamos el DTO para saltar el problema del constructor
        LocalDto dto = mock(LocalDto.class);
        when(dto.getNombre()).thenReturn("MarFuego Mall");
        when(dto.getDireccion()).thenReturn("Av. Pacifico 450");
        when(dto.getCiudad()).thenReturn("Puerto Montt");

        Local localGuardado = new Local();
        localGuardado.setId(2L);
        localGuardado.setNombre("MarFuego Mall");

        when(localRepository.save(any(Local.class))).thenReturn(localGuardado);

        Local resultado = localService.guardarLocal(dto);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("MarFuego Mall", resultado.getNombre());
    }

    @Test
    @DisplayName("Eliminar Local - Caso Exitoso")
    void eliminarLocal_Exitoso() {
        when(localRepository.existsById(1L)).thenReturn(true);
        doNothing().when(localRepository).deleteById(1L);

        assertDoesNotThrow(() -> localService.eliminarLocal(1L));
        verify(localRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar Local - Error No Existe")
    void eliminarLocal_ErrorNoExiste() {
        when(localRepository.existsById(1L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> localService.eliminarLocal(1L));
        verify(localRepository, never()).deleteById(anyLong());
    }

    // ==========================================
    // TESTS PARA MESAS
    // ==========================================

    @Test
    @DisplayName("Listar Mesas - Caso Exitoso")
    void listarMesas_Exitoso() {
        Mesa mesa = new Mesa();
        mesa.setId(10L);
        when(mesaRepository.findAll()).thenReturn(List.of(mesa));

        List<Mesa> resultado = localService.listarMesas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Buscar Mesa por ID - Caso Exitoso")
    void buscarMesaPorId_Exitoso() {
        Mesa mesa = new Mesa();
        mesa.setId(10L);
        when(mesaRepository.findById(10L)).thenReturn(Optional.of(mesa));

        Mesa resultado = localService.buscarMesaPorId(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
    }

    @Test
    @DisplayName("Buscar Mesa por ID - Error No Encontrado")
    void buscarMesaPorId_ErrorNoEncontrado() {
        when(mesaRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> localService.buscarMesaPorId(10L));
    }

    @Test
    @DisplayName("Guardar Mesa - Caso Exitoso")
    void guardarMesa_Exitoso() {
        MesaDto dto = mock(MesaDto.class);
        when(dto.getNumeroMesa()).thenReturn(5);
        when(dto.getCapacidad()).thenReturn(4);
        when(dto.getLocalId()).thenReturn(1L);
        when(dto.getEstado()).thenReturn(EstadoMesa.LIBRE);

        Local localFalso = new Local();
        localFalso.setId(1L);

        Mesa mesaGuardada = new Mesa();
        mesaGuardada.setId(100L);
        mesaGuardada.setNumeroMesa(5);

        when(localRepository.findById(1L)).thenReturn(Optional.of(localFalso));
        when(mesaRepository.save(any(Mesa.class))).thenReturn(mesaGuardada);

        Mesa resultado = localService.guardarMesa(dto);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals(5, resultado.getNumeroMesa());
    }

    @Test
    @DisplayName("Guardar Mesa - Error Local No Existe")
    void guardarMesa_ErrorLocalNoExiste() {
        MesaDto dto = new MesaDto();
        dto.setLocalId(1L);

        when(localRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> localService.guardarMesa(dto));
        verify(mesaRepository, never()).save(any(Mesa.class));
    }

    @Test
    @DisplayName("Eliminar Mesa - Caso Exitoso")
    void eliminarMesa_Exitoso() {
        when(mesaRepository.existsById(50L)).thenReturn(true);
        doNothing().when(mesaRepository).deleteById(50L);

        assertDoesNotThrow(() -> localService.eliminarMesa(50L));
    }

    @Test
    @DisplayName("Eliminar Mesa - Error No Existe")
    void eliminarMesa_ErrorNoExiste() {
        when(mesaRepository.existsById(50L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> localService.eliminarMesa(50L));
    }

    @Test
    @DisplayName("Obtener Mesas por Estado y Local")
    void obtenerMesaEstadoPorLocal_Exitoso() {
        when(mesaRepository.findByLocalIdAndEstado(1L, EstadoMesa.LIBRE)).thenReturn(Collections.emptyList());

        List<Mesa> resultado = localService.obtenerMesaEstadoPorLocal(1L, EstadoMesa.LIBRE);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Cambiar Estado Mesa - Caso Exitoso")
    void cambiarEstadoMesa_Exitoso() {
        Mesa mesa = new Mesa();
        mesa.setId(10L);
        mesa.setEstado(EstadoMesa.LIBRE);

        when(mesaRepository.findById(10L)).thenReturn(Optional.of(mesa));
        when(mesaRepository.save(any(Mesa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mesa resultado = localService.cambiarEstadoMesa(10L, EstadoMesa.OCUPADA);

        assertNotNull(resultado);
        assertEquals(EstadoMesa.OCUPADA, resultado.getEstado());
    }

    @Test
    @DisplayName("Cambiar Estado Mesa - Error No Encontrado")
    void cambiarEstadoMesa_ErrorNoEncontrado() {
        when(mesaRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> localService.cambiarEstadoMesa(10L, EstadoMesa.OCUPADA));
        verify(mesaRepository, never()).save(any(Mesa.class));
    }
}