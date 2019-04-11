package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Full Auto Ghost", group = "AUTO")
public class AUTONewFullAutoCopy extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();

        runToHangTowerPosition(6100); //deploy from lander

        //move away from lander
        driveStraight(180, -0.5);
        sleep(500);
        drive0(1450, -0.8);
        stopMotors();
        sleep(666);
        if(getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading() / 2));
        }
        else if(getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading() / 2);
        }
        else {}
        sleep(500);

        //align for mineral detection
        driveStraight(333, 1.0);
        sleep(500);
        deployMineralArm();
        sleep(2000);


        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveAndColor(2350, -0.2);
        moveAmount = Math.abs((this.motorFrontLeft.getCurrentPosition() - moveAmount));
        telemetry.addData("how far moved: ", moveAmount);
        telemetry.update();
        //driveStraight(50, -1.0);

        //knock off mineral and deploy marker
        drive0(1000, -1.0);
        sleep(666);
        drive0(650, 1.0);
        if(getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        }
        else if(getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        }
        else {

        }
        sleep(500);
        deployMarkerCopyGhost(moveAmount);
        retractMineralArm();
        drive0(350, 1.0);
        sleep(300);
        if(getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        }
        else if(getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        }
        else {

        }
        sleep(500);

        //drive to end and turn toward crater side
        driveStraight(2350 - moveAmount, -1.0);
        //drive0(350, -1.0);

        //drive to mineral and park on crater
        if (moveAmount < 1000) {
            driveStraight(500, -1.0);
            turnDegrees(1.0, 90);
            sleep(500);
            driveStraight(800, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(500, -1.0);
        } else if (moveAmount < 1600) {
            driveStraight(400, -1.0);
            turnDegrees(1.0, 90);
            sleep(500);
            driveStraight(1600, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(500, -1.0);
        } else if (moveAmount < 2000) {
            driveStraight(1000, -1.0);
            turnDegrees(1.0, 90);
            sleep(500);
            driveStraight(2600, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(500, -1.0);
        } else {
            driveStraight(420, -1.0);
            turnDegrees(1.0, 90);
            sleep(500);
            driveStraight(2600, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(500, -1.0);
        }
        sleep(1000);
    }
}
