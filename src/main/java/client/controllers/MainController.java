package client.controllers;

import client.CommandManager;
import client.utility.DialogManager;
import client.utility.Localizator;
import client.utility.SpaceMarineDescriber;
import common.Reply;
import common.commands.CommandType;
import common.content.Chapter;
import common.content.SpaceMarine;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.*;

public class MainController {
    private CommandManager commandManager;
    private EditController editController;
    private Stage stage;
    private HashMap<String, Color> colorMap;
    private HashMap<Integer, Label> infoMap;
    private Random random;
    private Localizator localizator;
    private HashMap<String, Locale> localeMap;
    private SpaceMarineDescriber describer;
    private String login;

    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label userLabel;

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
    private TableColumn<SpaceMarine, Integer> healthColumn;
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
    private AnchorPane visualPane;

    @FXML
    void initialize() {
        describer = new SpaceMarineDescriber();
        colorMap = new HashMap<>();
        infoMap = new HashMap<>();
        random = new Random();
        localeMap = new HashMap<>();
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("English(CA)", new Locale("en", "CA"));
        localeMap.put("Slovák", new Locale("sk", "SK"));
        localeMap.put("Shqiptare", new Locale("sq", "AL"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            localizator.setBundle(ResourceBundle.getBundle("client.bundles.gui", localeMap.get(newValue)));
            changeLang();
        });
        ownerColumn.setCellValueFactory(sm -> sm.getValue().getOwnerProperty());
        idColumn.setCellValueFactory(sm -> sm.getValue().getIDProperty().asObject());
        nameColumn.setCellValueFactory(sm -> sm.getValue().getNameProperty());
        xColumn.setCellValueFactory(sm -> sm.getValue().getXProperty().asObject());
        yColumn.setCellValueFactory(sm -> sm.getValue().getYProperty().asObject());
        dateColumn.setCellValueFactory(sm -> new SimpleStringProperty(localizator.getDate(sm.getValue().getCreationDateValue())));
        healthColumn.setCellValueFactory(sm -> sm.getValue().getHealthProperty().asObject());
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
                    doubleClickUpdate(sm);
                }
            });
            return row;
        });
        visualTab.setOnSelectionChanged(event -> visualise(false));
    }

    @FXML
    void info() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.INFO, null, null, null);
        if (reply.isSuccessful()) {
            MessageFormat format = new MessageFormat(localizator.getKeyString("InfoResult"));
            DialogManager.createAlert(localizator.getKeyString("Info"), format.format(reply.getMessage().split("_")), Alert.AlertType.INFORMATION, true);
        } else
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("DataBaseError"), Alert.AlertType.ERROR, false);
    }

    @FXML
    void help() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.HELP, null, null, null);
        if (reply.isSuccessful()) {
            DialogManager.createAlert(localizator.getKeyString("Help"), localizator.getKeyString("HelpResult"), Alert.AlertType.INFORMATION, true);
        } else
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("DataBaseError"), Alert.AlertType.ERROR, false);
    }

    @FXML
    void groupCountingByCoordinates() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.GROUP_COUNTING_BY_COORDINATES, null, null, null);
        if (reply.isSuccessful()) {
            MessageFormat format = new MessageFormat(localizator.getKeyString("GroupCountingByCoordinatesResult"));
            DialogManager.createAlert(localizator.getKeyString("GroupCountingByCoordinates"), format.format(reply.getMessage().split(" ")), Alert.AlertType.INFORMATION, false);
        } else
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("DataBaseError"), Alert.AlertType.ERROR, false);
    }

    @FXML
    void clear() throws IOException {
        Reply reply = commandManager.processCommand(CommandType.CLEAR, null, null, null);
        if (reply.isSuccessful()) {
            setCollection(reply.getCollection());
            visualise(true);
            DialogManager.createAlert(localizator.getKeyString("Clear"), localizator.getKeyString("ClearSuc"), Alert.AlertType.INFORMATION, false);
        } else
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("ClearErr"), Alert.AlertType.ERROR, false);
    }

    @FXML
    void removeKey() throws IOException {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("RemoveKey"), localizator.getKeyString("DialogKey"));
        if (input.isPresent() && !input.get().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.REMOVE_KEY, input.get(), null, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                visualise(true);
                DialogManager.createAlert(localizator.getKeyString("RemoveKey"), localizator.getKeyString("RemoveKeySuc"), Alert.AlertType.INFORMATION, false);
            } else
                DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RemoveKeyErr"), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void removeGreaterKey() throws IOException {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("RemoveGreaterKey"), localizator.getKeyString("DialogKey"));
        if (input.isPresent() && !input.get().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.REMOVE_GREATER_KEY, input.get(), null, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                visualise(true);
                DialogManager.createAlert(localizator.getKeyString("RemoveGreaterKey"), localizator.getKeyString("RemoveGreaterKeySuc"), Alert.AlertType.INFORMATION, false);
            } else
                DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RemoveGreaterKeyErr"), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void filterStartsWithName() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(localizator.getKeyString("FilterStartsWithName"));
        dialog.setHeaderText(null);
        dialog.setContentText(localizator.getKeyString("DialogName"));
        Optional<String> input = dialog.showAndWait();
        if (input.isPresent() && !input.get().trim().equals("")) {
            Reply reply = commandManager.processCommand(CommandType.FILTER_STARTS_WITH_NAME, input.get().trim(), null, null);
            if (reply.isSuccessful()) {
                DialogManager.createAlert(localizator.getKeyString("FilterStartsWithName"), localizator.getKeyString("FilterStartsWithNameResult") + reply.getMessage(), Alert.AlertType.INFORMATION, true);
            } else
                DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("DataBaseError"), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void filterByChapter() throws IOException {
        TextInputDialog dialogName = new TextInputDialog();
        dialogName.setTitle(localizator.getKeyString("FilterByChapter"));
        dialogName.setHeaderText(null);
        dialogName.setContentText(localizator.getKeyString("DialogName"));
        Optional<String> name = dialogName.showAndWait();
        if (name.isPresent() && !name.get().trim().equals("")) {
            TextInputDialog dialogWorld = new TextInputDialog();
            dialogWorld.setTitle(localizator.getKeyString("FilterByChapter"));
            dialogWorld.setHeaderText(null);
            dialogWorld.setContentText(localizator.getKeyString("DialogWorld"));
            Optional<String> world = dialogWorld.showAndWait();
            if (world.isPresent() && !world.get().trim().equals("")) {
                Reply reply = commandManager.processCommand(CommandType.FILTER_BY_CHAPTER, null, null, new Chapter(name.get().trim(), world.get().trim()));
                if (reply.isSuccessful()) {
                    DialogManager.createAlert(localizator.getKeyString("FilterByChapter"), localizator.getKeyString("FilterByChapterResult") + reply.getMessage(), Alert.AlertType.INFORMATION, true);
                } else
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("DataBaseError"), Alert.AlertType.ERROR, false);
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
                visualise(true);
                DialogManager.createAlert(localizator.getKeyString("RemoveGreater"), localizator.getKeyString("RemoveGreaterSuc"), Alert.AlertType.INFORMATION, false);
            } else
                DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RemoveGreaterErr"), Alert.AlertType.ERROR, false);
        }
    }

    @FXML
    void replaceIfGreater() throws IOException {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("ReplaceIfGreater"), localizator.getKeyString("DialogKey"));
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.REPLACE_IF_GREATER, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    visualise(true);
                    DialogManager.createAlert(localizator.getKeyString("ReplaceIfGreater"), localizator.getKeyString("ReplaceIfGreaterSuc"), Alert.AlertType.INFORMATION, false);
                } else
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("ReplaceIfGreaterErr"), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void insert() throws IOException {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("Insert"), localizator.getKeyString("DialogKey"));
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.INSERT, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    visualise(true);
                    DialogManager.createAlert(localizator.getKeyString("Insert"), localizator.getKeyString("InsertSuc"), Alert.AlertType.INFORMATION, false);
                } else
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("InsertErr"), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void update() throws IOException {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("Update"), "ID:");
        if (input.isPresent() && !input.get().equals("")) {
            editController.clear();
            editController.show();
            SpaceMarine spaceMarine = editController.getSM();
            if (spaceMarine != null) {
                Reply reply = commandManager.processCommand(CommandType.UPDATE, input.get(), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    visualise(true);
                    DialogManager.createAlert(localizator.getKeyString("Update"), localizator.getKeyString("UpdateSuc"), Alert.AlertType.INFORMATION, false);
                } else
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("UpdateErr"), Alert.AlertType.ERROR, false);
            }
        }
    }

    @FXML
    void executeScript() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
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
                            visualise(true);
                        } else
                            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RefreshFailed"), Alert.AlertType.ERROR, false);
                    } catch (SocketTimeoutException e) {
                        DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RefreshLost"), Alert.AlertType.ERROR, false);
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

    public void visualise(boolean refresh) {
        visualPane.getChildren().clear();
        infoMap.clear();
        for (SpaceMarine sm : tableTable.getItems()) {
            if (!colorMap.containsKey(sm.getOwner())) {
                double r = random.nextDouble();
                double g = random.nextDouble();
                double b = random.nextDouble();
                if (Math.abs(r - g) + Math.abs(r - b) + Math.abs(b - g) < 0.6) {
                    r += (1 - r) / 1.4;
                    g += (1 - g) / 1.4;
                    b += (1 - b) / 1.4;
                }
                colorMap.put(sm.getOwner(), Color.color(r, g, b));
            }
            double size = Math.min(125, Math.max(75, sm.getHealth() * 2) / 2);
            Circle circle = new Circle(size, colorMap.get(sm.getOwner()));
            double x = Math.abs(sm.getCoordinateX());
            if (x >= 720) {
                while (x >= 720) {
                    x = x / 10;
                }
            }
            double y = Math.abs(sm.getCoordinateY());
            if (y >= 370) {
                while (y >= 370) {
                    y = y / 3;
                }
            }
            if (y < 100) y += 125;
            Text id = new Text(String.valueOf(sm.getID()));
            Label info = new Label(describer.describe(sm, localizator));
            info.setVisible(false);
            circle.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    doubleClickUpdate(sm);
                }
            });
            circle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                circle.setFill(colorMap.get(sm.getOwner()).brighter());
            });
            circle.setOnMouseExited(mouseEvent -> {
                id.setVisible(true);
                info.setVisible(false);
                circle.setFill(colorMap.get(sm.getOwner()));
            });
            id.setFont(Font.font("Segoe UI", size / 1.4));
            info.setPrefWidth(280);
            info.setPrefHeight(230);
            info.setStyle("-fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-width: 2");
            info.setFont(Font.font("Segoe UI", 15));
            visualPane.getChildren().add(circle);
            visualPane.getChildren().add(id);
            infoMap.put(sm.getID(), info);
            if (!refresh) {
                Path path = new Path();
                path.getElements().add(new MoveTo(-500, -150));
                path.getElements().add(new HLineTo(x));
                path.getElements().add(new VLineTo(y));
                id.translateXProperty().bind(circle.translateXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.translateYProperty().add(id.getLayoutBounds().getHeight() / 4));
                info.translateXProperty().bind(circle.translateXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.translateYProperty().subtract(120));
                PathTransition transition = new PathTransition();
                transition.setDuration(Duration.millis(750));
                transition.setNode(circle);
                transition.setPath(path);
                transition.setOrientation(PathTransition.OrientationType.NONE);
                transition.play();
            } else {
                circle.setCenterX(x);
                circle.setCenterY(y);
                info.translateXProperty().bind(circle.centerXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.centerYProperty().subtract(120));
                id.translateXProperty().bind(circle.centerXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.centerYProperty().add(id.getLayoutBounds().getHeight() / 4));
                FillTransition darker = new FillTransition(Duration.millis(750), circle);
                darker.setFromValue(colorMap.get(sm.getOwner()));
                darker.setToValue(colorMap.get(sm.getOwner()).darker().darker());
                FillTransition brighter = new FillTransition(Duration.millis(750), circle);
                brighter.setFromValue(colorMap.get(sm.getOwner()).darker().darker());
                brighter.setToValue(colorMap.get(sm.getOwner()));
                SequentialTransition transition = new SequentialTransition(darker, brighter);
                transition.play();
            }
        }
        for (Integer id : infoMap.keySet()) {
            visualPane.getChildren().add(infoMap.get(id));
        }
    }

    private void doubleClickUpdate(SpaceMarine sm) {
        editController.fill(sm);
        editController.show();
        SpaceMarine spaceMarine = editController.getSM();
        if (spaceMarine != null) {
            try {
                Reply reply = commandManager.processCommand(CommandType.UPDATE, Integer.toString(sm.getID()), spaceMarine, null);
                if (reply.isSuccessful()) {
                    setCollection(reply.getCollection());
                    visualise(true);
                    DialogManager.createAlert(localizator.getKeyString("Update"), localizator.getKeyString("UpdateSuc"), Alert.AlertType.INFORMATION, false);
                } else
                    DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("UpdateErr"), Alert.AlertType.ERROR, false);
            } catch (SocketTimeoutException ignored) {

            }
        }
    }

    public void changeLang() {
        userLabel.setText(localizator.getKeyString("UserLabel") + " " + login);
        clearButton.setText(localizator.getKeyString("Clear"));
        updateButton.setText(localizator.getKeyString("Update"));
        insertButton.setText(localizator.getKeyString("Insert"));
        helpButton.setText(localizator.getKeyString("Help"));
        infoButton.setText(localizator.getKeyString("Info"));
        removeKeyButton.setText(localizator.getKeyString("RemoveKey"));
        removeGreaterButton.setText(localizator.getKeyString("RemoveGreater"));
        removeGreaterKeyButton.setText(localizator.getKeyString("RemoveGreaterKey"));
        replaceButton.setText(localizator.getKeyString("ReplaceIfGreater"));
        chapterButton.setText(localizator.getKeyString("FilterByChapter"));
        nameButton.setText(localizator.getKeyString("FilterStartsWithName"));
        coordinatesButton.setText(localizator.getKeyString("GroupCountingByCoordinates"));
        scriptButton.setText(localizator.getKeyString("ExecuteScript"));
        tableTab.setText(localizator.getKeyString("TableTab"));
        visualTab.setText(localizator.getKeyString("VisualTab"));
        ownerColumn.setText(localizator.getKeyString("Owner"));
        nameColumn.setText(localizator.getKeyString("Name"));
        dateColumn.setText(localizator.getKeyString("CreationDate"));
        healthColumn.setText(localizator.getKeyString("Health"));
        categoryColumn.setText(localizator.getKeyString("AstartesCategory"));
        weaponColumn.setText(localizator.getKeyString("Weapon"));
        meleeColumn.setText(localizator.getKeyString("MeleeWeapon"));
        cNameColumn.setText(localizator.getKeyString("ChapterName"));
        cWorldColumn.setText(localizator.getKeyString("ChapterWorld"));
        editController.changeLang();
        try {
            Reply reply = commandManager.processCommand(CommandType.SHOW, null, null, null);
            if (reply.isSuccessful()) {
                setCollection(reply.getCollection());
                visualise(true);
            } else
                DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RefreshFailed"), Alert.AlertType.ERROR, false);
        } catch (SocketTimeoutException e) {
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("RefreshLost"), Alert.AlertType.ERROR, false);
        }
    }

    public void setLogin(String login) {
        this.login = login;
        userLabel.setText(localizator.getKeyString("UserLabel") + " " + login);
    }

    public void setCollection(TreeMap<Integer, SpaceMarine> collection) {
        tableTable.setItems(FXCollections.observableArrayList(collection.values()));
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        commandManager.setLocalizator(localizator);
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
        editController.changeLang();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLocalizator(Localizator localizator) {
        this.localizator = localizator;
    }

    public void setPrevLang(String prevLang) {
        languageComboBox.setValue(prevLang);
        localizator.setBundle(ResourceBundle.getBundle("client.bundles.gui", localeMap.get(prevLang)));
        changeLang();
    }
}
