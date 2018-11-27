package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TEST Jewel", group="TEST")
@Disabled
public class TESTJewel extends LinearOpMode {

    ColorSensor colorSensor;

    @Override
    public void runOpMode() {
        Servo armServo = hardwareMap.servo.get("servoJewelArm");
        Servo flickerServo = hardwareMap.servo.get("servoJewelFlicker");

        armServo.setPosition(0.15);
        flickerServo.setPosition(0.3);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

            armServo.setPosition(.85);
            sleep(2000);

            colorSensor = hardwareMap.get(ColorSensor.class, "sensorJewelColor");
            int blue = colorSensor.blue();
            int red = colorSensor.red();

            if (red > blue) {
                // detected red knock it off
                flickerServo.setPosition(1);
            } else if (blue > red) {
                // detected blue - go the opposite direction
                flickerServo.setPosition(0.0);
            }
        telemetry.addData("red:", red);
        telemetry.addData("blue:", blue);
        telemetry.update();

            sleep(5000);
        armServo.setPosition(0.15);
            sleep(5000);
    }
}
