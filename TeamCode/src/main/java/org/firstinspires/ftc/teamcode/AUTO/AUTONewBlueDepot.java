package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO NEWNEWNEW Blue Depot Start", group = "AUTO")


public class AUTONewBlueDepot extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-11222);
        sleep(5555);
        driveStraight(500, 0.3);
        sleep(2000);
        drive0(2222, -0.8);
        //holonomic(0, 0, -0.8, MECANUM_MAX_SPEED);
//        sleep(100);
//        stopMotors();
//        sleep(1000);
//        holonomic(0, 0, -0.8, MECANUM_MAX_SPEED);
//        sleep(1000);
        stopMotors();
        /*telemetry.addData("current heading", gyroCurrentHeading);
        telemetry.update();
        sleep(3000);
        */
        if(getGyroCurrentHeading() < 0) {
            turnDegrees(0.4, getGyroCurrentHeading());
        }
        else {
            turnDegrees(-0.4, getGyroCurrentHeading());
        }
        sleep(1000);
        driveStraight(1220, 0.4);
        servoMineralArm.setPosition(0.08);
        driveStraightAndColor(3000, 0.4);



        //turn and deliver marker
        //drive to other minerals and knock off correct one
        //park on crater
    }
}
