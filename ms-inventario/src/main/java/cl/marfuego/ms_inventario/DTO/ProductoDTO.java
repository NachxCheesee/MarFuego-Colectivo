package cl.marfuego.ms_inventario.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Schema(name = "ProductoDTO", description = "Estructura de datos requerida para agregar/modificar un producto del inventario")
public class ProductoDTO {
    @Schema(description = "Identificador unico del producto", example = "1")
    private Long id;
    @Schema(description = "Identificador unico del local al que pertenece este producto", example = "3")
    private Long localId;
    @Schema(description = "Nombre del producto que fue agregado al inventario")
    private String nombre;
    @Schema(description = "Precio del producto en pesos chilenos", example = "$12.000")
    private double precio;
    @Schema(description = "Cantidad minima del stock que debe mantenerse para evitar el desabastecimiento", example = "20")
    private int stockMinimo;
    @Schema(description = "Cantidad actual del inventario", example = "150")
    private int stock;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, Long localId, String nombre, double precio, int stockMinimo, int stock) {
        this.id = id;
        this.localId = localId;
        this.nombre = nombre;
        this.precio = precio;
        this.stockMinimo = stockMinimo;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
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
}
