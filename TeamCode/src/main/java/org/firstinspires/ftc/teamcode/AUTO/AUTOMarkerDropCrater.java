package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Marker Drop Crater", group = "AUTO")
public class AUTOMarkerDropCrater extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-6350);
        sleep(3000);
        driveStraight(420, 0.3);
        sleep(500);
        drive0(1700, -1.0);
        stopMotors();
        sleep(500);
        driveStraight(3200, 1.0);
        turnDegrees(-0.4, 105);
        driveStraight(3200, 1.0);
        //deliver  marker
        stopMotors();
        sleep(2000);
        driveStraight(3200, -0.5);
        turnDegrees(0.4, 50);
        sleep(1000);
        drive0(1000, -1.0);
        driveStraight(1000, -1.0);
    }
}
