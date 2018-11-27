package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TEST Glyph", group="TEST")
@Disabled
public class TESTGlyph extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

            Servo leftServo = hardwareMap.servo.get("left_servo");
            Servo rightServo = hardwareMap.servo.get("right_servo");

            leftServo.setPosition(0.55);
            rightServo.setPosition(0.4);

            sleep(3000);

            leftServo.setPosition(0.6);
            rightServo.setPosition(0.35);

    }
}
