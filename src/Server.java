import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    public static final int PORT = 7777, MAX_CLIENTS = 64;

    private final List<ServerClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started");

            while (clients.size() < MAX_CLIENTS) {
                Socket socket = server.accept();
                clients.add(new ServerClient(socket, this));
            }

            for (ServerClient c : clients) {
                c.close();
            }

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forward(Message message) {
        for (ServerClient c : clients) {
            c.send(message);
        }
    }

    private static class ServerClient implements Runnable {

        private final Socket socket;
        private final Server server;

        private PrintWriter out;
        private Scanner in;

        public ServerClient(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;

            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(this).start();
        }

        public void send(Message message) {
            out.println(message.toString());
        }

        public void close() throws IOException {
            server.clients.remove(this);

            in.close();
            out.close();
            socket.close();
        }

        @Override
        public void run() {
            System.out.println(socket.getRemoteSocketAddress() + " connected");

            while (in.hasNextLine()) {
                Message message = Message.fromString(in.nextLine());

                message.setSender(socket.getRemoteSocketAddress().toString());

                message.decrypt(Cipher.REVERSE);
                message.decrypt(Cipher.ROT13);

                System.out.println(message);

                message.encrypt(Cipher.ROT13);
                message.encrypt(Cipher.REVERSE);

                server.forward(message);
            }

            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(socket.getRemoteSocketAddress() + " disconnected");
            }
        }
    }
}
