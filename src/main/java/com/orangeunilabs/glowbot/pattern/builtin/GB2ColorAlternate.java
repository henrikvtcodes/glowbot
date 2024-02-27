package com.orangeunilabs.glowbot.pattern.builtin;

import com.orangeunilabs.glowbot.GlowbotControllable;
import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

/**
 * A pattern to alternate while displaying two colors at a time
 */
public class GB2ColorAlternate implements LEDPattern {
    Color c1, c2;
    int len = -1;
    double duration, lastChange = 0;
    boolean flip = false;
    Timer flipTimer;

    /**
     *
     * @param color1
     * @param color2
     * @param length
     * @param durationSeconds
     */
    public GB2ColorAlternate(Color color1, Color color2, int length, double durationSeconds) {
        c1 = color1;
        c2 = color2;
        len = length;
        duration = durationSeconds;
        flipTimer = new Timer();
    }

    /**
     *
     * @param color1 color 1
     * @param color2 color 2
     * @param durationSeconds
     */
    public GB2ColorAlternate(Color color1, Color color2, double durationSeconds) {
        c1 = color1;
        c2 = color2;
        duration = durationSeconds;
        flipTimer = new Timer();
    }

    @Override
    public void runPattern(GlowbotControllable section, boolean isFirstRun) {
        double timestamp = Timer.getFPGATimestamp();
        if (timestamp - lastChange > duration) {
            flip = !flip;
            lastChange = timestamp;
        }

        int length = len;
        if (length == -1) {
            length = section.getLength() / 2;
        }

        if (flip) {
            section.setRange(0, length - 1, c2);
            section.setRange(0, section.getLength() - 1, c1);
        } else {
            section.setRange(0, length - 1, c1);
            section.setRange(length, section.getLength() - 1, c2);
        }


        for (int i = 0; i < section.getLength(); i += length - 1) {

        }
    }
}
