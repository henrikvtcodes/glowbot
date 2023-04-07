/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.NonNull;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * LEDStrip is an abstraction over {@link AddressableLED} and {@link
 * edu.wpi.first.wpilibj.AddressableLEDBuffer}. It provide enhanced functionality for static colors
 * and patterns
 */
public class LEDStrip {
    @Getter private final int pwmPort, length;
    private final AddressableLED leds;
    private QEAddressableLEDBuffer ledBuf;
    private LEDSection defaultSection;
    private Map<LEDSection, LEDPattern> runningPatterns;

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

        // Create a hashmap for holding patterns
        runningPatterns = new ConcurrentHashMap<LEDSection, LEDPattern>();

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
        return new LEDSection(clampWithinStrip(start), clampWithinStrip(start, end));
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

    /* -------- Color Setters -------- */

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

    /** Set all LEDs to Black, aka Off. */
    public void off() {
        setColor(Color.kBlack);
    }

    /* --------- EFFECT SETTER --------- */

    /**
     * Set a pattern on the LED strip
     *
     * @param pattern the pattern to set
     * @param shouldInterrupt whether this pattern should interrupt another pattern if it overlaps
     * @return whether the pattern was successfully set
     */
    public boolean setPattern(@NonNull LEDPattern pattern, boolean shouldInterrupt) {
        if (!shouldInterrupt) {
            /* If the pattern should not interrupt any running patterns, test for overlap and return false if overlap is found */
            for (LEDSection key : runningPatterns.keySet()) {
                if (key.testOverlap(pattern.getSection())) {
                    return false;
                }
            }

            runningPatterns.put(pattern.getSection(), pattern);
            return true;
        } else {
            /* Loop through all running patterns and remove overlapping ones
             */
            for (Map.Entry<LEDSection, LEDPattern> entry : runningPatterns.entrySet()) {
                LEDSection key = entry.getKey();
                LEDPattern value = entry.getValue();
                if (key.testOverlap(pattern.getSection())) {
                    runningPatterns.remove(key, value);
                }
            }

            runningPatterns.put(pattern.getSection(), pattern);
            return true;
        }
    }

    /** Removes all running patterns and sets the LED strip to black. */
    public void wipe() {
        runningPatterns.clear();
    }

    /* -------- PRIVATE HELPERS -------- */

    /**
     * Clamp number within [0, length)
     *
     * @param val index value to clamp
     * @return Clamped value
     */
    private int clampWithinStrip(int val) {
        return MathUtil.clamp(val, 0, length - 1);
    }

    /**
     * Clamp number within [start, length)
     *
     * @param start starting index
     * @param end ending index
     * @return end parameter clamped to [start, length)
     */
    private int clampWithinStrip(int start, int end) {
        return MathUtil.clamp(end, start, length - 1);
    }
}
