package vista;

import dao.PedidoDAO;
import modelo.Pedido;

import javax.swing.*;
import java.awt.GridLayout;

public class VentanaRegistroPedido extends JFrame {

    private JTextField campoId;
    private JTextField campoDireccion;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboEstado;

    private JButton botonGuardar;
    private JButton botonActualizar;
    private JButton botonEliminar;

    private PedidoDAO pedidoDAO;

    public VentanaRegistroPedido() {

        pedidoDAO = new PedidoDAO();

        setTitle("Gestión de Pedidos");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("ID:"));
        campoId = new JTextField();
        panel.add(campoId);

        panel.add(new JLabel("Dirección:"));
        campoDireccion = new JTextField();
        panel.add(campoDireccion);

        panel.add(new JLabel("Tipo:"));

        comboTipo = new JComboBox<>();
        comboTipo.addItem("COMIDA");
        comboTipo.addItem("ENCOMIENDA");
        comboTipo.addItem("EXPRESS");

        panel.add(comboTipo);

        panel.add(new JLabel("Estado:"));

        comboEstado = new JComboBox<>();
        comboEstado.addItem("PENDIENTE");
        comboEstado.addItem("EN_REPARTO");
        comboEstado.addItem("ENTREGADO");

        panel.add(comboEstado);

        botonGuardar = new JButton("Guardar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");

        panel.add(botonGuardar);
        panel.add(botonActualizar);
        panel.add(new JLabel());
        panel.add(botonEliminar);

        add(panel);

        botonGuardar.addActionListener(e -> guardarPedido());

        botonActualizar.addActionListener(e -> actualizarPedido());

        botonEliminar.addActionListener(e -> eliminarPedido());
    }

    private void guardarPedido() {

        String direccion = campoDireccion.getText();

        String tipo = (String) comboTipo.getSelectedItem();

        String estado = (String) comboEstado.getSelectedItem();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la dirección."
            );

            return;
        }

        Pedido pedido = new Pedido(
                0,
                direccion,
                tipo,
                estado
        );

        pedidoDAO.create(pedido);

        JOptionPane.showMessageDialog(
                this,
                "Pedido registrado correctamente."
        );

        limpiarCampos();
    }

    private void actualizarPedido() {

        try {

            int id = Integer.parseInt(campoId.getText());

            String direccion = campoDireccion.getText();

            String tipo = (String) comboTipo.getSelectedItem();

            String estado = (String) comboEstado.getSelectedItem();

            if (direccion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar la dirección."
                );

                return;
            }

            Pedido pedido = new Pedido(
                    id,
                    direccion,
                    tipo,
                    estado
            );

            pedidoDAO.update(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
            );

            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un ID válido."
            );
        }
    }

    private void eliminarPedido() {

        try {

            int id = Integer.parseInt(campoId.getText());

            pedidoDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
            );

            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un ID válido."
            );
        }
    }

    private void limpiarCampos() {

        campoId.setText("");
        campoDireccion.setText("");
        comboTipo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
    }
}