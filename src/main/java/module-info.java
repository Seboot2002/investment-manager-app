module com.seboot.portpocket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.seboot.portpocket to javafx.fxml;
    exports com.seboot.portpocket;
}