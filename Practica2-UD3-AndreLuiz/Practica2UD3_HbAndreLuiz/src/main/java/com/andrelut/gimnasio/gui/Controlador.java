package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.Clase;
import com.andrelut.gimnasio.Cliente;
import com.andrelut.gimnasio.Entrenador;
import com.andrelut.gimnasio.Suscripcion;
import com.andrelut.gimnasio.enums.TipoSuscripcion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controlador implements ActionListener, ListSelectionListener, ItemListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;
    private boolean claseEliminadaConExito;


    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;

        addActionListeners(this);
        addListSelectionListener(this);
        addItemListeners();

    }

    private void addItemListeners() {
        vista.comboTipoSuscripcion.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tipoSeleccionado = (String) e.getItem();
                TipoSuscripcion tipo = TipoSuscripcion.getPorNombre(tipoSeleccionado);
                if (tipo != null) {
                    vista.txtPrecio.setText(String.valueOf(tipo.getPrecio()));
                }
            }
        });


    }


    private void addActionListeners(ActionListener listener) {
        vista.itemConexion.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);

        vista.btnAddCliente.addActionListener(listener);
        vista.btnModificarCliente.addActionListener(listener);
        vista.btnEliminarCliente.addActionListener(listener);

        vista.btnAddSuscripciones.addActionListener(listener);
        vista.btnModificarSuscripciones.addActionListener(listener);
        vista.btnDeleteSuscripciones.addActionListener(listener);

        vista.btnAddEntrenador.addActionListener(listener);
        vista.btnModificarEntrenador.addActionListener(listener);
        vista.btnEliminarEntrenador.addActionListener(listener);

        vista.btnAddClase.addActionListener(listener);
        vista.btnModificarClase.addActionListener(listener);
        vista.btnDeleteClase.addActionListener(listener);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (!comando.equals("conectar") && !comando.equals("salir")) {
            conectado = modelo.estaConectado();
            if (!conectado) {
                JOptionPane.showMessageDialog(vista.frame, "No hay conexión a la base de datos. Por favor, conecte antes de continuar.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        switch (comando) {
            case "desconectar":
                modelo.desconectar();
                JOptionPane.showMessageDialog(vista.frame, "Desconectado de la base de datos.");
                vista.itemConexion.setText("Conectar");
                vista.itemConexion.setActionCommand("conectar");
                vista.comboClientesRegistrados.setEnabled(false);

                break;
            case "conectar":

                modelo.conectar();
                JOptionPane.showMessageDialog(vista.frame, "Conectado a la base de datos.");
                vista.itemConexion.setText("Desconectar");
                vista.itemConexion.setActionCommand("desconectar");
                vista.comboClientesRegistrados.setEnabled(true);
                actualizarComboClientesRegistrados();
                actualizarComboEntrenadoresElegir();


                break;
            case "salir":
                System.exit(0);
                break;
            case "añadirCliente":

                Cliente cliente = new Cliente();
                cliente.setNombre(vista.txtNombreCliente.getText());
                cliente.setDireccion(vista.txtDireccion.getText());
                cliente.setTelefono(vista.txtTelefono.getText());
                cliente.setEmail(vista.txtEmail.getText());
                if (modelo.existeEmail(cliente.getEmail())) {
                    JOptionPane.showMessageDialog(vista.frame, "El email ya existe. Por favor, introduce un email diferente.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    modelo.añadirCliente(cliente);
                    actualizarComboClientesRegistrados();

                }
                break;
            case "modificarCliente":
                Cliente clienteModificado = (Cliente) vista.listClientes.getSelectedValue();
                clienteModificado.setNombre(vista.txtNombreCliente.getText());
                clienteModificado.setDireccion(vista.txtDireccion.getText());
                clienteModificado.setTelefono(vista.txtTelefono.getText());
                clienteModificado.setEmail(vista.txtEmail.getText());
                modelo.modificarCliente(clienteModificado);

                break;
            case "eliminarCliente":
                Cliente clienteAEliminar = (Cliente) vista.listClientes.getSelectedValue();
                modelo.eliminarCliente(clienteAEliminar.getId());


                break;

            case "añadirSuscripcion":
                Cliente clienteSeleccionado = (Cliente) vista.comboClientesRegistrados.getSelectedItem();
                if (clienteSeleccionado != null) {
                    Suscripcion suscripcion = new Suscripcion();
                    suscripcion.setTipo(Objects.requireNonNull(vista.comboTipoSuscripcion.getSelectedItem()).toString());
                    suscripcion.setDuracion(Integer.parseInt(vista.txtDuracion.getText()));
                    suscripcion.setCosto(Double.parseDouble(vista.txtPrecio.getText()));
                    suscripcion.setCliente(clienteSeleccionado);

                    modelo.añadirSuscripcion(suscripcion);
                } else {
                    JOptionPane.showMessageDialog(vista.frame, "No hay clientes registrados. Por favor, añade un cliente antes de continuar.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "modificarSuscripcion":
                int idSuscripcionSeleccionada = obtenerIdSuscripcionSeleccionada();
                String nuevoTipo = vista.comboTipoSuscripcion.getSelectedItem().toString();
                int nuevaDuracion = Integer.parseInt(vista.txtDuracion.getText());
                modelo.modificarSuscripcion(idSuscripcionSeleccionada, nuevoTipo, nuevaDuracion);

                JOptionPane.showMessageDialog(vista.frame, "Suscripción modificada con éxito.");
                break;

            case "eliminarSuscripcion":
                System.out.println("Eliminar suscripción");
                int idSuscripcion = obtenerIdSuscripcionSeleccionada();
                modelo.eliminarSuscripcion(idSuscripcion);


                break;

            case "añadirClase":

                List<Entrenador> entrenadoresDisponibles = modelo.obtenerTodosLosEntrenadores();
                if (entrenadoresDisponibles == null || entrenadoresDisponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(vista.frame, "No hay entrenadores disponibles. Por favor, añade un entrenador antes de continuar.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Añadir clase");
                    Clase claseNueva = new Clase();
                    claseNueva.setNombre(vista.txtNombreClase.getText());
                    Entrenador entrenadorSeleccionado = entrenadoresDisponibles.get(vista.comboEntrenadoresElegir.getSelectedIndex());

                    System.out.println("Entrenador seleccionado: " + entrenadorSeleccionado);
                    claseNueva.setEntrenador(entrenadorSeleccionado);
                    modelo.añadirClase(claseNueva);

                }
                break;

            case "modificarClase":

                break;
            case "eliminarClase":
                Clase claseAEliminar = (Clase) vista.listClase.getSelectedValue();
                modelo.eliminarClase(claseAEliminar);


                break;


            case "añadirEntrenador":
                Entrenador entrenadorNuevo = new Entrenador();
                entrenadorNuevo.setNombre(vista.txtNombreEntrenador.getText());
                entrenadorNuevo.setEspecialidad(vista.comboEspecialidadEntrenador.getSelectedItem().toString());
                entrenadorNuevo.setHorario(vista.txtHorario.getText());
                modelo.añadirEntrenador(entrenadorNuevo);
                actualizarComboEntrenadoresElegir();


                break;
            case "modificarEntrenador":

                break;
            case "eliminarEntrenador":
                Entrenador entrenadorAEliminar = (Entrenador) vista.listEntrenadores.getSelectedValue();
                Clase claseAEliminarEntrenador = modelo.obtenerClasePorEntrenador(entrenadorAEliminar);
                if (claseAEliminarEntrenador != null) {
                    Entrenador entrenador = claseAEliminarEntrenador.getEntrenador();
                    if (entrenador != null && entrenador.equals(entrenadorAEliminar) && claseEliminadaConExito) {
                        JOptionPane.showMessageDialog(vista.frame, "No se puede eliminar el entrenador porque tiene clases asignadas.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        claseEliminadaConExito = true;
                        modelo.eliminarEntrenador(entrenadorAEliminar);
                    }
                } else {
                    claseEliminadaConExito = true;
                    modelo.eliminarEntrenador(entrenadorAEliminar);
                }

                break;
            case "añadirReserva":

                break;
            case "modificarReserva":

                break;
            case "eliminarReserva":

                break;

        }
        limpiarCampos();
        actualizar();

    }

    private int obtenerIdSuscripcionSeleccionada() {
        Suscripcion suscripcionSeleccionada = (Suscripcion) vista.listSuscripciones.getSelectedValue();
        return suscripcionSeleccionada.getId();
    }

    private void actualizarComboClientesRegistrados() {
        vista.comboClientesRegistrados.removeAllItems();
        ArrayList<Cliente> clientes = modelo.getClientes();
        for (Cliente cliente : clientes) {
            vista.comboClientesRegistrados.addItem(cliente);
        }
    }

    private void actualizarComboEntrenadoresElegir() {
        vista.comboEntrenadoresElegir.removeAllItems();
        List<Entrenador> entrenadores = modelo.obtenerTodosLosEntrenadores();
        for (Entrenador entrenador : entrenadores) {
            vista.comboEntrenadoresElegir.addItem(entrenador.getNombre());
        }
    }


    private void limpiarCampos() {
        limpiarCamposCliente();
        limpiarCamposSuscripciones();
        //  limpiarCamposClase();
        limpiarCamposEntrenador();
    }


    private void limpiarCamposEntrenador() {
        vista.txtNombreEntrenador.setText("");
        vista.comboEspecialidadEntrenador.setSelectedIndex(-1);
        vista.txtHorario.setText("");
    }

    private void limpiarCamposClase() {
        if (vista.txtNombreClase != null) {
            vista.txtNombreClase.setText("");
        } else {
            System.out.println("txtNombreClase es null");

        }
        if (vista.comboEntrenadorClase != null) {
            vista.comboEntrenadorClase.setSelectedIndex(-1);
        } else {
            System.out.println("comboEntrenadorClase es null");
        }
    }

    private void limpiarCamposSuscripciones() {
        vista.comboTipoSuscripcion.setSelectedIndex(-1);
        vista.comboClientesRegistrados.setSelectedIndex(-1);
        vista.txtDuracion.setText("");
        vista.txtPrecio.setText("");
    }

    private void limpiarCamposCliente() {
        vista.txtNombreCliente.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtEmail.setText("");
    }


    private void actualizar() {
        listarClientes(modelo.getClientes());
        listarSuscripciones(modelo.getSuscripciones());
        listarEntrenadores(modelo.getEntrenadores());
        listarClases(modelo.getClases());
    }


    private void listarClases(ArrayList<Clase> clases) {
        vista.dlmClases.clear();
        for (Clase clase : clases) {
            vista.dlmClases.addElement(clase);
        }
    }

    private void listarSuscripciones(ArrayList<Suscripcion> suscripciones) {
        vista.dlmSuscripciones.clear();
        for (Suscripcion suscripcion : suscripciones) {
            vista.dlmSuscripciones.addElement(suscripcion);
        }
    }

    private void listarEntrenadores(ArrayList<Entrenador> entrenadores) {
        vista.dlmEntrenadores.clear();
        for (Entrenador entrenador : entrenadores) {
            vista.dlmEntrenadores.addElement(entrenador);
        }
    }

    private void listarClientes(ArrayList<Cliente> clientes) {
        vista.dlmClientes.clear();
        for (Cliente cliente : clientes) {
            vista.dlmClientes.addElement(cliente);
        }

    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listClientes.getSelectionModel().addListSelectionListener(listener);
        vista.listClase.getSelectionModel().addListSelectionListener(listener);
        vista.listEntrenadores.getSelectionModel().addListSelectionListener(listener);
        vista.listReservas.getSelectionModel().addListSelectionListener(listener);
        vista.listSuscripciones.getSelectionModel().addListSelectionListener(listener);

    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Cliente cliente = (Cliente) vista.listClientes.getSelectedValue();
            if (cliente != null) {
                vista.txtNombreCliente.setText(cliente.getNombre());
                vista.txtDireccion.setText(cliente.getDireccion());
                vista.txtTelefono.setText(cliente.getTelefono());
                vista.txtEmail.setText(cliente.getEmail());
            }
            Suscripcion suscripcion = (Suscripcion) vista.listSuscripciones.getSelectedValue();
            if (suscripcion != null) {
                vista.comboClientesRegistrados.setSelectedItem(suscripcion.getCliente());
                vista.comboTipoSuscripcion.setSelectedItem(suscripcion.getTipo());
                vista.txtDuracion.setText(String.valueOf(suscripcion.getDuracion()));
                vista.txtPrecio.setText(String.valueOf(suscripcion.getCosto()));
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
