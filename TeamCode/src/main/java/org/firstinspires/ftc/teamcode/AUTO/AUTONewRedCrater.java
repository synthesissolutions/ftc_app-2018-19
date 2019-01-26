package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW Red Crater Start", group = "AUTO")

public class AUTONewRedCrater extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(900, 0.3);
        sleep(1000);
        drive0(1600, -0.8);
        stopMotors();
       /* if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.4, getGyroCurrentHeading());
        }
        else {
            turnDegrees(-0.4, getGyroCurrentHeading());
        }*/
        sleep(500);
        driveStraight(500, 0.4);
        sleep(500);
        deployMineralArm();
        sleep(2500);
        double moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4000, -0.25);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        sleep(500);
        servoMineralRotate.setPosition(1.0);
        sleep(1000);
        driveStraight(600, 0.5);
        if (moveAmount < 1600) {
            driveStraight(275, 0.5);
            retractMineralArm();
            turnDegrees(-0.35, 110);
            driveStraight(1000, -0.5);
        } else if (moveAmount < 2500) {
            //driveStraight(200, -0.5);
            sleep(500);
            turnDegrees(-0.35, 117);
            retractMineralArm();
            driveStraight(1320, -0.5);
        } else if (moveAmount < 3900) {
            driveStraight(150, 0.5);
            sleep(100);
            driveStraight(666, -0.5);
            drive0(400, -0.8);
            retractMineralArm();
            turnDegrees(-0.35, 130);
            driveStraight(1900, -0.5);
        } else {
            turnDegrees(-0.35, 130);
            retractMineralArm();
            driveStraight(1600, -0.5);
        }

    }
}
