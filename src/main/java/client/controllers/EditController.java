package client.controllers;

import client.utility.DialogManager;
import client.utility.Localizator;
import common.content.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.MessageFormat;

public class EditController {
    private Stage stage;
    private SpaceMarine spaceMarine;
    private Localizator localizator;

    @FXML
    private Label titleLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label healthLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label weaponLabel;
    @FXML
    private Label meleeLabel;
    @FXML
    private Label cNameLabel;
    @FXML
    private Label cWorldLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField healthField;
    @FXML
    private TextField cNameField;
    @FXML
    private TextField cWorldField;
    @FXML
    private TextField yField;

    @FXML
    private ChoiceBox<String> categoryBox;
    @FXML
    private ChoiceBox<String> weaponBox;
    @FXML
    private ChoiceBox<String> meleeBox;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {
        cancelButton.setOnAction(event -> stage.close());
        ObservableList<String> categories = FXCollections.observableArrayList("ASSAULT", "TACTICAL", "CHAPLAIN");
        categoryBox.setItems(categories);
        categoryBox.setStyle("-fx-font: 12px \"Sergoe UI\";");
        ObservableList<String> weapons = FXCollections.observableArrayList("BOLTGUN", "PLASMA_GUN", "COMBI_PLASMA_GUN", "GRENADE_LAUNCHER");
        weaponBox.setItems(weapons);
        weaponBox.setStyle("-fx-font: 12px \"Sergoe UI\";");
        ObservableList<String> melees = FXCollections.observableArrayList("CHAIN_SWORD", "CHAIN_AXE", "POWER_BLADE", "POWER_FIST");
        meleeBox.setItems(melees);
        meleeBox.setStyle("-fx-font: 12px \"Sergoe UI\";");
        healthField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}")) {
                healthField.setText(oldValue);
            } else if (newValue.length() == 10 && Long.parseLong(newValue) > Integer.MAX_VALUE || newValue.length() == 1 && Integer.parseInt(newValue) == 0) {
                healthField.setText(oldValue);
            }
        });
        xField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[-\\d]{0,11}")) {
                xField.setText(oldValue);
            } else {
                if (newValue.matches(".+-.*")) {
                    Platform.runLater(() -> xField.clear());
                } else if (newValue.length() == 10 && Long.parseLong(newValue) > Integer.MAX_VALUE || newValue.length() == 11 && Long.parseLong(newValue) < Integer.MIN_VALUE) {
                    xField.setText(oldValue);
                }
            }
        });
        yField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("[-\\d]{0,11}")) {
                yField.setText(oldValue);
            } else {
                if (newValue.matches(".+-.*")) {
                    Platform.runLater(() -> yField.clear());
                } else if (newValue.length() >= 3 && Long.parseLong(newValue) > 941 || newValue.length() == 11 && Long.parseLong(newValue) < Integer.MIN_VALUE) {
                    yField.setText(oldValue);
                }
            }
        });
    }

    @FXML
    void ok() {
        nameField.setText(nameField.getText().trim());
        cNameField.setText(cNameField.getText().trim());
        cWorldField.setText(cWorldField.getText().trim());
        if (nameField.getText().equals("") || xField.getText().equals("") || yField.getText().equals("") || cNameField.getText().equals("") || cWorldField.getText().equals("") || healthField.getText().equals("")) {
            MessageFormat format = new MessageFormat(localizator.getKeyString("EditError"));
            String[] params = {"Name", "X", "Y", "health", "chapter name", "chapter world"};
            DialogManager.createAlert(localizator.getKeyString("Error"), format.format(params), Alert.AlertType.ERROR, false);
        } else {
            AstartesCategory category = null;
            if (categoryBox.getValue() != null) category = AstartesCategory.valueOf(categoryBox.getValue());
            Weapon weapon = null;
            if (weaponBox.getValue() != null) weapon = Weapon.valueOf(weaponBox.getValue());
            MeleeWeapon meleeWeapon = null;
            if (meleeBox.getValue() != null) meleeWeapon = MeleeWeapon.valueOf(meleeBox.getValue());
            spaceMarine = new SpaceMarine(nameField.getText(), new Coordinates(Integer.parseInt(xField.getText()),
                    Integer.parseInt(yField.getText())), Integer.parseInt(healthField.getText()),
                    category, weapon, meleeWeapon, new Chapter(cNameField.getText(), cWorldField.getText()));
        }
        stage.close();
    }

    public SpaceMarine getSM() {
        SpaceMarine sm = spaceMarine;
        spaceMarine = null;
        return sm;
    }

    public void clear() {
        nameField.clear();
        xField.clear();
        yField.clear();
        healthField.clear();
        categoryBox.valueProperty().setValue(null);
        weaponBox.valueProperty().setValue(null);
        meleeBox.valueProperty().setValue(null);
        cNameField.clear();
        cWorldField.clear();
    }

    public void fill(SpaceMarine sm) {
        nameField.setText(sm.getName());
        xField.setText(Integer.toString(sm.getCoordinateX()));
        yField.setText(Integer.toString(sm.getCoordinateY()));
        healthField.setText(Integer.toString(sm.getHealth()));
        categoryBox.setValue(sm.getCategory().toString());
        weaponBox.setValue(sm.getWeaponType().toString());
        meleeBox.setValue(sm.getMeleeWeapon().toString());
        cNameField.setText(sm.getChapterName());
        cWorldField.setText(sm.getChapterWorld());
    }

    public void changeLang() {
        titleLabel.setText(localizator.getKeyString("EditTitle"));
        nameLabel.setText(localizator.getKeyString("Name"));
        healthLabel.setText(localizator.getKeyString("Health"));
        categoryLabel.setText(localizator.getKeyString("AstartesCategory"));
        weaponLabel.setText(localizator.getKeyString("Weapon"));
        meleeLabel.setText(localizator.getKeyString("MeleeWeapon"));
        cNameLabel.setText(localizator.getKeyString("ChapterName"));
        cWorldLabel.setText(localizator.getKeyString("ChapterWorld"));
        cancelButton.setText(localizator.getKeyString("CancelButton"));
    }

    public void show() {
        stage.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLocalizator(Localizator localizator) {
        this.localizator = localizator;
    }
}

