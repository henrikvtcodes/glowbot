/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

import java.util.Iterator;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/**
 * This is a custom extension of the {@link AdressableLEDBuffer} from WPILib. <br>
 * </br> <b>Useful Features</b>
 *
 * <ul>
 *   <li>Enhanced for loop support
 *   <li>Controlling the entire LED strip or sections of it with a single method call
 * </ul>
 */
public class QEAddressableLEDBuffer extends AddressableLEDBuffer implements Iterable<QELED> {
    /**
     * Create a new {@link QEAddressableLEDBuffer}. This simply runs the {@link
     * AddressableLEDBuffer} constructor.
     *
     * @param length
     */
    public QEAddressableLEDBuffer(int length) {
        super(length);
    }

    /**
     * Set the entire LED strip to a color
     *
     * @param color {@link Color}
     */
    public void set(Color color) {
        for (QELED led : this) {
            led.set(color);
        }
    }

    /**
     * Set the entire LED strip to a color
     *
     * @param color {@link Color8Bit}
     */
    public void set(Color8Bit color) {
        for (QELED led : this) {
            led.set(color);
        }
    }

    /**
     * Set a section of the LED strip to a color
     *
     * @param section Subsection of LEDs to set
     * @param color {@link Color}
     */
    public void setRange(LEDSection section, Color color) {
        for (int idx = section.startIdx; idx < section.endIdx; idx++) {
            setLED(idx, color);
        }
    }

    /**
     * Set a section of the LED strip to a color
     *
     * @param section Subsection of LEDs to set
     * @param color {@link Color8Bit}
     */
    public void setRange(LEDSection section, Color8Bit color) {
        for (int idx = section.startIdx; idx < section.endIdx; idx++) {
            setLED(idx, color);
        }
    }

    /* --------- Iterator Stuff  --------- */

    /** Iterate over LEDs using cool for-each syntax */
    @Override
    public Iterator<QELED> iterator() {
        return new Iterator<QELED>() {
            private int currentIndex = 0;
            private int endIndex = getLength();

            @Override
            public boolean hasNext() {
                return currentIndex < endIndex;
            }

            @Override
            public QELED next() {
                return new QELED(getBuffer(), currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Just used for creating the iterator
    private AddressableLEDBuffer getBuffer() {
        return this;
    }
}
