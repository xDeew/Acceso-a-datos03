package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class Modelo {

    private SessionFactory sessionFactory;

    public void desconectar() {
        if (sessionFactory != null && !sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    public void conectar() {
        Configuration conf = new Configuration();

        conf.configure("hibernate.cfg.xml");

        conf.addAnnotatedClass(Cliente.class);
        conf.addAnnotatedClass(Suscripcion.class);
        conf.addAnnotatedClass(Clase.class);
        conf.addAnnotatedClass(Entrenador.class);
        conf.addAnnotatedClass(Equipamiento.class);
        conf.addAnnotatedClass(Reserva.class);
        conf.addAnnotatedClass(ClienteEquipamiento.class);
        conf.addAnnotatedClass(EntrenadorEquipamiento.class);
        conf.addAnnotatedClass(ClienteClase.class);
        conf.addAnnotatedClass(ClienteEntrenador.class);

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(
                conf.getProperties()).build();

        sessionFactory = conf.buildSessionFactory(ssr);

    }


    public Session abrirSesion() {
        return sessionFactory.openSession();
    }

    ArrayList<Cliente> getClientes() {
        Session session = abrirSesion();
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) session.createQuery("from Cliente").list();
        session.close();
        return clientes;
    }

    ArrayList<Suscripcion> getSuscripciones() {
        Session session = abrirSesion();
        ArrayList<Suscripcion> suscripciones = (ArrayList<Suscripcion>) session.createQuery("from Suscripcion").list();
        session.close();
        return suscripciones;
    }

    ArrayList<Clase> getClases() {
        Session session = abrirSesion();
        ArrayList<Clase> clases = (ArrayList<Clase>) session.createQuery("from Clase").list();
        session.close();
        return clases;
    }

    ArrayList<Entrenador> getEntrenadores() {
        Session session = abrirSesion();
        ArrayList<Entrenador> entrenadores = (ArrayList<Entrenador>) session.createQuery("from Entrenador").list();
        session.close();
        return entrenadores;
    }

    ArrayList<Equipamiento> getEquipamiento() {
        Session session = abrirSesion();
        ArrayList<Equipamiento> equipamientos = (ArrayList<Equipamiento>) session.createQuery("from Equipamiento").list();
        session.close();
        return equipamientos;
    }

    ArrayList<Reserva> getReservas() {
        Session session = abrirSesion();
        ArrayList<Reserva> reservas = (ArrayList<Reserva>) session.createQuery("from Reserva").list();
        session.close();
        return reservas;
    }

    ArrayList<ClienteEquipamiento> getClienteEquipamiento() {
        Session session = abrirSesion();
        ArrayList<ClienteEquipamiento> clienteEquipamientos = (ArrayList<ClienteEquipamiento>) session.createQuery("from ClienteEquipamiento").list();
        session.close();
        return clienteEquipamientos;
    }

    ArrayList<EntrenadorEquipamiento> getEntrenadorEquipamiento() {
        Session session = abrirSesion();
        ArrayList<EntrenadorEquipamiento> entrenadorEquipamientos = (ArrayList<EntrenadorEquipamiento>) session.createQuery("from EntrenadorEquipamiento").list();
        session.close();
        return entrenadorEquipamientos;
    }

    ArrayList<ClienteClase> getInscripcionCliente() {
        Session session = abrirSesion();
        ArrayList<ClienteClase> inscripcionClientes = (ArrayList<ClienteClase>) session.createQuery("from InscripcionCliente").list();
        session.close();
        return inscripcionClientes;
    }

    public boolean estaConectado() {
        return sessionFactory != null && sessionFactory.isOpen();
    }

    public void añadirCliente(Cliente cliente) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(cliente);
        session.getTransaction().commit();
        session.close();
    }

    public void modificarCliente(Cliente clienteModificado) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.saveOrUpdate(clienteModificado);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existeEmail(String email) {
        Session session = abrirSesion();
        Long count = (Long) session.createQuery("select count(*) from Cliente where email = :email")
                .setParameter("email", email)
                .uniqueResult();
        session.close();
        return count > 0;
    }

    public void eliminarCliente(Cliente clienteAEliminar) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.delete(clienteAEliminar);
        session.getTransaction().commit();
        session.close();
    }

    public void añadirSuscripcion(Suscripcion suscripcion) {
    }
}
