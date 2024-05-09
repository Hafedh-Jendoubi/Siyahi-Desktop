package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class calendrierController implements Initializable {
    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;
    private CongeService congeService = new CongeService();
    private Map<Integer, Map<Integer, Map<Integer, Boolean>>> occupiedDates = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
        occupiedDates.clear();
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();
        Map<Integer, List<Conge>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);
        for (Map.Entry<Integer, List<Conge>> entry : calendarActivityMap.entrySet()) {
            int dayOfMonth = entry.getKey();
            List<Conge> activities = entry.getValue();

            // Check if the day is already occupied
            if (isDayOccupied(dayOfMonth)) {
                // Alert user about the conflict
                showAlert("Alert", "La case du calendrier est déjà occupée pour ce jour.");
                continue;
            }

            // Add the day to occupiedDates map
            occupiedDates.putIfAbsent(dateFocus.getYear(), new HashMap<>());
            occupiedDates.get(dateFocus.getYear()).putIfAbsent(dateFocus.getMonthValue(), new HashMap<>());
            occupiedDates.get(dateFocus.getYear()).get(dateFocus.getMonthValue()).put(dayOfMonth, true);

            // Your existing code to create calendar activity
        }
        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                        List<Conge> calendarActivities1 = calendarActivityMap.get(currentDate);

                        List<Conge> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }

        }
    }
    private boolean isDayOccupied(int dayOfMonth) {
        return occupiedDates.containsKey(dateFocus.getYear()) &&
                occupiedDates.get(dateFocus.getYear()).containsKey(dateFocus.getMonthValue()) &&
                occupiedDates.get(dateFocus.getYear()).get(dateFocus.getMonthValue()).containsKey(dayOfMonth);
    }


    private void createCalendarActivity(List<Conge> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getDescription() + ", " + calendarActivities.get(k).getDate_Debut());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, Map<Integer, Map<Integer, List<Conge>>>> createCalendarMap(List<Conge> calendarActivities) {
        Map<Integer, Map<Integer, Map<Integer, List<Conge>>>> calendarActivityMap = new HashMap<>();

        for (Conge activity : calendarActivities) {
            int activityYear = activity.getDate_Debut().toLocalDate().getYear();
            int activityMonth = activity.getDate_Debut().toLocalDate().getMonthValue();
            int activityDay = activity.getDate_Debut().toLocalDate().getDayOfMonth();

            if (!calendarActivityMap.containsKey(activityYear)) {
                calendarActivityMap.put(activityYear, new HashMap<>());
            }

            Map<Integer, Map<Integer, List<Conge>>> yearMap = calendarActivityMap.get(activityYear);

            if (!yearMap.containsKey(activityMonth)) {
                yearMap.put(activityMonth, new HashMap<>());
            }

            Map<Integer, List<Conge>> monthMap = yearMap.get(activityMonth);

            if (!monthMap.containsKey(activityDay)) {
                monthMap.put(activityDay, new ArrayList<>());
            }

            monthMap.get(activityDay).add(activity);
        }

        return calendarActivityMap;
    }


    private Map<Integer, List<Conge>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<Conge> calendarActivities = congeService.getAll(); // Récupérer tous les congés depuis la base de données
        Map<Integer, Map<Integer, Map<Integer, List<Conge>>>> calendarActivityMap = createCalendarMap(calendarActivities);
        int yearFocus = dateFocus.getYear();
        int monthFocus = dateFocus.getMonthValue();
        Map<Integer, List<Conge>> filteredActivities = new HashMap<>();

        if (calendarActivityMap.containsKey(yearFocus)) {
            Map<Integer, Map<Integer, List<Conge>>> yearMap = calendarActivityMap.get(yearFocus);

            if (yearMap.containsKey(monthFocus)) {
                Map<Integer, List<Conge>> monthMap = yearMap.get(monthFocus);

                for (Map.Entry<Integer, List<Conge>> entry : monthMap.entrySet()) {
                    int day = entry.getKey();
                    List<Conge> activities = entry.getValue();
                    // Filtrer les activités en fonction du statut
                    List<Conge> filteredActivitiesForDay = activities.stream()
                            .filter(Conge::isStatus)
                            .collect(Collectors.toList());
                    filteredActivities.put(day, filteredActivitiesForDay);
                }
            }
        }

        return filteredActivities;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

}}


