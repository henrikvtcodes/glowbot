/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

/** Interface for implementing an LED Pattern */
public interface QELEDPattern {
    /**
     * Use the provided {@link QEAddressableLEDBuffer} to control LEDs
     *
     * @param buffer
     */
    public void setPattern(QEAddressableLEDBuffer buffer);

    /**
     * Get the section of LEDs that this pattern controls
     *
     * @return {@link LEDSection} instance of the LEDs this controls
     */
    public LEDSection getSection();

    default boolean isAnimated() {
        return false;
    }
}
