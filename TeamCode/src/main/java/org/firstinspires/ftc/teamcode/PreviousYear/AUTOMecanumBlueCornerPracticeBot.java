package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Mecanum Blue Corner Single Glyph", group="AUTO")
@Disabled

public class AUTOMecanumBlueCornerPracticeBot extends AUTOMecanumAbstractCayenne {
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
        if (cryptoboxPos.equals("RIGHT"))
        {
            driveStraight(ONE_WHEEL_ROTATION*2+ONE_WHEEL_ROTATION/4, -0.5);
        } else if (cryptoboxPos.equals("CENTER"))
        {
            driveStraight(ONE_WHEEL_ROTATION+ 3 * ONE_WHEEL_ROTATION / 4, -0.5);//+3*ONE_WHEEL_ROTATION/4
        } else {
            driveStraight(ONE_WHEEL_ROTATION + 10 * ONE_WHEEL_ROTATION / 27, -0.5);//+3*ONE_WHEEL_ROTATION/4
        }
        sleep(500);
        turnDegrees(.7,94.0);
        driveStraight(ONE_WHEEL_ROTATION/3,0.2);
        //goLeftGlyphBlue();
        //drive0(-ONE_WHEEL_ROTATION,0.5);
        sleep(700);
        lowerTowerGlyph();
        sleep(500);
        releaseGlyph();
        driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
        driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        lowerJewel();
    }
}