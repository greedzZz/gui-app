package client.utility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class CommandSender {
    private final DatagramSocket socket;
    private final SocketAddress address;

    public CommandSender(SocketAddress address, DatagramSocket socket) {
        this.socket = socket;
        this.address = address;
    }

    public void send(byte[] bytes) throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address);
        socket.send(packet);
    }
}
