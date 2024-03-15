package org.example.examen_iage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.example.examen_iage.model.Animal;
import org.example.examen_iage.repository.AnimalRepository;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HelloController implements Initializable {
    private AnimalRepository animalRepository;

    @FXML
    private TextField cage;

    @FXML
    private TextField cid;

    @FXML
    private TextField cnom;

    @FXML
    private TextField cnombre;

    @FXML
    private TextField csearch;

    @FXML
    private ComboBox<String> ctype;

    @FXML
    private Label labelType;

    @FXML
    private TableColumn<Animal, Integer> tAge;

    @FXML
    private TableColumn<Animal, Integer> tId;

    @FXML
    private TableColumn<Animal, String> tNom;

    @FXML
    private TableColumn<Animal, Integer> tNombre;

    @FXML
    private TableColumn<Animal, String> tType;

    @FXML
    private TableView<Animal> tableau;

    @FXML
    private Label tP;
    @FXML
    void add(ActionEvent event) {
        Animal animal = new Animal(cnom.getText(),Integer.parseInt( cage.getText()),ctype.getValue(),Integer.parseInt( cnombre.getText()));
        animalRepository.add(animal);
        printAll();
        this.clear(event);
    }


    @FXML
    void clear(ActionEvent event) {
        cnom.setText("");
        cage.setText("");
        cnombre.setText("");
        ctype.setValue("");

    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        //alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Voulez vous supprimer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int id = tableau.getSelectionModel().getSelectedItem().getId();
            animalRepository.delete(id);
            printAll();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    void filter(KeyEvent event) {
        String searchQuery = csearch.getText().toLowerCase();
        try {
            ObservableList<Animal> filteredList = FXCollections.observableArrayList();
            ObservableList<Animal> list = animalRepository.findAll();

            for (Animal animal : list) {

                if (animal.getNom().toLowerCase().contains(searchQuery) || isNumeric(searchQuery) && animal.getAge() == Integer.parseInt(searchQuery) ) {
                    filteredList.add(animal);
                }

            }

            tId.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("id"));
            tNom.setCellValueFactory(new PropertyValueFactory<Animal, String>("nom"));
            tAge.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("age"));
            tType.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
            tNombre.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("nombre"));

            this.tableau.setItems(filteredList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Animal animal = new Animal(
                cnom.getText(),
                Integer.parseInt(cage.getText()),
                ctype.getValue(),
                Integer.parseInt(cnombre.getText())
                );
        animal.setId(Integer.parseInt(cid.getText()));
        animalRepository.update(animal);
        printAll();

    }
    @FXML
    void select(ActionEvent event) {
        String s = ctype.getSelectionModel().getSelectedItem().toString();
        labelType  = new Label(s);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Patte","Aile");
        ctype.setItems(list);
        this.animalRepository = new AnimalRepository();
        this.printAll();
    }

    public void charge(javafx.scene.input.MouseEvent mouseEvent) {

        if(mouseEvent.getClickCount() ==2){
            Animal animal =  tableau.getSelectionModel().getSelectedItem();
            cnom.setText(animal.getNom());
            cage.setText(animal.getAge()+"");
            ctype.setValue(animal.getType()+"");
            cnombre.setText(animal.getNombre()+"");
            cid.setText(animal.getId()+"");
        }

    }


    void printAll(){

        try {

            ObservableList<Animal> list = animalRepository.findAll();
            tId.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("id"));
            tNom.setCellValueFactory(new PropertyValueFactory<Animal, String>("nom"));
            tAge.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("age"));
            tType.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
            tNombre.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("nombre"));

            this.tableau.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static boolean isNumeric(String str) {

        return Pattern.matches("\\d+", str);
    }
}