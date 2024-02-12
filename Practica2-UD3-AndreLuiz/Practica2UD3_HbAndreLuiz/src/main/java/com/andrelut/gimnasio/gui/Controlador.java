package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.Cliente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;


    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;

        addActionListeners(this);
        addListSelectionListener(this);
    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listClase.getSelectionModel().addListSelectionListener(listener);
        vista.listClientes.getSelectionModel().addListSelectionListener(listener);
        vista.listEntrenadores.getSelectionModel().addListSelectionListener(listener);
        vista.listEquipamiento.getSelectionModel().addListSelectionListener(listener);
        vista.listReservas.getSelectionModel().addListSelectionListener(listener);
        vista.listSuscripciones.getSelectionModel().addListSelectionListener(listener);

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
        // Verifica si hay conexión antes de proceder con cualquier comando, excepto "conectar".
        if (!comando.equals("conectar")) {
            conectado = modelo.estaConectado(); // Actualiza el estado de la conexión.
            if (!conectado) {
                JOptionPane.showMessageDialog(vista.frame, "No hay conexión a la base de datos. Por favor, conecte antes de continuar.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return; // Sale del método si no hay conexión.
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

                break;
            case "salir":
                System.exit(0);
                break;
            case "añadirCliente":
                System.out.println("Añadir cliente");

                break;
            case "modificarCliente":
                System.out.println("Modificar cliente");

                break;
            case "eliminarCliente":
                System.out.println("Eliminar cliente");

                break;

            case "añadirSuscripcion":
                System.out.println("Añadir suscripción");

                break;
            case "modificarSuscripcion":
                System.out.println("Modificar suscripción");


                break;
            case "eliminarSuscripcion":
                System.out.println("Eliminar suscripción");


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

        if (conectado) {
            actualizar(); // Llama a actualizar solo si está conectado.
        }
    }

    private void actualizar() {
        listarClientes(modelo.getClientes());
    }

    private void listarClientes(ArrayList<Cliente> clientes) {
        vista.dlmClientes.clear();
        for (Cliente cliente : clientes) {
            vista.dlmClientes.addElement(cliente);
        }

    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
