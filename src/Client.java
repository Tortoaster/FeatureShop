import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private static final int WIDTH = 500, HEIGHT = 500, UI_HEIGHT = 30;

    private final JTextField ip;
    private final JTextField port;
    private final JTextField pass;
    private final JLabel status;
    private final JPanel messages;

    private final JFrame login = new JFrame();

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
                if (Conf.AUTH) {
                    authPanel.add(new JLabel("Password: "));
                    authPanel.add(pass);
                }
            }

            JButton button = new JButton("Log in");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new Thread(Client.this).start();
                }
            });

            status = new JLabel("Log in");

            panel.add(status);
            panel.add(serverPanel);
            if (Conf.AUTH) panel.add(authPanel);
            panel.add(button);
        }

        login.setContentPane(panel);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.pack();
        login.setVisible(true);
    }

    private final JFrame chat = new JFrame();

    {
        JPanel panel = new JPanel();
        {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            messages = new JPanel();
            messages.setPreferredSize(new Dimension(WIDTH, HEIGHT - UI_HEIGHT));

            JPanel input = new JPanel();
            {
                JColorButton color = new JColorButton(Conf.COLOR_DEFAULT);
                color.setPreferredSize(new Dimension(UI_HEIGHT, UI_HEIGHT));

                JButton send = new JButton("Send");
                send.setPreferredSize(new Dimension(100, UI_HEIGHT));

                JTextField text = new JTextField();
                text.setToolTipText("Say something...");
                int width = WIDTH - send.getPreferredSize().width;
                if (Conf.COLOR) width -= color.getPreferredSize().width;
                text.setPreferredSize(new Dimension(width, UI_HEIGHT));

                send.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Message message;
                        if (Conf.COLOR) message = new Message(text.getText(), color.getSelectedColor());
                        else message = new Message(text.getText(), Conf.COLOR_DEFAULT);

                        if (Conf.CRYPTO) {
                            message.encrypt(Conf.CRYPTO_FIRST_LAYER);
                            message.encrypt(Conf.CRYPTO_SECOND_LAYER);
                        }

                        out.println(Server.CODE_MESSAGE);
                        out.println(message.toString());
                        text.setText("");
                    }
                });

                if (Conf.COLOR) {
                    input.add(color);
                }

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

    private Scanner in;
    private PrintWriter out;

    private PrintWriter log;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        if (Conf.LOG) {
            try {
                log = new PrintWriter(new BufferedWriter(new FileWriter(Conf.LOG_CLIENT_PATH, true)), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip.getText(), Integer.parseInt(port.getText()));
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean verified;

        if (Conf.AUTH) {
            out.println(Server.CODE_LOGIN);
            out.println(pass.getText());
            verified = in.nextBoolean();
            in.nextLine();
        } else {
            verified = true;
        }

        if (verified) {
            login.dispose();
            chat.setVisible(true);

            while (in.hasNextLine()) {
                Message message = Message.fromString(in.nextLine());

                if (Conf.CRYPTO) {
                    message.decrypt(Conf.CRYPTO_SECOND_LAYER);
                    message.decrypt(Conf.CRYPTO_FIRST_LAYER);
                }

                if (Conf.LOG) log.println(message);

                JLabel label = new JLabel(message.getSender() + " " + message.getContent());
                label.setForeground(message.getColor());
                messages.add(label);
                messages.validate();
                messages.repaint();
            }
        } else {
            status.setText("Wrong password");
            status.setForeground(Color.RED);
        }

        in.close();
        out.close();
    }
}
