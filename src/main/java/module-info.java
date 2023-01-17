module com.example.filmaficionado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.media;


    opens com.example.filmaficionado to javafx.fxml;
    exports com.example.filmaficionado;
    exports com.example.filmaficionado.Controllers;
    opens com.example.filmaficionado.Controllers to javafx.fxml;
    exports com.example.filmaficionado.ControlObjects;
    opens com.example.filmaficionado.ControlObjects to javafx.fxml;





}