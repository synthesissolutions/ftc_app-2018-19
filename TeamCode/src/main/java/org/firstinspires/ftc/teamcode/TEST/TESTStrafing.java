package org.firstinspires.ftc.teamcode.TEST;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AUTO.AUTOMecanumAbstractPracticeBot;
@Autonomous(name = "Test Strafing", group = "AUTO")

public class TESTStrafing extends AUTOMecanumAbstractPracticeBot{
    @Override
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        //drive0(1220, .5);
        holonomic(0, 0, -0.8, MECANUM_MAX_SPEED);
        sleep(2000);
    }
}
