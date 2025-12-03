package training.jca;

import java.util.HexFormat;
import javax.crypto.KeyGenerator;

public class KeysMain {

    public static void main(String[] args) throws Exception {
        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // Alapból erős randomot használ, de paraméterül át is adható.
        var secretKey = keyGenerator.generateKey();
        var hex = HexFormat.of();
        System.out.println(hex.formatHex(secretKey.getEncoded()));
    }
}
