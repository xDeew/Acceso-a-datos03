package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente_clase", schema = "gimnasio", catalog = "")
public class InscripcionCliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "asistencia")
    private boolean asistencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscripcionCliente that = (InscripcionCliente) o;
        return id == that.id && asistencia == that.asistencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, asistencia);
    }
}
