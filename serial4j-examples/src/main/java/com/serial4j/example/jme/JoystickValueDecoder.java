package com.serial4j.example.jme;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            for (int i = 0; i < frame.length(); i++) {
                if (frame.charAt(i) == 'x') {
                    // i + 4 is the delimiter between the x and the value of x
                    x = getPotentiometerValue(frame, i + 4, ',');
                } else if (frame.charAt(i) == 'y') {
                    // i + 4 is the delimiter between the 'y' and the value of y
                    y = getPotentiometerValue(frame, i + 4, ']');
                }
            }
        } catch (Exception e) {
            x = 0;
            y = 0;
            Logger.getLogger(JoystickValue.class.getName())
                  .log(Level.WARNING, "Decoding Fails with '" + e.getMessage() + "'", e.getCause());
        }

        return new JoystickValue(x, y);
    }

    private static int getPotentiometerValue(String frame, int index, char delimiter) {
        final StringBuilder data = new StringBuilder();
        for (int i = index; frame.charAt(i) != delimiter; i++) {
            data.append(frame.charAt(i));
        }
        return Integer.getInteger(data.toString(), 0);
    }
}
