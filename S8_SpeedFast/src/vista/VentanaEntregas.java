package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class VentanaEntregas extends JFrame {

    private JTextField campoId;
    private JTextField campoFecha;
    private JTextField campoHora;

    private JComboBox<Pedido> comboPedido;
    private JComboBox<Repartidor> comboRepartidor;

    private JButton botonGuardar;
    private JButton botonActualizar;
    private JButton botonEliminar;

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    public VentanaEntregas() {

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Entregas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(5, 2));

        panelDatos.add(new JLabel("ID:"));
        campoId = new JTextField();
        panelDatos.add(campoId);

        panelDatos.add(new JLabel("Pedido:"));
        comboPedido = new JComboBox<>();
        panelDatos.add(comboPedido);

        panelDatos.add(new JLabel("Repartidor:"));
        comboRepartidor = new JComboBox<>();
        panelDatos.add(comboRepartidor);

        panelDatos.add(new JLabel("Fecha (yyyy-mm-dd):"));
        campoFecha = new JTextField();
        panelDatos.add(campoFecha);

        panelDatos.add(new JLabel("Hora (HH:mm:ss):"));
        campoHora = new JTextField();
        panelDatos.add(campoHora);

        JPanel panelBotones = new JPanel();

        botonGuardar = new JButton("Guardar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");

        panelBotones.add(botonGuardar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Pedido");
        modeloTabla.addColumn("Repartidor");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");

        tablaEntregas = new JTable(modeloTabla);

        add(panelDatos, BorderLayout.NORTH);
        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPedidos();
        cargarRepartidores();
        cargarEntregas();

        botonGuardar.addActionListener(e -> guardarEntrega());
        botonActualizar.addActionListener(e -> actualizarEntrega());
        botonEliminar.addActionListener(e -> eliminarEntrega());
    }

    private void cargarPedidos() {

        comboPedido.removeAllItems();

        List<Pedido> pedidos = pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {
            comboPedido.addItem(pedido);
        }
    }

    private void cargarRepartidores() {

        comboRepartidor.removeAllItems();

        List<Repartidor> repartidores = repartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {
            comboRepartidor.addItem(repartidor);
        }
    }

    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        List<Entrega> entregas = entregaDAO.readAll();

        for (Entrega entrega : entregas) {

            modeloTabla.addRow(new Object[]{
                    entrega.getId(),
                    entrega.getIdPedido(),
                    entrega.getIdRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()
            });
        }
    }

    private void guardarEntrega() {

        Pedido pedido = (Pedido) comboPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();

        String fecha = campoFecha.getText();
        String hora = campoHora.getText();

        if (pedido == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un pedido.");
            return;
        }

        if (repartidor == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un repartidor.");
            return;
        }

        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar la fecha.");
            return;
        }

        if (hora.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar la hora.");
            return;
        }

        Entrega entrega = new Entrega(
                0,
                pedido.getId(),
                repartidor.getId(),
                fecha,
                hora
        );

        entregaDAO.create(entrega);

        JOptionPane.showMessageDialog(this,
                "Entrega registrada correctamente.");

        limpiarCampos();
        cargarEntregas();
    }

    private void actualizarEntrega() {

        try {

            int id = Integer.parseInt(campoId.getText());

            Pedido pedido = (Pedido) comboPedido.getSelectedItem();
            Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();

            String fecha = campoFecha.getText();
            String hora = campoHora.getText();

            if (pedido == null || repartidor == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un pedido y un repartidor.");
                return;
            }

            if (fecha.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar fecha y hora.");
                return;
            }

            Entrega entrega = new Entrega(
                    id,
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            entregaDAO.update(entrega);

            JOptionPane.showMessageDialog(this,
                    "Entrega actualizada correctamente.");

            limpiarCampos();
            cargarEntregas();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un ID válido.");
        }
    }

    private void eliminarEntrega() {

        try {

            int id = Integer.parseInt(campoId.getText());

            entregaDAO.delete(id);

            JOptionPane.showMessageDialog(this,
                    "Entrega eliminada correctamente.");

            limpiarCampos();
            cargarEntregas();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un ID válido.");
        }
    }

    private void limpiarCampos() {

        campoId.setText("");
        campoFecha.setText("");
        campoHora.setText("");

        if (comboPedido.getItemCount() > 0) {
            comboPedido.setSelectedIndex(0);
        }

        if (comboRepartidor.getItemCount() > 0) {
            comboRepartidor.setSelectedIndex(0);
        }
    }
}
