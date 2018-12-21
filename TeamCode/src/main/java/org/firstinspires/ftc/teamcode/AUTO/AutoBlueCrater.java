package org.firstinspires.ftc.teamcode.AUTO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "AUTO Blue Crater Start", group = "AUTO")
public class AutoBlueCrater extends AUTOMecanumAbstractPracticeBot{

    public void runOpMode() throws InterruptedException {
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }

        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(600, 0.3);
        setHangTowerPosition(0);
        sleep(750);
        setHangTowerPosition(0);
        turnDegrees(-0.5, 40);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }

        sleep(750);
        driveStraight(890, -0.5);
        setHangTowerPosition(-11222);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        sleep(750);
        //turnDegrees(-0.4, 79);
        turnDegrees(0.5, 40);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        driveStraight(3700, 0.4);
/*
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
            driveStraight(275, 0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            while (y == -1 && opModeIsActive()) {
                sleep(500);
                y = tfodGet();
            }
            driveStraight(275, -0.25);
            sleep(100);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 58);
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
*/
      /*  turnDegrees(-0.3, 19);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }

        int z = -1;
        sleep(1000);
        wipeCamera();
        sleep(2000);
        while (z == -1) {
            z = tfodGetMultiple();
            sleep(666);
        }
        turnDegrees(0.3, 16);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        */
      int z = 2;
        driveStraight(3700, -0.5);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        turnDegrees(-0.3, 23);
        if (!opModeIsActive()) {
            stopMotors();
            return;
        }
        if (z == 0) {
            hitMineralBlueCrater(true, false, false);
        }
        else if (z == 1) {
            hitMineralBlueCrater(false, true, false);
        }
        else {
            hitMineralBlueCrater(false, false, true);
        }
        motorCollectRotate.setPower(-1);
        sleep(500);
        motorCollectRotate.setPower(0);
        sleep(6000);
    }
}
