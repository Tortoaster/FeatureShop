package com.toinerick.spl.plugins.ui;

import com.toinerick.spl.plugins.Message;

import java.util.Scanner;

public class TUI implements UI {
    private final Scanner input = new Scanner(System.in);

    public interface OnStartListener {
        void onStart();
    }

    public TUI(OnStartListener listener) {
        listener.onStart();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getIP() {
        System.out.print("IP: ");
        return input.nextLine();
    }

    @Override
    public int getPort() {
        System.out.print("Port: ");
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public String getPassword() {
        System.out.print("Password: ");
        return input.nextLine();
    }

    @Override
    public void onLogin(boolean success) {
        if (success) {
            System.out.println("Logged in successfully");
            input.close();
        } else {
            System.out.println("Wrong password");
        }
    }

    @Override
    public void onReceiveMessage(Message message) {
        System.out.println(message.getSender() + " " + message.getContent());
    }
}
