package com.toinerick.spl.plugins;

import com.toinerick.spl.plugins.ui.GUI;
import com.toinerick.spl.plugins.ui.TUI;
import com.toinerick.spl.plugins.ui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    //#if Log
//@    private static final String LOG_PATH = "logs/client/log.txt";
//@
//@    private PrintWriter log;
    //#endif

    private PrintWriter out;

    private final UI ui;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        //#if Log
//@        try {
//@            log = new PrintWriter(new BufferedWriter(new FileWriter(LOG_PATH, true)), true);
//@        } catch (IOException e) {
//@            e.printStackTrace();
//@        }
        //#endif

//        ui = new GUI(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        new Thread(Client.this).start();
//                    }
//                },
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        out.println(Server.CODE_MESSAGE);
//
//                        Message message = new Message(ui.getMessage());
//
//                        //#if Crypto
//                        message.encrypt(Server.CRYPTO_FIRST_LAYER);
//                        message.encrypt(Server.CRYPTO_SECOND_LAYER);
//                        //#endif
//
//                        out.println(message);
//                    }
//                });

        ui = new TUI(new TUI.OnStartListener() {
            @Override
            public void onStart() {
                new Thread(Client.this).start();
            }
        });
    }

    @Override
    public void run() {
        Scanner in;
        try {
            Socket socket = new Socket(ui.getIP(), ui.getPort());
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        boolean verified;

        //#if Auth
//@        out.println(Server.CODE_LOGIN);
//@        out.println(ui.getPassword());
//@        verified = in.nextBoolean();
//@        in.nextLine();
        //#else
        verified = true;
        //#endif

        if (verified) {
            ui.onLogin(true);

            while (in.hasNextLine()) {
                Message message = Message.fromString(in.nextLine());

                //#if Crypto
                message.decrypt(Server.CRYPTO_SECOND_LAYER);
                message.decrypt(Server.CRYPTO_FIRST_LAYER);
                //#endif

                //#if Log
//@                log.println(message);
                //#endif

                ui.onReceiveMessage(message);
            }
        } else {
            ui.onLogin(false);
        }

        in.close();
        out.close();
    }
}
