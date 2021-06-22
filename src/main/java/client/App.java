package client;

import client.controllers.AuthController;
import client.controllers.MainController;
import common.content.SpaceMarine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.TreeMap;

public class App extends Application {
    private static InetSocketAddress address;
    private static DatagramSocket socket;
    private static CommandManager commandManager;
    private Stage mainStage;

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            if (port <= 0 || port >= 65535) throw new NumberFormatException();
            connect(port);
            launch(args);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | SocketException e) {
            System.out.println("The program arguments are incorrectly specified.\n" +
                    "Correct option: \"port\"\n" +
                    "The port number must be integer between 0 and 65535.");
            System.exit(-1);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader authLoader = new FXMLLoader(getClass().getResource("/auth.fxml"));
        Parent authRoot = authLoader.load();
        AuthController authController = authLoader.getController();
        authController.setApp(this);
        authController.setCommandManager(commandManager);
        mainStage.setScene(new Scene(authRoot));
        mainStage.setTitle("Space marines");
        mainStage.setResizable(false);
        mainStage.show();
    }

    @Override
    public void stop() {
        socket.close();
        System.exit(1);
    }

    public void startMain(String login, TreeMap<Integer, SpaceMarine> collection) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.getController();
        mainController.setLogin(login);
        mainController.setCollection(collection);
        mainController.setApp(this);
        mainController.setCommandManager(commandManager);
        mainStage.setScene(new Scene(mainRoot));
        mainStage.show();
    }

    public static void connect(int port) throws SocketException {
        address = new InetSocketAddress("localhost", port);
        socket = new DatagramSocket();
        commandManager = new CommandManager(address, socket);
    }
}
