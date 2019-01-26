package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW Blue Depot Start", group = "AUTO")


public class AUTONewBlueDepot extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(500, 0.3);
        sleep(1000);
        drive0(2000, -0.8);
        stopMotors();
       /* if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.4, getGyroCurrentHeading());
        }
        else {
            turnDegrees(-0.4, getGyroCurrentHeading());
        }*/
        sleep(500);
        driveStraight(1220, 0.4);
        sleep(500);
        deployMineralArm();
        sleep(2500);
        double moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4000, -0.25);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        sleep(500);
        servoMineralRotate.setPosition(1.0);
        sleep(1000);
        driveStraight(750, 0.5);
        //sleep(666);
        //retractMineralArm();
        sleep(500);
        //driveStraight(250, 0.5);
        if (moveAmount < 1600) {
            driveStraight(400, 0.5);
            turnDegrees(-0.35, 78);
        }
        else if (moveAmount < 2500) {
            turnDegrees(-0.35, 111);
        }
        else if (moveAmount < 3900){
            drive0(300, -0.8);
            sleep(100);
            driveStraight(666, -0.5);
            turnDegrees(-0.35, 145);
        }
        else {
            turnDegrees(-0.35, 145);
        }
        sleep(500);
        retractMineralArm();
        driveStraight(2700, -0.5);
        sleep(500);
        setMarkerDeliveryPosition(0.6);
        sleep(1500);



        //turn and deliver marker
        //drive to other minerals and knock off correct one
        //park on crater
    }
}
