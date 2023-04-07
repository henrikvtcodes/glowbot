/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

/** Interface for implementing an LED Pattern */
public interface LEDPattern {
    /**
     * Use the provided {@link LEDSectionController} to control LEDs
     *
     * @param control
     */
    public void setPattern(LEDSectionController control);

    /**
     * Get the section of LEDs that this pattern controls
     *
     * @return {@link LEDSection} instance of the LEDs this controls
     */
    public LEDSection getSection();

    /**
     * Indicate if this pattern changes over time. This is also useful if you would like to
     * continuously posess it's section of LEDs. <br>
     * </br> If this is false, the pattern is run once and removed from the runningPatterns {@link
     * java.util.Map}.
     *
     * @return whether this pattern is animated; as in, it changes over time
     */
    default boolean isAnimated() {
        return false;
    }
}
