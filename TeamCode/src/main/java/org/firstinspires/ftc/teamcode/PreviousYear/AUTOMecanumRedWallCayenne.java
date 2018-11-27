package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Mecanum Red Wall", group="AUTO")
@Disabled
public class AUTOMecanumRedWallCayenne extends AUTOMecanumAbstractCayenne {
    @Override
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        grabGlyph();
        raiseTowerGlyph();
        jewelPressRed();
        sleep(500);
        String cryptoboxPos=checkRelicVuforia();
        telemetry.addData("cryptoboxPos: ",cryptoboxPos);
        telemetry.update();
        //driveStraight(2000, 0.5);//ONE_WHEEL_ROTATION + 3 * ONE_WHEEL_ROTATION / 8
        if (cryptoboxPos.equals("LEFT"))
        {
            driveStraight(2000, 0.5);
            turnDegrees(-.4,80.0);
            driveStraight(625,0.2);//ONE_WHEEL_ROTATION
            turnDegrees(.4,50.0);
            driveStraight(1,-0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        } else if (cryptoboxPos.equals("CENTER"))
        {
            driveStraight(1800, 0.5);
            turnDegrees(-.4,15.0);
            sleep(500);
            driveStraight(305,0.2);//ONE_WHEEL_ROTATION/4
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
            /*driveStraight(1950, 0.5);
            turnDegrees(-.4, 23.0);
            sleep(500);
            driveStraight(305,0.2);//ONE_WHEEL_ROTATION/4
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6*/
        } else {
            driveStraight(2200, 0.5);
            turnDegrees(-.4, 5);
            sleep(500);
            driveStraight(305,0.2);//ONE_WHEEL_ROTATION/4
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
            driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        }
    }
}