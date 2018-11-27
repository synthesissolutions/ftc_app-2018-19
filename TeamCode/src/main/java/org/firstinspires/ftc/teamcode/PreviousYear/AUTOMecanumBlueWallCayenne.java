package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@Autonomous(name="AUTO Mecanum Blue Wall", group="AUTO")
public class AUTOMecanumBlueWallCayenne extends AUTOMecanumAbstractCayenne {
    @Override
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        grabGlyph();
        raiseTowerGlyph();
        jewelPressBlue();
        sleep(500);
        String cryptoboxPos=checkRelicVuforia();
        telemetry.addData("cryptoboxPos: ",cryptoboxPos);
        telemetry.update();
        //driveStraight(1830, -0.5);//ONE_WHEEL_ROTATION + ONE_WHEEL_ROTATION / 2
        if (cryptoboxPos.equals("RIGHT"))
        {
            driveStraight(1375, -0.5);
            turnDegrees(-.4,90.0);
            driveStraight(1000,0.2);//ONE_WHEEL_ROTATION
            turnDegrees(-.4,48.0);
            sleep(700);
            driveStraight(1,-0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        } else if (cryptoboxPos.equals("CENTER"))
        //if (true)
        {
            driveStraight(1600, -0.5);
            turnDegrees(-.4,105.0);
            sleep(500);
            driveStraight(305,0.2);//ONE_WHEEL_ROTATION/4
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        } else {
            driveStraight(1420, -0.5);
            turnDegrees(-.4,153.0);//ONE_WHEEL_ROTATION/4
            sleep(500);
            driveStraight(305,0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        }
    }
}