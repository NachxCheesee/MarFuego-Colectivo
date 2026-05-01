package com.example.producto.controller;

import com.example.producto.dto.ProductoDTO;
import com.example.producto.model.Producto;
import com.example.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Producto> guardarProducto(@Valid @PathVariable ProductoDTO dto){
        Producto nuevoProducto = productoService.guardarProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        productoService.eliminaProducto(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }
}
