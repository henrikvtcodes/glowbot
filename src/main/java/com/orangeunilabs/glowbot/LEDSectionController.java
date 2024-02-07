package com.orangeunilabs.glowbot;

import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.Iterator;

/**
 * This is passed to {@link LEDPattern}s to control LEDs. It allows control of only a specified
 * subset of LEDs. With this, we can allow multiple patterns to run at once. <br>
 * By default, LED indexes are clamped so that LEDs outside this section cannot be controlled.
 */
public final class LEDSectionController implements Iterable<Pixel> {
    public final LEDSection section;
    public final int length;
    private final GlowbotLEDBuffer buffer;

    // Package-private constructor, so no JavaDocs
    LEDSectionController(GlowbotLEDBuffer buffer, LEDSection section) {
        this.section = section;
        this.buffer = buffer;
        length = section.length;
    }

    /**
     * Iterate over LEDs using cool for-each syntax
     */
    @Override
    public Iterator<Pixel> iterator() {
        return new Iterator<Pixel>() {
            private int currentIndex = section.startIdx;
            private final int endIndex = section.endIdx;

            @Override
            public boolean hasNext() {
                return currentIndex < endIndex;
            }

            @Override
            public Pixel next() {
                return new Pixel(buffer, currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("LEDs cannot be removed!");
            }
        };
    }

    /* --------- LED Control Methods --------- */

    /**
     * Get the color at a specified index
     *
     * @param index any valid LED index - this value will be clamped
     *              within the bounds of the LEDSection. 0 is the first LED
     * @return {@link Color} of the given LED
     */
    public Color get(int index) {
        return buffer.getLED(section.clampWithinSection(index));
    }


    /**
     * Get the color at a specified index
     *
     * @param index any valid LED index - this value will be clamped
     *              within the bounds of the LEDSection
     * @return {@link Color8Bit} of the given LED
     */
    public Color8Bit get8Bit(int index) {
        return buffer.getLED8Bit(section.clampWithinSection(index));
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
     * @param color {@link Color8Bit} 8 bit Color to set
     */
    public void set(Color8Bit color) {
        buffer.setRange(section, color);
    }

    /**
     * Set one LED to a color<br>
     * <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param color {@link Color}
     */
    public void setLED(int index, Color color) {
        buffer.setLED(section.clampWithinSection(index), color);
    }

    /**
     * Set one LED to a Color <br>
     * <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index
     * @param color {@link Color8Bit}
     */
    public void setLED(int index, Color8Bit color) {
        buffer.setLED(section.clampWithinSection(index), color);
    }

    /**
     * Set one LED to a Color <br>
     * <b>Important: All index values are clamped inside the section! </b>
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
     * <b>Important: All index values are clamped inside the section! </b>
     *
     * @param index Index of the LED
     * @param h Hue
     * @param s Saturation
     * @param v Value
     */
    public void setHSV(int index, int h, int s, int v) {
        buffer.setHSV(section.clampWithinSection(index), h, s, v);
    }

    /* --------- Basic abstractions --------- */

    /**
     * Get the length of the associated section of LEDs
     * @return length of the LED strip section
     */
    public int getLength() {
        return section.length;
    }

    private int calcRealIndex(int idx) {
        return idx + section.startIdx;
    }
}
