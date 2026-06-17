package cl.marfuego.ms_empleados.dto;

import cl.marfuego.ms_empleados.enums.Cargo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "EmpleadoRespuestaDto", description = "Objeto que contiene el detalle extendido del empleado junto con la información del local asociado")
public class EmpleadoRespuestaDto {

    @Schema(description = "Identificador único del empleado en el sistema", example = "1")
    private Long id;

    @Schema(description = "Nombre y apellido completo del trabajador", example = "Juan Carlos Bodoque")
    private String nombreCompleto;

    @Schema(description = "Puesto o rol que desempeña el empleado", example = "MESERO", allowableValues = {"DUEÑO", "GERENTE", "COCINERO", "MESERO", "CAJERO", "CONSERJE", "DELIVERY"})
    private Cargo cargo;

    @Schema(description = "Fecha en que el empleado fue dado de alta en el sistema", example = "2026-06-17")
    private LocalDate fechaIngreso;

    // Aquí incluimos el DTO que traemos de ms-locales
    @Schema(description = "Información detallada de la sucursal de MarFuego a la que pertenece el empleado")
    private LocalDto local;

    // Constructor vacío
    public EmpleadoRespuestaDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDto getLocal() {
        return local;
    }

    public void setLocal(LocalDto local) {
        this.local = local;
    }
}
