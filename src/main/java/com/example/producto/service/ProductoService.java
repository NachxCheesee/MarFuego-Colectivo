package com.example.producto.service;

import com.example.producto.dto.ProductoDTO;
import com.example.producto.exception.custom.ErrorNoEncontrado;
import com.example.producto.model.Producto;
import com.example.producto.repository.ProductoRepository;

import java.util.List;

public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos(){
         return productoRepository.findAll();
    }

    public Producto buscaPorId(Long id){
        return productoRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el local con ID: " + id));
    }

    public Producto guardarProducto(ProductoDTO dto){
        Producto producto = new Producto();
        producto.setTipo(dto.getTipo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setTipo(dto.getTipo());
        if(producto.getTipo()==null){
            throw new IllegalArgumentException("El tipo de producto es obligatorio y debe ser un valor válido del Enum (INGREDIENTE, VENTA_DIRECTA, etc).");
        }
        return productoRepository.save(producto);

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

}
