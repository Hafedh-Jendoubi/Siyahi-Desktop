package tn.esprit.siyahidesktop.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tn.esprit.siyahidesktop.models.Service;

public class SharedService {
    private static final SharedService instance = new SharedService();
    private final ObjectProperty<Service> selectedService = new SimpleObjectProperty<>();

    private SharedService() {}

    public static SharedService getInstance() {
        return instance;
    }

    public ObjectProperty<Service> selectedServiceProperty() {
        return selectedService;
    }

    public void setSelectedService(Service service) {
        this.selectedService.set(service);
    }

    public Service getSelectedService() {
        return selectedService.get();
    }
}
