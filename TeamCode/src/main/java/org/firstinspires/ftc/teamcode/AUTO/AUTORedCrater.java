package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Red Crater Start", group = "AUTO")


public class AUTORedCrater extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-20222);
        sleep(10000);
        driveStraight(ONE_WHEEL_ROTATION/6, -0.3);
        offsetPhoneGyro();
        setHangTowerPosition(0);
        turnDegrees(-0.8, 17);
        driveStraight(ONE_WHEEL_ROTATION + ONE_WHEEL_ROTATION/8, 0.3);
        turnDegrees(0.3,222);

        //turnDegrees(-0.2, 45 - vuforiaGetDataWIP());
        hitMineralRedCrater(false, false, true);
        sleep(6000);
    }
}
