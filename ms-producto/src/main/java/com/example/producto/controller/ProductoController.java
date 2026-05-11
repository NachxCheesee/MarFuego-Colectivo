package com.example.producto.controller;

import com.example.producto.dto.MovimientoStockDto;
import com.example.producto.dto.ProductoDto;
import com.example.producto.model.Producto;
import com.example.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos(){
        return ResponseEntity.ok(productoService.listarProductos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(productoService.buscaPorId(id));
    }
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody ProductoDto dto){
        Producto nuevoProducto = productoService.guardarProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

    }
    @PostMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto dto){
        Producto productoActualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(productoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        productoService.eliminaProducto(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }
    //metodo para gestion del stock
    //metodo para agregar cantidad al stock
    @PatchMapping("/{id}/stock/agregar")
    public ResponseEntity<Producto> agregarStock(@PathVariable Long id, @RequestBody MovimientoStockDto movimiento) {

        // Llamamos al servicio pasando el ID y el objeto con la cantidad
        Producto productoActualizado = productoService.agregarStock(id, movimiento);

        // Devolvemos el producto (Model) que sí incluye el ID en el JSON de respuesta
        return ResponseEntity.ok(productoActualizado);
    }
    //metodo para quitar stock
    @PatchMapping("/{id}/stock/quitar")
    public ResponseEntity<Producto> quitarStock(@PathVariable Long id, @RequestBody MovimientoStockDto movimiento){
        Producto productoActualizado = productoService.quitarStock(id, movimiento);
        return ResponseEntity.ok(productoActualizado);
    }
}
