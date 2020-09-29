package com.toinerick.spl.plugins;

import com.toinerick.spl.plugins.message.CipherFlag;
import com.toinerick.spl.plugins.message.MessageFlag;
import com.toinerick.spl.plugins.ui.GUI;
import com.toinerick.spl.plugins.ui.TUI;
import com.toinerick.spl.plugins.ui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable {

    //#if Log
//@    private static final String LOG_PATH = "logs/client/log.txt";
//@
//@    private PrintWriter log;
    //#endif

    private ObjectOutputStream out;

    private final UI ui;

    private final List<MessageFlag> defaultFlags = new ArrayList<>();

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        defaultFlags.add(new CipherFlag(Server.CRYPTO_FIRST_LAYER));
        defaultFlags.add(new CipherFlag(Server.CRYPTO_SECOND_LAYER));

        //#if Log
//@        try {
//@            log = new PrintWriter(new BufferedWriter(new FileWriter(LOG_PATH, true)), true);
//@        } catch (IOException e) {
//@            e.printStackTrace();
//@        }
        //#endif

        ui = new GUI(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new Thread(Client.this).start();
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Message message = new Message(ui.getMessage(), defaultFlags);

                        try {
                            out.writeByte(Server.CODE_MESSAGE);
                            out.writeObject(message);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

//        ui = new TUI(new TUI.OnStartListener() {
//            @Override
//            public void onStart() {
//                new Thread(Client.this).start();
//            }
//        });
    }

    @Override
    public void run() {
        ObjectInputStream in;

        try {
            Socket socket = new Socket(ui.getIP(), ui.getPort());
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        boolean verified;

        //#if Auth
//@        out.writeByte(Server.CODE_LOGIN);
//@        out.writeObject(ui.getPassword());
//@        out.flush();
//@        verified = in.readBoolean();
//@        in.nextLine();
        //#else
        verified = true;
        //#endif

        if (verified) {
            ui.onLogin(true);

            while (true) {
                Message message;

                try {
                    message = (Message) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }

                //#if Log
//@                log.println(message);
                //#endif

                ui.onReceiveMessage(message);
            }
        } else {
            ui.onLogin(false);
        }

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
