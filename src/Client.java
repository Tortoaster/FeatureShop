import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final JTextField ip;
    private final JTextField port;
    private final JTextArea messages;

    private Scanner in;
    private PrintWriter out;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        JFrame frame = new JFrame();
        {
            JPanel panel = new JPanel(new CardLayout());
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

                        JLabel passLabel = new JLabel("Password: ");
                        JPasswordField pass = new JPasswordField();

                        fields.add(ipLabel);
                        fields.add(ip);
                        fields.add(portLabel);
                        fields.add(port);
                        fields.add(passLabel);
                        fields.add(pass);
                    }

                    JButton button = new JButton("Log in");
                    Client client = this;
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            ((CardLayout) panel.getLayout()).next(panel);
                            new Thread(client).start();
                        }
                    });

                    login.add(fields);
                    login.add(button);
                }

                JPanel chat = new JPanel();
                {
                    chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

                    messages = new JTextArea();

                    JPanel input = new JPanel();
                    {
                        JTextField color = new JTextField("black");
                        color.setToolTipText("color");
                        color.setPreferredSize(new Dimension(100, 30));

                        JTextField text = new JTextField();
                        text.setToolTipText("Say something...");
                        text.setPreferredSize(new Dimension(300, 30));

                        JButton send = new JButton("Send");
                        send.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                Message message = new Message(text.getText(), color.getText());

                                message.encrypt(Cipher.ROT13);
                                message.encrypt(Cipher.REVERSE);

                                out.println(message.toString());
                                text.setText("");
                            }
                        });
                        messages.setPreferredSize(new Dimension(100, 30));

                        input.add(color);
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

        while (in.hasNextLine()) {
            Message message = Message.fromString(in.nextLine());

            message.decrypt(Cipher.ROT13);
            message.decrypt(Cipher.REVERSE);

            messages.setText(messages.getText() + "\n" + message.toString());
        }
    }
}
