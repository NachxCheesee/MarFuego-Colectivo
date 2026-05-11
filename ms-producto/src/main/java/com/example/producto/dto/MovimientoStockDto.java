package com.example.producto.dto;

public class MovimientoStockDto {
    private int cantidad;

    public MovimientoStockDto() {
    }

    public MovimientoStockDto(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
