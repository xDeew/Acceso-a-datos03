package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Clase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "horario")
    private String horario;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id_clase", nullable = false)
    private Equipamiento equipamientos;
    @ManyToMany(mappedBy = "clases")
    private List<Cliente> clientes;
    @ManyToMany
    @JoinTable(name = "clase_reserva", catalog = "", schema = "gimnasio", joinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_clase", referencedColumnName = "id", nullable = false))
    private List<Reserva> reservas;
    @ManyToMany(mappedBy = "clases")
    private List<Entrenador> entrenadores;

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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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
        return id == clase.id && Objects.equals(nombre, clase.nombre) && Objects.equals(horario, clase.horario) && Objects.equals(tipo, clase.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, horario, tipo);
    }

    public Equipamiento getEquipamientos() {
        return equipamientos;
    }

    public void setEquipamientos(Equipamiento equipamientos) {
        this.equipamientos = equipamientos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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
}