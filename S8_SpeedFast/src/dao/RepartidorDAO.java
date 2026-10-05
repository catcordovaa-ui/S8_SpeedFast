package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public void create(Repartidor repartidor) {

        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, repartidor.getNombre());

            preparedStatement.executeUpdate();

            System.out.println("Repartidor registrado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al registrar el repartidor: " + e.getMessage());
        }
    }

    public List<Repartidor> readAll() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidores";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Repartidor repartidor = new Repartidor(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre")
                );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los repartidores: " + e.getMessage());
        }

        return repartidores;
    }

    public void update(Repartidor repartidor) {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, repartidor.getNombre());
            preparedStatement.setInt(2, repartidor.getId());

            preparedStatement.executeUpdate();

            System.out.println("Repartidor actualizado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al actualizar el repartidor: " + e.getMessage());
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Repartidor eliminado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al eliminar el repartidor: " + e.getMessage());
        }
    }
}