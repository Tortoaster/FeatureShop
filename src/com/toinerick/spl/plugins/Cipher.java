package com.toinerick.spl.plugins;

public enum Cipher {
    REVERSE() {
        @Override
        public String encrypt(String plainText) {
            return new StringBuilder(plainText).reverse().toString();
        }

        @Override
        public String decrypt(String cipherText) {
            return encrypt(cipherText);
        }
    },
    ROT13() {
        @Override
        public String encrypt(String plainText) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < plainText.length(); i++) {
                char c = plainText.charAt(i);

                if (c >= 'a' && c <= 'm') c += 13;
                else if (c >= 'A' && c <= 'M') c += 13;
                else if (c >= 'n' && c <= 'z') c -= 13;
                else if (c >= 'N' && c <= 'Z') c -= 13;

                builder.append(c);
            }

            return builder.toString();
        }

        @Override
        public String decrypt(String cipherText) {
            return encrypt(cipherText);
        }
    };

    public abstract String encrypt(String plainText);
    public abstract String decrypt(String cipherText);
}
