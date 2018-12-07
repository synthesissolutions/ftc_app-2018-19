package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Blue Depot Start", group = "AUTO")

public class AUTOBlueDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }
        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-11111);
        sleep(6666);
        driveStraight(ONE_WHEEL_ROTATION/2, 0.3);
        sleep(1000);
        offsetPhoneGyro();
        //setHangTowerPosition(0);
        turnDegrees(-0.8, 15);
        sleep(1000);
        driveStraight(1525, -0.5);
        sleep(1000);
        turnDegrees(-0.5, 90);
        sleep(500);
        /*int x = tfodGet();
        while (x == -1) {
            x = tfodGet();
            sleep(500);
        }
        int y = -1;
        if (x != 0) {*/
            turnDegrees(0.5, 30);
            /*while (y == -1) {
                sleep(500);
                y = tfodGet();
            }*/
            turnDegrees(0.5, 45);
      /*  }
        else {*/
//            turnDegrees(0.3, 80);
        /*}
        int z = -1;
        if (x != 0 && y != 0) {
            z = 0;
        }
        if (x == 0 && y != 0) {
            z = 1;
        }
        if (x != 0 && y == 0) {
            z = 2;
        }*/
        driveStraight(ONE_WHEEL_ROTATION/2, -0.3);
        //turnDegrees(-0.3, 190);
        int z =0;
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
