package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JButton botonRegistrar;
    private JButton botonListar;
    private JButton botonEntregar;
    private JButton botonRepartidores;

    public VentanaPrincipal() {

        setTitle("SpeedFast");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        botonRegistrar = new JButton("Registrar pedido");
        botonListar = new JButton("Listar pedidos");
        botonEntregar = new JButton("Gestionar entregas");
        botonRepartidores = new JButton("Gestionar repartidores");

        panel.add(botonRegistrar);
        panel.add(botonListar);
        panel.add(botonEntregar);
        panel.add(botonRepartidores);

        add(panel);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRegistroPedido ventana = new VentanaRegistroPedido();
                ventana.setVisible(true);
            }
        });

        botonListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaListaPedidos ventana = new VentanaListaPedidos();
                ventana.setVisible(true);
            }
        });

        botonEntregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaEntregas ventana = new VentanaEntregas();
                ventana.setVisible(true);
            }
        });

        botonRepartidores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRepartidores ventana = new VentanaRepartidores();
                ventana.setVisible(true);
            }
        });
    }
}