package com.example.producto.model;

import com.example.producto.enums.TipoDeProducto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Tag(name = "Producto",
        description = "representa a todo lo que se puede vender en el local"
)
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            title = "identificador unico del producto",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY // campo solo de lectura
    )
    private Long id;

    @Column(name = "local_id")
    @Schema(
            title = "identificador unico de cada local asociado al producto",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY // campo solo de lectura
    )
    private Long localId;

    @Schema(
            title = "nombre del producto",
            description = "nombre descriptivo del insumo o ingrediente",
            example = "Salmón fresco"
    )
    private String nombre;

    @Schema(
            title = "precio del producto",
            description = "costo o valor unitario del producto en el inventario",
            example = "15000"
    )
    private double precio;

    @Enumerated(EnumType.STRING)
    @Schema(
            title = "tipo de producto",
            description = "clasificación o categoría del producto dentro del sistema",
            example = "{INGREDIENTE, BEBESTIBLE, OTRO}"
    )
    private TipoDeProducto tipo;

    @Schema(
            title = "stock mínimo",
            description = "cantidad límite que debe haber en el inventario antes de requerir reabastecimiento",
            example = "10"
    )
    private int stockMinimo;

    @Schema(
            title = "stock actual",
            description = "cantidad física del producto que se encuentra actualmente disponible",
            example = "45"
    )
    private int stock;
    public Producto() {
    }

    public Producto(Long id, Long localId, String nombre, double precio, TipoDeProducto tipo, int stockMinimo, int stock) {
        this.id = id;
        this.localId = localId;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
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

    public TipoDeProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeProducto tipo) {
        this.tipo = tipo;
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
