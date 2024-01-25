module com.example.sad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sad to javafx.fxml;
    exports com.example.sad;
}