package cl.marfuego.ms_empleados.model;

import cl.marfuego.ms_empleados.enums.Cargo;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    private Long localId;

    @Column(name = "fecha_ingreso", insertable = false, updatable = false)
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
