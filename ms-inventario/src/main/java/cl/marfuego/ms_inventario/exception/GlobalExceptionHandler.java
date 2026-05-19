package cl.marfuego.ms_inventario.exception;

import cl.marfuego.ms_inventario.DTO.ErrorDTO;
import cl.marfuego.ms_inventario.exception.custom.ErrorNoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Atrapamos errores de "No encontrado" (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrorLogica(RuntimeException ex) {

        // Creamos el objeto usando tu constructor manual
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorNoEncontrado.class)
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(ErrorNoEncontrado ex) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Errores de campos no validos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidaciones(MethodArgumentNotValidException ex) {
        // Buscamos el error, si no hay, ponemos un mensaje genérico
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Dato inválido");

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(), // 400
                mensaje,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Atrapamos cualquier otro error inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorGeneral(Exception ex) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado en el sistema MarFuego",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> manejarErrorLectura(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Error en el formato del JSON o valor de Enum inválido",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> manejarErrorTipoDato(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {

        // Creamos un mensaje amigable
        String nombreParametro = ex.getName();
        String valorEnviado = ex.getValue() != null ? ex.getValue().toString() : "nulo";
        String mensaje = String.format("El parámetro '%s' tiene un valor inválido: '%s'",
                nombreParametro, valorEnviado);

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                java.time.LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

