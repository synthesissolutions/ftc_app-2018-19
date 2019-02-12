package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW ALL Depot Start", group = "AUTO")

public class AUTONewRedDepot extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(500, 0.3);
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
        driveStraight(500, 0.5);
        if (moveAmount < 1600) {
            driveStraight(500, 0.5);
            turnDegrees(-0.35, 95);
            sleep(500);
            retractMineralArm();
        } else if (moveAmount < 2800) {
            sleep(500);
            turnDegrees(-0.35, 111);
            sleep(500);
            drive0(300, -0.8);
            retractMineralArm();
        } else if (moveAmount < 3900) {
            sleep(100);
            driveStraight(400, -0.5);
            drive0(300, -0.8);
            sleep(500);
            retractMineralArm();
            turnDegrees(-0.35, 150);
            driveStraight(300, -0.5);
        } else {
            turnDegrees(-0.35, 150);
        }
        driveStraight(2700, -0.5);
        sleep(500);
        setMarkerDeliveryPosition(0.6);
        sleep(1500);

    }
}
