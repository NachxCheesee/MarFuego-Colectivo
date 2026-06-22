package cl.marfuego.ms_pedidos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MesaDTO {
    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser mayor a 0")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de mesa es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es de 1 persona")
    @Max(value = 20, message = "Por seguridad, el máximo son 20 personas por mesa")
    private Integer capacidad;

    @NotNull(message = "El id del local al que pertenece es obligatorio")
    private Long localId;

    public MesaDTO() {}

    public MesaDTO(Integer numeroMesa, Integer capacidad, Long localId) {
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
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

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }
}