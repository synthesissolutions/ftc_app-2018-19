package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Blue Depot Start", group = "AUTO")

public class AUTOBlueDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-20222);
        sleep(10000);
        driveStraight(ONE_WHEEL_ROTATION/6, -0.3);
        offsetPhoneGyro();
        setHangTowerPosition(0);
        turnDegrees(-0.8, 15);
        driveStraight(ONE_WHEEL_ROTATION/2, 0.3);
        turnDegrees(0.3, 100);
        int x = tfodGet();
        int y = -1;
        if (x != 0) {
            turnDegrees(0.3, 15);
            y = tfodGet();
            turnDegrees(-0.3, 115);
        }
        else {
            turnDegrees(-0.3, 100);
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
        driveStraight(ONE_WHEEL_ROTATION/2, 0.3);
        turnDegrees(0.3, 222);

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
