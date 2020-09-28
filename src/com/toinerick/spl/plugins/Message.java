package com.toinerick.spl.plugins;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String content;

    public Message(String content) {
        this.content = content;
    }

    public void encrypt(Cipher cipher) {
        //#if Crypto
        content = cipher.encrypt(content);
        //#endif
    }

    public void decrypt(Cipher cipher) {
        //#if Crypto
        content = cipher.decrypt(content);
        //#endif
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }

    public static Message fromString(String string) {
        String[] split = string.split(": ", 2);
        Message message = new Message(split[1]);
        message.setSender(split[0]);
        return message;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
