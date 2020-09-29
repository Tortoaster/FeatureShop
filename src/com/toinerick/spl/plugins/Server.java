package com.toinerick.spl.plugins;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final byte CODE_LOGIN = 1, CODE_MESSAGE = 2;
    public static final int PORT = 7777;

    public static final Cipher CRYPTO_FIRST_LAYER = Cipher.ROT13;
    public static final Cipher CRYPTO_SECOND_LAYER = Cipher.REVERSE;

    //#if Auth
//@	private static final String AUTH_PASS = "12345";
    //#endif

    //#if Log
//@private static final String LOG_PATH = "logs/server/log.txt";
//@
//@private PrintWriter log;_
    //#endif

    private final List<ServerClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            ServerSocket server = new ServerSocket(PORT);

            //#if Log
//@            log = new PrintWriter(new BufferedWriter(new FileWriter(LOG_PATH, true)), true);
//@            log.println("Server started");
            //#endif

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

    public void forward(Message message) throws IOException {
        for (ServerClient c : clients) {
            c.send(message);
        }
    }

    private static class ServerClient implements Runnable {

        private boolean verified = true;

        private final Socket socket;
        private final Server server;

        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ServerClient(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;

            //#if Auth
//@            verified = false;
            //#endif

            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(this).start();
        }

        public void send(Message message) throws IOException {
            out.writeObject(message);
        }

        public void close() {
            server.clients.remove(this);

            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //#if Log
//@            server.log.println(socket.getRemoteSocketAddress() + " disconnected");
            //#endif
        }

        @Override
        public void run() {
            //#if Log
//@        	server.log.println(socket.getRemoteSocketAddress() + " connected");
            //#endif

            loop:
            while (true) {
                byte code;

                try {
                    code = in.readByte();
                } catch (IOException e) {
                    break;
                }

                switch (code) {
                    case CODE_LOGIN:
                        String password;

                        try {
                            password = (String) in.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            break loop;
                        }

                        //#if Auth
//@                        if (password.equals(AUTH_PASS)) {
//@                        	verified = true;
//@                        	out.println(true);
//@                        } else {
                        //#if Log
//@                        	server.log.println("Wrong password: " + password);
                        //#endif
//@                        	out.println(false);
//@	                        break loop;
//@                        }
                        //#endif

                        break;
                    case CODE_MESSAGE:
                        Message message;

                        try {
                            message = (Message) in.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            break loop;
                        }

                        if (verified) {
                            message.setSender(socket.getRemoteSocketAddress().toString());

                            //#if Log
//@                            server.log.println(message);
                            //#endif

                            try {
                                server.forward(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                }
            }

            close();
        }
    }
}
