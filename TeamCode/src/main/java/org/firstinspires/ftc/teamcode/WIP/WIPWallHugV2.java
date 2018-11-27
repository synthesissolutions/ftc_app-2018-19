package org.firstinspires.ftc.teamcode.WIP;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * {@link WIPWallHugV2} illustrates how to use the Modern Robotics
 * Range Sensor.
 *
 * The op mode assumes that the range sensor is configured with a name of "sensor_range".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * @see <a href="http://modernroboticsinc.com/range-sensor">MR Range Sensor</a>
 */
@TeleOp(name = "Wall Hug v2", group = "Sensor")
//@Disabled   // comment out or remove this line to enable this opmode
public class WIPWallHugV2 extends LinearOpMode {

    ModernRoboticsI2cRangeSensor rangeSensor1;
    ModernRoboticsI2cRangeSensor rangeSensor2;
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;

    static final double LOW_SPEED = .3;
    static final double HIGH_SPEED = .5;
    static final double MAX_VALUE = 25;
    static final double MIN_VALUE = 15;


    @Override public void runOpMode() {
        double leftSpeed = HIGH_SPEED;
        double rightSpeed = HIGH_SPEED;
        double leftCurrent = LOW_SPEED;
        double rightCurrent = LOW_SPEED;
        double closestSensor;

        // get a reference to our compass
        rangeSensor1 = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range1");
        rangeSensor2 = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range2");
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // wait for the start button to be pressed
        waitForStart();



        while (opModeIsActive()) {
            leftSpeed = LOW_SPEED;
            rightSpeed = LOW_SPEED;

            telemetry.addData("raw ultrasonic1", rangeSensor1.rawUltrasonic());
            telemetry.addData("raw optical1", rangeSensor1.rawOptical());
            telemetry.addData("cm optical1", "%.2f cm", rangeSensor1.cmOptical());
            telemetry.addData("cm", "%.2f cm1", rangeSensor1.getDistance(DistanceUnit.CM));
            telemetry.addData("raw ultrasonic2", rangeSensor2.rawUltrasonic());
            telemetry.addData("raw optical2", rangeSensor2.rawOptical());
            telemetry.addData("cm optical2", "%.2f cm", rangeSensor2.cmOptical());
            telemetry.addData("cm", "%.2f cm2", rangeSensor2.getDistance(DistanceUnit.CM));
            telemetry.update();

            if (rangeSensor1.rawUltrasonic() < rangeSensor2.rawUltrasonic() ){
                closestSensor=1;
            }
            else {
                closestSensor=2;
            }

            if (rangeSensor1.rawUltrasonic() <= MAX_VALUE && rangeSensor1.rawUltrasonic() >= MIN_VALUE && rangeSensor2.rawUltrasonic() <= MAX_VALUE && rangeSensor2.rawUltrasonic() >= MIN_VALUE){
                leftDrive.setPower(HIGH_SPEED);
                rightDrive.setPower(-HIGH_SPEED);
            }
            else if (closestSensor == 1) {
                leftDrive.setPower(LOW_SPEED);
                rightDrive.setPower(-HIGH_SPEED);
            }
            else {
                rightDrive.setPower(-LOW_SPEED);
                leftDrive.setPower(HIGH_SPEED);
            }

        }


    }}