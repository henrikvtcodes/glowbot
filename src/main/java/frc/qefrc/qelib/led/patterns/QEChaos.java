/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led.patterns;

import frc.qefrc.qelib.led.LEDPatternBase;
import frc.qefrc.qelib.led.LEDSection;
import frc.qefrc.qelib.led.LEDSectionController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;

/** Pure color chaos. Random colors on each pixel. */
public class QEChaos extends LEDPatternBase {
    private boolean firstRun = true;

    public QEChaos(LEDSection mySection) {
        super(mySection);
    }

    @Override
    public void setPattern(LEDSectionController control) {
        if (firstRun) {
            for (int index = 0; index < control.getLength(); index++) {
                control.setLED(index, new Color(Math.random(), Math.random(), Math.random()));
            }
            firstRun = false;
        }
        for (int index = 0; index < control.getLength(); index++) {
            control.setLED(index, randomColorShift(control.get(index)));
        }
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    private Color randomColorShift(Color aColor) {
        return new Color(
                randomShift(aColor.red), randomShift(aColor.green), randomShift(aColor.blue));
    }

    private double randomShift(double value) {
        double sign = Math.random() >= 0.5 ? 1.0 : -1.0;
        double amount = Math.random() / 10;
        return MathUtil.clamp(value + sign * amount, 0, 1);
    }
}
