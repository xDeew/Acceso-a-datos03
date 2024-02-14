package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.*;
import com.andrelut.gimnasio.enums.TipoClase;
import com.andrelut.gimnasio.enums.TipoSuscripcion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controlador implements ActionListener, ListSelectionListener, ItemListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;

    private Entrenador entrenadorAsignado;


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
        vista.comboTiposClases.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tipoClaseString = vista.comboTiposClases.getSelectedItem().toString().replace(" ", "_").toUpperCase();
                TipoClase selectedTipoClase = TipoClase.valueOf(tipoClaseString);

                String equipamiento = String.valueOf(selectedTipoClase.getEquipamiento());
                vista.txtTipoEquipamientoClase.setText(equipamiento);
            }
        });


//        vista.comboTiposClases.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                String tipoClaseString = vista.comboTiposClases.getSelectedItem().toString().replace(" ", "_").toUpperCase();
//                TipoClase selectedTipoClase = TipoClase.valueOf(tipoClaseString);
//
//                entrenadorAsignado = modelo.obtenerEntrenadorPorEspecialidad(selectedTipoClase.getNombre());
//                if (entrenadorAsignado != null) {
//                    vista.txtEntrenadorClase.setText(entrenadorAsignado.getNombre());
//                } else {
//                    vista.txtEntrenadorClase.setText("No hay entrenador disponible");
//                }
//            }
//        });


    }

    private Entrenador obtenerEntrenadorPorTipoClase(TipoClase selectedTipoClase) {
        String especialidad = selectedTipoClase.name();
        int idEntrenador = asignarIdEntrenadorPorEspecialidad(especialidad);
        return modelo.getEntrenadorPorId(idEntrenador);
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
                Clase clase = new Clase();
                clase.setNombre(vista.txtNombreClase.getText());
                clase.setHorario(vista.txtHorarioClase.getText());
                clase.setTipo(vista.comboTiposClases.getSelectedItem().toString());
                clase.setEquipamiento(vista.txtTipoEquipamientoClase.getText()); // Aquí asumes un String, pero deberías considerar cómo manejas esta relación

                // Busca el Entrenador por nombre o ID, según lo que tengas disponible
                Entrenador entrenador = modelo.obtenerEntrenadorPorNombre(vista.comboEntrenadoresElegir.getSelectedItem().toString());
                if (entrenador != null) {
                   clase.setEntrenador(entrenador); // Asegúrate de tener este setter en Clase
                } else {
                    // Manejar el caso de no encontrar el entrenador
                    JOptionPane.showMessageDialog(null, "Entrenador no encontrado.");
                    return;
                }

                // Persiste la clase
                modelo.añadirClase(clase);
                break;

            case "modificarClase":

                break;
            case "eliminarClase":

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
                modelo.eliminarEntrenador(entrenadorAEliminar);

                break;


            case "añadirEquipamiento":
                Equipamiento equipamientoNuevo = new Equipamiento();
                equipamientoNuevo.setTipoEquipamiento(vista.comboEquipamiento.getSelectedItem().toString());
                equipamientoNuevo.setCosto(new BigDecimal(vista.txtCostoEquipamiento.getText()));
                equipamientoNuevo.setEstado(vista.comboEstadoEquipamiento.getSelectedItem().toString());
                modelo.añadirEquipamiento(equipamientoNuevo);

                break;
            case "modificarEquipamiento":

                break;
            case "eliminarEquipamiento":
                Equipamiento equipamientoAEliminar = (Equipamiento) vista.listEquipamiento.getSelectedValue();
                modelo.eliminarEquipamiento(equipamientoAEliminar);

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

    private int asignarIdEntrenadorPorEspecialidad(String especialidad) {
        int id = 0;
        switch (especialidad) {
            case "YOGA":
                id = 1;
                break;
            case "PILATES":
                id = 2;
                break;
            case "ZUMBA":
                id = 3;
                break;
            case "CROSSFIT":
                id = 4;
                break;
            case "SPINNING":
                id = 5;
                break;
            case "FITNESS":
                id = 6;
                break;
        }
        return id;

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
        // Limpiar el JComboBox
        vista.comboEntrenadoresElegir.removeAllItems();

        // Obtener todos los entrenadores
        List<Entrenador> entrenadores = modelo.obtenerTodosLosEntrenadores();

        // Añadir cada entrenador al JComboBox
        for (Entrenador entrenador : entrenadores) {
            vista.comboEntrenadoresElegir.addItem(entrenador.getNombre());
        }
    }


    private void limpiarCampos() {
        limpiarCamposCliente();
        limpiarCamposSuscripciones();
        //  limpiarCamposClase();
        limpiarCamposEntrenador();
        limpiarCamposEquipamiento();
    }

    private void limpiarCamposEquipamiento() {
        vista.comboEquipamiento.setSelectedIndex(-1);
        vista.txtCostoEquipamiento.setText("");
        vista.comboEstadoEquipamiento.setSelectedIndex(-1);
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
        vista.txtHorarioClase.setText("");
        if (vista.comboEntrenadorClase != null) {
            vista.comboEntrenadorClase.setSelectedIndex(-1);
        } else {
            System.out.println("comboEntrenadorClase es null");
        }
        vista.comboTiposClases.setSelectedIndex(-1);
        vista.txtTipoEquipamientoClase.setText("");
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
        listarEquipamiento(modelo.getEquipamiento());
        listarClases(modelo.getClases());
    }

    private void listarEquipamiento(ArrayList<Equipamiento> equipamiento) {
        vista.dlmEquipamiento.clear();
        for (Equipamiento equip : equipamiento) {
            vista.dlmEquipamiento.addElement(equip);
        }
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
