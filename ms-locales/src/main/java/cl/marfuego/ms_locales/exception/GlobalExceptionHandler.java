package cl.marfuego.ms_locales.exception;

import cl.marfuego.ms_locales.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapamos errores de "No encontrado" (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> manejarNoEncontrado(RuntimeException ex) {

        // Creamos el objeto usando tu constructor manual
        ErrorDto error = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Atrapamos cualquier otro error inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> manejarErrorGeneral(Exception ex) {

        ErrorDto error = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado en el sistema MarFuego",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}