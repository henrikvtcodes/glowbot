package com.orangeunilabs.glowbot.pattern.builtin;

import com.orangeunilabs.glowbot.GlowbotControllable;
import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Displays a solid color
 */
public class GBSolid implements LEDPattern {
    private Color solidColor;
    public GBSolid(Color color) {
        solidColor = color;
    }

    @Override
    public void runPattern(GlowbotControllable section, boolean isFirstRun) {
        section.setRange(0, section.getLength() - 1, solidColor);
    }
}
