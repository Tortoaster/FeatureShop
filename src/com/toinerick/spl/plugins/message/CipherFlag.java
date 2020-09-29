package com.toinerick.spl.plugins.message;

import com.toinerick.spl.plugins.Cipher;

public class CipherFlag implements MessageFlag {

    private final Cipher cipher;

    // It might feel strange to include the functionality for encrypting and decrypting a message inside that message,
    // as anyone that receives the message can then simply use this functionality to decrypt the message. However, this
    // is only because our current "encryption" schemes are completely stateless, i.e. they have a hard-coded "key".
    // More serious encryption schemes would have a key external to this flag, so that it won't be sent along with the
    // message.
    public CipherFlag(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    public String preprocess(String message) {
        return cipher.encrypt(message);
    }

    @Override
    public String postprocess(String message) {
        return cipher.decrypt(message);
    }
}
