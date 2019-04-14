package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Crater Side Only Ghost", group = "AUTO")
public class AUTOCraterSideOnlyGhost extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        runToHangTowerPosition(6100); //deploy from lander

        //move away from lander
        driveStraight(175, -0.5);
        sleep(500);
        drive0(1620, -1.0);
        stopMotors();
        sleep(666);
        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-3 * getGyroCurrentHeading()) / 4);
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, 3 * getGyroCurrentHeading() / 4);
        } else {
        }
        sleep(500);

        //align for mineral detection
        driveStraight(333, 1.0);
        sleep(500);
        deployMineralArm();
        sleep(2000);



        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveAndColor(2350, -0.2);
        driveStraight(75, 1.0);
        moveAmount = Math.abs((this.motorFrontLeft.getCurrentPosition() - moveAmount));
        sleep(1000);
        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-3 * getGyroCurrentHeading()) / 4);
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, 3 * getGyroCurrentHeading() / 4);
        } else {
        }
        sleep(500);



        //knock off mineral
        drive0(1000, -1.0);
        sleep(500);
        drive0(1100, 1.0);
//        turnDegrees(-1.0, 110);
//        driveStraight(800, -1.0);

        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()) / 2);
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading() / 2);
        } else {
        }
        sleep(500);

        driveStraight(moveAmount, 1.0);
        turnDegrees(-1.0, 90);
        driveStraight(900, 1.0);
        //deliver  marker
        stopMotors();
        drive0(200, -1.0);
        sleep(500);
        deployMarker(-5);
        retractMineralArm();
        drive0(200, 1.0);
        sleep(500);
        if (getGyroCurrentHeading() + 90 < -4) {
            turnDegrees(0.3, (-1 * (getGyroCurrentHeading() + 90)) / 2);
        } else if (getGyroCurrentHeading() + 90 > 4) {
            turnDegrees(-0.3, (getGyroCurrentHeading() + 90) / 2);
        } else {
        }
        driveStraight(2000, -1.0);
        turnDegrees(1.0, 40);
        sleep(1000);
        drive0(666, -1.0);
        driveStraight(666, -1.0);
    }
}
