package com.toinerick.spl.plugins.message;

import com.toinerick.spl.plugins.Cipher;

public class CipherFlag implements MessageFlag {

    private final Cipher cipher;

    public CipherFlag(Cipher cipher) {
        this.cipher = cipher;
    }
}
