package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Mecanum Blue Corner 2 Blocks", group="AUTO")
@Disabled
public class AUTOMecanumBlueCornerCayenne2Blocks extends AUTOMecanumAbstractCayenne {
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
            driveStraight(2900, -0.5);//ONE_WHEEL_ROTATION*2+3*ONE_WHEEL_ROTATION/8
        } else if (cryptoboxPos.equals("CENTER"))
        {
            driveStraight(2300, -0.5);//ONE_WHEEL_ROTATION*2
        } else {
            driveStraight(1600, -0.5);//ONE_WHEEL_ROTATION + 3 * ONE_WHEEL_ROTATION / 7
        }
        sleep(500);
        turnDegrees(.55,95.0);
        driveStraight(406,0.2);//ONE_WHEEL_ROTATION/3
        //goLeftGlyphBlue();
        //drive0(-ONE_WHEEL_ROTATION,0.5);
        sleep(700);
        lowerTowerGlyph();
        sleep(500);
        releaseGlyph();
        driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
        driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
        //if vuforia says right then turn clockwise, go forward, and grab glyph
        //if vuforia says center then turn clockwise, go forward, and grab glyph
        //if vuforia says left then turn clockwise, go forward, turn clockwise again, go forward, and grab glyph
        if (cryptoboxPos.equals("RIGHT"))
        {
           turnDegrees(.55,-135);
           driveStraight(ONE_WHEEL_ROTATION, .22);
           //grab glyph
        } else if (cryptoboxPos.equals("CENTER"))
        {
            turnDegrees(.55,-135);
            driveStraight(ONE_WHEEL_ROTATION, .22);
            //grab glyph
        } else {
            turnDegrees(.55,-45);
            driveStraight(ONE_WHEEL_ROTATION, .22);
            turnDegrees(.55,-90);
            driveStraight(ONE_WHEEL_ROTATION, .22);
            //grab glyph
        }
    }

}