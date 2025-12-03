package training.jca;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HexFormat;

public class KeyPairMain {

    public static void main(String[] args) throws Exception {
        // Kulcspár generálás
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); //n, modulus mérete
        var pair = generator.generateKeyPair();

        // Privát kulcs
        PrivateKey privateKey = pair.getPrivate();

        var rsaPrivateKey = (java.security.interfaces.RSAPrivateKey) privateKey;

        System.out.println("Private key: " + rsaPrivateKey);
        System.out.println("Algoritmus: " + rsaPrivateKey.getAlgorithm());
        System.out.println("Format: " + rsaPrivateKey.getFormat());

        var encodedPrivateKey = privateKey.getEncoded();
        System.out.println(encodedPrivateKey);

        var hex = HexFormat.of();
        System.out.println(hex.formatHex(encodedPrivateKey));

        // Publikus kulcs
        PublicKey publicKey = pair.getPublic();
        System.out.println(publicKey.getAlgorithm());
        System.out.println(publicKey.getFormat());
    }
}
