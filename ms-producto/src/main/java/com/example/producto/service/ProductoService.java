package com.example.producto.service;

import com.example.producto.client.LocalClient;
import com.example.producto.dto.LocalDto;
import com.example.producto.dto.MovimientoStockDto;
import com.example.producto.dto.ProductoDto;
import com.example.producto.exception.custom.ErrorNoEncontrado;
import com.example.producto.model.Producto;
import com.example.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final LocalClient localClient;

    public ProductoService(ProductoRepository productoRepository, LocalClient localClient ) {
        this.productoRepository = productoRepository;
        this.localClient = localClient;
    }

    public List<Producto> listarProductos(){
         return productoRepository.findAll();
    }

    public Producto buscaPorId(Long id){
        return productoRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el local con ID: " + id));
    }

    public Producto guardarProducto(ProductoDto dto){
        // 1. VALIDACIÓN EXTERNA (OpenFeign)
        // Llamamos al micro de tu compa antes de crear nada
            LocalDto local = localClient.obtenerLocalPorId(dto.getLocalId());
        if (local == null) {
            throw new ErrorNoEncontrado("El local con ID " + dto.getLocalId() + " no existe.");
        }

        Producto producto = new Producto();
        producto.setTipo(dto.getTipo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setTipo(dto.getTipo());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setStock(dto.getStock());
        producto.setLocalId(dto.getLocalId());
        if(producto.getTipo()==null){
            throw new IllegalArgumentException("El tipo de producto es obligatorio y debe ser un valor válido del Enum (INGREDIENTE, VENTA_DIRECTA, etc).");
        }
        return productoRepository.save(producto);

    }
    public Producto actualizarProducto(Long id, ProductoDto dto){
        // 1. Buscamos si existe (si no, lanza la excepción personalizada)
        Producto productoExistente = buscaPorId(id);

        // 2. Actualizamos los datos
        productoExistente.setNombre(dto.getNombre());
        productoExistente.setPrecio(dto.getPrecio());
        productoExistente.setTipo(dto.getTipo());
        productoExistente.setStockMinimo(dto.getStockMinimo());
        // 3. Guardamos los cambios
        return productoRepository.save(productoExistente);
    }
    public boolean productoExistePorId(Long id){
        return productoRepository.existsById(id);

    }
    public void eliminaProducto(Long id){
        if(!productoRepository.existsById(id)){
            throw new ErrorNoEncontrado("no se puede eliminar el producto el id"+id+ "no existe");

        }
        productoRepository.deleteById(id);
    }
    //metodos del movimiento stock
    public Producto agregarStock(Long id, MovimientoStockDto movimiento){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()->new ErrorNoEncontrado("producto no encontrado"));

        //  LÓGICA DE NEGOCIO: Costo Promedio Ponderado (CPP)
        // Verificamos si enviaron el costo de la compra desde Postman/Proveedor
        if (movimiento.getCostoTotalCompra() != null && movimiento.getCostoTotalCompra() > 0) {

            // 1. ¿Cuánto dinero en inventario tenemos ahora mismo de este producto?
            double valorInventarioActual = producto.getStock() * producto.getPrecio();

            // 2. ¿Cuánto pagamos por el stock nuevo?
            double valorNuevoIngreso = movimiento.getCostoTotalCompra();

            // 3. ¿Cuánto stock total tendremos al finalizar esta operación?
            int nuevoStockTotal = producto.getStock() + movimiento.getCantidad();

            // 4. Calculamos el nuevo precio unitario promedio
            double nuevoPrecioPromedio = (valorInventarioActual + valorNuevoIngreso) / nuevoStockTotal;

            // 5. Se lo asignamos al producto (redondeado a 2 decimales)
            producto.setPrecio(Math.round(nuevoPrecioPromedio * 100.0) / 100.0);
        }

        // Finalmente, sumamos la cantidad al stock del model
        producto.setStock(producto.getStock() + movimiento.getCantidad());

        return productoRepository.save(producto);
    }
    public Producto quitarStock(Long id, MovimientoStockDto movimiento) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ErrorNoEncontrado("Producto no encontrado"));

        int nuevoStock = producto.getStock() - movimiento.getCantidad();
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        producto.setStock(nuevoStock);

        // Lógica de stock mínimo usando los atributos del Model
        if (producto.getStock() <= producto.getStockMinimo()) {
            // Esto se verá en color amarillo/rojo en la consola de IntelliJ
            System.err.println("⚠️ ALERTA DE STOCK: " + producto.getNombre() + " tiene solo " + producto.getStock());
        }
        return productoRepository.save(producto);
    }
}

