package cl.marfuego.ms_empleados.dto;

import cl.marfuego.ms_empleados.enums.Cargo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "EmpleadoDto", description = "Estructura de datos requerida para registrar o actualizar la información de un empleado en el sistema")
public class EmpleadoDto {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Schema(description = "Nombre y apellido completo del nuevo trabajador", example = "Juan Carlos Bodoque")
    private String nombreCompleto;

    @NotNull(message = "El cargo es obligatorio")
    @Schema(description = "Puesto o rol asignado al trabajador", example = "MESERO", allowableValues = {"DUEÑO", "GERENTE", "COCINERO", "MESERO", "CAJERO", "CONSERJE", "DELIVERY"})
    private Cargo cargo;

    @NotNull(message = "El ID del local es obligatorio")
    @Schema(description = "Identificador único (ID) de la sucursal de MarFuego donde trabajará", example = "1")
    private Long localId;

    public EmpleadoDto() {}

    public EmpleadoDto(String nombreCompleto, Cargo cargo, Long localId) {
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.localId = localId;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public Long getLocalId() { return localId; }
    public void setLocalId(Long localId) { this.localId = localId; }

}