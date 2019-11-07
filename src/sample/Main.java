package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class Main extends Application {
DataModel dataModel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println();

        dataModel = new DataModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource( "EventsListView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Calendarius 2.0");
        primaryStage.setScene(new Scene(root, 1240, 860));
        primaryStage.show();
        EventsListViewController eventsListViewController = loader.getController();
        eventsListViewController.setDataModel(dataModel);
        CEvent event = new CEvent("Praca", LocalDateTime.now());
        event.setEndDateTime(LocalDateTime.now());
        event.setLocalization("POLSKA");
        event.setDescription("Pierwszy opis");

    }


    public static void main(String[] args) {
        launch(args);
    }
}
