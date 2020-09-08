public class Message {

    private final String sender;
    private String content;
    private final String color;

    public Message(String sender, String content, String color) {
        this.sender = sender;
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
        return new Message(split[0], split[2].substring(0, split[2].length() - 2), split[3]);
    }
}
