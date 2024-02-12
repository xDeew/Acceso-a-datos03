package com.andrelut.gimnasio.enums;

import java.sql.Time;

public enum TipoClase {
    AEROBICO("Aerobico", 30, Time.valueOf("01:00:00"), TipoEquipamiento.CINTA_CORRER),
    MUSCULACION("Musculacion", 15, Time.valueOf("01:00:00"), TipoEquipamiento.PESAS_LIBRES),
    CROSSFIT("Crossfit", 20, Time.valueOf("00:50:00"), TipoEquipamiento.EQUIPAMIENTO_CROSSFIT),
    YOGA("Yoga", 25, Time.valueOf("01:00:00"), TipoEquipamiento.ESTERILLA_YOGA),
    PILATES("Pilates", 20, Time.valueOf("01:00:00"), TipoEquipamiento.REFORMER_PILATES),
    ZUMBA("Zumba", 30, Time.valueOf("01:00:00"), TipoEquipamiento.SISTEMA_SONIDO),
    GIMNASIA("Gimnasia", 15, Time.valueOf("01:00:00"), TipoEquipamiento.EQUIPAMIENTO_GIMNASIA),
    SPINNING("Spinning", 25, Time.valueOf("00:45:00"), TipoEquipamiento.BICICLETA_SPINNING);

    private String nombre;
    private int capacidad;
    private Time duracion;
    private final TipoEquipamiento equipamiento;

    /**
     * Constructor de la clase
     * @param nombre
     * @param capacidad
     * @param duracion
     * @param equipamiento
     */
    TipoClase(String nombre, int capacidad, Time duracion, TipoEquipamiento equipamiento) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.duracion = duracion;
        this.equipamiento = equipamiento;
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

    public TipoEquipamiento getEquipamiento() {
        return equipamiento;
    }
}