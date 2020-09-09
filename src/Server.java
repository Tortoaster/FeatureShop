import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    public static final byte CODE_LOGIN = 1, CODE_MESSAGE = 2;
    public static final int PORT = 7777;

    private static final String PASSWORD = "12345";

    private final List<ServerClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                Socket socket = server.accept();
                clients.add(new ServerClient(socket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (ServerClient c : clients) {
                c.close();
            }
        }
    }

    public void forward(Message message) {
        for (ServerClient c : clients) {
            c.send(message);
        }
    }

    private static class ServerClient implements Runnable {

        private boolean verified;

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
            out.println(message);
        }

        public void close() {
            server.clients.remove(this);

            in.close();
            out.close();

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(socket.getRemoteSocketAddress() + " disconnected");
        }

        @Override
        public void run() {
            System.out.println(socket.getRemoteSocketAddress() + " connected");

            loop:
            while (in.hasNextLine()) {
                byte code = in.nextByte();
                in.nextLine();
                switch (code) {
                    case CODE_LOGIN:
                        String password = in.nextLine();

                        if (password.equals(PASSWORD)) {
                            verified = true;
                            out.println(true);
                        } else {
                            System.out.println("Wrong password: " + password);
                            out.println(false);
                            break loop;
                        }

                        break;
                    case CODE_MESSAGE:
                        Message message = Message.fromString(in.nextLine());

                        message.setSender(socket.getRemoteSocketAddress().toString());

                        if (verified) {
                            message.decrypt(Cipher.REVERSE);
                            message.decrypt(Cipher.ROT13);

                            System.out.println(message);

                            message.encrypt(Cipher.ROT13);
                            message.encrypt(Cipher.REVERSE);

                            server.forward(message);
                        }

                        break;
                }
            }

            close();
        }
    }
}
