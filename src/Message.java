import java.awt.*;

public class Message {

    private String sender;
    private boolean isLogin = false;
    private String content;
    private Color color = Color.BLACK;

    public Message(boolean isLogin, String password) {
        this.isLogin = isLogin;
        this.content = password;
    }

    public Message(String content, Color color) {
        this.content = content;
        this.color = color;
    }

    public void encrypt(Cipher cipher) {
        content = cipher.encrypt(content);
    }

    public void decrypt(Cipher cipher) {
        content = cipher.decrypt(content);
    }

    @Override
    public String toString() {
        return color.getRGB() + " " + sender + ": " + content;
    }

    public static Message fromString(String string) {
        String[] split = string.split(" ", 4);
        Message message = new Message(split[2], new Color(Integer.parseInt(split[0])));
        message.setSender(split[1]);
        return message;
    }

    public boolean getIsLogin() { return isLogin; }

    public String getContent() { return content; }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
