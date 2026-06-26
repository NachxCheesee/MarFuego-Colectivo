package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;
@Schema(title = "PlatoDTO",description = "Objeto de transferencia de datos para registrar o actualizar un plato en el menú")
public class PlatoDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Schema(description = "Nombre del plato que ofrece el menú",example = "Mariscos con limón ")
    private String nombre;


    @NotNull(message = "El stock minimo es obligatorio")
    @Positive(message = "El stock minimo debe ser un número mayor a cero")
    @Schema(description = "Precio de venta del plato en pesos chilenos",example = "19990.0")
    private double precioVenta;


    @NotNull(message = "La disponibilidad no puede ser nula")
    @Schema(description = "Indica si el plato está disponible para la venta", example = "true")
    private boolean disponible;


    @NotNull(message = "el id no puede ser nulo")
    @Schema(description = "Id del local al que pertenece este plato",example = "1")
    private Long localId;


    @NotEmpty(message = "Un plato debe tener al menos un ingrediente")
    @Schema(description = "Lista de id de productos que componen el plato",example = "1,3,7")
    private List<Long> productoIds;

    public PlatoDTO() {
    }

    public PlatoDTO(String nombre, double precioVenta, boolean disponible, Long localId, List<Long> productoIds) {
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
