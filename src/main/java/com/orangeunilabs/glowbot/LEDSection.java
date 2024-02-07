package com.orangeunilabs.glowbot;

import edu.wpi.first.math.MathUtil;

/**
 * Represents a subsection of an LED Strip. This class does not handle any actual control, it is simply for
 * <br>
 *
 */
public final class LEDSection {
    public final int startIdx;
    public final int endIdx;
    public final int length;

    // No docs because this isn't public
    LEDSection(int start, int end) {
        startIdx = start;
        endIdx = end;
        length = Math.abs(end - start);
    }

    /**
     * Check if a given index is within the section
     *
     * @param index index to check
     * @return whether the index is within the section
     */
    public boolean isWithinSection(int index) {
        return (index >= startIdx && index <= endIdx);
    }

    /**
     * Clamp a value within the section
     *
     * @param index value to clamp
     * @return value clamped to [start, end]
     */
    public int clampWithinSection(int index) {
        return MathUtil.clamp(index, startIdx, endIdx);
    }

    /**
     * Check if two {@link LEDSection}'s overlap
     *
     * @param checkSection other section to check
     * @return true if the sections overlap, false if they're separated
     */
    public boolean testOverlap(LEDSection checkSection) {
        // check if the given start or end points are within this section
        return !isWithinSection(checkSection.startIdx) && !isWithinSection(checkSection.endIdx);
    }
}

