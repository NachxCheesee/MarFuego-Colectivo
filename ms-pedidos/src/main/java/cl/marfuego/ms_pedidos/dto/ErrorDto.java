package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ErrorDto", description = "Estructura estándar de respuesta devuelta por el sistema ante fallas, excepciones o solicitudes incorrectas")
public class ErrorDto {
    @Schema(description = "Código de estado HTTP que representa la falla generada",example = "404")
    private int status;

    @Schema(description = "Mensaje detallado que explica el motivo del error", example = "Descripción detallada del error ocurrido en el sistema")
    private String mensaje;

    @Schema(description = "Fecha y hora exacta en la que se produjo la excepcion en el servidor", example = "2026-06-23T22:32:00")
    private LocalDateTime timestamp;

    public ErrorDto() {
    }

    public ErrorDto(int status, String mensaje, LocalDateTime timestamp) {
        this.status = status;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
