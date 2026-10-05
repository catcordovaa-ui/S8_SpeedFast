package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class VentanaRepartidores extends JFrame {

    private JTextField campoId;
    private JTextField campoNombre;

    private JButton botonGuardar;
    private JButton botonActualizar;
    private JButton botonEliminar;

    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;

    private RepartidorDAO repartidorDAO;

    public VentanaRepartidores() {

        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Repartidores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(2, 2));

        panelDatos.add(new JLabel("ID:"));
        campoId = new JTextField();
        panelDatos.add(campoId);

        panelDatos.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelDatos.add(campoNombre);

        JPanel panelBotones = new JPanel();

        botonGuardar = new JButton("Guardar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");

        panelBotones.add(botonGuardar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");

        tablaRepartidores = new JTable(modeloTabla);

        add(panelDatos, BorderLayout.NORTH);
        add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarRepartidores();

        botonGuardar.addActionListener(e -> guardarRepartidor());
        botonActualizar.addActionListener(e -> actualizarRepartidor());
        botonEliminar.addActionListener(e -> eliminarRepartidor());
    }

    private void guardarRepartidor() {

        String nombre = campoNombre.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el nombre del repartidor.");
            return;
        }

        Repartidor repartidor = new Repartidor(0, nombre);

        repartidorDAO.create(repartidor);

        JOptionPane.showMessageDialog(this,
                "Repartidor registrado correctamente.");

        limpiarCampos();
        cargarRepartidores();
    }

    private void actualizarRepartidor() {

        try {

            int id = Integer.parseInt(campoId.getText());
            String nombre = campoNombre.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar el nombre del repartidor.");
                return;
            }

            Repartidor repartidor = new Repartidor(id, nombre);

            repartidorDAO.update(repartidor);

            JOptionPane.showMessageDialog(this,
                    "Repartidor actualizado correctamente.");

            limpiarCampos();
            cargarRepartidores();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un ID válido.");
        }
    }

    private void eliminarRepartidor() {

        try {

            int id = Integer.parseInt(campoId.getText());

            repartidorDAO.delete(id);

            JOptionPane.showMessageDialog(this,
                    "Repartidor eliminado correctamente.");

            limpiarCampos();
            cargarRepartidores();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un ID válido.");
        }
    }

    private void cargarRepartidores() {

        modeloTabla.setRowCount(0);

        List<Repartidor> repartidores = repartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {

            modeloTabla.addRow(new Object[]{
                    repartidor.getId(),
                    repartidor.getNombre()
            });
        }
    }

    private void limpiarCampos() {

        campoId.setText("");
        campoNombre.setText("");
    }
}