package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Red Depot Start", group = "AUTO")

public class AutoRedDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }
        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(555, 0.3);
        sleep(750);
//        setHangTowerPosition(0);
        turnDegrees(-0.5, 25);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }

        sleep(750);
        driveStraight(1120, -0.5);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        sleep(750);
        turnDegrees(-0.5, 80);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        sleep(500);
        int x = tfodGet();
        while (x == -1 && opModeIsActive()) {
            x = tfodGet();
            sleep(500);
        }
        int y = -1;
        if (x != 0 && opModeIsActive()) {
            turnDegrees(0.5, 30);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(150, 0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            while (y == -1 && opModeIsActive()) {
                sleep(500);
                y = tfodGet();
            }
            driveStraight(150, -0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 60);
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
            hitMineralBlueDepot(true, false, false);
        }
        else if (z == 1) {
            hitMineralBlueDepot(false, true, false);
        }
        else {
            hitMineralBlueDepot(false, false, true);
        }
        sleep(6000);
    }
}
