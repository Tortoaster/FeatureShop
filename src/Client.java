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
                log = new PrintWriter(new BufferedWriter(new FileWriter("logs/client/log.txt", true)), true);
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

                        JLabel passLabel = new JLabel("Password: ");
                        pass = new JPasswordField();

                        fields.add(ipLabel);
                        fields.add(ip);
                        fields.add(portLabel);
                        fields.add(port);
                        fields.add(passLabel);
                        fields.add(pass);
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

                    JPanel input = new JPanel();
                    {
                        JColorButton color = new JColorButton(Color.BLACK);
                        color.setPreferredSize(new Dimension(30, 30));

                        JTextField text = new JTextField();
                        text.setToolTipText("Say something...");
                        text.setPreferredSize(new Dimension(300, 30));

                        JButton send = new JButton("Send");
                        send.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                Message message = new Message(text.getText(), color.getSelectedColor());

                                message.encrypt(Cipher.ROT13);
                                message.encrypt(Cipher.REVERSE);

                                out.println(Server.CODE_MESSAGE);
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

        out.println(Server.CODE_LOGIN);
        out.println(pass.getText());

        if (in.nextBoolean()) {
            in.nextLine();
            ((CardLayout) panel.getLayout()).next(panel);

            while (in.hasNextLine()) {
                Message message = Message.fromString(in.nextLine());

                message.decrypt(Cipher.ROT13);
                message.decrypt(Cipher.REVERSE);

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
