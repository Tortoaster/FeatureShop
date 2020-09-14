import java.awt.*;

public class Conf {
    public static final boolean COLOR = true;
    public static final Color COLOR_DEFAULT = Color.BLACK;

    public static final boolean LOG = false;
    public static final String LOG_SERVER_PATH = "logs/server/log.txt";
    public static final String LOG_CLIENT_PATH = "logs/client/log.txt";

    public static final boolean CRYPTO = true;
    public static final Cipher CRYPTO_FIRST_LAYER = Cipher.ROT13;
    public static final Cipher CRYPTO_SECOND_LAYER = Cipher.REVERSE;

    public static final boolean AUTH = false;
    public static final String AUTH_PASS = "12345";
}
