package com.orangeunilabs.glowbot.pattern.builtin;

import com.orangeunilabs.glowbot.GlowbotControllable;
import com.orangeunilabs.glowbot.LEDUtils;
import com.orangeunilabs.glowbot.pattern.LEDPattern;

/**
 * LED chaotic storm!
 * @author FRC team 5013, Trobots
 */
public class GBChaos implements LEDPattern {
    /**
     *
     */
    public GBChaos() {
        // Default Constructor
    }

    @Override
    public void runPattern(GlowbotControllable section, boolean isFirstRun) {
        if (isFirstRun){
            for (int index = 0; index < section.getLength(); index++) {
                section.set(index, LEDUtils.randomColor());
            }
        }
        for (int index = 0; index < section.getLength(); index++) {
            section.set(index, LEDUtils.randomColorShift(section.get(index)));
        }
    }
}
