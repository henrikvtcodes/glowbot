package com.orangeunilabs.glowbot;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/** Custom Representation of a single LED Pixel. */
public final class Pixel {
    private AddressableLEDBuffer buffer;
    private final int idx;
    private final int rootIdx;

    // Lack of `public` modifer prevents end users from instantiating
    Pixel(AddressableLEDBuffer ledBuffer, int index, int rootIndex) {
        buffer = ledBuffer;
        rootIdx = rootIndex;
        idx = index;
    }

    /* -------- GETTERS -------- */

    /**
     * Get the 12-bit Color of this object
     *
     * @return {@link Color} of this LED
     */
    public Color get() {
        return buffer.getLED(rootIdx);
    }

    /**
     * Get the 8-bit Color of this object
     *
     * @return {@link Color8Bit} of this LED
     */
    public Color8Bit get8Bit() {
        return buffer.getLED8Bit(rootIdx);
    }

    /** @return this LED's index in the buffer */
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
        buffer.setLED(rootIdx, color);
    }

    /**
     * Set the color of this LED
     *
     * @param color {@link Color8Bit} to set on this LED
     */
    public void set(Color8Bit color) {
        buffer.setLED(rootIdx, color);
    }

    /**
     * Set the color of this LED <br>
     * Input values are clamped to the listed ranges.
     * @param r the r value [0-256)
     * @param g the g value [0-256)
     * @param b the b value [0-256)
     */
    public void setRGB(int r, int g, int b) {
        buffer.setRGB(rootIdx, clamp255(r), clamp255(g), clamp255(b));
    }

    /**
     * Set the color of this LED <br>
     * Input values are clamped to the listed ranges.
     * @param h the h value [0-180)
     * @param s the s value [0-256)
     * @param v the v value [0-256)
     */
    public void setHSV(int h, int s, int v) {
        buffer.setHSV(rootIdx, clamp180(h), clamp255(s), clamp255(v));
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

