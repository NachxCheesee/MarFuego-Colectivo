package com.example.producto.dto;


import com.example.producto.enums.TipoDeProducto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Tag(
        name = "ProductoDto",
        description = "Objeto de transferencia de datos con validaciones para la creación o actualización de un producto en el inventario"
)
public class ProductoDto {

    @Schema(
            title = "tipo de producto",
            description = "clasificación o categoría a la que pertenece el producto",
            example = "{INGREDIENTE, BEBESTIBLE, OTRO}"
    )
    private TipoDeProducto tipo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 letras")
    @Schema(
            title = "nombre del producto",
            description = "nombre descriptivo del insumo o ingrediente",
            example = "Aceite de Oliva Extra Virgen"
    )
    private String nombre;

    @NotNull(message = "El precio de costo es obligatorio")
    @Positive(message = "El precio debe ser un número mayor a cero")
    @Schema(
            title = "precio de costo",
            description = "valor unitario de adquisición del producto",
            example = "8500"
    )
    private double precio;

    @NotNull(message = "El stock minimo es obligatorio")
    @Positive(message = "El stock minimo debe ser un número mayor a cero")
    @Schema(
            title = "stock mínimo",
            description = "cantidad mínima permitida en inventario antes de requerir una alerta de reposición",
            example = "15"
    )
    private int stockMinimo;

    @NotNull(message = "El stock de es obligatorio")
    @Positive(message = "El stock debe ser un número mayor a cero")
    @Schema(
            title = "stock inicial o actual",
            description = "cantidad física del producto disponible en el inventario",
            example = "100"
    )
    private int stock;

    @NotNull(message = "el id no puede ser nulo")
    @Schema(
            title = "identificador del local",
            description = "ID único de la sucursal o local dueño de este producto",
            example = "1"
    )
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