package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Mecanum Blue Wall Single Glyph", group="AUTO")
@Disabled

public class AUTOMecanumBlueWallPracticeBot extends AUTOMecanumAbstractCayenne {
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
        driveStraight(ONE_WHEEL_ROTATION + ONE_WHEEL_ROTATION / 2, -0.5);//+3*ONE_WHEEL_ROTATION/4
        if (cryptoboxPos.equals("RIGHT"))
        {
            turnDegrees(-.4,90.0);
            driveStraight(ONE_WHEEL_ROTATION,0.2);
            turnDegrees(-.6,60.0);
            sleep(700);
            driveStraight(1,-0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        } else if (cryptoboxPos.equals("CENTER"))
        {
            turnDegrees(-.4,125.0);
            sleep(500);
            driveStraight(ONE_WHEEL_ROTATION/4,0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        } else {
            turnDegrees(-.4,142.0);
            sleep(500);
            driveStraight(ONE_WHEEL_ROTATION/4,0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        }
    }
}