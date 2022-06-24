module com.moutasim.registrationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens com.moutasim.registrationsystem to javafx.fxml;
    exports com.moutasim.registrationsystem;
}