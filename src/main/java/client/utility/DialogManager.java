package client.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class DialogManager {
    public static void createAlert(String title, String content, Alert.AlertType type, boolean scrollable) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        if (!scrollable) alert.setContentText(content);
        else {
            TextArea area = new TextArea(content);
            area.setPrefWidth(620);
            area.setPrefHeight(320);
            alert.getDialogPane().setContent(area);
        }
        alert.setResizable(false);
        alert.showAndWait();
    }

    public static Optional<String> createDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        dialog.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                dialog.getEditor().setText(oldValue);
            }
        });
        return dialog.showAndWait();
    }
}
