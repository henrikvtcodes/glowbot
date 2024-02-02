package com.orangeunilabs.roboled.blinkin;


import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.wpi.first.wpilibj.motorcontrol.Spark;


/** This class represents a REV Robotics Blinkin LED Controller */
public class Blinkin extends BlinkinConstants {

    private final Spark blinkin;
    private final int pwmPort;
    private double currentValue = SolidColor.Off.val;

    public Blinkin(int port) {
        pwmPort = port;
        blinkin = new Spark(pwmPort);
    }

    public final int getPWMPort() {
        return pwmPort;
    }

    public final double getCurrentValue() {
        return currentValue;
    }

    /**
     * Set a pattern using one of the fixed {@link ColorScheme ColorScheme}'s and {@link
     * FixedPatternType fixed pattern}s.
     *
     * @param scheme {@link ColorScheme ColorScheme} to use; Rainbow, Party, Ocean, Lava, or Forest
     * @param pattern {@link FixedPatternType Pattern}; Rainbow, Sinelon, Beats Per Minute,
     *     Twinkles, or Color Wave
     */
    public final void setPattern(ColorScheme scheme, FixedPatternType pattern) {
        setLED(scheme.val + pattern.val);
    }

    /**
     * Set a pattern using a
     *
     * @param color Programmed color to use; Color 1 or Color 2
     * @param pattern Pattern to use with color
     */
    public final void setPattern(Color color, CustomPatternType pattern) {
        setLED(color.val + pattern.val);
    }

    /**
     * Display a pattern using both programmed colors
     *
     * @param pattern Combination pattern to use
     */
    public final void setPattern(CustomCombiPattern pattern) {
        setLED(pattern.val);
    }

    /**
     * Display one of the built-in solid colors
     *
     * @param color Built In Color to set
     */
    public final void setColor(SolidColor color) {
        setLED(color.val);
    }

    /**
     * Private method that limits decimal places to 2 for precision and sets them on the Spark
     * object
     *
     * @param num percent power that goes to PWM for Blinkin
     */
    private void setLED(double num) {
        currentValue = new BigDecimal(num).setScale(2, RoundingMode.HALF_UP).doubleValue();
        synchronized (blinkin) {
            blinkin.set(currentValue);
        }
    }
}
