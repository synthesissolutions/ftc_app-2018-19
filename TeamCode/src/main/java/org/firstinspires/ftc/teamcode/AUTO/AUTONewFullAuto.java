package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Full Auto", group = "AUTO")
public class AUTONewFullAuto extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-6350);
        sleep(3000);
        driveStraight(420, 0.3);
        sleep(500);
        drive0(2000, -1.0);
        stopMotors();
       /* if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.4, getGyroCurrentHeading());
        }
        else {
            turnDegrees(-0.4, getGyroCurrentHeading());
        }*/
        sleep(500);
        driveStraight(750, 1.0);
        sleep(500);

        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4700, -0.25);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        sleep(2000);
        driveStraight(4700 - moveAmount, -1.0);
        turnDegrees(0.4, 105);
        sleep(500);
        drive0(350, -0.8);
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

//        driveStraight(4444, -0.5);
//        turnDegrees(0.4, 107);
//        driveStraight(1000, -0.5);
    }
}
