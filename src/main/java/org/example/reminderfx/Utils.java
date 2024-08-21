package org.example.reminderfx;

import javafx.scene.control.*;

import java.time.LocalDate;

public class Utils {



    public static boolean verifyInput(TextField descField, DatePicker dateField, ComboBox<String> priorityCB) {
        return !(descField.getText().isEmpty() || dateField.getValue() == null || priorityCB.getValue() == null);
    }

    /*
     *if the input of the dateField is different of date, the method will return false
     * if the date inserted is before the current date, the method will return false
     * if the input of the descField is empty, the method will return false
     * if the input of the priority is non selected, the method will return false
     */
    public static void initializeFields(TextField descField, DatePicker dateField, Spinner<Integer> hourSpinner, Spinner<Integer> minuteSpinner, ComboBox<String> priorityCB) {
        descField.clear();
        dateField.setValue(null);
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30));
        priorityCB.getSelectionModel().clearSelection();
    }

    public static void populateFields(Reminder reminder, TextField descField, DatePicker dateField, Spinner<Integer> hourSpinner, Spinner<Integer> minuteSpinner, ComboBox<String> priorityCB) {
        descField.setText(reminder.getDescription());
        dateField.setValue(LocalDate.parse(reminder.getDate()));

        String[] timeParts = reminder.getTime().split(":");
        hourSpinner.getValueFactory().setValue(Integer.parseInt(timeParts[0]));
        minuteSpinner.getValueFactory().setValue(Integer.parseInt(timeParts[1]));

        priorityCB.setValue(reminder.getPriority());
    }
}
