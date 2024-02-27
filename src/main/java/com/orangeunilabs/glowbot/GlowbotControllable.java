package com.orangeunilabs.glowbot;


import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/**
 * This represents a common set of methods used to control LEDs
 */
public interface GlowbotControllable extends Iterable<Pixel> {
    /**
     * Get the color at a specified index.
     *
     * @param index the index of the LED. This index may be relative to a section.
     * @return The {@link Color} at that position
     */
    Color get(int index);

    /**
     * Get the color at a specified index.
     *
     * @param index the index of the LED. This index may be relative to a section.
     * @return The {@link Color8Bit} at that position
     */
    Color8Bit get8Bit(int index);

    /**
     * Set the color of the LED at a given index
     *
     * @param index the index of the LED. This index may be relative to a section.
     * @param color the color to set the LED to
     */
    void set(int index, Color color);

    /**
     * Set the color of the LED at a given index
     *
     * @param index the index of the LED. This index may be relative to a section.
     * @param color the color to set the LED to
     */
    void set(int index, Color8Bit color);

    void setRange(int start, int end, Color8Bit color);

    void setRange(int start, int end, Color color);

    int getLength();
}
