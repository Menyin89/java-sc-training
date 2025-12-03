package training.jca;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.Arrays;
import java.util.HexFormat;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;

public class MacMain {

    public static void main(String[] args) throws  Exception {
        var input = "Hello, MAC!".getBytes(StandardCharsets.UTF_8);

        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        var key = keyGenerator.generateKey();

        var mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        var signature = mac.doFinal(input);

        var hex = HexFormat.of();
        System.out.println(hex.formatHex(signature));

        // Bob
        var bobSignature = mac.doFinal(input);
        System.out.println(hex.formatHex(bobSignature));

        // Arrays.equals nem használható kripto összehasonlításnál.
        // Mérhető, hogy hol tért vissza az eltéréssel, mert megy végig a karaktereken és ahol eltérés onnna visszajön.
        //var valid = Arrays.equals(signature, bobSignature);

        // Konstans idejű összehasonlítás kell - mindenképpen végig megy a teljes tömbön
        var valid = MessageDigest.isEqual(signature, bobSignature);

        System.out.println(valid);
    }
}
