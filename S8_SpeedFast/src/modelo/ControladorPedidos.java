package modelo;

import java.util.ArrayList;

public class ControladorPedidos {

    private static ArrayList<Pedido> pedidos = new ArrayList<>();

    public static void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public static ArrayList<Pedido> obtenerPedidos() {
        return pedidos;
    }
}