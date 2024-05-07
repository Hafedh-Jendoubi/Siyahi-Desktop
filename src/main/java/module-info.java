module tn.esprit.siyahidesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires org.controlsfx.controls;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires jdk.jsobject;


    // Exports your packages where your controllers and main application class are located
    exports tn.esprit.siyahidesktop;

    // Opens packages to JavaFX, only needed if reflection is used extensively (e.g., FXML)
    opens tn.esprit.siyahidesktop to javafx.graphics;
    opens tn.esprit.siyahidesktop.controllers to javafx.fxml, javafx.graphics;
    exports tn.esprit.siyahidesktop.controllers;


}