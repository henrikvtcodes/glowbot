package com.orangeunilabs.glowbot;


import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.Iterator;

/**
 * This is a custom extension of the {@link AddressableLEDBuffer} from WPILib. In addition to being used within
 * GlowbotRio, it can also be used standalone due to its useful features. <br>
 * <b>Useful Features</b>
 *
 * <ul>
 *   <li>Enhanced for loop support
 *   <li>Controlling the entire LED strip or sections of it with a single method call
 * </ul>
 */
public class GlowbotLEDBuffer extends AddressableLEDBuffer implements Iterable<Pixel>  {
    public GlowbotLEDBuffer(int length) {
        super(length);
    }

    /** Iterate over LEDs using cool for-each syntax */
    @Override
    public Iterator<Pixel> iterator() {
        return new Iterator<Pixel>() {
            private int currentIndex = 0;
            private int endIndex = getLength() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex <= endIndex;
            }

            @Override
            public Pixel next() {
                return new Pixel(getBuffer(), currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("LEDs cannot be removed!");
            }
        };
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

    // Just used for creating the iterator
    private AddressableLEDBuffer getBuffer() {
        return this;
    }
}
