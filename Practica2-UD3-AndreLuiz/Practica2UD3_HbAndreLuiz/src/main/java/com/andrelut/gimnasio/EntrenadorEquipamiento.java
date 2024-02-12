package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "entrenador_equipamiento", schema = "gimnasio", catalog = "")
public class EntrenadorEquipamiento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "frecuencia_uso")
    private String frecuenciaUso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrecuenciaUso() {
        return frecuenciaUso;
    }

    public void setFrecuenciaUso(String frecuenciaUso) {
        this.frecuenciaUso = frecuenciaUso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntrenadorEquipamiento that = (EntrenadorEquipamiento) o;
        return id == that.id && Objects.equals(frecuenciaUso, that.frecuenciaUso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, frecuenciaUso);
    }
}
