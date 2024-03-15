module org.example.examen_iage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens org.example.examen_iage to javafx.fxml;
    exports org.example.examen_iage;
    exports org.example.examen_iage.model;

}