module tn.esprit.siyahidesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires org.controlsfx.controls;

    opens tn.esprit.siyahidesktop to javafx.fxml;
    exports tn.esprit.siyahidesktop;
    exports tn.esprit.siyahidesktop.controllers to javafx.fxml;
    opens tn.esprit.siyahidesktop.controllers to javafx.fxml;


}