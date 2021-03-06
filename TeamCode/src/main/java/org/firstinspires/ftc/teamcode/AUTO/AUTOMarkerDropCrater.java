package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Marker Drop Crater", group = "AUTO")
public class AUTOMarkerDropCrater extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-7000);
        sleep(3000);
        driveStraight(420, 0.3);
        sleep(500);
        drive0(1700, -1.0);
        stopMotors();
        sleep(500);
        driveStraight(3000, 1.0);
        turnDegrees(-0.4, 105);
        driveStraight(2200, 1.0);
        //deliver  marker
        stopMotors();
        sleep(1000);
        deployMarker(2000);
        retractMineralArm();
        driveStraight(3200, -0.5);
        turnDegrees(0.4, 65);
        sleep(1000);
        drive0(666, -1.0);
        driveStraight(2000, -1.0);
    }
}
