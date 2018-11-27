package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TEST Relic Claw", group="TEST")

@Disabled
public class TESTRelicClaw extends OpMode {

    Servo servo;
    Servo servoIdolClaw;
    double servoPosition =.5;
    double servoCP;
    @Override
    public void init() {

        servo = hardwareMap.servo.get("servo");
        servoIdolClaw = hardwareMap.servo.get("servoIdolClaw");
        servoCP = .5;

        servo.setPosition(servoPosition);
        servoIdolClaw.setPosition(servoCP);

    }

    @Override
    public void loop() {
        servo.setPosition(servoPosition);
        servoIdolClaw.setPosition(servoCP);
        if(gamepad1.a){
            servoPosition = 0;
        }

        if(gamepad1.b){
            servoPosition = .5;
        }

        if(gamepad1.x){
            servoPosition = 1;
        }

        if (gamepad1.right_bumper) {
            servoCP += 0;
        }
        else if (gamepad1.left_bumper) {
            servoCP -= .5;
        }

    }
}
