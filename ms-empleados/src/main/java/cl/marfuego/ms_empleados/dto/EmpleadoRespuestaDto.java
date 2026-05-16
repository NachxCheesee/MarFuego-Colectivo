package cl.marfuego.ms_empleados.dto;

import cl.marfuego.ms_empleados.enums.Cargo;

import java.time.LocalDate;

public class EmpleadoRespuestaDto {

    private Long id;
    private String nombreCompleto;
    private Cargo cargo;
    private LocalDate fechaIngreso;

    // Aquí incluimos el DTO que traemos de ms-locales
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
