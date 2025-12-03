package training.jca;

import java.util.HexFormat;

public class HexMain {

    public static void main(String[] args) {
        var input = "Hello, Hex!";
        var hex = HexFormat.of();
        var formatted = hex.formatHex(input.getBytes());
        System.out.println("Hex: " + formatted);
    }
}
