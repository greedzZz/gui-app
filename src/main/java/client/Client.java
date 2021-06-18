package client;

import client.utility.ClientAsker;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class Client {
    private SocketAddress address;
    private DatagramSocket socket;
    private final String HOST = "localhost";

    public static void main(String[] args) {
        ClientAsker clientAsker = new ClientAsker();
        Client client = new Client();
        boolean tryingToConnect = true;
        while (tryingToConnect) {
            try {
                client.connect(Integer.parseInt(args[0]));
                client.run();
            } catch (IOException e) {
                System.out.println("Unfortunately, the server is currently unavailable.");
                if (clientAsker.ask("Try to send a command again?") <= 0) {
                    tryingToConnect = false;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("The program arguments are incorrectly specified.\n" +
                        "Correct option: \"port\"\n" +
                        "The port number must be integer, greater than 1024.");
                System.out.println("The program is finished.");
                System.exit(0);
            }
        }
        client.getSocket().close();
        System.out.println("The program is finished.");
    }

    public void connect(int port) throws SocketException {
        if (port < 1024) {
            throw new NumberFormatException();
        }
        address = new InetSocketAddress(HOST, port);
        socket = new DatagramSocket();
    }

    public void run() throws IOException {
        CommandManager commandManager = new CommandManager();
        commandManager.readInput(address, socket);
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }
}
