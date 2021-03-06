package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name = "AUTO Red Depot Start", group = "AUTO")
@Disabled
public class AutoRedDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }
        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(700, 0.3);
        setHangTowerPosition(0);
        sleep(750);
        setHangTowerPosition(0);
        turnDegrees(-0.5, 33);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }

        sleep(750);
        driveStraight(1000, -0.5);
        setHangTowerPosition(-11222);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        sleep(750);
        turnDegrees(-0.5, 79);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        /*
        sleep(500);
        driveStraight(150, 0.25);
        sleep(100);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }*/
        wipeCamera();
        int x = -1;
        sleep(1000);
        x = tfodGet();

        /*
        driveStraight(150, -0.25);
        sleep(100);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }*/

        int y = -1;
        if (x != 0 && opModeIsActive()) {
            turnDegrees(0.5, 32);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(400, 0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            wipeCamera();
            sleep(1000);
            y = tfodGet();
            driveStraight(400, -0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 55);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else {
            turnDegrees(0.5, 90);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        int z = -1;
        if (x != 0 && y != 0) {
            z = 0;
        }
        if (x == 0 && y != 0) {
            z = 1;
        }
        if (x != 0 && y == 0) {
            z = 2;
        }
        //driveStraight(ONE_WHEEL_ROTATION/2, -0.3);
        //turnDegrees(0.5, 5);
        //turnDegrees(-0.3, 190);
        //int z = 2;
        //turnDegrees(-0.2, 45 - vuforiaGetDataWIP());
        if (z == 0) {
//            hitMineralRedDepot(true, false, false);
        }
        else if (z == 1) {
//            hitMineralRedDepot(false, true, false);
        }
        else {
//            hitMineralRedDepot(false, false, true);
        }
        sleep(6000);
    }
}
