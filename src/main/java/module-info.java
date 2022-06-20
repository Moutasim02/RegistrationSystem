module com.moutasim.registrationsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.moutasim.registrationsystem to javafx.fxml;
    exports com.moutasim.registrationsystem;
}