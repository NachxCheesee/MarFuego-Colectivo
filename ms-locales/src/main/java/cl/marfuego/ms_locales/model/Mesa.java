package cl.marfuego.ms_locales.model;

import cl.marfuego.ms_locales.enums.EstadoMesa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroMesa;
    private int capacidad;

    @Enumerated(EnumType.STRING)
    private EstadoMesa estado = EstadoMesa.LIBRE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;


    public Mesa() {}

    public Mesa(Long id, int numeroMesa, int capacidad, EstadoMesa estado, Local local) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
