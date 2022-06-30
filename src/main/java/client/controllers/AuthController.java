package client.controllers;

import client.App;
import client.CommandManager;
import client.utility.DialogManager;
import client.utility.Localizator;
import common.Reply;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthController {
    private CommandManager commandManager;
    private App app;
    private Localizator localizator;
    private HashMap<String, Locale> localeMap;

    @FXML
    private Label titleLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox signUpButton;
    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    void initialize() {
        localeMap = new HashMap<>();
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("English(CA)", new Locale("en", "CA"));
        localeMap.put("Slovák", new Locale("sk", "SK"));
        localeMap.put("Shqiptare", new Locale("sq", "AL"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        languageComboBox.setValue("Русский");
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            localizator.setBundle(ResourceBundle.getBundle("client.bundles.gui", localeMap.get(newValue)));
            changeLang();
        });
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
                    DialogManager.createAlert(localizator.getKeyString("Info"), localizator.getKeyString("RegisterSuccess"), Alert.AlertType.INFORMATION, false);
                }
                app.startMain(loginField.getText(), languageComboBox.getValue(), reply.getCollection());
            } else {
                if (signUpButton.isSelected()) {
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("SignUpError"), Alert.AlertType.ERROR, false);
                } else {
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("SignInError"), Alert.AlertType.ERROR, false);
                }
            }
        } else {
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("SignInEmpty"), Alert.AlertType.ERROR, false);
        }
    }

    public void changeLang() {
        titleLabel.setText(localizator.getKeyString("AuthTitle"));
        loginField.setPromptText(localizator.getKeyString("LoginField"));
        passwordField.setPromptText(localizator.getKeyString("PasswordField"));
        signUpButton.setText(localizator.getKeyString("SignUpButton"));
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setLocalizator(Localizator localizator) {
        this.localizator = localizator;
    }
}
