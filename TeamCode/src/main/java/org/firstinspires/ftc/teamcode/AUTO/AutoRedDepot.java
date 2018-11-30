package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Red Depot Start", group = "AUTO")

public class AutoRedDepot extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-20222);
        sleep(10000);
        driveStraight(ONE_WHEEL_ROTATION/6, -0.3);
        offsetPhoneGyro();
        setHangTowerPosition(0);
        turnDegrees(-0.8, 15);
        driveStraight(ONE_WHEEL_ROTATION + ONE_WHEEL_ROTATION/8, 0.3);
        turnDegrees(0.3,222);

        //turnDegrees(-0.2, 45 - vuforiaGetDataWIP());
        hitMineralRedDepot(true, false, false);
        sleep(6000);
    }
}
