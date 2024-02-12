package com.andrelut.gimnasio;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "email")
    private String email;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id_cliente", nullable = false)
    private Suscripcion suscripcion;
    @ManyToMany
    @JoinTable(name = "cliente_entrenador", catalog = "", schema = "gimnasio", joinColumns = @JoinColumn(name = "id_entrenador", referencedColumnName = "id", nullable = false), inverseJoinColumns = {})
    private List<Entrenador> entrenadores;
    @ManyToMany
    @JoinTable(name = "cliente_clase", catalog = "", schema = "gimnasio", joinColumns = @JoinColumn(name = "id_clase", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false))
    private List<Clase> clases;
    @ManyToMany
    @JoinTable(name = "cliente_equipamiento", catalog = "", schema = "gimnasio", joinColumns = @JoinColumn(name = "id_equipamiento", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false))
    private List<Equipamiento> equipamientos;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id && Objects.equals(nombre, cliente.nombre) && Objects.equals(direccion, cliente.direccion) && Objects.equals(telefono, cliente.telefono) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, direccion, telefono, email);
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

    public List<Equipamiento> getEquipamientos() {
        return equipamientos;
    }

    public void setEquipamientos(List<Equipamiento> equipamientos) {
        this.equipamientos = equipamientos;
    }
}
