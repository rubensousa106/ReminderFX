package org.example.reminderfx;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditReminderController {
    private Reminder reminder;

    @FXML
    private TextField descField;
    @FXML
    private DatePicker dateField;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private ComboBox<String> priorityCB;

    /*
     *initialize the hourSpinner with a value 12
     *initialize the minuteSpinner with a value 30
     *clear the descField
     *clear the dateField
     *set the hourSpinner with the value 12
     *set the minuteSpinner with the value 30
     *clear the priorityCB
     */
    public void initialize() {
        Utils.initializeFields(descField, dateField, hourSpinner, minuteSpinner, priorityCB);
    }

    /*

     */
    public void onSaveButtonClick(ActionEvent actionEvent) {
        NewReminderController newReminderController = new NewReminderController();
        //if the verifyInput method returns true, we will write in the file
        if (Utils.verifyInput(descField, dateField, priorityCB)) {
            System.out.println("Reminder Edited");
            newReminderController.writeInFile();

            // Update Reminder
            // TODO: Update reminder in the file and in the table view . refresh it
            // Close Window
            Stage stage = (Stage) descField.getScene().getWindow();
            stage.close();


        } else {
            System.out.println("Invalid input");
        }
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;

        // Populate Fields
        descField.setText(reminder.getDescription());
        dateField.setValue(LocalDate.parse(reminder.getDate()));
        hourSpinner.setValueFactory(hourSpinner.getValueFactory());
        minuteSpinner.setValueFactory(minuteSpinner.getValueFactory());
        priorityCB.setValue(reminder.getPriority());
    }

    /*
     *if the reset button is clicked, the initialize method will be called
     */
    public void onResetButtonClick(ActionEvent actionEvent) {
        initialize();
    }


}
