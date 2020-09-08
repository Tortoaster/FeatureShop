public class Message {

    private String sender;
    private String content;
    private final String color;

    public Message(String content, String color) {
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
        return sender + " (in " + color + "): " + content;
    }

    public static Message fromString(String string) {
        String[] split = string.split(" ", 4);
        Message message = new Message(split[3], split[2].substring(0, split[2].length() - 2));
        message.setSender(split[0]);
        return message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
