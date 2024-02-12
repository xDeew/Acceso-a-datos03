package com.andrelut.gimnasio.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    public JTextField txtNombre;
    public JTextField txtEmail;
    public JTextField txtDireccion;
    public JComboBox comboClientesRegistrados;
    public JFrame frame;
    public JComboBox comboTipoSuscripcion;
    public JTextField txtPrecio;
    public JButton btnAddCliente;
    public JButton btnModificarCliente;
    public JButton btnEliminarCliente;
    public JTextField txtTelefono;
    public JButton btnAddSuscripciones;
    public JButton btnModificarSuscripciones;
    public JButton btnDeleteSuscripciones;
    public JButton btnGananciaMensual;
    public DefaultTableModel dtmClientes;
    public DefaultTableModel dtmSuscripciones;
    public JMenuItem itemConexion;
    public JMenuItem itemSalir;
    public JPasswordField adminPassword;
    public JDialog adminPasswordDialog;
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
    public JButton btnFechasContrato;
    public JButton btnBuscarPorNombre;
    public DefaultTableModel dtmEquipamiento;
    public DefaultTableModel dtmEntrenadores;
    public DefaultTableModel dtmClases;
    public JButton btnLimpiarSeleccionClientes;
    public JButton btnLimpiarSeleccionSuscripciones;
    public JButton btnLimpiarSeleccionEntrenadores;
    public JButton btnLimpiarSeleccionEquipamiento;
    public JButton btnLimpiarSeleccionClases;
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
        super(TITULOFRAME);
        initFrame();
    }

    /**
     * Inicializa la ventana principal
     */
    public void initFrame() {
        panel1.setLayout(new BorderLayout());
        panel1.add(tabbedPanedGimnasio, BorderLayout.CENTER);


        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth() + 500, this.getHeight() + 150));
        this.setLocationRelativeTo(null);


        setMenu();
        setComboBox();
        setListModels();

        Color lightBlue = new Color(173, 216, 230);
        Color skyBlue = new Color(135, 206, 235);

        btnAddCliente.setBackground(lightBlue);
        btnModificarCliente.setBackground(lightBlue);
        btnEliminarCliente.setBackground(lightBlue);
        btnLimpiarSeleccionClientes.setBackground(skyBlue);

        btnAddSuscripciones.setBackground(lightBlue);
        btnModificarSuscripciones.setBackground(lightBlue);
        btnDeleteSuscripciones.setBackground(lightBlue);
        btnLimpiarSeleccionSuscripciones.setBackground(skyBlue);

        btnAddEntrenador.setBackground(lightBlue);
        btnModificarEntrenador.setBackground(lightBlue);
        btnEliminarEntrenador.setBackground(lightBlue);
        btnLimpiarSeleccionEntrenadores.setBackground(skyBlue);

        btnAddClase.setBackground(lightBlue);
        btnModificarClase.setBackground(lightBlue);
        btnDeleteClase.setBackground(lightBlue);
        btnLimpiarSeleccionClases.setBackground(skyBlue);

        btnAddEquipamiento.setBackground(lightBlue);
        btnModificarEquipamiento.setBackground(lightBlue);
        btnEliminarEquipamiento.setBackground(lightBlue);
        btnLimpiarSeleccionEquipamiento.setBackground(skyBlue);

        btnGananciaMensual.setBackground(lightBlue);
        btnFechasContrato.setBackground(lightBlue);
        btnBuscarPorNombre.setBackground(lightBlue);


        this.setVisible(true);
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
        itemConexion = new JMenuItem("Desconectar");
        itemConexion.setActionCommand("desconectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("salir");
        menu.add(itemConexion);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }


    /**
     * Establece los valores de los ComboBox
     */
    private void setComboBox() {


    }


    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }
}
