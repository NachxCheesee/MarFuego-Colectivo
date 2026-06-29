package cl.marfuego.ms_inventario.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "InventarioDTO", description = "Estructura para la creación y actualización del inventario")
public class InventarioDTO {

    @NotBlank(message = "El nombre del inventario es un campo obligatorio")
    @Size(min = 5, max = 50, message = "El rango del nombre tiene que estar entre los 5 y 50")
    @Schema(description = "Nombre con el que se registró el inventario", example = "El muelle del faro")
    @JsonProperty("nombre") // Forzamos el nombre del campo en JSON
    private String nombre;

    @NotNull(message = "El id del local es un campo obligatorio")
    @Schema(description = "Identificador único del local al que pertenece el inventario")
    @JsonProperty("local_id") // Forzamos el nombre exacto en JSON
    private Long local_id;

    // Constructor vacío (requerido por Jackson)
    public InventarioDTO() {
    }

    // Constructor con parámetros (para crear objetos manualmente)
    @JsonCreator // Indica a Jackson que use este constructor
    public InventarioDTO(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("local_id") Long local_id) {
        this.nombre = nombre;
        this.local_id = local_id;
    }

    // Getters y setters
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