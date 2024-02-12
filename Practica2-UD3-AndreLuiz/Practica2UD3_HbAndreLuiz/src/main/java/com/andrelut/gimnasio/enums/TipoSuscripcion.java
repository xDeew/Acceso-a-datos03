package com.andrelut.gimnasio.enums;

public enum TipoSuscripcion {

    BASICA("Básica", 20.00),
    PREMIUM("Premium", 35.00),
    FAMILIAR("Familiar", 50.00),
    ESTUDIANTE("Estudiante", 15.00);

    private String nombre;
    private double precio;

    /**
     * Constructor de la clase
     * @param nombre
     * @param precio
     */
    TipoSuscripcion(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

}
