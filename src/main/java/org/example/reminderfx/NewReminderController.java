package org.example.reminderfx;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewReminderController {

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
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30));
        descField.clear();
        dateField.setValue(null);
        hourSpinner.getValueFactory().setValue(12);
        minuteSpinner.getValueFactory().setValue(30);
        priorityCB.getSelectionModel().clearSelection();
    }


    /*
     *if the save button is clicked, the verifyInput method will be called
     * if the verifyInput method returns true, the writeInFile method will be called
     * if the verifyInput method returns false, the console will print "Invalid input"
     * and the verifyInput method will be called
     */
    public void onSaveButtonClick(ActionEvent actionEvent) {
        //if the verifyInput method returns true, we will write in the file
        if (Utils.verifyInput(descField, dateField, priorityCB)) {
            writeInFile();
            System.out.println("Reminder Saved");
        } else {
            System.out.println("Invalid input");
        }
    }

    /*
     *if the reset button is clicked, the initialize method will be called
     * and the fields will be cleared
     */
    public void onResetButtonClick(ActionEvent actionEvent) {
        initialize();
    }





    /*
     *write in the file the description, date, hour, minute and priority of the reminder
     *the data will be written in the file in the format: description;date;hour;minute;priority
     */
    public void writeInFile() {
        String filePath = "src/main/resources/data/data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true /* append */))) {
            String data = String.format("%s;%s;%02d;%02d;%s\n",
                    descField.getText(),
                    dateField.getValue().toString(),
                    hourSpinner.getValue(),
                    minuteSpinner.getValue(),
                    priorityCB.getValue());
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
