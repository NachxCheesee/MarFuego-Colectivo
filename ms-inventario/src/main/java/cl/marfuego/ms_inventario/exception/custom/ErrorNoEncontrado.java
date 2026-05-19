package cl.marfuego.ms_inventario.exception.custom;

public class ErrorNoEncontrado extends RuntimeException {
    public ErrorNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
