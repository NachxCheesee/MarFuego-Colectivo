package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(title = "EmpleadoDTO", description = "Objeto de transferencia de datos para representar a un empleado que pueda gestionar pedidos")
public class EmpleadoDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Schema(description = "Nombre del empleado", example = "Javiera Lopez Mansilla")
    private String nombreCompleto;

    @NotNull(message = "El ID del local es obligatorio")
    @Schema(description = "Id del local al que pertenece el empleado", example = "1")
    private Long localId;

    public EmpleadoDTO() {}

    public EmpleadoDTO(String nombreCompleto, Long localId) {
        this.nombreCompleto = nombreCompleto;
        this.localId = localId;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Long getLocalId() { return localId; }
    public void setLocalId(Long localId) { this.localId = localId; }
}