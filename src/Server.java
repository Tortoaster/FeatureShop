import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static class Clientje implements Runnable {

        private boolean running = true;

        private final Socket socket;
        private final Server server;

        private PrintWriter out;
        private BufferedReader in;

        private final Thread thread = new Thread(this);

        public Clientje(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            thread.start();
        }

        public void send(Message message) {
            out.write(message.toString());
        }

        public void close() throws IOException {
            server.clients.remove(this);

            running = false;

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            in.close();
            out.close();
            socket.close();
        }

        @Override
        public void run() {
            while (running) {
                try {
                    while (!in.ready()) {
                        if(!running) break;
                    }

                    Message message = Message.fromString(in.readLine());

                    message.decrypt(Cipher.REVERSE);
                    message.decrypt(Cipher.ROT13);

                    System.out.println(message);

                    message.encrypt(Cipher.ROT13);
                    message.encrypt(Cipher.REVERSE);

                    server.forward(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static final int PORT = 7777;

    private boolean running = true;

    private final List<Clientje> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started\n");

            while (running) {
                Socket socket = server.accept();
                clients.add(new Clientje(socket, this));
                System.out.println(socket.getRemoteSocketAddress() + " connected");
            }

            for (Clientje c : clients) {
                c.close();
            }

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forward(Message message) {
        for (Clientje c : clients) {
            c.send(message);
        }
    }
}
