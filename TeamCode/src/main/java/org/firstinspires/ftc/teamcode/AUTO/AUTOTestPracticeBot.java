package org.firstinspires.ftc.teamcode.AUTO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PreviousYear.AUTOMecanumAbstractCayenne;



@Autonomous(name="AUTO Test", group="AUTO")
@Disabled
public class AUTOTestPracticeBot extends AUTOMecanumAbstractPracticeBot {
    @Override
    public void runOpMode() throws InterruptedException {
        startAutoOp();

        sleep(500);
        driveStraight(ONE_WHEEL_ROTATION * 2, -0.5);//+3*ONE_WHEEL_ROTATION/4


        turnDegrees(-.4,90.0);
        sleep(500);

        turnDegrees(.4,90.0);
        sleep(500);

        turnDegrees(-.4,135.0);
        sleep(500);

        turnDegrees(-.4,135.0);
        sleep(500);

        turnDegrees(-.4,90.0);
        sleep(500);




        drive0(ONE_WHEEL_ROTATION * 2, -0.5);
        sleep(500);

        drive0(ONE_WHEEL_ROTATION * 2, 0.5);
        sleep(500);

        drive45(ONE_WHEEL_ROTATION * 2, -0.5);
        sleep(500);

        drive45(ONE_WHEEL_ROTATION * 2, 0.5);
        sleep(500);

        drive135(ONE_WHEEL_ROTATION * 2, -0.5);
        sleep(500);

        drive135(ONE_WHEEL_ROTATION * 2, 0.5);
        sleep(500);

    }
}