package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "MesaDTO", description = "Estructura de datos que representa la informacion de una mesa asociada a un pedido en una sucursal")
public class MesaDTO {

    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser mayor a 0")
    @Schema(description = "Número identificador físico de la mesa dentro del local", example = "12")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de mesa es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es de 1 persona")
    @Max(value = 20, message = "Por seguridad, el máximo son 20 personas por mesa")
    @Schema(description = "Cantidad máxima de clientes permitidos en esta mesa", example = "6")
    private Integer capacidad;

    @NotNull(message = "El id del local al que pertenece es obligatorio")
    @Schema(description = "ID numérico del local de MarFuego donde se instalará físicamente la mesa", example = "1")
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