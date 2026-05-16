package com.example.producto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovimientoStockDto {

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser un número mayor a cero")
    private int cantidad;

    //  NUEVO ATRIBUTO: El precio total pagado al proveedor por esta entrada
    // Solo lo usaremos cuando sea una entrada de stock
    @Positive(message = "El costo total de compra debe ser un número positivo")
    private Double costoTotalCompra;

    public MovimientoStockDto() {
    }

    public MovimientoStockDto(int cantidad, Double costoTotalCompra) {
        this.cantidad = cantidad;
        this.costoTotalCompra = costoTotalCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCostoTotalCompra() {
        return costoTotalCompra;
    }

    public void setCostoTotalCompra(Double costoTotalCompra) {
        this.costoTotalCompra = costoTotalCompra;
    }
}
