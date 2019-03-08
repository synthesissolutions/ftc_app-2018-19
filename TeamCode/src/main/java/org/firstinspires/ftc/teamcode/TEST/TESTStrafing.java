package org.firstinspires.ftc.teamcode.TEST;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AUTO.AUTOMecanumAbstractPracticeBot;
@Autonomous(name = "Test Strafing", group = "AUTO")
@Disabled

public class TESTStrafing extends AUTOMecanumAbstractPracticeBot{
    @Override
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        driveStraightAndColor(1220, 0.25);
        //drive0(1220, .5);
        sleep(30000);
    }
}
