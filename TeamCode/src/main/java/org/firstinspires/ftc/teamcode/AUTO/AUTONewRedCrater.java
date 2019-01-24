package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW Red Crater Start", group = "AUTO")

public class AUTONewRedCrater extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(1100, 0.3);
        sleep(1000);
        drive0(1500, -0.8);
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
        driveStraight(1000, 0.5);
        retractMineralArm();
        driveStraight(275, 0.5);
        if (moveAmount < 1600) {
            driveStraight(125, 0.5);
            turnDegrees(-0.35, 78);
            driveStraight(750, -0.5);
        } else if (moveAmount < 2500) {
            driveStraight(200, -0.5);
            turnDegrees(-0.35, 111);
            driveStraight(1220, -0.5);
        } else if (moveAmount < 3900) {
            sleep(100);
            driveStraight(666, -0.5);
            drive0(400, -0.8);
            turnDegrees(-0.35, 120);
            driveStraight(1400, -0.5);
        } else {
            turnDegrees(-0.35, 130);
            driveStraight(900, -0.5);
        }
        sleep(500);

    }
}
