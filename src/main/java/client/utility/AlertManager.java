package client.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class AlertManager {
    public static void createAlert(String title, String content, Alert.AlertType type, boolean scrollable) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        if (!scrollable) alert.setContentText(content);
        else alert.getDialogPane().setContent(new TextArea(content));
        alert.setResizable(false);
        alert.showAndWait();
    }
}
