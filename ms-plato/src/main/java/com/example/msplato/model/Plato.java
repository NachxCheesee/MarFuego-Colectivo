package com.example.msplato.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "platos")
@Tag(
        name = "plato",
        description = "Representa a toda cosa que se puede preparar y servir en la cocina"
)
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            title = "identificador unico para cada plato",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY //campo solo de lectura y no es necesario
            // hacer un post
    )
    private Long id;
    @Schema(
            title = "identificador unico de cada local asociado al plato",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY //campo solo de lectura y no es necesario
            // hacer un post
    )
    private Long localId;
    @Schema(
            title = "nombre del plato",
            description = "nombre comercial del plato",
            example = "arroz con pollo"
    )
    private String nombre;
    @Schema(
            title = "precio del plato",
            description = "valor que se le otorga al plato",
            example = "90000"
    )
    private double precioVenta;
    @Schema(
            title = "disponibilidad del plato",
            description = "Indica si el plato está actualmente disponible para ser pedido en el menú",
            defaultValue = "true"
    )
    private boolean disponible;
    @Schema(
            title = "IDs de los productos del plato",
            description = "Lista con los identificadores (IDs) de los productos o ingredientes que componen este plato",
            example = "[1, 5, 12]"
    )
    @ElementCollection // Crea una tabla automática platos_producto_ids
    @CollectionTable(name = "plato_productos", joinColumns = @JoinColumn(name = "plato_id"))//configuramos esa tabla, le decimos que cree una columna plato_id
    // para que sepa a que plato va a pertenecer cada ingredientes
    @Column(name = "producto_id")//es la columna de nombre producto id que va a guardar los numeros de los ids
    private List<Long> productoId = new ArrayList<>();

    public Plato() {
    }

    public Plato(Long id, Long localId, String nombre, double precioVenta, boolean disponible, List<Long> productoId) {
        this.id = id;
        this.localId = localId;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.disponible = disponible;
        this.productoId = productoId;
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

    public List<Long> getProductoId() {
        return productoId;
    }

    public void setProductoId(List<Long> productoId) {
        this.productoId = productoId;
    }
}
