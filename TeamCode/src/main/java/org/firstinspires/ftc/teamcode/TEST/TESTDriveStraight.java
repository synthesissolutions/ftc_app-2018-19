package org.firstinspires.ftc.teamcode.TEST;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AUTO.AUTOMecanumAbstractPracticeBot;


@Autonomous(name = "Drive Straight Test", group = "AUTO")

@Disabled

public class TESTDriveStraight extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        driveStraight(ONE_WHEEL_ROTATION, 0.5);
//        sleep(5000);
        getGyroReading();
        sleep(5000);
        turnDegrees(0.5, 90);
        getGyroReading();
        sleep(5000);
    }
}
