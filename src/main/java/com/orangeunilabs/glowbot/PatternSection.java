package com.orangeunilabs.glowbot;

import com.orangeunilabs.glowbot.pattern.LEDPattern;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import lombok.Getter;
import lombok.NonNull;

import java.util.Iterator;

public class PatternSection implements Iterable<Pixel>, GlowbotControllable {
    @Getter
    private String name;
    /**
     * These ints represent the start and end indexes of this section in the actual LED buffer. They are
     */
    private final int rootStartIdx, rootEndIdx;
    private boolean isRoot;
    @Getter
    private final int length;
    private final int sectionStartIdx, sectionEndIdx;
    private final GlowbotLEDBuffer rootBuffer;
    private LEDPattern currentPattern;
    private boolean patternFirstRun = true;

    /**
     * This represents a section of LEDs that a pattern can run on.
     * The start and end indexes are inclusive. Ranges must be at least 2 pixels
     *
     * @param buffer   the root buffer to set patterns on
     * @param startIdx the first LED within the range of control (must be greater than zero and less than
     * @param endIdx   the last LED within the range of control
     * @param sectionName an optional name for the section (i.e. frame, underglow) if not needed, pass in null.
     */
    public PatternSection(GlowbotLEDBuffer buffer, int startIdx, int endIdx, String sectionName) {
        rootBuffer = buffer;

        // Validate LED indexes
        if (startIdx < 0 || startIdx > endIdx) {
            throw new GlowbotException(String.format("PatternSection: Invalid Start Index %s", startIdx));
        } else if (endIdx > buffer.getLength() - 1) {
            throw new GlowbotException(String.format("Glowbot(PatternSection): Invalid End Index: " +
                    "%s ", startIdx));
        } else if ((endIdx - startIdx) + 1 < 2) {
            throw new GlowbotException("Glowbot(PatternSection): Section must be 2 pixels or greater.");
        }

        // The root indexes are the literal postions of the section in the buffer that
        // represents the physical LED strip
        rootStartIdx = startIdx;
        rootEndIdx = endIdx;
        sectionStartIdx = 0;
        sectionEndIdx = rootEndIdx - rootStartIdx;
        // End minus start does not return ALL the LEDs.
        length = (rootEndIdx - rootStartIdx) + 1;

        if (sectionName != null) {
            name = sectionName;
        }

        currentPattern = null;
    }

    /**
     * Iterate over LEDs using cool for-each syntax
     */
    @Override
    public Iterator<Pixel> iterator() {
        return new Iterator<>() {
            private final int endRootIndex = rootEndIdx;
            private int currentRootIndex = rootStartIdx;
            // This gets incremented and passed to the Pixel for consistency
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentRootIndex <= endRootIndex;
            }

            @Override
            public Pixel next() {
                currentIndex++;
                currentRootIndex++;
                return new Pixel(currentIndex, () -> rootBuffer.getLED(currentRootIndex), (Color color) -> rootBuffer.setLED(currentRootIndex, color));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("LEDs cannot be removed!");
            }
        };
    }

    @Override
    public Color get(int index) {
        return rootBuffer.getLED(calcRealIndex(index));
    }

    @Override
    public Color8Bit get8Bit(int index) {
        return rootBuffer.getLED8Bit(calcRealIndex(index));
    }

    @Override
    public void set(int index, Color color) {
        rootBuffer.setLED(calcRealIndex(index), color);
    }

    @Override
    public void set(int index, Color8Bit color) {
        rootBuffer.setLED(calcRealIndex(index), color);
    }

    /**
     * Set a section of this section to a color
     *
     * @param start the index of the LED at the start of the range (starting at 0)
     * @param end   the index of the LED at the end of the range
     * @param color {@link Color8Bit}
     */
    @Override
    public void setRange(int start, int end, Color8Bit color) {
        start = MathUtil.clamp(start, sectionStartIdx, sectionEndIdx);
        end = MathUtil.clamp(end, sectionStartIdx, sectionEndIdx);

        int rangeRootStart = calcRealIndex(start);
        int rangeRootEnd = calcRealIndex(end);

        for (int idx = rangeRootStart; idx <= rangeRootEnd; idx++) {
            rootBuffer.setLED(idx, color);
        }
    }

    /**
     * Set a section of this section to a color
     *
     * @param start the index of the LED at the start of the range (starting at 0)
     * @param end   the index of the LED at the end of the range
     * @param color {@link Color}
     */
    @Override
    public void setRange(int start, int end, Color color) {
        start = MathUtil.clamp(start, sectionStartIdx, sectionEndIdx);
        end = MathUtil.clamp(end, sectionStartIdx, sectionEndIdx);

        int rangeRootStart = calcRealIndex(start);
        int rangeRootEnd = calcRealIndex(end);

        for (int idx = rangeRootStart; idx <= rangeRootEnd; idx++) {
            rootBuffer.setLED(idx, color);
        }
    }


    public void setName(@NonNull String newName) {
        name = newName;
    }

    public void setCurrentPattern(LEDPattern newPattern) {
        if (currentPattern != newPattern) {
            patternFirstRun = true;
            currentPattern = newPattern;
        }
    }

    /**
     * Calls the periodic method of the currently running pattern.
     */
    public void periodic() {
        if (currentPattern != null) {
            GlowbotControllable controllable = this;
            currentPattern.runPattern(controllable, patternFirstRun);
            patternFirstRun = false;
        }
    }

    /**
     * Check if another pattern section overlaps with this one
     * @param compare the section to compare with
     * @return whether the given section overlaps with this one
     */
    public boolean overlaps(PatternSection compare){
        return overlaps(compare.rootStartIdx, compare.rootEndIdx);
    }

    /**
     * Check if a set of indexes overlaps with this section
     * @param start the starting index
     * @param end the ending index
     * @return whether the given section overlaps with this one
     */
    public boolean overlaps(int start, int end) {
        return start <= rootEndIdx || end <= rootStartIdx;
    }

    private int calcRealIndex(int idx) {
        return idx + rootStartIdx;
    }

    @Override
    public String toString() {
        return String.format("LED Section %s; starting at %d; ending at %d", name, rootStartIdx, rootEndIdx);
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;

        PatternSection psCompare = (PatternSection) obj;

        if (psCompare == this) return true;

        return psCompare.rootStartIdx == rootStartIdx && psCompare.rootEndIdx == rootEndIdx;
    }
}
