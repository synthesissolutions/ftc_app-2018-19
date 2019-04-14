package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Depot Side Only Ghost", group = "AUTO")
public class AUTODepotSideOnlyGhost extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();

        runToHangTowerPosition(6100); //deploy from lander

        //move away from lander
        driveStraight(180, -0.5);
        sleep(500);
        drive0(1550, -0.8);
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
        drive0(400, 1.0);
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

        //drive to other crater and park
        driveStraight(moveAmount + 750, 1.0);
        turnDegrees(-1.0, 42);
        drive0(250, -1.0);
        driveStraight(500, 0.7);

    }
}
