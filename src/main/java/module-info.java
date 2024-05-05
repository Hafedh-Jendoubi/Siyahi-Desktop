module com.example.azizkh {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.pdfbox;
    requires mysql.connector.java;
    requires twilio;

    opens View to javafx.fxml;
    exports App;
    exports View;
}
