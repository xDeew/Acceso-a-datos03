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



    ArrayList<EntrenadorEquipamiento> getEntrenadorEquipamiento() {
        Session session = abrirSesion();
        ArrayList<EntrenadorEquipamiento> entrenadorEquipamientos = (ArrayList<EntrenadorEquipamiento>) session.createQuery("from EntrenadorEquipamiento").list();
        session.close();
        return entrenadorEquipamientos;
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
        Suscripcion suscripcion = clienteAEliminar.getSuscripcion();
        if (suscripcion != null) {
            suscripcion.setCliente(null);
            session.delete(suscripcion);
        }
        session.delete(clienteAEliminar);
        session.getTransaction().commit();
        session.close();
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
        Session session = abrirSesion();
        session.beginTransaction();
        Suscripcion suscripcion = session.get(Suscripcion.class, idSuscripcion);
        if (suscripcion != null) {
            Cliente cliente = suscripcion.getCliente();
            if (cliente != null) {
                cliente.setSuscripcion(null);
                session.delete(cliente);
            }
            session.delete(suscripcion);
            session.getTransaction().commit();
        } else {
            System.out.println("La suscripción con el ID proporcionado no existe.");
        }
        session.close();
    }

    public void añadirClase(Clase clase) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(clase);
        session.getTransaction().commit();
        session.close();
    }

    public void añadirEquipamiento(Equipamiento equipamiento) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(equipamiento);
        session.getTransaction().commit();
        session.close();
    }


    public void eliminarEquipamiento(Equipamiento equipamientoAEliminar) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.delete(equipamientoAEliminar);
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

    public Entrenador getEntrenadorPorId(int idEntrenador) {
        Session session = abrirSesion();
        Entrenador entrenador = session.get(Entrenador.class, idEntrenador);
        session.close();
        return entrenador;
    }

    public Entrenador obtenerEntrenadorPorEspecialidad(String especialidad) {
        String hql = "FROM Entrenador WHERE especialidad = :especialidad";
        try (Session session = sessionFactory.openSession()) {
            Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
            query.setParameter("especialidad", especialidad);
            List<Entrenador> entrenadores = query.list();
            if (!entrenadores.isEmpty()) {
                return entrenadores.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entrenador obtenerEntrenadorPorNombre(String nombreEntrenador) {
        String hql = "FROM Entrenador WHERE nombre = :nombre";
        try (Session session = sessionFactory.openSession()) {
            Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
            query.setParameter("nombre", nombreEntrenador);
            List<Entrenador> entrenadores = query.list();
            if (!entrenadores.isEmpty()) {
                return entrenadores.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Equipamiento obtenerEquipamientoPorTipo(String text) {
        String hql = "FROM Equipamiento WHERE tipoEquipamiento = :tipo";
        try (Session session = sessionFactory.openSession()) {
            Query<Equipamiento> query = session.createQuery(hql, Equipamiento.class);
            query.setParameter("tipo", text);
            List<Equipamiento> equipamientos = query.list();
            if (!equipamientos.isEmpty()) {
                return equipamientos.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
