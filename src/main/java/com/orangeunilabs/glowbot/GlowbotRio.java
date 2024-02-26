package com.orangeunilabs.glowbot;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The GlowbotRio class is the core abstraction for using the Rio to drive LEDs directly.
 * It contains the buffer, periodic methods, section logic, etc
 */
public class GlowbotRio implements AutoCloseable, GlowbotControllable {
    public static final int PERIODIC_UPDATE_FREQUENCY_HERTZ = 50;
    private static int instanceCount = 0;
    public final int length, port;
    private final String name;

    private final AddressableLED ledStrip;
    private final GlowbotLEDBuffer buffer;
    private final Notifier notifier;
    private final ArrayList<PatternSection> sections;
    private final PatternSection defaultSection;
    private boolean notifierIsRunning = false;
    private boolean outputStarted = false;
    private int currentNotifierFrequency = PERIODIC_UPDATE_FREQUENCY_HERTZ;

    /**
     * @param port   the PWM port that the LED Strip is attached to.
     * @param length the length of the LED Strip (number of pixels). Setting the length of an LED Strip is an
     *               expensive low-level call and should not need to be changed while the robot is operating, thus
     */
    public GlowbotRio(int port, int length) {
        this(new GlowbotRioConfig(port, length));
    }

    /**
     * Create a GlowbotRio object with a config. This is only recommend if using LEDs that are not WS2812B compatible
     * @param config the config
     */
    public GlowbotRio(GlowbotRioConfig config) {
        instanceCount++;

        this.port = config.port;
        this.length = config.length;

        name = String.format("GlowbotRio@%s (port: %d, length: %d)", super.hashCode(), port, length);

        if (instanceCount > 1) {
            // WPILib & the roboRio currently only support running 1 Addressable LED strip directly.
            DriverStation.reportWarning("GlowbotRio Warning: More than 1 instance created! This is not supported by the roboRio!", true);
        }

        // Create low level resources
        ledStrip = new AddressableLED(port);
        ledStrip.setLength(length);
        buffer = new GlowbotLEDBuffer(length);
        defaultSection = new PatternSection(buffer, 0, length - 1, null);

        if (config.hasAlternateBitTiming) {
            config.configureBitTiming(ledStrip);
        }

        notifier = new Notifier(this::notifierPeriodic);
        notifier.setName(name);

        sections = new ArrayList<>();

        startOutput();
    }

    /**
     * Start the built-in notifier with the default frequency (50Hz) <br>
     * This will implicitly start output if it has been stopped.
     */
    public void start() {
        start(PERIODIC_UPDATE_FREQUENCY_HERTZ);
        if (!outputStarted) {
            startOutput();
        }
    }

    /**
     * Start the periodic updater with a custom frequency. It is best to call this in the {@link TimedRobot#robotInit()}
     *
     * @param hertz the update frequency in Hertz (i.e. cycles per second)
     */
    public void start(int hertz) {
        // Convert hertz to an interval in seconds
        double frequencySeconds = (double) 1 / hertz;
        currentNotifierFrequency = hertz;

        notifier.startPeriodic(frequencySeconds);
        notifierIsRunning = true;
    }

    /**
     * Stops the built-in notifier. LED Output is not stopped.
     */
    public void stop() {
        notifier.stop();
        notifierIsRunning = false;
    }

    /**
     * This starts the output of the LED strip by calling {@link AddressableLED#start()}
     */
    public void startOutput() {
        ledStrip.start();
        outputStarted = true;
    }

    /**
     * This stops the output to the LED strip by calling {@link AddressableLED#stop()}
     */
    public void stopOutput() {
        ledStrip.stop();
        outputStarted = false;
    }

    /**
     * Periodic method for use in {@link TimedRobot#addPeriodic(Runnable, double)} or in a
     * subsystem periodic method instead of using the {@link GlowbotRio#start()} method with the built in
     * Notifier. <br>
     * If you use this method while the notifier is running, it WILL throw an error.
     */
    public void periodic() {
        if (notifierIsRunning) {
            throw new GlowbotException("You cannot use the built-in periodic functionality & the periodic method at the same time!");
        }
        // Run all of the currently running patterns to get the latest data in the buffer
        sections.forEach(PatternSection::periodic);
        ledStrip.setData(buffer);
    }

    /**
     * This functions almost exactly like {@link this#periodic()} with the difference that it is called when the
     * notifier is used.
     */
    private void notifierPeriodic() {
        // Run all of the currently running patterns to get the latest data in the buffer
        sections.forEach(PatternSection::periodic);
        ledStrip.setData(buffer);
    }

    /**
     * Get the frequency that the currently running numerator is running at.
     *
     * @return the frequency in Hertz if the notifier is running, otherwise, -1
     */
    public int getNotifierFrequency() {
        return notifierIsRunning ? currentNotifierFrequency : -1;
    }

    /**
     * Get a new section that does not overlap with existing sections
     *
     * @param start the index of the first LED in the requested section. This cannot be less than 0, or
     *              greater than the end index
     * @param end   the index of the last LED in the requested section. This cannot be less than the start index, or
     *              greater than the length - 1.
     * @return if there are no overlapping sections, a new {@link PatternSection}. if there is overlap, returns null.
     */
    public PatternSection getSection(int start, int end) {
        return getSection(start, end, false);
    }

    /**
     * Get a new section.
     *
     * @param start     the index of the first LED in the requested section. This cannot be less than 0, or
     *                  greater than the end index
     * @param end       the index of the last LED in the requested section. This cannot be less than the start index, or
     *                  greater than the length - 1.
     * @param supersede if the requested pattern overlaps with those patterns, delete those patterns if this is true. if false, return null
     * @return null or a new {@link PatternSection}, depending on the value of `supersede`.
     */
    public PatternSection getSection(int start, int end, boolean supersede) {
        for (PatternSection section : sections) {
            boolean doesOverlap = section.overlaps(start, end);
            if (doesOverlap) {
                if (supersede) {
                    sections.remove(section);
                } else {
                    return null;
                }
            }
        }

        PatternSection newSection = new PatternSection(buffer, start, end, null);
        sections.add(newSection);
        return newSection;
    }

    /* --------- Implemented Methods --------- */

    @Override
    public Color get(int index) {
        return defaultSection.get(index);
    }

    @Override
    public Color8Bit get8Bit(int index) {
        return defaultSection.get8Bit(index);
    }

    @Override
    public void set(int index, Color color) {
        defaultSection.set(index, color);
    }

    @Override
    public void set(int index, Color8Bit color) {
        defaultSection.set(index, color);
    }

    @Override
    public void setRange(int start, int end, Color color) {
        defaultSection.setRange(start, end, color);
    }

    @Override
    public void setRange(int start, int end, Color8Bit color) {
        defaultSection.setRange(start, end, color);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return buffer.iterator();
    }

    @Override
    public void close() {
        stop();
        notifier.close();
        stopOutput();
        ledStrip.close();
    }

    @Override
    public String toString() {
        return name;
    }
}
