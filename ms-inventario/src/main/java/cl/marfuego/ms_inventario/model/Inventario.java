package cl.marfuego.ms_inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventario")
@Schema(name = "Inventario", description = "Entidad que representa el inventario de un local de MarFuego")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Identificador unico",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotBlank(message = "el campo del nombre es obligatorio, favor de rellenar")
    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre comercial o ficticio del inventario",
            example = "El muelle del faro")
    private String nombre;

    @NotNull(message = "La id del local es un campo obligatorio, favor de rellenar")
    @Column(name = "local_id", nullable = false)
    @Schema(description = "Identificador único del local al que pertenece este inventario",
            example = "2")
    private long local_id;

    public Inventario() {
    }

    public Inventario(long id, String nombre, long local_id) {
        this.id = id;
        this.nombre = nombre;
        this.local_id = local_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngrediente() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }
}
