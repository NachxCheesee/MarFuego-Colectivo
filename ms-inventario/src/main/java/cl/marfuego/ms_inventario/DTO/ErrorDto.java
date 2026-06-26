package cl.marfuego.ms_inventario.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ErrorDto", description = "Estructura estándar de respuesta devuelta por el sistema ante fallas, excepciones o solicitudes incorrectas")
public class ErrorDto {
    @Schema(description = "Código de estado HTTP que representa la falla generada",example = "404")
    private int Status;
    @Schema(description = "Mensaje detallado que explica el motivo del error", example = "Descripción detallada del error ocurrido en el sistema")
    private String mensaje;
    @Schema(description = "Fecha y hora exacta en la que se produjo la excepcion en el servidor", example = "2026-06-22T21:30:00")
    private LocalDateTime fecha;


    public ErrorDto() {
    }

    public ErrorDto(int status, String mensaje, LocalDateTime fecha) {
        Status = status;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
