package com.serial4j.example.jme;

/**
 * Decodes a UART frame into {@link JoystickValue}s.
 * 
 * @author pavl_g.
 */
public final class JoystickValueDecoder {

    private JoystickValueDecoder() {
    }

    public static JoystickValue getJoystickValue(String frame) {
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < frame.length(); i++) {
            if (frame.charAt(i) == 'x') {
                // i + 4 is the delimeter between the x and the value of x
                x = getPotentiometerValue(frame, i + 4, ',');
            } else if (frame.charAt(i) == 'y') {
                // i + 4 is the delimeter between the 'y' and the value of y
                y = getPotentiometerValue(frame, i + 4, ']');
            }
        }

        return new JoystickValue(x, y);
    }

    private static int getPotentiometerValue(String frame, int index, char delimiter) {
        final StringBuffer buffer = new StringBuffer("");
        for (int i = index; frame.charAt(i) != delimiter; i++) {
            buffer.append(frame.charAt(i));
        }
        return Integer.parseInt(buffer.toString());
    }
}
