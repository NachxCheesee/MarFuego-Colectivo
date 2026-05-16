package com.example.msplato.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public class PlatoDto {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;
    @NotNull(message = "El stock minimo es obligatorio")
    @Positive(message = "El stock minimo debe ser un número mayor a cero")
    private double precioVenta;
    @NotNull(message = "La disponibilidad no puede ser nula")
    private boolean disponible;
    @NotNull(message = "el id no puede ser nulo")
    private Long localId;
    @NotEmpty(message = "Un plato debe tener al menos un ingrediente")
    private List<Long> productoIds;
    public PlatoDto() {
    }

    public PlatoDto(String nombre, double precioVenta, boolean disponible, Long localId, List<Long> productoIds) {
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.disponible = disponible;
        this.localId = localId;
        this.productoIds = productoIds;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }
}
