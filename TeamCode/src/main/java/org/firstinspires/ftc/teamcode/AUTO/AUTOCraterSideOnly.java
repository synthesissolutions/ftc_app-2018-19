package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Crater Only", group = "AUTO")
public class AUTOCraterSideOnly extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-7000); //deploy from lander
        sleep(3000);

        //move away from lander
        driveStraight(420, 0.5);
        sleep(500);
        drive0(1900, -1.0);
        stopMotors();
        sleep(666);
        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        } else {
    }
        sleep(500);

        //align for mineral detection
        driveStraight(400, 1.0);
        deployMineralArm();
        sleep(1000);



        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4350, -0.3);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        driveStraight(50, -1.0);
        sleep(1000);



        //knock off mineral
        drive0(1000, -1.0);
        sleep(500);
        drive0(1100, 1.0);
//        turnDegrees(-1.0, 110);
//        driveStraight(800, -1.0);

        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        } else {
        }
        sleep(500);

        driveStraight(moveAmount + 1250, 1.0);
        turnDegrees(-1.0, 98);
        driveStraight(2200, 1.0);
        //deliver  marker
        stopMotors();
        drive0(666, -1.0);
        sleep(500);
        deployMarker(-5);
        retractMineralArm();
        //drive0(500, 1.0);
        sleep(500);
        driveStraight(3200, -1.0);
        turnDegrees(1.0, 65);
        sleep(1000);
        drive0(666, -1.0);
        driveStraight(2000, -1.0);

    }
}
