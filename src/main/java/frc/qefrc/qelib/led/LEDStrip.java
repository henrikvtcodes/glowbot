/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

import lombok.Getter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Subsystem;

/** LEDStrip Docs TODO */
public class LEDStrip {
    @Getter private final int pwmPort, length;
    private final AddressableLED leds;
    private QEAddressableLEDBuffer ledBuf;
    private LEDSection defaultSection;

    /**
     * Creates a new LEDStrip object.
     *
     * @param port PWM port the strip is atta
     * @param stripLength
     */
    public LEDStrip(int port, int stripLength) {
        pwmPort = port;
        length = stripLength;

        leds = new AddressableLED(port);
        leds.setLength(length);

        ledBuf = new QEAddressableLEDBuffer(stripLength);

        // When an LED section isnt specified, this is used
        defaultSection = new LEDSection(0, length - 1);

        start();
    }

    /* -------- CORE METHODS -------- */

    /**
     * Use a subsection of the LED strip
     *
     * @param start Index of the first LED
     * @param end Index of the last LED
     * @return {@link LEDSection} instance that can be passed to address subsections of the LED
     *     Strip
     */
    public LEDSection getSection(int start, int end) {
        return new LEDSection(clampWithinStrip(start), clampWithinStrip(end));
    }

    /** Start LED Output */
    public void start() {
        leds.start();
        leds.setData(ledBuf);
    }

    /** Stop LED Output */
    public void stop() {
        leds.stop();
    }

    /**
     * Update the LED strip with the most recent LED Buffer data. This must be called semi
     * frequently to keep the LEDs updated. <br>
     * </br> <b>Important: </b> This method works best when called in a Subsystem {@link
     * Subsystem#periodic() periodic()} method or the {@link TimedRobot#robotPeriodic()} method.
     */
    public void update() {
        leds.setData(ledBuf);
    }

    /* -------- Color & Effect Setters -------- */

    /**
     * Set a static color on a section of LEDs
     *
     * @param color {@link Color} to set
     * @param section The range of LEDs to set. See {@link LEDStrip#getSection(int, int) getSection}
     *     for more info.
     */
    public void setColorRange(Color color, LEDSection section) {
        ledBuf.setRange(section, color);
    }

    /**
     * Set a static color on a section of LEDs
     *
     * @param color {@link Color8Bit} to set
     * @param section The range of LEDs to set. See {@link LEDStrip#getSection(int, int) getSection}
     *     for more info.
     */
    public void setColorRange(Color8Bit color, LEDSection section) {
        ledBuf.setRange(section, color);
    }

    /**
     * Set one color on the whole LED Strip
     *
     * @param color {@link Color} to set
     */
    public void setColor(Color color) {
        setColorRange(color, defaultSection);
    }

    /**
     * Set one color on the whole LED Strip
     *
     * @param color {@link Color8Bit} to set
     */
    public void setColor(Color8Bit color) {
        setColorRange(color, defaultSection);
    }

    /* -------- PRIVATE HELPERS -------- */

    // This helper makes sure that we don't try to address nonexistant LEDs
    private int clampWithinStrip(int val) {
        return MathUtil.clamp(val, 0, length - 1);
    }
}
