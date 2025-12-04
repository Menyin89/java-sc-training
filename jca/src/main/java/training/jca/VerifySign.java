package training.jca;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

public class VerifySign {

    public static void main(String[] args) throws Exception {
        // Dokumentum
        var data = "Hello World!".getBytes();

        var keyStore = KeyStore.getInstance("PKCS12");

        try (var input = Files.newInputStream(Path.of("training-keystore-just-certificate.p12")))  {
            keyStore.load(input, "secret".toCharArray());
        }

        var certificate = (X509Certificate) keyStore.getCertificate("training-cert");

        var signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(certificate.getPublicKey());

        signature.update(data);

        var signatureBytes = Files.readAllBytes(Path.of("signature.bin"));

        var valid = signature.verify(signatureBytes);

        System.out.println("Signature valid: " + valid);
    }
}
