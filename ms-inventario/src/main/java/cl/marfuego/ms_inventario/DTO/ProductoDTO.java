package cl.marfuego.ms_inventario.DTO;

import com.example.producto.enums.TipoDeProducto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductoDTO {
    private Long id;
    private Long localId;
    private String nombre;
    private double precio;
    private TipoDeProducto tipo;
    private int stockMinimo;
    private int stock;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, Long localId, String nombre, double precio, TipoDeProducto tipo, int stockMinimo, int stock) {
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
