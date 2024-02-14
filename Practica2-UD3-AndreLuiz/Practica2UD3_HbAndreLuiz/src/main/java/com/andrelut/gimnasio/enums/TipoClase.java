package com.andrelut.gimnasio.enums;

import java.sql.Time;

public enum TipoClase {
    AEROBICO("Aerobico", 30, Time.valueOf("01:00:00")),
    MUSCULACION("Musculacion", 15, Time.valueOf("01:00:00")),
    CROSSFIT("Crossfit", 20, Time.valueOf("00:50:00")),
    YOGA("Yoga", 25, Time.valueOf("01:00:00")),
    PILATES("Pilates", 20, Time.valueOf("01:00:00")),
    ZUMBA("Zumba", 30, Time.valueOf("01:00:00")),
    GIMNASIA("Gimnasia", 15, Time.valueOf("01:00:00")),
    SPINNING("Spinning", 25, Time.valueOf("00:45:00"));

    private String nombre;
    private int capacidad;
    private Time duracion;

    /**
     * Constructor de la clase
     *
     * @param nombre
     * @param capacidad
     * @param duracion
     */
    TipoClase(String nombre, int capacidad, Time duracion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Time getDuracion() {
        return duracion;
    }

}