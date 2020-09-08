import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    public static final int PORT = 7777;

    private boolean running;

    private List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }

    public void forward(Message message) throws IOException {
        for (Socket socket : clients) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.print(message.toString());
            out.close();
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started\n");

            running = true;

            while (running) {
                Socket socket = server.accept();
                clients.add(socket);
                System.out.println(socket.getRemoteSocketAddress() + " connected");
            }

            for (Socket socket : clients) {
                socket.close();
            }

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
