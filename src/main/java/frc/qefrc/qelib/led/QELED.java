/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/** Custom Representation of a single LED */
public final class QELED {
    private AddressableLEDBuffer buffer;
    int idx;

    // Lack of `public` modifer prevents end users from instantiating
    QELED(AddressableLEDBuffer ledBuffer, int index) {
        buffer = ledBuffer;
        idx = index;
    }

    /* -------- GETTERS -------- */

    /**
     * Get the 12-bit Color of this object
     *
     * @return {@link Color} of this LED
     */
    public Color get() {
        return buffer.getLED(idx);
    }

    /**
     * Get the 8-bit Color of this object
     *
     * @return {@link Color8Bit} of this LED
     */
    public Color8Bit get8Bit() {
        return buffer.getLED8Bit(idx);
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
        buffer.setLED(idx, color);
    }

    /**
     * Set the color of this LED
     *
     * @param color {@link Color8Bit} to set on this LED
     */
    public void set(Color8Bit color) {
        buffer.setLED(idx, color);
    }

    /**
     * Set the color of this LED
     *
     * @param r the r value [0-255]
     * @param g the g value [0-255]
     * @param b the b value [0-255]
     */
    public void setRGB(int r, int g, int b) {
        buffer.setRGB(idx, clamp255(r), clamp255(g), clamp255(b));
    }

    /**
     * Set the color of this LED
     *
     * @param h the h value [0-180)
     * @param s the s value [0-255]
     * @param v the v value [0-255]
     */
    public void setHSV(int h, int s, int v) {
        buffer.setHSV(idx, clamp180(h), clamp255(s), clamp255(v));
    }

    /* -------- Private Helpers -------- */

    // used to clamp R, G, B, S, and V values, in range [0, 255]
    private int clamp255(int val) {
        return MathUtil.clamp(val, 0, 255);
    }

    // used to clamp H values in hsv colors, in range [0, 180)
    private int clamp180(int val) {
        return MathUtil.clamp(val, 0, 179);
    }
}
