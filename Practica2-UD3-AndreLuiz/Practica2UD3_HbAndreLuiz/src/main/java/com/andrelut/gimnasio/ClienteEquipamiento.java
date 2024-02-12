package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente_equipamiento", schema = "gimnasio", catalog = "")
public class ClienteEquipamiento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "duracion_uso")
    private Integer duracionUso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDuracionUso() {
        return duracionUso;
    }

    public void setDuracionUso(Integer duracionUso) {
        this.duracionUso = duracionUso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEquipamiento that = (ClienteEquipamiento) o;
        return id == that.id && Objects.equals(duracionUso, that.duracionUso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duracionUso);
    }
}
