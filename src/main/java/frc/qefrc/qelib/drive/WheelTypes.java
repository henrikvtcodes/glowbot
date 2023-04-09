/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.drive;

import edu.wpi.first.math.util.Units;

/** Built in conversions for common wheel sizes. Wheel sizes provided in meters. */
public enum WheelTypes {
    SixInch(Units.inchesToMeters(6)),
    FourInch(Units.inchesToMeters(4)),
    ThreeInch(Units.inchesToMeters(3)),
    ;

    public final double diameter;

    private WheelTypes(double dia) {
        diameter = dia;
    }
}
