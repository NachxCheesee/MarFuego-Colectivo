<<<<<<<< HEAD:ms-pedidos/src/main/java/cl/marfuego/ms_pedidos/exception/CapturadorGlobalException.java
package cl.marfuego.ms_pedidos.exception;

import cl.marfuego.ms_pedidos.dto.ErrorDTO;
import cl.marfuego.ms_pedidos.exception.custom.ErrorNoEncontrado;
========
package com.example.msplato.exception;

import com.example.msplato.dto.ErrorDto;
import com.example.msplato.exception.custom.ErrorNoEncontrado;
>>>>>>>> main:ms-plato/src/main/java/com/example/msplato/exception/CapturadorGlobalException.java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class CapturadorGlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrorLogica(RuntimeException ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorNoEncontrado.class)
<<<<<<<< HEAD:ms-pedidos/src/main/java/cl/marfuego/ms_pedidos/exception/CapturadorGlobalException.java
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(ErrorNoEncontrado ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
========
    public ResponseEntity<ErrorDto> manejarRecursoNoEncontrado(ErrorNoEncontrado ex) {
        ErrorDto error = new ErrorDto(
                HttpStatus.NOT_FOUND.value(), // 404
>>>>>>>> main:ms-plato/src/main/java/com/example/msplato/exception/CapturadorGlobalException.java
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidaciones(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Dato inválido");

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

<<<<<<<< HEAD:ms-pedidos/src/main/java/cl/marfuego/ms_pedidos/exception/CapturadorGlobalException.java
========
    // Atrapamos cualquier otro error inesperado (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> manejarErrorGeneral(Exception ex) {
        ex.printStackTrace();
        ErrorDto error = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado en el sistema MarFuego",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

>>>>>>>> main:ms-plato/src/main/java/com/example/msplato/exception/CapturadorGlobalException.java
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> manejarErrorLectura(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Error en el formato del JSON o valor de Enum inválido (Usa LOCAL o DELIVERY)",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> manejarErrorTipoDato(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        String mensaje = String.format("El parámetro '%s' debe ser de tipo %s",
                ex.getName(), ex.getRequiredType().getSimpleName());

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

<<<<<<<< HEAD:ms-pedidos/src/main/java/cl/marfuego/ms_pedidos/exception/CapturadorGlobalException.java
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorGeneral(Exception ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado en el sistema MarFuego: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
========
}
>>>>>>>> main:ms-plato/src/main/java/com/example/msplato/exception/CapturadorGlobalException.java
