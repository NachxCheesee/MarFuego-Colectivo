package cl.marfuego.ms_inventario.DTO;
import java.time.LocalDateTime;

public class ErrorDTO {
    private int Status;
    private String mensaje;
    private LocalDateTime fecha;

    public ErrorDTO() {
    }

    public ErrorDTO(int status, String mensaje, LocalDateTime fecha) {
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
