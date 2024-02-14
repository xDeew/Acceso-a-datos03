package com.andrelut.gimnasio;

import com.andrelut.gimnasio.enums.TipoEquipamiento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "equipamiento")
public class Equipamiento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "estado")
    private String estado;
    @Basic
    @Column(name = "costo")
    private BigDecimal costo;

    @Basic
    @Column(name = "tipo_equipamiento")
    private String tipoEquipamiento;

    @OneToMany(mappedBy = "equipamientos")
    private List<Clase> clase;
    @ManyToMany(mappedBy = "equipamientos")
    private List<Entrenador> entrenadores;
    @ManyToMany(mappedBy = "equipamientos")
    private List<Cliente> clientes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }


    public String getTipoEquipamiento() {
        return tipoEquipamiento;
    }

    public void setTipoEquipamiento(String tipoEquipamiento) {
        this.tipoEquipamiento = tipoEquipamiento;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamiento that = (Equipamiento) o;
        return id == that.id && Objects.equals(tipoEquipamiento, that.tipoEquipamiento) && Objects.equals(estado, that.estado) && Objects.equals(costo, that.costo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoEquipamiento, estado, costo);
    }

    public List<Clase> getClase() {
        return clase;
    }

    public void setClase(List<Clase> clase) {
        this.clase = clase;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return String.format(
                "Equipamiento: [ID: %d, Tipo: %s, Estado: %s, Costo: %s]",
                id, tipoEquipamiento, estado, costo
        );
    }

}
