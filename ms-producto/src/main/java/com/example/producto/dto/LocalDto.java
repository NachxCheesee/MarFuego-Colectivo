package com.example.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Tag(name = "LocalDto", description = "Objeto de transferencia de datos que representa a un local o sucursal del restaurante"
)
public class LocalDto {
    @Schema(
            title = "identificador unico del local",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            title = "nombre del local",
            description = "nombre comercial o de fantasía de la sucursal",
            example = "Mar Fuego - Sede Central"
    )
    private String nombre;

    @Schema(
            title = "dirección del local",
            description = "ubicación física exacta de la sucursal",
            example = "Av. Costanera 123"
    )
    private String direccion;

    @Schema(
            title = "ciudad del local",
            description = "ciudad donde se encuentra operando el local",
            example = "Puerto Montt"
    )
    private String ciudad;
    public LocalDto() {
    }

    public LocalDto(Long id, String nombre, String direccion, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
