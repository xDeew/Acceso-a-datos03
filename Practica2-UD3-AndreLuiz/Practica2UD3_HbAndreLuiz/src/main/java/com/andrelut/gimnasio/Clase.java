package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.List;
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
    @Column(name = "tipo")
    private String tipo;
    @ManyToMany
    @JoinTable(name = "clase_reserva", catalog = "", schema = "gimnasiodb", joinColumns = @JoinColumn(name = "id_clase", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id", nullable = false))
    private List<Reserva> reservas;
    @ManyToMany(mappedBy = "clases")
    private List<Entrenador> entrenadores;

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



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase clase = (Clase) o;
        return id == clase.id && Objects.equals(nombre, clase.nombre) && Objects.equals(tipo, clase.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, tipo);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    @Override
    public String toString() {
        return String.format(
                "Clase { id=%d, nombre='%s', tipo='%s'}",
                id, nombre, tipo
        );
    }
}
