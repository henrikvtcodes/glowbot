/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.led;

/**
 * Represents a subsection of an LED Strip <br>
 * </br>
 */
final class LEDSection {
    public final int startIdx;
    public final int endIdx;
    public final int length;

    public LEDSection(int start, int end) {
        startIdx = start;
        endIdx = end;
        length = Math.abs(end - start);
    }
}
