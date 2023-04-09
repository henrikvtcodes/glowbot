/* Copyright (c) 2023 Urban Inspire Corp 501(c)3 d.b.a. Questionable Engineering. All rights reserved. */
/* This work is licensed under the terms of the MPL 2.0 license */
/* found in the root directory of this project. */
package frc.qefrc.qelib.drive;

import java.util.ArrayList;
import java.util.List;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * <b> NOT DONE DO NOT USE </b> <br>
 * </br> Fancier Differential Drive handler class that is designed for use with 4 REV Robotics
 * NEO/Spark Max pairs (2 Per Side) attached to a single-speed gearbox. Current defaults are based
 * on a 2023 Kit of Parts chassis.
 */
public class QEDiffDrive {
    protected DifferentialDriveKinematics kinematics;
    protected final CANSparkMax leftFront, leftRear, rightFront, rightRear;
    protected RelativeEncoder leftEncoder, rightEncoder;
    protected SparkMaxPIDController leftPID, rightPID;
    protected double ratio, wheelSize;
    protected final List<CANSparkMax> allMotors;

    /**
     * @param leftFrontCtrl
     * @param leftRearCtrl
     * @param rightFrontCtrl
     * @param rightRearCtrl
     * @param gearboxRatio
     * @param wheelSizeMeters Wheel diameter. <i> Must be provided in meters! </i>
     */
    public QEDiffDrive(
            CANSparkMax leftFrontCtrl,
            CANSparkMax leftRearCtrl,
            CANSparkMax rightFrontCtrl,
            CANSparkMax rightRearCtrl,
            double gearboxRatio,
            double wheelSizeMeters) {
        leftFront = leftFrontCtrl;
        leftRear = leftRearCtrl;
        rightFront = rightFrontCtrl;
        rightRear = rightRearCtrl;
        allMotors = new ArrayList<CANSparkMax>(List.of(leftFront, leftRear, rightFront, rightRear));

        for (CANSparkMax ctrl : allMotors) {
            ctrl.restoreFactoryDefaults(true);
        }
    }

    /**
     * @param leftFrontID
     * @param leftBackID
     * @param rightFrontID
     * @param rightBackID
     * @param gearboxRatio Ratio of your gearbox. For example, this would be 8.45 for an 8.45:1
     *     gearbox reduction.
     * @param wheelSizeMeters Wheel diameter. <i> Must be provided in meters! </i>
     */
    public QEDiffDrive(
            int leftFrontID,
            int leftBackID,
            int rightFrontID,
            int rightBackID,
            double gearboxRatio,
            double wheelSizeMeters) {
        this(
                new CANSparkMax(rightBackID, MotorType.kBrushless),
                new CANSparkMax(rightBackID, MotorType.kBrushless),
                new CANSparkMax(rightBackID, MotorType.kBrushless),
                new CANSparkMax(rightBackID, MotorType.kBrushless),
                gearboxRatio,
                wheelSizeMeters);
    }
    /**
     * Create an instance with a custom gearbox ratio and Common Wheel sth
     *
     * @param leftFrontID CAN ID for the Left Front Motor (Left Leader)
     * @param leftBackID CAN ID for the Left Rear Motor
     * @param rightFrontID CAN ID for the Right Front Motor (Right Leader)
     * @param rightBackID CAN ID for the Right Rear Motor
     * @param gearboxRatio Ratio of your gearbox. For example, this would be 8.45 for an 8.45:1
     *     gearbox reduction.
     * @param wheelType {@link WheelTypes}
     */
    public QEDiffDrive(
            int leftFrontID,
            int leftBackID,
            int rightFrontID,
            int rightBackID,
            double gearboxRatio,
            WheelTypes wheelType) {
        this(leftFrontID, leftBackID, rightFrontID, rightBackID, gearboxRatio, wheelType.diameter);
    }

    /**
     * Creates an instance with all defaults: 6 inch wheels and an 8.45:1 AndyMark Toughbox Gearbox.
     * These are based on the 2023 Kit Of Parts.
     *
     * @param leftFrontID CAN ID for the Left Front Motor (Left Leader)
     * @param leftBackID CAN ID for the Left Rear Motor
     * @param rightFrontID CAN ID for the Right Front Motor (Right Leader)
     * @param rightBackID CAN ID for the Right Rear Motor
     */
    public QEDiffDrive(int leftFrontID, int leftBackID, int rightFrontID, int rightBackID) {
        this(leftFrontID, leftBackID, rightFrontID, rightBackID, 8.45, WheelTypes.SixInch);
    }
}
