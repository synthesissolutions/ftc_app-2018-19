package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Full Auto Copy", group = "AUTO")
public class AUTONewFullAutoCopy extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-1000); //deploy from lander
        sleep(3000);

        //move away from lander
        driveStraight(300, -0.5);
        sleep(500);
        drive0(1475, -1.0);
        stopMotors();
        sleep(666);
        if(getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        }
        else if(getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        }
        else {}
        sleep(500);

        //align for mineral detection
        driveStraight(333, 1.0);
        deployMineralArm();
        sleep(2000);


        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveAndColor(2200, -0.33);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        telemetry.addData("how far moved: ", moveAmount);
        telemetry.update();
        //driveStraight(50, -1.0);
        sleep(1000);

        //knock off mineral and deploy marker
        drive0(500, -1.0);
        sleep(666);
        drive0(200, 1.0);
        deployMarkerCopyGhost(moveAmount);
        retractMineralArm();
        drive0(150, 1.0);
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
        driveStraight(2200 + moveAmount, -1.0);
        turnDegrees(1.0, 90);
        sleep(500);
        //drive0(350, -1.0);

        //drive to mineral and park on crater
        if (moveAmount < 500) {
            driveStraight(1650, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(800, -1.0);
        } else if (moveAmount < 1000) {
            driveStraight(3000, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(800, -1.0);
        } else if (moveAmount < 1500) {
            driveStraight(4200, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(800, -1.0);
        } else {
            driveStraight(1500, -1.0);
            turnDegrees(-1.0, 110);
            driveStraight(800, -1.0);
        }
        sleep(1000);
    }
}
