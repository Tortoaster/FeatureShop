public enum Cipher {
    REVERSE() {
        @Override
        String encrypt(String plainText) {
            return new StringBuilder(plainText).reverse().toString();
        }

        @Override
        String decrypt(String cipherText) {
            return encrypt(cipherText);
        }
    },
    ROT13() {
        @Override
        String encrypt(String plainText) {
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
        String decrypt(String cipherText) {
            return encrypt(cipherText);
        }
    };

    abstract String encrypt(String plainText);
    abstract String decrypt(String cipherText);
}
