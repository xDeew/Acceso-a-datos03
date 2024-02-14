package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.*;
import com.andrelut.gimnasio.enums.TipoSuscripcion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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
        conf.addAnnotatedClass(Reserva.class);

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


    ArrayList<Reserva> getReservas() {
        Session session = abrirSesion();
        ArrayList<Reserva> reservas = (ArrayList<Reserva>) session.createQuery("from Reserva").list();
        session.close();
        return reservas;
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

    public void eliminarCliente(int idCliente) {
        Transaction tx = null;
        try (Session session = abrirSesion()) {
            tx = session.beginTransaction();
            Cliente cliente = session.get(Cliente.class, idCliente);
            if (cliente != null) {
                Suscripcion suscripcion = cliente.getSuscripcion();
                if (suscripcion != null) {
                    session.delete(suscripcion);
                }
                session.delete(cliente);
                tx.commit();
            } else {
                System.out.println("El cliente con el ID proporcionado no existe.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void añadirSuscripcion(Suscripcion suscripcion) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(suscripcion);
        session.getTransaction().commit();
        session.close();
    }

    public void modificarSuscripcion(int idSuscripcion, String nuevoTipo, int nuevaDuracion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Suscripcion suscripcion = session.get(Suscripcion.class, idSuscripcion);
            if (suscripcion != null) {
                suscripcion.setTipo(nuevoTipo);
                suscripcion.setDuracion(nuevaDuracion);
                TipoSuscripcion tipo = TipoSuscripcion.getPorNombre(nuevoTipo);
                if (tipo != null) {
                    suscripcion.setCosto(tipo.getPrecio());
                }

                session.update(suscripcion);
                tx.commit();
            } else {
                System.out.println("La suscripción con el ID proporcionado no existe.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarSuscripcion(int idSuscripcion) {
        Transaction tx = null;
        try (Session session = abrirSesion()) {
            tx = session.beginTransaction();
            Suscripcion suscripcion = session.get(Suscripcion.class, idSuscripcion);
            if (suscripcion != null) {
                Cliente cliente = suscripcion.getCliente();
                session.delete(suscripcion);
                session.delete(cliente);
                tx.commit();
            } else {
                System.out.println("La suscripción con el ID proporcionado no existe.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void añadirClase(Clase clase) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(clase);
        session.getTransaction().commit();
        session.close();
    }


    public void añadirEntrenador(Entrenador entrenador) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(entrenador);
        session.getTransaction().commit();
        session.close();
    }

    public void eliminarEntrenador(Entrenador entrenadorAEliminar) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.delete(entrenadorAEliminar);
        session.getTransaction().commit();
        session.close();
    }


    public List<Entrenador> obtenerTodosLosEntrenadores() {
        String hql = "FROM Entrenador";
        try (Session session = sessionFactory.openSession()) {
            Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
