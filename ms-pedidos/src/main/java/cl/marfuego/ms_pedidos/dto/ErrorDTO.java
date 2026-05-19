<<<<<<<< HEAD:ms-pedidos/src/main/java/cl/marfuego/ms_pedidos/dto/ErrorDTO.java
package cl.marfuego.ms_pedidos.dto;
========
package com.example.msplato.dto;
>>>>>>>> main:ms-plato/src/main/java/com/example/msplato/dto/ErrorDto.java

import java.time.LocalDateTime;
public class ErrorDTO {
    private int status;
    private String mensaje;
    private LocalDateTime timestamp;

    public ErrorDTO() {
    }

    public ErrorDTO(int status, String mensaje, LocalDateTime timestamp) {
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
