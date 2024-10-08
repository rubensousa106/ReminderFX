package org.example.reminderfx;

import javafx.scene.control.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Utils {

    private final static String  filePath = "src/main/resources/data/data.csv";

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

    /*
    * this method will generate a hash using the SHA-256 algorithm
    * the hash will be generated using the data inserted
    * the method will return the hash generated
    * if the algorithm is not found, the method will throw a runtime exception
     */
    public static String generateSHA256Hash(String data) {
        try {
            System.out.println("Generating hash for data: " + data);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * this method will convert a byte array into a hexadecimal string
    * the method will return the hexadecimal string
    * the method will receive a byte array as a parameter
    * the method will iterate over the byte array and convert each byte into a hexadecimal string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    /*
    * this method will delete a reminder from the CSV file
     */
    public static void deleteReminderByIndex(int index) {
        File inputFile = new File(filePath);
        File tempFile = new File("src/main/resources/data/temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            int currentIndex = 0;

            while ((currentLine = reader.readLine()) != null) {
                if (currentIndex != index) {
                    writer.write(currentLine + System.lineSeparator());
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deleta o arquivo original
        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        // Renomeia o arquivo temporário para o nome do arquivo original
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }
    }


}
