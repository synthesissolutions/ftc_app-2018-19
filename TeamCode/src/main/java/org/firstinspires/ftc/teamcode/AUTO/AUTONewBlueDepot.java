package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW Blue Depot Start", group = "AUTO")


public class AUTONewBlueDepot extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(500, 0.3);
        sleep(3000);
        holonomic(0, 0, -0.8, MECANUM_MAX_SPEED);
        sleep(100);
        stopMotors();
        sleep(1000);
        holonomic(0, 0, -0.8, MECANUM_MAX_SPEED);
        sleep(1000);
    }
}
