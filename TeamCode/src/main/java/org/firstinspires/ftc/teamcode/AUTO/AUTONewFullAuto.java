package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Full Auto", group = "AUTO")
public class AUTONewFullAuto extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        //setHangTowerPosition(-6350); //deploy from lander
        //sleep(3000);

        //move away from lander
        driveStraight(420, 0.3);
        sleep(500);
        drive0(2222, -1.0);
        stopMotors();
        sleep(1000);
        if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.25, (-1 * getGyroCurrentHeading()));
        }
        else {
            turnDegrees(-0.25, getGyroCurrentHeading());
        }
        sleep(500);

        //align for mineral detection
        driveStraight(750, 1.0);
        deployMineralArm();
        sleep(1000);


        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4700, -0.25);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        sleep(1000);

        //knock off mineral and deploy marker
        drive0(1000, -0.8);
        deployMarker(moveAmount);
        retractMineralArm();
        drive0(1000, 0.8);
        sleep(1000);

        //drive to end and turn toward crater side
        driveStraight(4700 - moveAmount, -1.0);
        turnDegrees(0.4, 105);
        sleep(500);
        drive0(350, -0.8);

        //drive to mineral and park on crater
        if (moveAmount < 1600) {
            driveStraight(1500, -1.0);
            turnDegrees(-0.4, 110);
            driveStraight(400, -1.0);
        } else if (moveAmount < 2800) {
            driveStraight(3200, -1.0);
            turnDegrees(-0.4, 110);
            driveStraight(400, -1.0);
        } else if (moveAmount < 3900) {
            driveStraight(4500, -1.0);
            turnDegrees(-0.4, 110);
            driveStraight(400, -1.0);
        } else {
            driveStraight(1500, -1.0);
            turnDegrees(-0.4, 110);
            driveStraight(400, -1.0);
        }
    }
}
