package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Red Corner Ghost", group="AUTO")
@Disabled
public class AUTOMecanumRedCornerGhost extends AUTOMecanumAbstractGhost {
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
        if (cryptoboxPos.equals("LEFT"))
        {
            driveStraight(3500, 0.5); // ONE_WHEEL_ROTATION*2+11*ONE_WHEEL_ROTATION/10
        } else if (cryptoboxPos.equals("CENTER"))
        {
            driveStraight(2900, 0.5);//ONE_WHEEL_ROTATION*2+ONE_WHEEL_ROTATION/2
        } else {
            driveStraight(2200, 0.5);//ONE_WHEEL_ROTATION + 3 * ONE_WHEEL_ROTATION / 8
        }
        sleep(500);
        turnDegrees(.55,55.0);
        driveStraight(305,0.2);//ONE_WHEEL_ROTATION/4
        //goLeftGlyphBlue();
        //drive0(-ONE_WHEEL_ROTATION,0.5);
        sleep(700);
        lowerTowerGlyph();
        sleep(500);
        releaseGlyph();
        driveStraightOrTime(9760,0.2,eTime.seconds(),1.0);//ONE_WHEEL_ROTATION*8
        driveStraight(203,-0.2);//ONE_WHEEL_ROTATION/6
    }
}