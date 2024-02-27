package com.orangeunilabs.glowbot.pattern.builtin;

import com.orangeunilabs.glowbot.GlowbotControllable;
import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

/**
 * A simple rainbow pattern
 * @author FRC team 5013, Trobots
 */
public class GBRainbow implements LEDPattern {
    private int firstHue = 0;
    public GBRainbow() {
        // Default constructor
    }

    @Override
    public void runPattern(GlowbotControllable section, boolean isFirstRun) {
        int currentHue;
        for (int index = 0; index < section.getLength(); index++){
            currentHue = (firstHue + (index * 180 / section.getLength())) % 180;
            section.set(index, Color.fromHSV(currentHue, 255, 128));
        }

        firstHue = (firstHue + 3) % 180;
    }
}
