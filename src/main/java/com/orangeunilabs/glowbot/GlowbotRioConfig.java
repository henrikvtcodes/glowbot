package com.orangeunilabs.glowbot;

import edu.wpi.first.wpilibj.AddressableLED;
import lombok.Getter;

/**
 * GlowbotRioConfig is for more advanced use cases, i.e. if you're not using WS2812B or other compatible LEDs
 */
public class GlowbotRioConfig {
    @Getter
    public final int port, length;

    /**
     * WPILib is set up for WS2812B LEDs by default
     */
    boolean hasAlternateBitTiming = false;
    /**
     * Bit timings for LEDs, stored in Nanoseconds (ns). WPILib is set up for WS2812B LEDs by default.
     */
    @Getter()
    private int lowTime0, highTime0, highTime1, lowTime1;

    /**
     * The most basic config. Using the default bit timings which are configured for WS2812B LEDs.
     * <p>This exists mostly for consistency's sake, but if you only need this you should just use
     * {@link GlowbotRio#GlowbotRio(int, int) default GlowbotRio constructor}.</p>
     *
     * @param pwmPort PWM port that the LED Strip is plugged into
     * @param length Number of LED pixels on the strip
     */
    public GlowbotRioConfig(int pwmPort, int length) {
        hasAlternateBitTiming = false;
        this.port = pwmPort;
        this.length = port;
    }

    public GlowbotRioConfig(int pwmPort, int length, int highTime0NanoSeconds,
                         int lowTime0NanoSeconds,
                         int highTime1NanoSeconds,
                         int lowTime1NanoSeconds) {
        this.port = pwmPort;
        this.length = length;
        this.hasAlternateBitTiming = false;
        this.highTime0 = highTime0NanoSeconds;
        this.lowTime0 = lowTime0NanoSeconds;
        this.highTime1 = highTime1NanoSeconds;
        this.lowTime1 = lowTime1NanoSeconds;
    }

    /**
     * Sets the bit timing.
     *
     * <p>By default, the driver is set up to drive WS2812Bs, so nothing needs to be set for those.
     *
     * @param highTime0NanoSeconds high time for 0 bit (default 400ns)
     * @param lowTime0NanoSeconds  low time for 0 bit (default 900ns)
     * @param highTime1NanoSeconds high time for 1 bit (default 900ns)
     * @param lowTime1NanoSeconds  low time for 1 bit (default 600ns)
     */
    public void setBitTiming(
            int highTime0NanoSeconds,
            int lowTime0NanoSeconds,
            int highTime1NanoSeconds,
            int lowTime1NanoSeconds) {
        this.hasAlternateBitTiming = true;
        this.highTime0 = highTime0NanoSeconds;
        this.lowTime0 = lowTime0NanoSeconds;
        this.highTime1 = highTime1NanoSeconds;
        this.lowTime1 = lowTime1NanoSeconds;
    }

    void configureBitTiming (
            AddressableLED ledStrip
    ) {
        if (hasAlternateBitTiming) {
            return;
        }
        ledStrip.setBitTiming(highTime0, lowTime0, highTime1, lowTime1);
    }

}
