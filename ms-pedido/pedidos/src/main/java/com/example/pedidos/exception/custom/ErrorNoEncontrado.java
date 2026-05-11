package com.example.pedidos.exception.custom;

public class ErrorNoEncontrado extends RuntimeException {

    public ErrorNoEncontrado(String mensaje) {

        super(mensaje);
    }
}