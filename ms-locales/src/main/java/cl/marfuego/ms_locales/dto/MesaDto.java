package cl.marfuego.ms_locales.dto;

import cl.marfuego.ms_locales.enums.EstadoMesa;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MesaDto {

    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser mayor a 0")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de mesa es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es de 1 persona")
    @Max(value = 20, message = "Por seguridad, el máximo son 20 personas por mesa")
    private Integer capacidad;

    private EstadoMesa estado;

    @NotNull(message = "El id del local al que pertenece es obligatorio")
    private Long localId;

    public MesaDto() {}

    public MesaDto(Integer numeroMesa, Integer capacidad, EstadoMesa estado, Long localId) {
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.localId = localId;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }
}
