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

    private JTextField ip;
    private JTextField port;
    private JTextField pass;
    private JPanel messages;
    private JPanel panel;

    private Scanner in;
    private PrintWriter out;

    private PrintWriter log;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        if(Conf.LOG) {
            try {
                log = new PrintWriter(new BufferedWriter(new FileWriter(Conf.LOG_CLIENT_PATH, true)), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame();
        {
            panel = new JPanel(new CardLayout());
            {
                JPanel login = new JPanel();
                {
                    login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));

                    JPanel fields = new JPanel(new GridLayout(3, 2));
                    {
                        JLabel ipLabel = new JLabel("IP: ");
                        ip = new JTextField("localhost");

                        JLabel portLabel = new JLabel("Port: ");
                        port = new JTextField("" + Server.PORT);

                        fields.add(ipLabel);
                        fields.add(ip);
                        fields.add(portLabel);
                        fields.add(port);

                        if(Conf.AUTH) {
                            JLabel passLabel = new JLabel("Password: ");
                            pass = new JPasswordField();
                            fields.add(passLabel);
                            fields.add(pass);
                        }
                    }

                    JButton button = new JButton("Log in");
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            new Thread(Client.this).start();
                        }
                    });

                    login.add(fields);
                    login.add(button);
                }

                JPanel chat = new JPanel();
                {
                    chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

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
                        if(Conf.COLOR) width -= color.getPreferredSize().width;
                        text.setPreferredSize(new Dimension(width, UI_HEIGHT));

                        send.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                Message message;
                                if(Conf.COLOR) message = new Message(text.getText(), color.getSelectedColor());
                                else message = new Message(text.getText(), Conf.COLOR_DEFAULT);

                                if(Conf.CRYPTO) {
                                    message.encrypt(Conf.CRYPTO_FIRST_LAYER);
                                    message.encrypt(Conf.CRYPTO_SECOND_LAYER);
                                }

                                out.println(Server.CODE_MESSAGE);
                                out.println(message.toString());
                                text.setText("");
                            }
                        });

                        if(Conf.COLOR) {
                            input.add(color);
                        }

                        input.add(text);
                        input.add(send);
                    }

                    chat.add(messages);
                    chat.add(input);
                }

                panel.add(login);
                panel.add(chat);
            }

            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
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

        boolean verified = false;

        if(Conf.AUTH) {
            out.println(Server.CODE_LOGIN);
            out.println(pass.getText());
            verified = in.nextBoolean();
            in.nextLine();
        } else {
            verified = true;
        }

        if (verified) {
            ((CardLayout) panel.getLayout()).next(panel);

            while (in.hasNextLine()) {
                Message message = Message.fromString(in.nextLine());

                if(Conf.CRYPTO) {
                    message.decrypt(Conf.CRYPTO_SECOND_LAYER);
                    message.decrypt(Conf.CRYPTO_FIRST_LAYER);
                }

                if(Conf.LOG) log.println(message);

                JLabel label = new JLabel(message.getSender() + " " + message.getContent());
                label.setForeground(message.getColor());
                label.setBackground(Color.WHITE);
                messages.add(label);
                messages.validate();
                messages.repaint();
            }
        }

        in.close();
        out.close();
    }
}
