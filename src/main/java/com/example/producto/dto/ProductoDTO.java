package com.example.producto.dto;


import com.example.producto.enums.TipoDeProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductoDTO {

    private TipoDeProducto tipo;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100,  message = "El nombre debe tener entre 3 y 100 letras")
    private String nombre;
    @NotNull(message = "El precio de costo es obligatorio")
    @Positive(message = "El precio debe ser un número mayor a cero")
    private double precio;
//stock

    public ProductoDTO() {
    }

    public ProductoDTO(TipoDeProducto tipo, String nombre, double precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
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
}
