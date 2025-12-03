package training.jca;

public class Base64Main {

    public static void main(String[] args) {
        var input = "Hello, Base64!";
        var encoder = java.util.Base64.getEncoder();

        String encoded = encoder.encodeToString(input.getBytes());
        System.out.println("Encoded: " + encoded);
    }
}
