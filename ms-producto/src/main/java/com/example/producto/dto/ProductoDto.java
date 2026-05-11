package com.example.producto.dto;


import com.example.producto.enums.TipoDeProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductoDto {

    private TipoDeProducto tipo;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 letras")
    private String nombre;
    @NotNull(message = "El precio de costo es obligatorio")
    @Positive(message = "El precio debe ser un número mayor a cero")
    private double precio;
    @NotNull(message = "El stock minimo es obligatorio")
    @Positive(message = "El stock minimo debe ser un número mayor a cero")
    private int stockMinimo;
    @NotNull(message = "El stock de es obligatorio")
    @Positive(message = "El stock debe ser un número mayor a cero")
    private int stock;
    @NotNull(message = "el id no puede ser nulo")
    private Long localId;

    public ProductoDto() {
    }

    public ProductoDto(TipoDeProducto tipo, String nombre, double precio, int stockMinimo, int stock, Long localId) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.stockMinimo = stockMinimo;
        this.stock = stock;
        this.localId = localId;
    }

    public TipoDeProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeProducto tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }
}