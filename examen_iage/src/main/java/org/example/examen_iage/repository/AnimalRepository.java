package org.example.examen_iage.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.examen_iage.model.Animal;
import org.example.examen_iage.model.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalRepository {
    private Connection connection;

    public AnimalRepository() {
        this.connection = new DB().getConnection();
    }

   public  void add(Animal animal){
        String sql = "INSERT INTO animaux (nom,age,type,nombre) values (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,animal.getNom());
            preparedStatement.setInt(2,animal.getAge());
            preparedStatement.setString(3,animal.getType());
            preparedStatement.setInt(4,animal.getNombre());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update (Animal animal){
        String sql = "UPDATE animaux set nom = ?, age =?, type=?, nombre=? where id =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,animal.getNom());
            preparedStatement.setInt(2,animal.getAge());
            preparedStatement.setString(3,animal.getType());
            preparedStatement.setInt(4,animal.getNombre());
            preparedStatement.setInt(5,animal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  ObservableList<Animal> findAll() throws SQLException {
        ObservableList<Animal> animaux = FXCollections.observableArrayList();
        String sql = "SELECT * FROM animaux";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result= statement.executeQuery();

        while(result.next()){

            Animal animal = new Animal(result.getString("nom"),
                    result.getInt("age"),
                    result.getString("type"),
                    result.getInt("nombre"));
            animal.setId(result.getInt("id"));

            animaux.add(animal);
        }

        return animaux;
    }

    public void delete(int id) throws SQLException {
        String sql = "Delete from animaux where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        statement.executeUpdate();

    }
}
