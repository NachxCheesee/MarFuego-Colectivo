package cl.marfuego.ms_proveedores.service;

import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.repository.ProveedorRepository;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    @DisplayName("Listar Proveedores - Caso Exitoso")
    void listarProveedores_Exitoso() {
        Proveedor prov = new Proveedor();
        prov.setId(1L);
        prov.setNombre("Distribuidora Mar Fuego");

        when(proveedorRepository.findAll()).thenReturn(List.of(prov));

        List<Proveedor> resultado = proveedorService.listarProveedores();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Distribuidora Mar Fuego", resultado.get(0).getNombre());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar Proveedor por ID - Caso Exitoso")
    void buscarProveedorPorId_Exitoso() {
        Proveedor prov = new Proveedor();
        prov.setId(1L);
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(prov));

        Proveedor resultado = proveedorService.buscarProveedorPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Buscar Proveedor por ID - Error No Encontrado")
    void buscarProveedorPorId_ErrorNoEncontrado() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> proveedorService.buscarProveedorPorId(1L));
    }

    @Test
    @DisplayName("Guardar Proveedor - Caso Exitoso")
    void guardarProveedor_Exitoso() {
        // Mockeamos el DTO para simular sus getters sin importar los Enums que use por dentro
        ProveedorDto dto = mock(ProveedorDto.class);
        when(dto.getNombre()).thenReturn("Proveedor Alfa");

        // Aquí si tus campos usan Enums (ej. TipoInsumo o Estado), Mockito devolverá null por defecto,
        // lo cual es perfectamente válido para que pase por el service sin caerse.

        Proveedor proveedorGuardado = new Proveedor();
        proveedorGuardado.setId(50L);
        proveedorGuardado.setNombre("Proveedor Alfa");

        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorGuardado);

        Proveedor resultado = proveedorService.guardarProveedor(dto);

        assertNotNull(resultado);
        assertEquals(50L, resultado.getId());
        assertEquals("Proveedor Alfa", resultado.getNombre());
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Eliminar Proveedor - Caso Exitoso")
    void eliminarProveedor_Exitoso() {
        when(proveedorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(proveedorRepository).deleteById(1L);

        assertDoesNotThrow(() -> proveedorService.eliminarProveedor(1L));
        verify(proveedorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar Proveedor - Error No Existe")
    void eliminarProveedor_ErrorNoExiste() {
        when(proveedorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> proveedorService.eliminarProveedor(1L));
        verify(proveedorRepository, never()).deleteById(anyLong());
    }
}