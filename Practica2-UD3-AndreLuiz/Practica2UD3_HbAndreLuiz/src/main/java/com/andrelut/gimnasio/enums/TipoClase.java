package com.andrelut.gimnasio.enums;

public enum TipoClase {
    AEROBICO("Aerobico"),
    MUSCULACION("Musculacion"),
    CROSSFIT("Crossfit"),
    YOGA("Yoga"),
    PILATES("Pilates"),
    ZUMBA("Zumba"),
    GIMNASIA("Gimnasia"),
    SPINNING("Spinning");

    private String nombre;

    /**
     * Constructor de la clase
     *
     * @param nombre
     */
    TipoClase(String nombre) {
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }


}