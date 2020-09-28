package com.toinerick.spl.plugins.ui;

import com.toinerick.spl.plugins.Message;

public interface UI {
    String getMessage();

    String getIP();

    int getPort();

    String getPassword();

    void onLogin(boolean success);

    void onReceiveMessage(Message message);
}
