package com.example.producto.service;

import com.example.producto.client.LocalClient;
import com.example.producto.dto.LocalDto;
import com.example.producto.dto.MovimientoStockDto;
import com.example.producto.dto.ProductoDto;
import com.example.producto.enums.TipoDeProducto;
import com.example.producto.exception.custom.ErrorNoEncontrado;
import com.example.producto.model.Producto;
import com.example.producto.repository.ProductoRepository;
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
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private LocalClient localClient;

    @InjectMocks
    private ProductoService productoService;

    // Variables globales para usar en los tests
    private Producto productoMock;
    private ProductoDto productoDtoMock;

    @BeforeEach
    void setUp() {
        // Inicializamos datos de prueba antes de cada test
        productoMock = new Producto(1L, 1L, "Salmón", 15000.0, TipoDeProducto.INGREDIENTE, 10, 50);

        productoDtoMock = new ProductoDto();
        productoDtoMock.setNombre("Salmón");
        productoDtoMock.setPrecio(15000.0);
        productoDtoMock.setTipo(TipoDeProducto.INGREDIENTE);
        productoDtoMock.setStockMinimo(10);
        productoDtoMock.setStock(50);
        productoDtoMock.setLocalId(1L);
    }

    // ==========================================
    // TESTS PARA LISTAR Y BUSCAR
    // ==========================================

    @Test
    void listarProductos_DebeRetornarListaDeProductos() {
        // Arrange (Preparar)
        when(productoRepository.findAll()).thenReturn(Arrays.asList(productoMock, new Producto()));

        // Act (Actuar)
        List<Producto> resultado = productoService.listarProductos();

        // Assert (Afirmar)
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void buscaPorId_CuandoExiste_DebeRetornarProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));

        Producto resultado = productoService.buscaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Salmón", resultado.getNombre());
    }

    @Test
    void buscaPorId_CuandoNoExiste_DebeLanzarErrorNoEncontrado() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ErrorNoEncontrado.class, () -> productoService.buscaPorId(99L));
    }

    // ==========================================
    // TESTS PARA GUARDAR PRODUCTO
    // ==========================================

    @Test
    void guardarProducto_ConLocalValido_DebeGuardarYRetornarProducto() {
        // Mock de FeignClient (Simulamos que el local existe)
        LocalDto localMock = new LocalDto(1L, "Sede Central", "Dir", "Ciudad");
        when(localClient.obtenerLocalPorId(1L)).thenReturn(localMock);

        // Mock del Repository
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        Producto resultado = productoService.guardarProducto(productoDtoMock);

        assertNotNull(resultado);
        assertEquals("Salmón", resultado.getNombre());
        verify(productoRepository).save(any(Producto.class)); // Verifica que se llamó al guardado
    }

    @Test
    void guardarProducto_ConLocalInexistente_DebeLanzarErrorNoEncontrado() {
        // Simulamos que el microservicio de Locales devuelve nulo (no existe)
        when(localClient.obtenerLocalPorId(1L)).thenReturn(null);

        // Verificamos que lance la excepción antes de intentar guardar
        assertThrows(ErrorNoEncontrado.class, () -> productoService.guardarProducto(productoDtoMock));
        verify(productoRepository, never()).save(any(Producto.class)); // Nunca debió llegar al save
    }

    @Test
    void guardarProducto_ConTipoNulo_DebeLanzarIllegalArgumentException() {
        LocalDto localMock = new LocalDto(1L, "Sede", "Dir", "Ciudad");
        when(localClient.obtenerLocalPorId(1L)).thenReturn(localMock);

        productoDtoMock.setTipo(null); // Forzamos el error

        assertThrows(IllegalArgumentException.class, () -> productoService.guardarProducto(productoDtoMock));
    }

    // ==========================================
    // TESTS PARA ACTUALIZAR Y ELIMINAR
    // ==========================================

    @Test
    void actualizarProducto_CuandoExiste_DebeActualizarDatos() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        productoDtoMock.setNombre("Salmón Premium"); // Cambiamos el nombre en el DTO

        Producto resultado = productoService.actualizarProducto(1L, productoDtoMock);

        assertEquals("Salmón Premium", resultado.getNombre());
        verify(productoRepository).save(productoMock);
    }

    @Test
    void eliminaProducto_CuandoExiste_DebeEliminarlo() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.eliminaProducto(1L);

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminaProducto_CuandoNoExiste_DebeLanzarErrorNoEncontrado() {
        when(productoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ErrorNoEncontrado.class, () -> productoService.eliminaProducto(99L));
        verify(productoRepository, never()).deleteById(anyLong());
    }

    @Test
    void productoExistePorId_DebeRetornarBooleanoCorrecto() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        assertTrue(productoService.productoExistePorId(1L));
    }

    // ==========================================
    // TESTS PARA GESTIÓN DE STOCK (CON LÓGICA DE NEGOCIO)
    // ==========================================

    @Test
    void agregarStock_SinCosto_SoloDebeSumarCantidad() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        MovimientoStockDto movimiento = new MovimientoStockDto();
        movimiento.setCantidad(20);
        // Costo no enviado (null)

        // Stock original: 50. Sumamos 20 = 70.
        Producto resultado = productoService.agregarStock(1L, movimiento);

        assertEquals(70, resultado.getStock());
        assertEquals(15000.0, resultado.getPrecio()); // El precio no debió cambiar
    }

    @Test
    void agregarStock_ConCosto_DebeRecalcularPrecioPromedio() {
        // Stock inicial: 50 | Precio inicial: 15,000 (Valor total: 750,000)
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        MovimientoStockDto movimiento = new MovimientoStockDto();
        movimiento.setCantidad(50); // Ingresan 50 más (Total stock será 100)
        movimiento.setCostoTotalCompra(1000000.0); // Costo de la nueva compra: 1,000,000

        // Lógica esperada (CPP): (750,000 + 1,000,000) / 100 = 17,500

        Producto resultado = productoService.agregarStock(1L, movimiento);

        assertEquals(100, resultado.getStock());
        assertEquals(17500.0, resultado.getPrecio()); // El precio promedio debió actualizarse
    }

    @Test
    void quitarStock_ConStockSuficiente_DebeRestarCantidad() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        MovimientoStockDto movimiento = new MovimientoStockDto();
        movimiento.setCantidad(15); // Restamos 15 de 50 = 35

        Producto resultado = productoService.quitarStock(1L, movimiento);

        assertEquals(35, resultado.getStock());
    }

    @Test
    void quitarStock_ConStockInsuficiente_DebeLanzarExcepcion() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));

        MovimientoStockDto movimiento = new MovimientoStockDto();
        movimiento.setCantidad(100); // Intentamos quitar 100, pero solo hay 50

        assertThrows(IllegalArgumentException.class, () -> productoService.quitarStock(1L, movimiento));
        verify(productoRepository, never()).save(any(Producto.class)); // No debe guardar si falla
    }
}