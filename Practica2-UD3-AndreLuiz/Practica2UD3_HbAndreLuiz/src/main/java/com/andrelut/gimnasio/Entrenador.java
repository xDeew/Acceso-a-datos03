package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "entrenadores", schema = "gimnasiodb", catalog = "")
public class Entrenador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "especialidad")
    private String especialidad;
    @Basic
    @Column(name = "horario")
    private String horario;
    @ManyToMany
    @JoinTable(name = "entrenador_clase", catalog = "", schema = "gimnasiodb", joinColumns = @JoinColumn(name = "id_entrenador", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_clase", referencedColumnName = "id", nullable = false))
    private List<Clase> clases;


    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(especialidad, that.especialidad) && Objects.equals(horario, that.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, especialidad, horario);
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

    @Override
    public String toString() {
        return String.format(
                "Entrenador { id=%d, nombre='%s', especialidad='%s', horario='%s'}",
                id, nombre, especialidad, horario
        );
    }
}
