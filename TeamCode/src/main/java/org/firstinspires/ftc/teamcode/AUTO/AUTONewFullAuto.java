package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Full Auto", group = "AUTO")
public class AUTONewFullAuto extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(500, 0.3);
        sleep(1000);
        drive0(1400, -0.8);
        stopMotors();
       /* if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.4, getGyroCurrentHeading());
        }
        else {
            turnDegrees(-0.4, getGyroCurrentHeading());
        }*/
        sleep(500);
        driveStraight(300, 0.4);
        sleep(500);
        deployMineralArm();
        sleep(2500);
        double moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4000, -0.25);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        sleep(500);
        servoMineralRotate.setPosition(1.0);
        sleep(666);
        driveStraight(550, 0.5);
        retractMineralArm();
        driveStraight(3000, 0.5);
        turnDegrees(0.4, 100);

        if (moveAmount < 1600) {
            deployMineralArm();
            turnDegrees(0.4, 111);
            driveStraight(1000, 0.5);
        } else if (moveAmount < 2800) {
            driveStraight(2700, 0.5);
            turnDegrees(0.4, 111);
            driveStraight(1000, 0.5);
        } else if (moveAmount < 3900) {
            driveStraight(3600, 0.5);
            turnDegrees(0.4, 111);
            driveStraight(1000, 0.5);
        } else {

        }
    }
}
