package cl.marfuego.ms_inventario.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "InventarioDTO", description = "Estructura para la creacion y actualización del inventario")
public class InventarioDTO {

    @NotBlank(message = "El nombre del inventario es un campo obligatorio")
    @Size(min = 5, max = 50, message = "el rango del nombre tiene que estar entre los 5 y 50")
    @Schema(description = "Nombre con el que se registró el inventario", example = "El muelle del faro")
    private String nombre;

    @NotNull(message = "el id del local es un campo obligatorio")
    @Schema(description = "Identificador unico del local al que pertenece el inventario")
    private Long local_id;

    public InventarioDTO() {
    }

    public InventarioDTO(String nombre, Long local_id) {
        this.nombre = nombre;
        this.local_id = local_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Long local_id) {
        this.local_id = local_id;
    }
}
