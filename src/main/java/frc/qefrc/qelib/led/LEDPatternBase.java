/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

/** {@inheritDoc LEDPattern} */
public abstract class LEDPatternBase implements LEDPattern {
    protected final LEDSection section;

    public LEDPatternBase(LEDSection mySection) {
        section = mySection;
    }

    public LEDSection getSection() {
        return section;
    }
}
