package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LocalDTO", description = "Objeto de transferencia de datos que representa la información básica de la sucursal asociada al pedido")
public class LocalDTO {

    @Schema(description = "Identificador único del local", example = "1")
    private Long id;

    @Schema(description = "Nombre comercial o de fantasía de la sucursal", example = "MarFuego Costanera")
    private String nombre;

    @Schema(description = "Dirección física exacta donde se ubica el local", example = "Calle Falsa 123")
    private String direccion;

    @Schema(description = "Ciudad de ubicación del establecimiento", example = "Puerto Montt")
    private String ciudad;

    public LocalDTO() {
    }

    public LocalDTO(Long id, String nombre, String direccion, String ciudad) {
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
