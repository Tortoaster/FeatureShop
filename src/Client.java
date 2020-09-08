import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {

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
                        JTextField ip = new JTextField("localhost");

                        JLabel portLabel = new JLabel("Port: ");
                        JTextField port = new JTextField("" + Server.PORT);

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
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {

                        }
                    });

                    login.add(fields);
                    login.add(button);
                }

                JPanel chat = new JPanel();
                {
                    chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));

                    JTextArea messages = new JTextArea();

                    JPanel input = new JPanel();
                    {
                        JTextField text = new JTextField();
                        text.setToolTipText("Say something...");
                        text.setPreferredSize(new Dimension(400, 30));

                        JButton send = new JButton("Send");
                        messages.setPreferredSize(new Dimension(100, 30));

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
            Socket socket = new Socket("localhost", Server.PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.print("Received string: '");

            while (!in.ready()) {
            }
            System.out.println(in.readLine()); // Read one line and output it

            System.out.print("'\n");
            in.close();
        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }
}
