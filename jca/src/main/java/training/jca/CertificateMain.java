package training.jca;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Date;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateMain {

    public static void main(String[] args) throws Exception {
        // Bouncy Castle felvétele providerként
        Security.addProvider(new BouncyCastleProvider());

        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        var pair = generator.generateKeyPair();

        // Leíró információk előállítása
        // Név az X.500 szabvány szerint
        var dnName = new X500Name("CN=TrainingSelfSignedCertificate");
        var serialNumber = java.math.BigInteger.valueOf(System.currentTimeMillis());
        var notBefore = new Date();
        var notAfter = new Date(notBefore.getTime() + Duration.ofDays(365).toMillis()); // Egy évig érvényes

        // Tanúsítvány előállítása
        var certificateBuilder = new JcaX509v1CertificateBuilder(dnName, serialNumber, notBefore, notAfter, dnName, pair.getPublic());

        // Aláírás
        var signer = new JcaContentSignerBuilder("SHA256withRSA").build(pair.getPrivate());
        var holder = certificateBuilder.build(signer); // A már kész tanúsítványt tartalmazza

        // X509 cert - itt térünk vissza standard javaba
        X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(holder);

        // Memóriában ott a tanúsítvány
        // Olvasható, nem szabványos formátum
        System.out.println(certificate);

        // DER (Windows - bináris) formátum
        Files.write(Path.of("training-cert.der"), certificate.getEncoded());

        // PEM (Linux - Base64 kódolt) formátum
        try (var writer = new JcaPEMWriter(Files.newBufferedWriter(Path.of("training-cert.pem")))) {
            writer.writeObject(certificate);
        }

        // Tanúsítvány és privát kulcs mentése PKCS#12 keystore formátumba
        var keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null); // Üres keystore létrehozása
        keyStore.setKeyEntry("training", pair.getPrivate(), "secret".toCharArray(), new X509Certificate[]{certificate});
        try (var output = Files.newOutputStream(Path.of("training-keystore.p12"))) {
            keyStore.store(output, "secret".toCharArray());
        }

        // Egy olyan kulcstár generálása, amiben csak  atanúsítvány van
        keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry("training", pair.getPrivate(), "secret".toCharArray(), new X509Certificate[]{certificate});
        try (var output = Files.newOutputStream(Path.of("training-keystore-just-certificate.p12"))) {
            keyStore.store(output, "secret".toCharArray());
        }
    }
}
