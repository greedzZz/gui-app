package client.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class AlertManager {
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
}
