package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AUTO Depot Only", group = "AUTO")
public class AUTODepotSideOnly extends AUTOMecanumAbstractPracticeBot{
    public void runOpMode() throws InterruptedException {
        startAutoOp();
        setHangTowerPosition(-6350); //deploy from lander
        sleep(3000);

        //move away from lander
        driveStraight(420, 0.5);
        sleep(500);
        drive0(1900, -1.0);
        stopMotors();
        sleep(666);
        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        } else {
        }
        sleep(500);

        //align for mineral detection
        driveStraight(400, 1.0);
        deployMineralArm();
        sleep(1000);


        //read mineral
        int moveAmount = this.motorFrontLeft.getCurrentPosition();
        driveStraightAndColor(4350, -0.3);
        moveAmount = (this.motorFrontLeft.getCurrentPosition() - moveAmount);
        driveStraight(50, -1.0);
        sleep(1000);

        //knock off mineral and deploy marker
        drive0(1200, -1.0);
        sleep(666);
        drive0(400, 1.0);
        deployMarker(moveAmount);
        retractMineralArm();
        drive0(800, 1.0);
        sleep(666);
        if (getGyroCurrentHeading() < -4) {
            turnDegrees(0.3, (-1 * getGyroCurrentHeading()));
        } else if (getGyroCurrentHeading() > 4) {
            turnDegrees(-0.3, getGyroCurrentHeading());
        } else {
        }
        sleep(500);

        //drive to opposite crater and park
        driveStraight(moveAmount + 1500, 1.0);
        turnDegrees(-1.0, 48);
        drive0(420, -1.0);
        driveStraight(700, 1.0);
    }
}
