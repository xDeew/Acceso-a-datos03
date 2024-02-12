package com.andrelut.gimnasio.enums;

public enum EstadoPago {
    PAGADO("Pagado"),
    PENDIENTE("Pendiente");

    private final String valor;

    /**
     * Constructor
     *
     * @param valor
     */
    EstadoPago(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}