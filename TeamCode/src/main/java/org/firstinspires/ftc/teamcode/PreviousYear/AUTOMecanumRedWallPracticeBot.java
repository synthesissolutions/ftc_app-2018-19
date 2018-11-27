package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="AUTO Mecanum Red Wall Single Glyph", group="AUTO")
@Disabled

public class AUTOMecanumRedWallPracticeBot extends AUTOMecanumAbstractCayenne {
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
        driveStraight(ONE_WHEEL_ROTATION + 4 * ONE_WHEEL_ROTATION / 8, 0.5);//+3*ONE_WHEEL_ROTATION/4
        if (true)
        {
            turnDegrees(-.73,90.0);
            driveStraight(ONE_WHEEL_ROTATION/3,0.2);
            telemetry.addData("GyroDegree: ", currentHeading);
            telemetry.update();
            sleep(3000);
            turnDegrees(.45,38.0);
            /*driveStraight(1,-0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);*/
        } else if (cryptoboxPos.equals("CENTER"))
        {
            turnDegrees(-.7,15.0);
            sleep(500);
            driveStraight(ONE_WHEEL_ROTATION/4,0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        } else {
            turnDegrees(-.623,2.0);
            sleep(500);
            driveStraight(ONE_WHEEL_ROTATION/4,0.2);
            sleep(700);
            lowerTowerGlyph();
            sleep(500);
            releaseGlyph();
            driveStraightOrTime(ONE_WHEEL_ROTATION*8,0.2,eTime.seconds(),1.0);
            driveStraight(ONE_WHEEL_ROTATION/6,-0.2);
        }
        releaseGlyph();
    }
}