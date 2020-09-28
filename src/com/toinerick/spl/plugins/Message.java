package com.toinerick.spl.plugins;

import com.toinerick.spl.plugins.message.MessageFlag;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private String sender;
    private String content;

    private final List<MessageFlag> flags;

    public Message(String content, List<MessageFlag> flags) {
        this.content = content;
        this.flags = flags;
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

    void addFlag(MessageFlag flag) {
        flags.add(flag);
    }

    @Override
    public String toString() {
        return sender + ": " + content;
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
