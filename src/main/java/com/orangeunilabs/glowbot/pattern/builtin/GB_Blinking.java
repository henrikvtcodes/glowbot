package com.orangeunilabs.glowbot.pattern.builtin;

import com.orangeunilabs.glowbot.GlowbotControllable;
import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Blinks a color at a specified duration
 */
public class GB_Blinking implements LEDPattern {
    Color onColor;
    double duration, lastChange = 0;
    boolean on = true;

    /**
     * @param color         the color to display while on
     * @param blinkDuration how long the leds should wait before switching on or off
     */
    public GB_Blinking(Color color, double blinkDuration) {
        onColor = color;
        duration = blinkDuration;
    }

    @Override
    public void runPattern(GlowbotControllable section, boolean isFirstRun) {
        double timestamp = Timer.getFPGATimestamp();
        if (timestamp - lastChange > duration) {
            on = !on;
            lastChange = timestamp;
        }

        if (on) {
            section.setRange(0, section.getLength() - 1, onColor);
        } else {
            section.setRange(0, section.getLength() - 1, Color.kBlack);
        }
    }
}
