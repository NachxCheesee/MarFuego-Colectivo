package com.example.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Tag(
        name = "MovimientoStockDto",
        description = "Objeto de transferencia de datos para registrar entradas o salidas de stock de un producto"
)
public class MovimientoStockDto {

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser un número mayor a cero")
    @Schema(
            title = "cantidad del movimiento",
            description = "número de unidades físicas que van a ingresar o salir del inventario",
            example = "50"
    )
    private int cantidad;

    // NUEVO ATRIBUTO: El precio total pagado al proveedor por esta entrada
    // Solo lo usaremos cuando sea una entrada de stock
    @Positive(message = "El costo total de compra debe ser un número positivo")
    @Schema(
            title = "costo total de la compra",
            description = "precio total pagado al proveedor por la entrada de stock (se utiliza únicamente para movimientos de entrada)",
            example = "45000"
    )
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
