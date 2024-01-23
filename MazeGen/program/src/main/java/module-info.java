module com.example.program {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires java.compiler;


    opens com.example.program.control to javafx.fxml;
    exports com.example.program.control;
}