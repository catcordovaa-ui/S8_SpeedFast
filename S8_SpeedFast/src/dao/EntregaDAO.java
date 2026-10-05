package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public void create(Entrega entrega) {

        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, entrega.getIdPedido());
            preparedStatement.setInt(2, entrega.getIdRepartidor());
            preparedStatement.setString(3, entrega.getFecha());
            preparedStatement.setString(4, entrega.getHora());

            preparedStatement.executeUpdate();

            System.out.println("Entrega registrada correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al registrar la entrega: " + e.getMessage());
        }
    }

    public List<Entrega> readAll() {

        List<Entrega> entregas = new ArrayList<>();

        String sql = "SELECT * FROM entregas";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Entrega entrega = new Entrega(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_pedido"),
                        resultSet.getInt("id_repartidor"),
                        resultSet.getString("fecha"),
                        resultSet.getString("hora")
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar las entregas: " + e.getMessage());
        }

        return entregas;
    }

    public void update(Entrega entrega) {

        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, entrega.getIdPedido());
            preparedStatement.setInt(2, entrega.getIdRepartidor());
            preparedStatement.setString(3, entrega.getFecha());
            preparedStatement.setString(4, entrega.getHora());
            preparedStatement.setInt(5, entrega.getId());

            preparedStatement.executeUpdate();

            System.out.println("Entrega actualizada correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al actualizar la entrega: " + e.getMessage());
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Entrega eliminada correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al eliminar la entrega: " + e.getMessage());
        }
    }
}
