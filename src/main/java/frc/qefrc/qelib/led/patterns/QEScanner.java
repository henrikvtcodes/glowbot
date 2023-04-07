/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led.patterns;

import frc.qefrc.qelib.led.LEDPatternBase;
import frc.qefrc.qelib.led.LEDSection;
import frc.qefrc.qelib.led.LEDSectionController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Color Scanning effect; the "eye" color scans back and forth. This is different than "chase"
 * because the eye fades in and out over it's length {@category LEDPattern}
 */
public class QEScanner extends LEDPatternBase {
    private int eyePosition = 0, scanDirection = 1, length;
    private Color background, eye;

    /**
     * @param mySection LEDSection to apply to
     * @param bg Background Color
     * @param eyeColor Eye (Center) color
     * @param eyeLength Length of Eye
     */
    public QEScanner(LEDSection mySection, Color bg, Color eyeColor, int eyeLength) {
        super(mySection);
        eyeLength = length;
        background = bg;
        eye = eyeColor;
    }

    @Override
    public void setPattern(LEDSectionController control) {
        int bufferLength = control.getLength();
        double intensity, red, green, blue, distanceFromEye;

        for (int index = 0; index < bufferLength; index++) {
            distanceFromEye = MathUtil.clamp(Math.abs(eyePosition - index), 0, length);
            intensity = 1 - distanceFromEye / length;
            red = MathUtil.interpolate(background.red, eye.red, intensity);
            green = MathUtil.interpolate(background.green, eye.green, intensity);
            blue = MathUtil.interpolate(background.blue, eye.blue, intensity);

            control.setLED(index, new Color(red, green, blue));
        }

        if (eyePosition == 0) {
            scanDirection = 1;
        } else if (eyePosition == bufferLength - 1) {
            scanDirection = -1;
        }

        eyePosition += scanDirection;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }
}
