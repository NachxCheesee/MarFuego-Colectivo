package com.example.msplato.service;

import com.example.msplato.client.LocalClient;
import com.example.msplato.client.ProductoClient;
import com.example.msplato.dto.LocalDto;
import com.example.msplato.dto.PlatoDto;
import com.example.msplato.dto.ProductoDto;
import com.example.msplato.enums.TipoDeProducto;
import com.example.msplato.exception.custom.ErrorNoEncontrado;
import com.example.msplato.model.Plato;
import com.example.msplato.repository.PlatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoRepository platoRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private LocalClient localClient;

    @InjectMocks
    private PlatoService platoService;

    // Variables globales para usar en los tests
    private Plato platoMock;
    private PlatoDto platoDtoMock;
    private ProductoDto productoIngredienteMock;
    private ProductoDto productoBebestibleMock;

    @BeforeEach
    void setUp() {
        // Inicializamos datos de prueba antes de cada test
        platoMock = new Plato();
        platoMock.setId(1L);
        platoMock.setNombre("Ceviche de Salmón");
        platoMock.setLocalId(1L);
        platoMock.setPrecioVenta(15000.0);
        platoMock.setDisponible(true);
        platoMock.setProductoId(Arrays.asList(10L, 11L)); // IDs de ingredientes ficticios

        platoDtoMock = new PlatoDto();
        platoDtoMock.setNombre("Ceviche de Salmón");
        platoDtoMock.setLocalId(1L);
        platoDtoMock.setPrecioVenta(15000.0);
        platoDtoMock.setDisponible(true);
        platoDtoMock.setProductoIds(Arrays.asList(10L, 11L));

        // Simulamos un producto que SÍ es ingrediente
        productoIngredienteMock = new ProductoDto(10L, 1L, "Salmón", 2500.0, TipoDeProducto.INGREDIENTE, 10, 50);

        // Simulamos un producto que NO es ingrediente (para forzar errores)
        productoBebestibleMock = new ProductoDto(12L, 1L, "Jugo de Naranja", 1500.0, TipoDeProducto.BEBESTIBLE, 10, 50);
    }

    // ==========================================
    // TESTS PARA LISTAR Y BUSCAR
    // ==========================================

    @Test
    void listarPlatos_DebeRetornarListaDePlatos() {
        // Arrange
        when(platoRepository.findAll()).thenReturn(Arrays.asList(platoMock, new Plato()));

        // Act
        List<Plato> resultado = platoService.listarPlatos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(platoRepository, times(1)).findAll();
    }

    @Test
    void buscarPlatoPorId_CuandoExiste_DebeRetornarPlato() {
        when(platoRepository.findById(1L)).thenReturn(Optional.of(platoMock));

        Plato resultado = platoService.buscarPlatoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Ceviche de Salmón", resultado.getNombre());
    }

    @Test
    void buscarPlatoPorId_CuandoNoExiste_DebeLanzarErrorNoEncontrado() {
        when(platoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> platoService.buscarPlatoPorId(99L));
    }

    // ==========================================
    // TESTS PARA GUARDAR PLATO
    // ==========================================

    @Test
    void guardarPlato_ConDatosCorrectos_DebeGuardarYRetornarPlato() {
        // Arrange: Simulamos que el local existe
        LocalDto localMock = new LocalDto();
        when(localClient.obtenerLocalPorId(1L)).thenReturn(localMock);

        // Arrange: Simulamos que ambos productos del DTO (10 y 11) existen y son INGREDIENTES
        when(productoClient.obtenerProductosPorId(10L)).thenReturn(productoIngredienteMock);
        when(productoClient.obtenerProductosPorId(11L)).thenReturn(productoIngredienteMock);

        // Arrange: Simulamos el guardado
        when(platoRepository.save(any(Plato.class))).thenReturn(platoMock);

        // Act
        Plato resultado = platoService.guardarPlato(platoDtoMock);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ceviche de Salmón", resultado.getNombre());
        verify(platoRepository).save(any(Plato.class));
    }

    @Test
    void guardarPlato_ConLocalInexistente_DebeLanzarErrorNoEncontrado() {
        // Arrange: Simulamos que FeignClient arroja un error porque el local no existe
        when(localClient.obtenerLocalPorId(1L)).thenThrow(new RuntimeException("Error Feign"));

        // Act & Assert
        assertThrows(ErrorNoEncontrado.class, () -> platoService.guardarPlato(platoDtoMock));
        verify(platoRepository, never()).save(any(Plato.class)); // Verificamos que no intentó guardar
    }

    @Test
    void guardarPlato_ConProductoQueNoEsIngrediente_DebeLanzarIllegalArgumentException() {
        // Arrange: Local válido
        when(localClient.obtenerLocalPorId(1L)).thenReturn(new LocalDto());

        // Arrange: El primer producto en la lista es un BEBESTIBLE (No permitido)
        platoDtoMock.setProductoIds(Arrays.asList(12L));
        when(productoClient.obtenerProductosPorId(12L)).thenReturn(productoBebestibleMock);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> platoService.guardarPlato(platoDtoMock));

        // Verificamos que el mensaje contenga el texto correcto
        assertTrue(exception.getMessage().contains("no es un ingrediente"));
        verify(platoRepository, never()).save(any(Plato.class));
    }

    // ==========================================
    // TESTS PARA ACTUALIZAR PLATO
    // ==========================================

    @Test
    void actualizarPlato_CuandoExisteYDatosCorrectos_DebeActualizar() {
        // Arrange: El plato existe en la BD
        when(platoRepository.findById(1L)).thenReturn(Optional.of(platoMock));

        // Arrange: Los productos asociados son válidos
        when(productoClient.obtenerProductosPorId(10L)).thenReturn(productoIngredienteMock);
        when(productoClient.obtenerProductosPorId(11L)).thenReturn(productoIngredienteMock);

        // Arrange: Repositorio guarda exitosamente
        when(platoRepository.save(any(Plato.class))).thenReturn(platoMock);

        platoDtoMock.setNombre("Ceviche Premium");

        // Act
        Plato resultado = platoService.actualizarPlato(1L, platoDtoMock);

        // Assert
        assertEquals("Ceviche Premium", resultado.getNombre());
        verify(platoRepository).save(platoMock);
    }

    @Test
    void actualizarPlato_CuandoNoExiste_DebeLanzarErrorNoEncontrado() {
        when(platoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> platoService.actualizarPlato(99L, platoDtoMock));
        verify(platoRepository, never()).save(any(Plato.class));
    }

    // ==========================================
    // TESTS PARA EXISTENCIA Y ELIMINAR
    // ==========================================

    @Test
    void existePlatoPorId_DebeRetornarBooleanoCorrecto() {
        when(platoRepository.existsById(1L)).thenReturn(true);
        assertTrue(platoService.existePlatoPorId(1L));

        when(platoRepository.existsById(99L)).thenReturn(false);
        assertFalse(platoService.existePlatoPorId(99L));
    }

    @Test
    void eliminarPlato_CuandoExiste_DebeEliminarlo() {
        when(platoRepository.existsById(1L)).thenReturn(true);

        platoService.eliminarPlato(1L);

        verify(platoRepository).deleteById(1L);
    }

    @Test
    void eliminarPlato_CuandoNoExiste_DebeLanzarErrorNoEncontrado() {
        when(platoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> platoService.eliminarPlato(99L));
        verify(platoRepository, never()).deleteById(anyLong());
    }
}