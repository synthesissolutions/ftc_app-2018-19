package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Red Depot Start", group = "AUTO")

public class AutoRedDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }
        setHangTowerPosition(-11222);
        sleep(6000);
        driveStraight(ONE_WHEEL_ROTATION/2, 0.3);
        sleep(1000);
        turnDegrees(-0.5, 27);
        sleep(1000);
        driveStraight(1200, -0.5);
        sleep(1000);
        turnDegrees(-0.5, 80);
        sleep(500);
        int x = tfodGet();
        while (x == -1) {
            x = tfodGet();
            sleep(500);
        }
        int y = -1;
        if (x != 0) {
            turnDegrees(0.5, 30);
            while (y == -1) {
                sleep(500);
                y = tfodGet();
            }
            turnDegrees(0.5, 60);
        }
        else {
            turnDegrees(0.5, 90);
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

        if (z == 0) {
            hitMineralRedDepot(true, false, false);
        }
        else if (z == 1) {
            hitMineralRedDepot(false, true, false);
        }
        else {
            hitMineralRedDepot(false, false, true);
        }
        sleep(6000);
    }
}
