public class Message {

    private String content;
    private String color;

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
        return "(" + color + ") " + content;
    }
}
