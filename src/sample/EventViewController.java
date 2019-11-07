package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventViewController  {
    ObservableList<Integer> hours = FXCollections.observableArrayList();
    ObservableList<Integer> minutes = FXCollections.observableArrayList();
    @FXML
    TextField nameTextField, localizationTextField;
    @FXML
    DatePicker startDatePicker, endDatePicker;
    @FXML
    ComboBox<Integer> startHourPicker, startMinutesPicker, endHourPicker, endMinutesPicker;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    Button addButton, cancelButton;
    @FXML
    Label nameErrorLabel, startDateTimeErrorLabel;
    DataModel dataModel;
    CEvent buforEvent;


    public void setModel(DataModel m){
         dataModel = m;
    }
    public void setEditEvent(CEvent e){
         buforEvent = e;
    }



    public void initialize() {
        Optional<CEvent> event = Optional.ofNullable(buforEvent);
        System.err.println(buforEvent);
        System.err.println(event);
        hours.setAll(IntStream.range(0,24).boxed().collect(Collectors.toList()));
        minutes.setAll(IntStream.range(0,60).boxed().collect(Collectors.toList()));
        startHourPicker.setItems(hours);
        startMinutesPicker.setItems(minutes);
        endHourPicker.setItems(hours);
        endMinutesPicker.setItems(minutes);
        event.ifPresent(editEvent->{
            nameTextField.setText(editEvent.getName());
            startDatePicker.setValue(editEvent.getStartDateTime().toLocalDate());
            endDatePicker.setValue(editEvent.getEndDateTime().toLocalDate());
            startHourPicker.setValue(editEvent.getStartDateTime().getHour());
            startMinutesPicker.setValue(editEvent.getStartDateTime().getMinute());
            endHourPicker.setValue(editEvent.getEndDateTime().getHour());
            endMinutesPicker.setValue(editEvent.getEndDateTime().getMinute());
            localizationTextField.setText(editEvent.getLocalization());
            descriptionTextArea.setText(editEvent.getDescription());

        });


    }
@FXML
private void saveEvent(){

    boolean editWindow;
    boolean exceptionFlag = Boolean.FALSE;

    if(buforEvent == null){
        editWindow = false;
        buforEvent = new CEvent(nameTextField.getText(), LocalDateTime.of(startDatePicker.getValue(),LocalTime.of(startHourPicker.getValue(),startMinutesPicker.getValue())));
    }
    else{
        editWindow = true;
        try {
            buforEvent.setName(nameTextField.getText());
            nameErrorLabel.setVisible(false);
            nameErrorLabel.setMaxHeight(0);
            nameErrorLabel.setMinHeight(0);
        } catch (NameNullPointerException ex) {
            nameErrorLabel.setText(ex.getMessage());
            nameErrorLabel.setMaxHeight(30);
            nameErrorLabel.setMinHeight(30);
            nameErrorLabel.setVisible(true);
                exceptionFlag = true;
            }
        try {
            if (startDatePicker.getEditor().getText().equals("")) buforEvent.setStartDateTime(null);
                buforEvent.setStartDateTime(LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(startHourPicker.getValue(), startMinutesPicker.getValue())));
                startDateTimeErrorLabel.setVisible(false);
                startDateTimeErrorLabel.setMaxHeight(0);
                startDateTimeErrorLabel.setMinHeight(0);
        } catch(StartDateTimeNullPointerException ex){
                startDateTimeErrorLabel.setText(ex.getMessage());
                startDateTimeErrorLabel.setMaxHeight(30);
                startDateTimeErrorLabel.setMinHeight(30);
                startDateTimeErrorLabel.setVisible(true);
                exceptionFlag = true;
                }
        }
    if(exceptionFlag) return;
    if (endDatePicker.getEditor().getText().equals("")) buforEvent.setEndDateTime(null);
    else  buforEvent.setEndDateTime(LocalDateTime.of(endDatePicker.getValue(),LocalTime.of(endHourPicker.getValue(),endMinutesPicker.getValue())));
        buforEvent.setDescription(descriptionTextArea.getText());
        buforEvent.setLocalization(localizationTextField.getText());
        buforEvent.choosePriority();
        if(editWindow == false) {
            dataModel.addCEvent(buforEvent);
        }
        else{
            dataModel.editCEvent(buforEvent);
        }
         Stage window = (Stage) cancelButton.getScene().getWindow();
         window.close();
}


    public void cancelEventView(ActionEvent actionEvent) {
        Stage window = (Stage) cancelButton.getScene().getWindow();
        window.close();
    }
}
