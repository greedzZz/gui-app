package client.controllers;

import client.App;
import client.CommandManager;
import client.utility.AlertManager;
import common.Reply;
import common.commands.CommandType;
import common.content.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class MainController {
    private CommandManager commandManager;
    private App app;

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
    }

    @FXML
    void info() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.INFO, null);
        if (reply.isSuccessful()) {
            AlertManager.createAlert("Info", reply.getMessage(), Alert.AlertType.INFORMATION, false);
        } else AlertManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void help() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.HELP, null);
        if (reply.isSuccessful()) {
            AlertManager.createAlert("Help", reply.getMessage(), Alert.AlertType.INFORMATION, true);
        } else AlertManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
    }

    @FXML
    void groupCountingByCoordinates() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.GROUP_COUNTING_BY_COORDINATES, null);
        if (reply.isSuccessful()) {
            AlertManager.createAlert("Group counting by coordinates", reply.getMessage(), Alert.AlertType.INFORMATION, false);
        } else AlertManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
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

    public void setApp(App app) {
        this.app = app;
    }
}
