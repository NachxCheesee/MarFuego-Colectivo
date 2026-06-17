package cl.marfuego.ms_empleados.model;

import cl.marfuego.ms_empleados.enums.Cargo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Schema(name = "Empleado", description = "Entidad que representa a un miembro del personal o trabajador dentro de la cadena MarFuego")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autoincremental (no se coloca de manera manual) del empleado en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del trabajador (Nombres y Apellidos)", example = "Juan Carlos Bodoque")
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Rol o puesto que desempeña el empleado en el local", example = "MESERO", allowableValues = {"DUEÑO", "GERENTE", "COCINERO", "MESERO", "CAJERO", "CONSERJE", "DELIVERY"})
    private Cargo cargo;

    @Schema(description = "Identificador único (ID) del local de MarFuego al cual está asignado el empleado", example = "1")
    private Long localId;

    @Column(name = "fecha_ingreso", insertable = false, updatable = false)
    @Schema(description = "Fecha de registro o contratación del empleado (asignada automáticamente por el sistema)", example = "2026-06-17")
    private java.time.LocalDate fechaIngreso;

    public Empleado() {}

    public Empleado(Long id, String nombreCompleto, Cargo cargo, Long localId, LocalDate fechaIngreso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.localId = localId;
        this.fechaIngreso = fechaIngreso;
    }

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

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
