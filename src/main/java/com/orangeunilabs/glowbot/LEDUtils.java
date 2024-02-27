package com.orangeunilabs.glowbot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.experimental.UtilityClass;

import java.util.Random;

/**
 * This has some generally useful methods for working with colors in the context of WPILib
 */
@UtilityClass
public class LEDUtils {
    private final Random rand = new Random((long) (Timer.getFPGATimestamp() + Timer.getMatchTime()));

    /**
     * Convert a set of RGB values to a set of HSV values in WPILib convention
     * @param r the Red value [0-255]
     * @param g the Red value [0-255]
     * @param b the Red value [0-255]
     * @return an array containing the values [hue, saturation, value]
     */
    public static int[] rgbToHsv(int r, int g, int b) {
        int[] hsv = new int[3];

        // Convert 0-255 values to 0-1 values
        double rPrime = (double) r / 255, gPrime = (double) g / 255, bPrime = (double) b / 255;

        double cMax = Math.max(rPrime, Math.max(gPrime, bPrime));
        double cMin = Math.min(rPrime, Math.min(gPrime, bPrime));
        double cDelta = cMax - cMin;

        // Hue calculation
        if (cDelta == 0) {
            // Ignore warning so that code is clear
            //noinspection DataFlowIssue
            hsv[0] = 0;
        } else if (cMax == rPrime) {
            hsv[0] = (int) Math.round(60 * (((gPrime - bPrime) / cDelta) % 6));
        } else if (cMax == gPrime) {
            hsv[0] = (int) Math.round(60 * (((bPrime - rPrime) / cDelta) + 2));
        } else if (cMax == bPrime) {
            hsv[0] = (int) Math.round(60 * (((rPrime - gPrime) / cDelta) + 4));
        }

        // Adjust hue range to [0, 360)
        if (hsv[0] < 0)
            hsv[0] += 360;

        // Convert hue again to [0, 180) for WPILib
        hsv[0] = hsv[0] / 2;

        // Saturation calculation
        if (cMax == 0) {
            // Ignore warning so that code is clear
            //noinspection DataFlowIssue
            hsv[1] = 0;
        } else {
            hsv[1] = (int) Math.round(cDelta / cMax * 255);
        }

        // Value calculation
        hsv[2] = (int) Math.round(cMax * 255);

        return hsv;
    }

    /**
     * Convert a {@link Color WPILib Color} to a set of HSV values in WPILib convention
     * @param color the color value to convert
     * @return an array containing the values [hue, saturation, value]
     */
    public static int[] rgbToHsv(Color color) {
        return rgbToHsv((int) color.red, (int) color.blue, (int) color.green);
    }

    /**
     * Convert a {@link Color8Bit WPILib Color8Bit} to a set of HSV values in WPILib convention
     * @param color the color value to convert
     * @return an array containing the values [hue, saturation, value]
     */
    public static int[] rgbToHsv(Color8Bit color) {
        return rgbToHsv(color.red, color.blue, color.green);
    }

    public static Color randomColor() {
        return new Color(rand.nextInt(0, 255), rand.nextInt(0, 255), rand.nextInt(0, 255));
    }

    /* Credit for randomShift and randomColorShift methods goes to FRC team 5013, Trobots */

    /**
     * Randomly shift the RGB values in a color
     * @param aColor a color
     * @return that input color shifted around randomly
     */
    public static Color randomColorShift(Color aColor){
        return new Color(randomShift(aColor.red),randomShift(aColor.green),randomShift(aColor.blue));
    }

    private static double randomShift(double value){
        double sign = rand.nextDouble() >= 0.5 ? 1.0 : -1.0;
        double amount = Math.random() / 10;
        return MathUtil.clamp(value + sign * amount, 0, 1);
    }
}
