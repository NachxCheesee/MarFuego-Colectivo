package com.example.msplato.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;

import java.util.List;
@Tag(name = "PlatoDto",
        description = "Objeto de transferencia de datos para la creación, actualización y visualización de platos"
)
public class PlatoDto {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Schema(
            title = "nombre del plato",
            description = "nombre comercial del plato que se mostrará en el menú",
            example = "Ceviche de Salmón"
    )
    private String nombre;

    @NotNull(message = "El stock minimo es obligatorio")
    @Positive(message = "El stock minimo debe ser un número mayor a cero")
    @Schema(
            title = "precio de venta del plato",
            description = "valor económico que se le cobra al cliente por el plato",
            example = "12500"
    )
    private double precioVenta;

    @NotNull(message = "La disponibilidad no puede ser nula")
    @Schema(
            title = "disponibilidad del plato",
            description = "indica si el plato se encuentra actualmente en el menú para ser ordenado",
            defaultValue = "true"
    )
    private boolean disponible;

    @NotNull(message = "el id no puede ser nulo")
    @Schema(
            title = "identificador del local",
            description = "ID único de la sucursal a la que pertenece este plato",
            example = "1"
    )
    private Long localId;

    @NotEmpty(message = "Un plato debe tener al menos un ingrediente")
    @Schema(
            title = "IDs de los productos requeridos",
            description = "lista con los identificadores (IDs) de los productos o ingredientes necesarios para preparar el plato",
            example = "[1, 5, 12]"
    )
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
