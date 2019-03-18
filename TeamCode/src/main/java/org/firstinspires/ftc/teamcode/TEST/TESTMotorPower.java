package org.firstinspires.ftc.teamcode.TEST;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AUTO.AUTOMecanumAbstractPracticeBot;


@Autonomous(name = "Test Motor Power", group = "AUTO")

//@Disabled

public class TESTMotorPower extends LinearOpMode {
    public void runOpMode() throws InterruptedException{
        DcMotor  testMotor   = null ;

        this.hardwareMap = null; {
        testMotor  = this.hardwareMap.get(DcMotor.class, "test_motor");

        testMotor.setPower(0);

        waitForStart();
            //testMotor = this.hardwareMap.get(Servo.class, "left_hand");
                testMotor.setPower(1);
                sleep(20000);
                testMotor.setPower(0);
        }
    }
}