package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Blue Depot Start", group = "AUTO")

public class AUTOBlueDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        //double angle = 0;
        setHangTowerPosition(-20222);
        sleep(10000);
        driveStraight(ONE_WHEEL_ROTATION/6, -0.3);
        offsetPhoneGyro();
        setHangTowerPosition(0);
        turnDegrees(-0.8, 15);
        driveStraight(ONE_WHEEL_ROTATION, 0.3);
        turnDegrees(0.3,222);

        //turnDegrees(-0.2, 45 - vuforiaGetDataWIP());
        hitMineralBlueDepot(false, false, true);
        sleep(6000);
    }

}
