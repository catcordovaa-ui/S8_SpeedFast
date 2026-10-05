package dao;

import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void create(Pedido pedido) {

        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, pedido.getDireccion());
            preparedStatement.setString(2, pedido.getTipo());
            preparedStatement.setString(3, pedido.getEstado());

            preparedStatement.executeUpdate();

            System.out.println("Pedido registrado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al registrar el pedido: " + e.getMessage());
        }
    }

    public List<Pedido> readAll() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedidos";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Pedido pedido = new Pedido(
                        resultSet.getInt("id"),
                        resultSet.getString("direccion"),
                        resultSet.getString("tipo"),
                        resultSet.getString("estado")
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    public void update(Pedido pedido) {

        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, pedido.getDireccion());
            preparedStatement.setString(2, pedido.getTipo());
            preparedStatement.setString(3, pedido.getEstado());
            preparedStatement.setInt(4, pedido.getId());

            preparedStatement.executeUpdate();

            System.out.println("Pedido actualizado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Pedido eliminado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}