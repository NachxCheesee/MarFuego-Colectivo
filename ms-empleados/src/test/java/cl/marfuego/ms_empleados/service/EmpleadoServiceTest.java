package cl.marfuego.ms_empleados.service;

import cl.marfuego.ms_empleados.client.LocalClient;
import cl.marfuego.ms_empleados.dto.EmpleadoDto;
import cl.marfuego.ms_empleados.dto.EmpleadoRespuestaDto;
import cl.marfuego.ms_empleados.dto.LocalDto;
import cl.marfuego.ms_empleados.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_empleados.model.Empleado;
import cl.marfuego.ms_empleados.repository.EmpleadoRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private LocalClient localClient;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    @DisplayName("Listar Empleados - Caso Exitoso")
    void listarEmpleados_Exitoso() {
        Empleado emp = new Empleado();
        emp.setId(1L);
        emp.setNombreCompleto("Juan Pérez");

        when(empleadoRepository.findAll()).thenReturn(List.of(emp));

        List<Empleado> resultado = empleadoService.listarEmpleados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCompleto());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar Empleado por ID - Caso Exitoso")
    void buscarEmpleadoPorId_Exitoso() {
        Empleado emp = new Empleado();
        emp.setId(1L);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(emp));

        Empleado resultado = empleadoService.buscarEmpleadoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Buscar Empleado por ID - Error No Encontrado")
    void buscarEmpleadoPorId_ErrorNoEncontrado() {
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> empleadoService.buscarEmpleadoPorId(1L));
    }

    @Test
    @DisplayName("Obtener Empleado Detallado - Caso Exitoso")
    void obtenerEmpleadoDetallado_Exitoso() {
        Empleado emp = new Empleado();
        emp.setId(1L);
        emp.setNombreCompleto("Juan Pérez");
        emp.setLocalId(10L);

        LocalDto localDtoMock = mock(LocalDto.class);

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(localClient.obtenerDatosDeLocal(10L)).thenReturn(localDtoMock);

        EmpleadoRespuestaDto resultado = empleadoService.obtenerEmpleadoDetallado(1L);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals(localDtoMock, resultado.getLocal());
    }

    @Test
    @DisplayName("Guardar Empleado - Caso Exitoso")
    void guardarEmpleado_Exitoso() {
        EmpleadoDto dto = mock(EmpleadoDto.class);
        when(dto.getLocalId()).thenReturn(10L);
        when(dto.getNombreCompleto()).thenReturn("Juan Pérez");
        when(dto.getCargo()).thenReturn(cl.marfuego.ms_empleados.enums.Cargo.DUEÑO);
        LocalDto localDtoMock = mock(LocalDto.class);
        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(100L);
        empleadoGuardado.setNombreCompleto("Juan Pérez");

        when(localClient.obtenerDatosDeLocal(10L)).thenReturn(localDtoMock);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        Empleado resultado = empleadoService.guardarEmpleado(dto);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
    }

    @Test
    @DisplayName("Guardar Empleado - Error Local No Existe")
    void guardarEmpleado_ErrorLocalNoExiste() {
        EmpleadoDto dto = mock(EmpleadoDto.class);
        when(dto.getLocalId()).thenReturn(10L);

        // Simulamos que el cliente Feign lanza una excepción (El local no existe)
        when(localClient.obtenerDatosDeLocal(10L)).thenThrow(new RuntimeException("Feign error"));

        assertThrows(ErrorNoEncontrado.class, () -> empleadoService.guardarEmpleado(dto));
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    @DisplayName("Eliminar Empleado - Caso Exitoso")
    void eliminarEmpleado_Exitoso() {
        when(empleadoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(empleadoRepository).deleteById(1L);

        assertDoesNotThrow(() -> empleadoService.eliminarEmpleado(1L));
        verify(empleadoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar Empleado - Error No Existe")
    void eliminarEmpleado_ErrorNoExiste() {
        when(empleadoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> empleadoService.eliminarEmpleado(1L));
        verify(empleadoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Actualizar Empleado - Caso Exitoso")
    void actualizarEmpleado_Exitoso() {
        EmpleadoDto dto = mock(EmpleadoDto.class);
        when(dto.getLocalId()).thenReturn(10L);
        when(dto.getNombreCompleto()).thenReturn("Juan Modificado");
        when(dto.getCargo()).thenReturn(cl.marfuego.ms_empleados.enums.Cargo.CAJERO);

        Empleado empExistente = new Empleado();
        empExistente.setId(1L);
        empExistente.setLocalId(5L);

        LocalDto localDtoMock = mock(LocalDto.class);

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empExistente));
        when(localClient.obtenerDatosDeLocal(10L)).thenReturn(localDtoMock);
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Empleado resultado = empleadoService.actualizarEmpleado(1L, dto);

        assertNotNull(resultado);
        assertEquals("Juan Modificado", resultado.getNombreCompleto());
        assertEquals(10L, resultado.getLocalId());
    }

    @Test
    @DisplayName("Actualizar Empleado - Error Local No Existe")
    void actualizarEmpleado_ErrorLocalNoExiste() {
        EmpleadoDto dto = mock(EmpleadoDto.class);
        when(dto.getLocalId()).thenReturn(10L);

        Empleado empExistente = new Empleado();
        empExistente.setId(1L);

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empExistente));
        when(localClient.obtenerDatosDeLocal(10L)).thenThrow(new RuntimeException("Feign error"));

        assertThrows(ErrorNoEncontrado.class, () -> empleadoService.actualizarEmpleado(1L, dto));
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }
}