package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.Clase;
import com.andrelut.gimnasio.Cliente;
import com.andrelut.gimnasio.Entrenador;
import com.andrelut.gimnasio.Suscripcion;
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

    /**
     * Cierra la sesión de Hibernate.
     */
    public void desconectar() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    /**
     * Conecta a la base de datos.
     */
    public void conectar() {
        Configuration conf = new Configuration();

        conf.configure("hibernate.cfg.xml");

        conf.addAnnotatedClass(Cliente.class);
        conf.addAnnotatedClass(Suscripcion.class);
        conf.addAnnotatedClass(Clase.class);
        conf.addAnnotatedClass(Entrenador.class);

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(
                conf.getProperties()).build();

        sessionFactory = conf.buildSessionFactory(ssr);


    }


    /**
     * Abre una sesión de Hibernate.
     *
     * @return la sesión abierta, o null si no se pudo abrir.
     */
    public Session abrirSesion() {
        if (estaConectado()) {
            return sessionFactory.openSession();
        } else {
            return null;
        }
    }

    /**
     * Obtiene la lista de clientes.
     *
     * @return la lista de clientes.
     */
    ArrayList<Cliente> getClientes() {
        Session session = abrirSesion();
        if (session != null) {
            ArrayList<Cliente> clientes = (ArrayList<Cliente>) session.createQuery("from Cliente").list();
            session.close();
            return clientes;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene la lista de suscripciones.
     *
     * @return la lista de suscripciones.
     */
    ArrayList<Suscripcion> getSuscripciones() {
        Session session = abrirSesion();
        if (session != null) {
            ArrayList<Suscripcion> suscripciones = (ArrayList<Suscripcion>) session.createQuery("from Suscripcion").list();
            session.close();
            return suscripciones;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene la lista de clases.
     *
     * @return la lista de clases.
     */
    ArrayList<Clase> getClases() {
        Session session = abrirSesion();
        if (session != null) {
            ArrayList<Clase> clases = (ArrayList<Clase>) session.createQuery("from Clase").list();
            session.close();
            return clases;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene la lista de entrenadores.
     *
     * @return la lista de entrenadores.
     */
    ArrayList<Entrenador> getEntrenadores() {
        Session session = abrirSesion();
        if (session != null) {
            ArrayList<Entrenador> entrenadores = (ArrayList<Entrenador>) session.createQuery("from Entrenador").list();
            session.close();
            return entrenadores;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Verifica si la conexión a la base de datos está abierta.
     *
     * @return true si la conexión está abierta, false si no.
     */
    public boolean estaConectado() {
        return sessionFactory != null && sessionFactory.isOpen();
    }

    /**
     * Añade un cliente a la base de datos.
     *
     * @param cliente
     */

    public void addCliente(Cliente cliente) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(cliente);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Modifica un cliente en la base de datos.
     *
     * @param clienteModificado
     */
    public void modificarCliente(Cliente clienteModificado) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.saveOrUpdate(clienteModificado);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Comprueba si un cliente con el email proporcionado ya existe en la base de datos.
     *
     * @param email
     * @return true si el cliente existe, false si no.
     */
    public boolean existeEmail(String email) {
        Session session = abrirSesion();
        Long count = (Long) session.createQuery("select count(*) from Cliente where email = :email")
                .setParameter("email", email)
                .uniqueResult();
        session.close();
        return count > 0;
    }

    /**
     * Elimina un cliente de la base de datos.
     *
     * @param idCliente
     */
    public void eliminarCliente(int idCliente) {
        Transaction tx = null; // Transaction es una clase de Hibernate que permite agrupar operaciones en una sola transacción.
        try (Session session = abrirSesion()) {
            tx = session.beginTransaction(); // comienza la transacción
            Cliente cliente = session.get(Cliente.class, idCliente);
            if (cliente != null) {
                Suscripcion suscripcion = cliente.getSuscripcion();
                if (suscripcion != null) {
                    session.delete(suscripcion);
                }
                session.delete(cliente);
                tx.commit(); // termina la transacción y confirma los cambios
            } else {
                System.out.println("El cliente con el ID proporcionado no existe.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback(); // si hay un error, se deshacen los cambios
            e.printStackTrace();
        }
    }

    /**
     * Añade una suscripción a la base de datos.
     *
     * @param suscripcion
     */
    public void addSuscripcion(Suscripcion suscripcion) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(suscripcion);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Modifica una suscripción en la base de datos.
     *
     * @param idSuscripcion
     * @param nuevoTipo
     * @param nuevaDuracion
     */
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

    /**
     * Elimina una suscripción de la base de datos.
     *
     * @param idSuscripcion
     */
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

    /**
     * Añade una clase a la base de datos.
     *
     * @param clase
     */
    public void addClase(Clase clase) {
        Session session = abrirSesion(); // abre una sesión
        session.beginTransaction(); // comienza una transacción
        session.save(clase); // guarda la clase
        session.getTransaction().commit(); // termina la transacción y confirma los cambios
        session.close(); // cierra la sesión
    }

    /**
     * Modifica una clase en la base de datos.
     *
     * @param entrenador
     */
    public void addEntrenador(Entrenador entrenador) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.save(entrenador);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Elimina un entrenador de la base de datos.
     *
     * @param entrenadorAEliminar
     */
    public void eliminarEntrenador(Entrenador entrenadorAEliminar) {
        Transaction tx = null;
        try (Session session = abrirSesion()) {
            tx = session.beginTransaction();
            Entrenador entrenador = session.get(Entrenador.class, entrenadorAEliminar.getId());
            if (entrenador != null) {
                List<Clase> clases = new ArrayList<>(entrenador.getClases());
                for (Clase clase : clases) {
                    session.delete(clase);
                }
                session.delete(entrenador);
                tx.commit();
            } else {
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    /**
     * Elimina una clase de la base de datos.
     *
     * @param claseAEliminar
     */
    public void eliminarClase(Clase claseAEliminar) {
        Transaction tx = null; // agrupa operaciones en una sola transacción para que se realicen todas
        try (Session session = abrirSesion()) {
            tx = session.beginTransaction();  // comienza la transacción
            Clase clase = session.get(Clase.class, claseAEliminar.getId());
            if (clase != null) {
                session.delete(clase); // elimina la clase
                tx.commit(); // termina la transacción y confirma los cambios
            } else {
                System.out.println("La clase con el ID proporcionado no existe.");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback(); // si hay un error, se deshacen los cambios
            e.printStackTrace();
        }
    }

    public List<Clase> obtenerClasePorEntrenadorListado(Entrenador entrenadorAEliminar) {
        String hql = "FROM Clase WHERE entrenador = :entrenador"; // hql lenguaje consulta hibernate
        try (Session session = sessionFactory.openSession()) {
            Query<Clase> query = session.createQuery(hql, Clase.class);
            query.setParameter("entrenador", entrenadorAEliminar);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Modifica un entrenador en la base de datos.
     *
     * @param entrenadorModificado
     */
    public void modificarEntrenador(Entrenador entrenadorModificado) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.saveOrUpdate(entrenadorModificado);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param nuevoEntrenador
     * @return el cliente, o null si no se encontró.
     */
    public Entrenador obtenerEntrenadorPorNombre(String nuevoEntrenador) {
        String hql = "FROM Entrenador WHERE nombre = :nombre"; // hql lenguaje consulta hibernate
        try (Session session = sessionFactory.openSession()) {
            Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
            query.setParameter("nombre", nuevoEntrenador);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene una suscripción por su ID.
     *
     * @param claseModificada
     */
    public void modificarClase(Clase claseModificada) {
        Session session = abrirSesion();
        session.beginTransaction();
        session.saveOrUpdate(claseModificada);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Obtiene un entrenador por su especialidad.
     *
     * @param especialidad
     * @return el entrenador, o null si no se encontró.
     */
    public List<Entrenador> obtenerEntrenadoresPorEspecialidad(String especialidad) {
        String hql = "FROM Entrenador WHERE especialidad = :especialidad"; // hql lenguaje consulta hibernate
        try (Session session = sessionFactory.openSession()) {
            Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
            query.setParameter("especialidad", especialidad);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
}