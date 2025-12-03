package training.jca;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashMain {

    public static void main(String[] args) throws Exception {
        var input = "Hello, Hash!".getBytes(StandardCharsets.UTF_8);
        var digest = MessageDigest.getInstance("SHA-256");
        var hash = digest.digest(input);

        var hex = HexFormat.of();
        System.out.println(hex.formatHex(hash));
    }
}
