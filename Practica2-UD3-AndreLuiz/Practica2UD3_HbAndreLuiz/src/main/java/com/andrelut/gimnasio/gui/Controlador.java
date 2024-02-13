package com.andrelut.gimnasio.gui;

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
import java.util.Objects;

public class Controlador implements ActionListener, ListSelectionListener, ItemListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;


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

        vista.btnAddEquipamiento.addActionListener(listener);
        vista.btnModificarEquipamiento.addActionListener(listener);
        vista.btnEliminarEquipamiento.addActionListener(listener);


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
                if (!comprobarClienteSuscripcion(clienteAEliminar)) {
                    modelo.eliminarCliente(clienteAEliminar);
                } else {
                    JOptionPane.showMessageDialog(vista.frame, "No se puede eliminar el cliente porque tiene una suscripción activa.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "añadirSuscripcion":
                Suscripcion suscripcion = new Suscripcion();
                Cliente clienteSeleccionado = (Cliente) vista.comboClientesRegistrados.getSelectedItem();
                if (clienteSeleccionado != null && clienteSeleccionado.getId() != 0) {
                    suscripcion.setCliente(clienteSeleccionado);
                    String tipoSeleccionado = Objects.requireNonNull(vista.comboTipoSuscripcion.getSelectedItem()).toString();
                    TipoSuscripcion tipo = TipoSuscripcion.getPorNombre(tipoSeleccionado);
                    if (tipo != null) {
                        suscripcion.setTipo(tipo.getNombre());
                        suscripcion.setDuracion(Integer.parseInt(vista.txtDuracion.getText()));
                        suscripcion.setCosto(tipo.getPrecio());
                        modelo.añadirSuscripcion(suscripcion);
                    }
                } else {
                    JOptionPane.showMessageDialog(vista.frame, "Por favor, selecciona un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
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

                break;
            case "modificarClase":

                break;
            case "eliminarClase":

                break;


            case "añadirEntrenador":


                break;
            case "modificarEntrenador":

                break;
            case "eliminarEntrenador":

                break;


            case "añadirEquipamiento":

                break;
            case "modificarEquipamiento":

                break;
            case "eliminarEquipamiento":

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

    private void limpiarCampos() {
        limpiarCamposCliente();
    }

    private void limpiarCamposCliente() {
        vista.txtNombreCliente.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtEmail.setText("");
    }


    private boolean comprobarClienteSuscripcion(Cliente clienteAEliminar) {
        for (Cliente cliente : modelo.getClientes()) {
            if (cliente.getSuscripcion() != null) {
                if (cliente.getSuscripcion().getCliente().equals(clienteAEliminar)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void actualizar() {
        listarClientes(modelo.getClientes());
        listarSuscripciones(modelo.getSuscripciones());
        listarEntrenadores(modelo.getEntrenadores());
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
        vista.listEquipamiento.getSelectionModel().addListSelectionListener(listener);
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
                vista.comboTipoSuscripcion.setSelectedItem(suscripcion.getTipo());
                vista.txtDuracion.setText(String.valueOf(suscripcion.getDuracion()));
                vista.txtPrecio.setText(String.valueOf(suscripcion.getCosto()));
                vista.comboClientesRegistrados.setSelectedItem(suscripcion.getCliente());
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
