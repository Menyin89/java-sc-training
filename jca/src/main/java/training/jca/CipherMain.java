package training.jca;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HexFormat;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;

public class CipherMain {

    public static void main(String[] args) throws Exception {
        var inout = "Hello, Cipher!".getBytes(StandardCharsets.UTF_8);

        // Kulcsgenerálás
        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        var key = keyGenerator.generateKey();

        // init vector (IV) - Üzenetenként más és más
        byte[] iv = new byte[12];
        var random = SecureRandom.getInstanceStrong();
        random.nextBytes(iv);

        // Titkosítás paramtérezeése
        var cipher = Cipher.getInstance("AES/GCM/NoPadding"); // GCM - üzeneteken belüli blokkok közötti eltérésért felelős
        var spec = new GCMParameterSpec(128, iv); // 128 bites az authentikációs tag
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        // Titkosítás
        var encrypted = cipher.doFinal(inout);

        var hex = HexFormat.of();
        System.out.println("Encrypted length: " + encrypted.length);
        System.out.println("Encrypted data: " + hex.formatHex(encrypted));

        // Visszafejtés
        var decipher = Cipher.getInstance("AES/GCM/NoPadding");
        decipher.init(Cipher.DECRYPT_MODE, key, spec);
        var decrypted = decipher.doFinal(encrypted);
        System.out.println("Decrypted data: " + new String(decrypted, StandardCharsets.UTF_8));
    }
}
