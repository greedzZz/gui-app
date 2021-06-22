package client.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label xLabel;

    @FXML
    private Label yLabel;

    @FXML
    private Label healthLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label weaponLabel;

    @FXML
    private Label meleeLabel;

    @FXML
    private Label cNameLable;

    @FXML
    private Label cWorldLable;

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
    private ChoiceBox<?> categoryBox;

    @FXML
    private ChoiceBox<?> weaponBox;

    @FXML
    private ChoiceBox<?> meleeBox;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {

    }
}

