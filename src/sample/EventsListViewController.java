package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class EventsListViewController implements CEventDAO {
DataModel dataModel;
    Stage primaryStage;
    Stage eventView;
    FXMLLoader eventViewLoader;
    Parent eventViewRoot;
    EventViewController eventViewController;
    Scene eventViewScene;
    @FXML
    private TableView<CEvent> criticTableView, highTableView, mediumTableView, normalTableView;
    @FXML
    private MenuItem newEventMenuItem;



public void setDataModel(DataModel model){
    dataModel = model;
    refreshTableViews();

}
    ObservableList<CEvent> criticList = FXCollections.observableArrayList();
    ObservableList<CEvent> highList = FXCollections.observableArrayList();
    ObservableList<CEvent> mediumList = FXCollections.observableArrayList();
    ObservableList<CEvent> normalList = FXCollections.observableArrayList();

    ArrayList<TableView<CEvent>> priorityTableViewList = new ArrayList<>();

    @FXML
    public void onMouseClickedReaction(MouseEvent event)  {
        TableView selectedTable = (TableView) event.getSource();
        if (event.getClickCount() ==2 && event.getButton() == MouseButton.PRIMARY) {
            try {
                openEventWindow(getSelectionRow(event));

            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        if(event.getButton() == MouseButton.SECONDARY){
                if(selectedTable.getContextMenu() != null) return;
                ContextMenu contextMenu = new ContextMenu();
                selectedTable.setContextMenu(contextMenu);

                MenuItem editMenuItem = new MenuItem("Edytuj");
                editMenuItem.setOnAction((e) -> {
                    try {
                        openEventWindow(getSelectionRow(event));

                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                });
                MenuItem deleteMenuitem = new MenuItem("Usuń");
                deleteMenuitem.setOnAction((e) -> {
                    dataModel.deleteCEvent(getSelectionRow(event));
                    refreshTableViews();
                });
                contextMenu.getItems().setAll(editMenuItem, deleteMenuitem);
                contextMenu.show(selectedTable, event.getScreenX(), event.getScreenY());

        }

        priorityTableViewList.forEach(item->{
            if( !(item.equals(selectedTable))){
                item.getSelectionModel().clearSelection();
            }

        });

    }
    public CEvent getSelectionRow(Event event){
        Object selectedTable = event.getSource();
       return   priorityTableViewList.stream()
                                    .filter(item -> item.equals(selectedTable))
                                    .findFirst()
                                    .get().getSelectionModel()
                                    .getSelectedItem();
    }

    private void openEventWindow(CEvent event) throws IOException{

        primaryStage = (Stage) criticTableView.getScene().getWindow();
        eventView = new Stage();
        eventViewLoader = new FXMLLoader(getClass().getResource("fxml/EventView.fxml"));
        eventViewRoot = eventViewLoader.load();
        eventViewScene = new Scene(eventViewRoot,600,700);
        eventViewController = eventViewLoader.getController();
        eventViewController.setModel(dataModel);
        eventViewController.setEditEvent(event);
        eventViewController.initialize();
        eventView.initModality(Modality.WINDOW_MODAL);
        eventView.initOwner(primaryStage);
        eventView.setScene(eventViewScene);
        eventView.show();
        eventView.setResizable(false);
        eventView.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                refreshTableViews();
            }
        });
    }

    @FXML
    public void addNewEvent(ActionEvent event)throws IOException {
    openEventWindow(null);
    refreshTableViews();

    }

public void refreshTableViews(){
    priorityTableViewList.add(criticTableView);
    priorityTableViewList.add(highTableView);
    priorityTableViewList.add(mediumTableView);
    priorityTableViewList.add(normalTableView);


    criticList.setAll(dataModel.getListByPredicate(p-> p.getPriority()==Priority.CRITIC));
    highList.setAll(dataModel.getListByPredicate(p-> p.getPriority()==Priority.HIGH));
    mediumList.setAll(dataModel.getListByPredicate(p-> p.getPriority()==Priority.MEDIUM));
    normalList.setAll(dataModel.getListByPredicate(p-> p.getPriority()==Priority.NORMAL));

    criticTableView.setItems(criticList);
    highTableView.setItems(highList);
    mediumTableView.setItems(mediumList);
    normalTableView.setItems(normalList);
}


}
