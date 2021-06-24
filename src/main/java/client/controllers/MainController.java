package client.controllers;

import client.CommandManager;
import client.utility.DialogManager;
import common.Reply;
import common.commands.CommandType;
import common.content.Chapter;
import common.content.SpaceMarine;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class MainController {
    private CommandManager commandManager;
    private EditController editController;
    private Stage stage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label languageLabel;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label userLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Button clearButton;

    @FXML
    private Button chapterButton;

    @FXML
    private Button nameButton;

    @FXML
    private Button coordinatesButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button insertButton;

    @FXML
    private Button removeGreaterButton;

    @FXML
    private Button removeGreaterKeyButton;

    @FXML
    private Button removeKeyButton;

    @FXML
    private Button replaceButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button scriptButton;

    @FXML
    private Tab tableTab;

    @FXML
    private TableView<SpaceMarine> tableTable;

    @FXML
    private TableColumn<SpaceMarine, String> ownerColumn;

    @FXML
    private TableColumn<SpaceMarine, Integer> idColumn;

    @FXML
    private TableColumn<SpaceMarine, String> nameColumn;

    @FXML
    private TableColumn<SpaceMarine, Integer> xColumn;

    @FXML
    private TableColumn<SpaceMarine, Integer> yColumn;

    @FXML
    private TableColumn<SpaceMarine, String> dateColumn;

    @FXML
    private TableColumn<SpaceMarine, String> healthColumn;

    @FXML
    private TableColumn<SpaceMarine, String> categoryColumn;

    @FXML
    private TableColumn<SpaceMarine, String> weaponColumn;

    @FXML
    private TableColumn<SpaceMarine, String> meleeColumn;

    @FXML
    private TableColumn<SpaceMarine, String> cNameColumn;

    @FXML
    private TableColumn<SpaceMarine, String> cWorldColumn;

    @FXML
    private Tab visualTab;

    @FXML
    void initialize() {
        ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Slovák", "Shqiptare", "English(CA)");
        languageComboBox.setItems(languages);
        languageComboBox.setValue("Русский");
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");
        ownerColumn.setCellValueFactory(sm -> sm.getValue().getOwnerProperty());
        idColumn.setCellValueFactory(sm -> sm.getValue().getIDProperty().asObject());
        nameColumn.setCellValueFactory(sm -> sm.getValue().getNameProperty());
        xColumn.setCellValueFactory(sm -> sm.getValue().getXProperty().asObject());
        yColumn.setCellValueFactory(sm -> sm.getValue().getYProperty().asObject());
        dateColumn.setCellValueFactory(sm -> sm.getValue().getDateProperty());
        healthColumn.setCellValueFactory(sm -> sm.getValue().getHealthProperty());
        categoryColumn.setCellValueFactory(sm -> sm.getValue().getCategoryProperty());
        weaponColumn.setCellValueFactory(sm -> sm.getValue().getWeaponProperty());
        meleeColumn.setCellValueFactory(sm -> sm.getValue().getMeleeProperty());
        cNameColumn.setCellValueFactory(sm -> sm.getValue().getChapterNameProperty());
        cWorldColumn.setCellValueFactory(sm -> sm.getValue().getChapterWorldProperty());
        tableTable.setRowFactory(tableView -> {
            TableRow<SpaceMarine> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    SpaceMarine sm = row.getItem();
                    editController.fill(sm);
                    editController.show();
                    SpaceMarine spaceMarine = editController.getSM();
                    if (spaceMarine != null) {
                        try {
                            Reply reply = commandManager.processCommand(CommandType.UPDATE, Integer.toString(sm.getID()), spaceMarine, null);
                            if (reply.isSuccessful()) {
                                setCollection(reply.getCollection());
                                DialogManager.createAlert("Update", reply.getMessage(), Alert.AlertType.INFORMATION, false);
                            } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
                        } catch (SocketTimeoutException ignored) {

                        }
                    }
                }
            });
            return row;
        });
    }

    @FXML
    void info() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.INFO, null, null, null);
        if (reply.isSuccessful()) {
            DialogManager.createAlert("Info", reply.getMessage(), Alert.AlertType.INFORMATION, false);
        } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void help() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.HELP, null, null, null);
        if (reply.isSuccessful()) {
            DialogManager.createAlert("Help", reply.getMessage(), Alert.AlertType.INFORMATION, true);
        } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void groupCountingByCoordinates() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.GROUP_COUNTING_BY_COORDINATES, null, null, null);
        if (reply.isSuccessful()) {
            DialogManager.createAlert("Group counting by coordinates", reply.getMessage(), Alert.AlertType.INFORMATION, false);
        } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void clear() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.CLEAR, null, null, null);
        if (reply.isSuccessful()) {
            setCollection(reply.getCollection());
            DialogManager.createAlert("Clear", reply.getMessage(), Alert.AlertType.INFORMATION, true);
        } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void removeKey() throws IOException {
        Optional<String> input = DialogManager.createDialog("Remove key", "Key:");
        if (input.isPresent() && !input.get().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.REMOVE_KEY, input.get(), null, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                DialogManager.createAlert("Remove key", reply.getMessage(), Alert.AlertType.INFORMATION, false);
            } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void removeGreaterKey() throws IOException {
        Optional<String> input = DialogManager.createDialog("Remove greater key", "Key:");
        if (input.isPresent() && !input.get().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.REMOVE_GREATER_KEY, input.get(), null, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                DialogManager.createAlert("Remove greater key", reply.getMessage(), Alert.AlertType.INFORMATION, true);
            } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void filterStartsWithName() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter starts with name");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");
        Optional<String> input = dialog.showAndWait();
        if (input.isPresent() && !input.get().trim().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.FILTER_STARTS_WITH_NAME, input.get().trim(), null, null);
            if (reply.isSuccessful()) {
                DialogManager.createAlert("Filter starts with name", reply.getMessage(), Alert.AlertType.INFORMATION, true);
            } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void filterByChapter() throws IOException {
        TextInputDialog dialogName = new TextInputDialog();
        dialogName.setTitle("Filter by chapter");
        dialogName.setHeaderText(null);
        dialogName.setContentText("Name:");
        Optional<String> name = dialogName.showAndWait();
        if (name.isPresent() && !name.get().trim().equals("")) {
            TextInputDialog dialogWorld = new TextInputDialog();
            dialogWorld.setTitle("Filter by chapter");
            dialogWorld.setHeaderText(null);
            dialogWorld.setContentText("World:");
            Optional<String> world = dialogWorld.showAndWait();
            if (world.isPresent() && !world.get().trim().equals("")) {
                Reply reply = commandManager.processCommand(CommandType.FILTER_BY_CHAPTER, null, null, new Chapter(name.get().trim(), world.get().trim()));
                if (reply.isSuccessful()) {
                    DialogManager.createAlert("Filter by chapter", reply.getMessage(), Alert.AlertType.INFORMATION, true);
                } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void removeGreater() throws IOException {
        editController.clear();
        editController.show();
        SpaceMarine spaceMarine = editController.getSM();
        if (spaceMarine != null) {
            Reply reply = commandManager.processCommand(CommandType.REMOVE_GREATER, null, spaceMarine, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                DialogManager.createAlert("Remove greater", reply.getMessage(), Alert.AlertType.INFORMATION, true);
            } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void replaceIfGreater() throws IOException {
        Optional<String> input = DialogManager.createDialog("Replace if greater", "Key:");
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.REPLACE_IF_GREATER, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    DialogManager.createAlert("Replace if greater", reply.getMessage(), Alert.AlertType.INFORMATION, false);
                } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void insert() throws IOException {
        Optional<String> input = DialogManager.createDialog("Insert", "Key:");
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.INSERT, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    DialogManager.createAlert("Insert", reply.getMessage(), Alert.AlertType.INFORMATION, false);
                } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void update() throws IOException {
        Optional<String> input = DialogManager.createDialog("Update", "ID:");
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.UPDATE, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    DialogManager.createAlert("Update", reply.getMessage(), Alert.AlertType.INFORMATION, false);
                } else DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void executeScript() throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            commandManager.processCommand(CommandType.EXECUTE_SCRIPT, file.getAbsolutePath(), null, null);
        }
    }

    public void refresh() {
        Thread refresher = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    try {
                        Reply reply = commandManager.processCommand(CommandType.SHOW, null, null, null);
                        if (reply.isSuccessful()) {
                            setCollection(reply.getCollection());
                        } else
                            DialogManager.createAlert("Error", "Failed to update the table due to server issues.", Alert.AlertType.ERROR, false);
                    } catch (SocketTimeoutException e) {
                        DialogManager.createAlert("Error", "Lost connection to server.", Alert.AlertType.ERROR, false);
                    }
                });
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
            }
        });
        refresher.start();
    }

    public void setLogin(String login) {
        loginLabel.setText(login);
    }

    public void setCollection(TreeMap<Integer, SpaceMarine> collection) {
        tableTable.setItems(FXCollections.observableArrayList(collection.values()));
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
