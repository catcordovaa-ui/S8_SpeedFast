package vista;

import dao.PedidoDAO;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;

    public VentanaListaPedidos() {

        pedidoDAO = new PedidoDAO();

        setTitle("Lista de Pedidos");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado");

        tablaPedidos = new JTable(modeloTabla);

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        cargarPedidos();
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        List<Pedido> pedidos = pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {

            modeloTabla.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getDireccion(),
                    pedido.getTipo(),
                    pedido.getEstado()
            });
        }
    }
}