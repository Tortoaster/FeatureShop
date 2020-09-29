package com.toinerick.spl.plugins;

import com.toinerick.spl.plugins.message.MessageFlag;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private String sender;
    private String content;

    private final List<MessageFlag> flags;

    public Message(String content, List<MessageFlag> flags) {
        this.flags = flags;

        for(MessageFlag f: flags) {
            content = f.preprocess(content);
        }

        this.content = content;
    }

    @Override
    public String toString() {
        String processed = content;

        for(int i = flags.size() - 1; i >= 0; i--) {
            processed = flags.get(i).postprocess(processed);
        }

        return sender + ": " + processed;
    }

    public void setContent(String content) {
        this.content = content;
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
