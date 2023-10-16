/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.hvtc.roboled.patterns;

import lombok.Getter;
import lombok.Setter;

import edu.wpi.first.wpilibj.util.Color;
import frc.hvtc.roboled.LEDPatternBase;
import frc.hvtc.roboled.LEDSection;
import frc.hvtc.roboled.LEDSectionController;

/**
 * Color Chasing Pattern; segments of each color "chase" down the led strip {@category LEDPattern}
 */
public class RLChase extends LEDPatternBase {
    private int chaseOffset;
    @Getter @Setter private int segWidth;
    private Color[] colors;

    /**
     * @param segmentWidth how wide each color segment should be
     * @param myColors colors to chase with
     */
    public RLChase(LEDSection mySection, int segmentWidth, Color[] myColors) {
        super(mySection);
        segWidth = segmentWidth;
        colors = myColors;
    }

    @Override
    public void setPattern(LEDSectionController control) {
        int numberOfColors = colors.length;
        int effectiveIndex;
        int colorIndex;
        int bufferLength = control.getLength();
        for (int index = 0; index < bufferLength; index++) {
            effectiveIndex = (index + chaseOffset) % bufferLength;
            colorIndex = (index / segWidth) % numberOfColors;
            control.setLED(effectiveIndex, colors[colorIndex]);
        }

        chaseOffset = (chaseOffset + 1) % bufferLength;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }
}
