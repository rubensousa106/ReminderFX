package org.example.reminderfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReminderController {

    @FXML
    private TableView<Reminder> reminderTableView;
    @FXML
    private TableColumn<Reminder, String> descColumn;
    @FXML
    private TableColumn<Reminder, String> dateColumn;
    @FXML
    private TableColumn<Reminder, String> timeColumn;
    @FXML
    private TableColumn<Reminder, String> priorityColumn;
    @FXML
    private TableColumn<Reminder, Void> editColumn;
    @FXML
    private TableColumn<Reminder, Void> deleteColumn;

    @FXML
    public void initialize() {
        // Data
        descColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority()));

        // Buttons
        addEditButtonToTable();
        addDeleteButtonToTable();

        // Update table view
        updateTableView();
    }

    private void addEditButtonToTable() {
        Callback<TableColumn<Reminder, Void>, TableCell<Reminder, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reminder, Void> call(final TableColumn<Reminder, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction(event -> {
                            Reminder reminder = getTableView().getItems().get(getIndex());

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-reminder.fxml"));
                                Parent root = loader.load();

                                EditReminderController controller = loader.getController();
                                controller.setReminder(reminder);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Edit Reminder");
                                stage.show();



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        editColumn.setCellFactory(cellFactory);
    }



    private void addDeleteButtonToTable() {
        Callback<TableColumn<Reminder, Void>, TableCell<Reminder, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reminder, Void> call(final TableColumn<Reminder, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Eliminar");

                    {
                        btn.setOnAction(event -> {
                            Reminder reminder = getTableView().getItems().get(getIndex());
                            // Lógica para remover o reminder
                            getTableView().getItems().remove(reminder);
                            System.out.println("Eliminado: " + reminder);
                            // Adicionalmente, remova do arquivo CSV se necessário
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        deleteColumn.setCellFactory(cellFactory);
    }

    public void onNewReminderButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-reminder.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("New Reminder");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTableView() {
        reminderTableView.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    String time = String.format("%02d:%02d", Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    reminderTableView.getItems().add(new Reminder(parts[0], parts[1], time, parts[4]));
                } else {
                    System.err.println("Linha inválida no arquivo CSV: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
