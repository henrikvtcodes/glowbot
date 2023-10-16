/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.hvtc.roboled;

import java.util.Iterator;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/**
 * This is passed to {@link LEDPattern}s to control LEDs. It allows control of only a specified
 * subset of LEDs. With this, we can allow multiple patterns to run at once. <br>
 * </br> By default, LED indexes are clamped so that LEDs outside this section cannot be controlled.
 */
public final class LEDSectionController implements Iterable<RLED> {
    public final LEDSection section;
    private final RLAddressableLEDBuffer buffer;

    // Package-private constructor, so no JavaDocs
    LEDSectionController(RLAddressableLEDBuffer myBuffer, LEDSection mySection) {
        section = mySection;
        buffer = myBuffer;
    }

    /** Iterate over LEDs using cool for-each syntax */
    @Override
    public Iterator<RLED> iterator() {
        return new Iterator<RLED>() {
            private int currentIndex = section.startIdx;
            private int endIndex = section.endIdx;

            @Override
            public boolean hasNext() {
                return currentIndex < endIndex;
            }

            @Override
            public RLED next() {
                return new RLED(buffer, currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("LEDs cannot be removed!");
            }
        };
    }

    /**
     * @param index
     * @param clamped
     * @return Color
     */
    /* --------- LED Control Methods --------- */

    /**
     * Get the color at a specified index
     *
     * @param index any valid LED index
     * @param clamped whether the index value should be clamped to within the section
     * @return {@link Color} of the given LED
     */
    public Color get(int index, boolean clamped) {
        return clamped ? buffer.getLED(section.clampWithinSection(index)) : buffer.getLED(index);
    }

    /**
     * Get the color at a specified index <br>
     * </br> Note: This method does not clamp the index within the section by default. If you wish
     * to control this behavior, use the overloaded method {@link LEDSectionController#get(int,
     * boolean) get(int index, boolean clamped)}
     *
     * @param index any valid LED index
     * @return {@link Color} of the given LED
     */
    public Color get(int index) {
        return get(index, false);
    }

    /**
     * Get the color at a specified index
     *
     * @param index any valid LED index
     * @param clamped whether the index value should be clamped to within the section
     * @return {@link Color8Bit} of the given LED
     */
    public Color8Bit get8Bit(int index, boolean clamped) {
        return clamped
                ? buffer.getLED8Bit(section.clampWithinSection(index))
                : buffer.getLED8Bit(index);
    }

    /**
     * Get the color at a specified index <br>
     * </br> Note: This method does not clamp the index within the section by default. If you wish
     * to control this behavior, use the overloaded method {@link LEDSectionController#get(int,
     * boolean) get(int index, boolean clamped)}
     *
     * @param index any valid LED index
     * @return {@link Color8Bit} of the given LED
     */
    public Color8Bit get8Bit(int index) {
        return get8Bit(index, false);
    }

    /**
     * Set the LED strip section to a color
     *
     * @param color {@link Color}
     */
    public void set(Color color) {
        buffer.setRange(section, color);
    }

    /**
     * Set the LED strip section to a color
     *
     * @param color {@link Color8Bit}
     */
    public void set(Color8Bit color) {
        buffer.setRange(section, color);
    }

    /**
     * Set one LED to a color<br>
     * </br> <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param color {@link Color}
     */
    public void setLED(int index, Color color) {
        buffer.setLED(section.clampWithinSection(index), color);
    }

    /**
     * Set one LED to a Color <br>
     * </br> <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param color {@link Color8Bit}
     */
    public void setLED(int index, Color8Bit color) {
        buffer.setLED(section.clampWithinSection(index), color);
    }

    /**
     * Set one LED to a Color <br>
     * </br> <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param r
     * @param b
     * @param g
     */
    public void setRGB(int index, int r, int b, int g) {
        buffer.setRGB(section.clampWithinSection(index), r, g, b);
    }

    /**
     * Set one LED to a Color <br>
     * </br> <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param h
     * @param s
     * @param v
     */
    public void setHSV(int index, int h, int s, int v) {
        buffer.setHSV(section.clampWithinSection(index), h, s, v);
    }

    /* --------- Basic abstractions --------- */

    public int getLength() {
        return section.length;
    }
}
