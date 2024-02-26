package com.orangeunilabs.glowbot.pattern;

import com.orangeunilabs.glowbot.GlowbotControllable;

/**
 * All patterns to be used with {}
 */
public interface LEDPattern {
    void runPattern(GlowbotControllable section, boolean isFirstRun);
}
