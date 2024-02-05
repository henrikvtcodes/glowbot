package com.orangeunilabs.glowbot.rio;

import com.orangeunilabs.glowbot.GlowbotException;
import edu.wpi.first.wpilibj.*;

/** The GlowbotRio class is the core abstraction for using the Rio to drive LEDs directly.
 *
 */
public class GlowbotRio implements AutoCloseable {
    private static int instanceCount = 0;
    public static final int PERIODIC_UPDATE_FREQUENCY_HERTZ = 50;
    public final int length, port;
    private final String name;

    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer buffer;
    private final Notifier notifier;
    private boolean notifierIsRunning = false;
    private int currentNotifierFrequency = PERIODIC_UPDATE_FREQUENCY_HERTZ;

    /**
     *
     * @param port the PWM port that the LED Strip is attached to.
     * @param length the length of the LED Strip (number of pixels). Setting the length of an LED Strip is an
     *               expensive low-level call and should not need to be changed while the robot is operating, thus
     */
    public GlowbotRio(int port, int length) {
        this(new GlowbotRioConfig(port, length));
    }

    public GlowbotRio(GlowbotRioConfig config) {
        instanceCount++;

        this.port = config.port;
        this.length = config.length;

        name = String.format("RoboLED %s (port: %d, length: %d)", super.hashCode() ,port, length);

        if (instanceCount > 1) {
            // WPILib & the roboRio currently only support running 1 Addressable LED strip directly.
            DriverStation.reportWarning("RoboLED Warning: More than 1 instance created! This is not supported by the roboRio!", true);
        }

        ledStrip = new AddressableLED(port);
        ledStrip.setLength(length);
        buffer = new AddressableLEDBuffer(length);

        if (config.hasAlternateBitTiming) {
            config.configureBitTiming(ledStrip);
        }

        notifier = new Notifier(this::notifierPeriodic);
        notifier.setName(name);
    }

    /**
     * Start the built-in notifier with the default frequency (50Hz)
     */
    public void start() {
        start(PERIODIC_UPDATE_FREQUENCY_HERTZ);
    }

    /**
     * Start the periodic updater with a custom frequency. It is best to call this in the {@link TimedRobot#robotInit()}
     * @param hertz the update frequency in Hertz (i.e. cycles per second)
     */
    public void start(int hertz) {
        // Convert hertz to
        double frequencySeconds = (double) 1 / hertz;
        currentNotifierFrequency = hertz;

        notifier.startPeriodic(frequencySeconds);
        notifierIsRunning = true;
    }

    /**
     * Stops the built-in
     */
    public void stop() {

        notifier.stop();
        notifierIsRunning = false;
    }

    /**
     * Periodic method for use in
     * {@link TimedRobot#addPeriodic(Runnable, double)}
     * instead of using the {@link GlowbotRio#start()} method with the built in Notifier. <br>
     * If you use this method while the notifier is running, it WILL throw an error.
     */
    public void periodic() {
        if (notifierIsRunning) {
            throw new GlowbotException("You cannot use the built-in periodic functionality & the periodic method at the same time!");
        }
        ledStrip.setData(buffer);
    }

    /**
     * This functions almost exactly like {@link this#periodic()} with the difference that it is called when the
     * notifier is used.
     */
    private void notifierPeriodic() {
        ledStrip.setData(buffer);
    }

    /**
     * Get the frequency that the currently running numerator is running at.
     * @return the frequency in Hertz if the notifier is running, otherwise, -1
     */
    public int getNotifierFrequency() {
        return notifierIsRunning ? currentNotifierFrequency : -1;
    }

    @Override
    public void close() {
        ledStrip.stop();
        ledStrip.close();
        stop();
        notifier.close();
    }

    @Override
    public String toString() {
        return name;
    }
}
