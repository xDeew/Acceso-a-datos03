package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "clases", schema = "gimnasiodb", catalog = "")
public class Clase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;


    @ManyToOne
    @JoinColumn(name = "id_entrenador", referencedColumnName = "id")
    private Entrenador entrenador;


    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase clase = (Clase) o;
        return id == clase.id && Objects.equals(nombre, clase.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }


    @Override
    public String toString() {
        return String.format(
                "Clase { id=%d, nombre='%s', descripcion='%s', entrenador='%s', especialidad='%s'}",
                id, nombre, descripcion, entrenador.getNombre(), entrenador.getEspecialidad()
        );
    }
}
