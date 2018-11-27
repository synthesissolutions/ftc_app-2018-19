package org.firstinspires.ftc.teamcode.PreviousYear.MINIBOT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MINIBOT BALLOON WAGGLER", group="MINIBOT")
@Disabled
public class MINIBOTWavingBalloon extends OpMode {
    Servo servoBalloon;
    double balloonH=0.7;
    double balloonHprime=0.01;

    @Override
    public void init() {
        servoBalloon = hardwareMap.servo.get("servoBalloon");
    }

    @Override
    public void loop() {
        balloonH+=balloonHprime;
        if (balloonH>=0.99 || balloonH<=0.5)
        {
            balloonHprime=-balloonHprime;

        }
        servoBalloon.setPosition(balloonH);
    }


    @Override
    public void stop() {
    }
}