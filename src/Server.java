import java.io.IOException;
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

    public void forward() {
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        System.out.print("Sending string: '" + data + "'\n");
//
//        out.print(data);
//        out.close();
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
