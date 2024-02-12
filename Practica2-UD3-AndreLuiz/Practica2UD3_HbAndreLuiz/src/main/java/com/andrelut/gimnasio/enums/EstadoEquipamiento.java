package com.andrelut.gimnasio.enums;

public enum EstadoEquipamiento {
    NUEVO("Nuevo"),
    USADO("Usado"),
    REPARADO("Reparado");

    private String valor;

    /**
     * Constructor de la clase
     * @param valor
     */
    EstadoEquipamiento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
