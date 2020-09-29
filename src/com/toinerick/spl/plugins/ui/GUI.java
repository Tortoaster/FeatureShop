package com.toinerick.spl.plugins.ui;

import com.toinerick.spl.plugins.JColorButton;
import com.toinerick.spl.plugins.Message;
import com.toinerick.spl.plugins.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI implements UI {

    private static final int WIDTH = 500, HEIGHT = 500, SIZE = 30;

    private static final Color COLOR_DEFAULT = Color.BLACK;

    private final JTextField ip;
    private final JTextField port;
    private final JTextField pass;
    private final JLabel status;
    private final JPanel messages;
    private final JTextField text;

    private final JFrame login = new JFrame();
    private final JFrame chat = new JFrame();

    public GUI(ActionListener loginListener, ActionListener sendListener) {
        {
            JPanel panel = new JPanel();
            {
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                JPanel serverPanel = new JPanel();
                {
                    ip = new JTextField("localhost", 15);
                    port = new JTextField("" + Server.PORT, 5);

                    serverPanel.add(new JLabel("IP: "));
                    serverPanel.add(ip);
                    serverPanel.add(new JLabel("Port: "));
                    serverPanel.add(port);
                }

                JPanel authPanel = new JPanel();
                {
                    pass = new JPasswordField(10);

                    //#if Auth
//@                authPanel.add(new JLabel("Password: "));
//@                authPanel.add(pass);
                    //#endif
                }

                JButton button = new JButton("Log in");
                button.addActionListener(loginListener);

                status = new JLabel("Log in");

                panel.add(status);
                panel.add(serverPanel);

                //#if Auth
//@            panel.add(authPanel);
                //#endif

                panel.add(button);
            }

            login.setContentPane(panel);
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            login.setLocationRelativeTo(null);
            login.pack();
            login.setVisible(true);
        }

        {
            JPanel panel = new JPanel();
            {
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                messages = new JPanel();
                messages.setPreferredSize(new Dimension(WIDTH, HEIGHT - SIZE));

                JPanel input = new JPanel();
                {
                    JColorButton color = new JColorButton(COLOR_DEFAULT);
                    color.setPreferredSize(new Dimension(SIZE, SIZE));

                    JButton send = new JButton("Send");
                    send.setPreferredSize(new Dimension(100, SIZE));

                    text = new JTextField();
                    text.setToolTipText("Say something...");
                    int width = WIDTH - send.getPreferredSize().width;

                    //#if Color
                    width -= color.getPreferredSize().width;
                    //#endif

                    text.setPreferredSize(new Dimension(width, SIZE));

                    send.addActionListener(sendListener);

                    //#if Color
                    input.add(color);
                    //#endif

                    input.add(text);
                    input.add(send);
                }

                panel.add(messages);
                panel.add(input);
            }

            chat.setContentPane(panel);
            chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chat.setLocationRelativeTo(null);
            chat.pack();
        }
    }

    @Override
    public String getMessage() {
        String message = text.getText();
        text.setText("");
        return message;
    }

    @Override
    public String getIP() {
        return ip.getText();
    }

    @Override
    public int getPort() {
        return Integer.parseInt(port.getText());
    }

    @Override
    public String getPassword() {
        return pass.getText();
    }

    @Override
    public void onLogin(boolean success) {
        if (success) {
            login.dispose();
            chat.setVisible(true);
        } else {
            status.setText("Wrong password");
            status.setForeground(Color.RED);
        }
    }

    @Override
    public void onReceiveMessage(Message message) {
        JLabel label = new JLabel(message.toString());
        label.setForeground(Color.MAGENTA);
        messages.add(label);
        messages.validate();
        messages.repaint();
    }
}
