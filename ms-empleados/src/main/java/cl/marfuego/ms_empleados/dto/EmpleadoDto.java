package cl.marfuego.ms_empleados.dto;

import cl.marfuego.ms_empleados.enums.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpleadoDto {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombreCompleto;

    @NotNull(message = "El cargo es obligatorio")
    private Cargo cargo;

    @NotNull(message = "El ID del local es obligatorio")
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