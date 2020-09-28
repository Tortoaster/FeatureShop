package com.toinerick.spl.plugins;

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

    //#if Crypto
    public static final Cipher CRYPTO_FIRST_LAYER = Cipher.ROT13;
    public static final Cipher CRYPTO_SECOND_LAYER = Cipher.REVERSE;
    //#endif

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

    public void forward(Message message) {
        for (ServerClient c : clients) {
            c.send(message);
        }
    }

    private static class ServerClient implements Runnable {

        private boolean verified = true;

        private final Socket socket;
        private final Server server;

        private PrintWriter out;
        private Scanner in;

        public ServerClient(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;

            //#if Auth
//@            verified = false;
            //#endif

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
            while (in.hasNextLine()) {
                byte code = in.nextByte();
                in.nextLine();
                switch (code) {
                    case CODE_LOGIN:
                        String password = in.nextLine();

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
                        Message message = Message.fromString(in.nextLine());

                        if (verified) {
                            message.setSender(socket.getRemoteSocketAddress().toString());

                            //#if Log
//@                            //#if Crypto
//@                            message.decrypt(CRYPTO_SECOND_LAYER);
//@                            message.decrypt(CRYPTO_FIRST_LAYER);
//@                            //#endif
//@
//@
//@                            server.log.println(message);
//@
//@                            //#if Crypto
//@                            message.encrypt(CRYPTO_FIRST_LAYER);
//@                            message.encrypt(CRYPTO_SECOND_LAYER);
//@                            //#endif
                            //#endif

                            server.forward(message);
                        }

                        break;
                }
            }

            close();
        }
    }
}