package com.andrelut.gimnasio.gui;

import com.andrelut.gimnasio.enums.EstadoEquipamiento;
import com.andrelut.gimnasio.enums.TipoClase;
import com.andrelut.gimnasio.enums.TipoEquipamiento;
import com.andrelut.gimnasio.enums.TipoSuscripcion;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
    private static final String TITULOFRAME = "Gimnasio";


    public JTabbedPane tabbedPanedGimnasio;
    public JPanel panel1;
    public JPanel JPanelClientes;
    public JPanel JPanelSuscripciones;
    public JPanel JPanelClases;
    public JPanel JPanelEntrenadores;
    public JPanel JPanelEquipamiento;
    public JTextField txtNombreCliente;
    public JTextField txtEmail;
    public JTextField txtDireccion;
    public JComboBox comboClientesRegistrados;
    public JFrame frame;
    public JComboBox comboTipoSuscripcion;
    public JTextField txtDuracion;
    public JButton btnAddCliente;
    public JButton btnModificarCliente;
    public JButton btnEliminarCliente;
    public JTextField txtTelefono;
    public JButton btnAddSuscripciones;
    public JButton btnModificarSuscripciones;
    public JButton btnDeleteSuscripciones;
    public JMenuItem itemConexion;
    public JMenuItem itemSalir;
    public JTextField txtHorarioClase;
    public JComboBox comboEntrenadorClase;
    public JTextField txtNombreEntrenador;
    public JTextField txtHorario;
    public JButton btnAddEntrenador;
    public JButton btnModificarEntrenador;
    public JButton btnEliminarEntrenador;
    public JComboBox comboEspecialidadEntrenador;
    public JButton btnAddClase;
    public JButton btnModificarClase;
    public JButton btnDeleteClase;
    public JComboBox comboEquipamiento;
    public JTextField txtCostoEquipamiento;
    public JComboBox comboEstadoEquipamiento;
    public JButton btnAddEquipamiento;
    public JButton btnEliminarEquipamiento;
    public JButton btnModificarEquipamiento;

    public JList listClientes;
    public JList listSuscripciones;
    public JList listEntrenadores;
    public JList listEquipamiento;
    public JComboBox comboTiposClases;
    public JTextField txtTipoClase;
    public JList listClase;
    public JPanel JPanelReservas;

    public JButton btnAddReserva;
    public JButton btnModificarReserva;
    public JButton btnDeleteReserva;
    public JList listReservas;
    public JTextField txtPrecio;
    public DefaultListModel dlmClientes;
    public DefaultListModel dlmSuscripciones;
    public DefaultListModel dlmEntrenadores;
    public DefaultListModel dlmEquipamiento;
    public DefaultListModel dlmClases;
    public DefaultListModel dlmReservas;


    /**
     * Constructor
     * <p>
     * Crea una nueva ventana principal
     */
    public Vista() {
        frame = new JFrame("Gimnasio");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));


        setMenu();
        setListModels();
        setComboBox();

    }


    private void setListModels() {
        dlmClientes = new DefaultListModel();
        listClientes.setModel(dlmClientes);

        dlmSuscripciones = new DefaultListModel();
        listSuscripciones.setModel(dlmSuscripciones);

        dlmEntrenadores = new DefaultListModel();
        listEntrenadores.setModel(dlmEntrenadores);

        dlmEquipamiento = new DefaultListModel();
        listEquipamiento.setModel(dlmEquipamiento);

        dlmClases = new DefaultListModel();
        listClase.setModel(dlmClases);

        dlmReservas = new DefaultListModel();
        listReservas.setModel(dlmReservas);


    }

    /**
     * Establece el menú de la ventana principal
     */
    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemConexion = new JMenuItem("Conectar");
        itemConexion.setActionCommand("conectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("salir");
        menu.add(itemConexion);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        frame.setJMenuBar(mbBar); // Añade el JMenuBar al JFrame
    }


    /**
     * Establece los valores de los ComboBox
     */
    private void setComboBox() {
        for (TipoSuscripcion tipo : TipoSuscripcion.values()) {
            comboTipoSuscripcion.addItem(tipo.getNombre());
        }
        comboTipoSuscripcion.setSelectedIndex(-1);

        for (TipoClase tipo : TipoClase.values()) {
            comboTiposClases.addItem(tipo.getNombre());
        }
        comboTiposClases.setSelectedIndex(-1);

        for (TipoClase tipo : TipoClase.values()) {
            comboEspecialidadEntrenador.addItem(tipo.getNombre());
        }
        comboEspecialidadEntrenador.setSelectedIndex(-1);

        for (TipoEquipamiento tipo : TipoEquipamiento.values()) {
            comboEquipamiento.addItem(tipo.getDescripcion());
        }
        comboEquipamiento.setSelectedIndex(-1);

        for (EstadoEquipamiento estado : EstadoEquipamiento.values()) {
            comboEstadoEquipamiento.addItem(estado.getValor());
        }
        comboEstadoEquipamiento.setSelectedIndex(-1);

    }


    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }
}
