package com.orangeunilabs.glowbot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Custom Representation of a single LED Pixel.
 */
public final class Pixel {
    private final int idx;
    /**
     * Function that gets the pixel color
     */
    private final Supplier<Color> getter;
    /**
     * Function to set the pixel color
     */
    private final Consumer<Color> setter;
    private AddressableLEDBuffer buffer;

    // Lack of `public` modifer prevents end users from instantiating
    Pixel(int index, Supplier<Color> colorGetter, Consumer<Color> colorSetter) {
        idx = index;
        getter = colorGetter;
        setter = colorSetter;
    }

    /* -------- GETTERS -------- */

    /**
     * Get the 12-bit Color of this object
     *
     * @return {@link Color} of this LED
     */
    public Color get() {
        return getter.get();
    }

    /**
     * Get the 8-bit Color of this object
     *
     * @return {@link Color8Bit} of this LED
     */
    public Color8Bit get8Bit() {
        return new Color8Bit(getter.get());
    }

    /**
     * @return this LED's index in the section
     */
    public int getIndex() {
        return idx;
    }

    /* -------- SETTERS -------- */

    /**
     * Set the color of this LED
     *
     * @param color {@link Color} to set on this LED
     */
    public void set(Color color) {
        setter.accept(color);
    }

    /**
     * Set the color of this LED
     *
     * @param color {@link Color8Bit} to set on this LED
     */
    public void set(Color8Bit color) {
        setter.accept(new Color(color));
    }

    /**
     * Set the color of this LED <br>
     * Input values are clamped to the listed ranges.
     *
     * @param r the r value [0-256)
     * @param g the g value [0-256)
     * @param b the b value [0-256)
     */
    public void setRGB(int r, int g, int b) {
        setter.accept(new Color(r, g, b));
    }

    /**
     * Set the color of this LED <br>
     * Input values are clamped to the listed ranges.
     *
     * @param h the h value [0-180)
     * @param s the s value [0-256)
     * @param v the v value [0-256)
     */
    public void setHSV(int h, int s, int v) {
        setter.accept(Color.fromHSV(h, s, v));
    }

    /* -------- Private Helpers -------- */

    // used to clamp R, G, B, S, and V values, in range [0, 256)
    private int clamp255(int val) {
        return MathUtil.clamp(val, 0, 255);
    }

    // used to clamp H values in hsv colors, in range [0, 180)
    private int clamp180(int val) {
        return MathUtil.clamp(val, 0, 179);
    }
}

