package client.controllers;

import client.App;
import client.CommandManager;
import client.utility.DialogManager;
import common.Reply;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController {
    private CommandManager commandManager;
    private App app;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button okButton;

    @FXML
    private CheckBox signUpButton;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label languageLabel;

    @FXML
    void initialize() {
        ObservableList<String> languages = FXCollections.observableArrayList("Русский", "Slovák", "Shqiptare", "English(CA)");
        languageComboBox.setItems(languages);
        languageComboBox.setValue("Русский");
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");
        loginField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\w{0,16}")) {
                loginField.setText(oldValue);
            }
        });
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\S*")) {
                passwordField.setText(oldValue);
            }
        });
    }

    @FXML
    void ok() throws IOException {
        if (loginField.getText().length() > 0 && passwordField.getText().length() > 0) {
            Reply reply = commandManager.authorize(signUpButton.isSelected(), loginField.getText(), passwordField.getText());
            if (reply.isSuccessful()) {
                if (signUpButton.isSelected()) {
                    DialogManager.createAlert("Info", "Registration completed successfully!", Alert.AlertType.INFORMATION, false);
                }
                app.startMain(loginField.getText(), reply.getCollection());
            } else {
                DialogManager.createAlert("Error", reply.getMessage(), Alert.AlertType.ERROR, false);
            }
        } else {
            DialogManager.createAlert("Error", "Login/password cannot be empty word.", Alert.AlertType.ERROR, false);
        }
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
