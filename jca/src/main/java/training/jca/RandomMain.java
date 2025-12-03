package training.jca;

import java.security.SecureRandom;
import java.util.Random;

public class RandomMain {

    public static void main(String[] args) throws Exception {
        var random = new Random(); // Kriptográfiai célra nem használható!
        System.out.println(random.nextInt(100));

        var secureRandom = new SecureRandom();
        System.out.println(secureRandom.nextInt(100));

        var specSecureRandom = SecureRandom.getInstance("SHA1PRNG");
        System.out.println(specSecureRandom.nextInt(100));

        var strongSecureRandom = SecureRandom.getInstanceStrong(); // Legerősebb algoritmust adja vissza
        System.out.println(strongSecureRandom.nextInt(100));
    }
}
