package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREPracticeBot;

@TeleOp(name="Mecanum Teleop PracticeBot New", group="TELE")
public class TELEMecanumPracticeBotNew extends OpMode  {

    HARDWAREPracticeBot robot = new HARDWAREPracticeBot();

    @Override
    public void init() {
        robot.initializePracticeBot(hardwareMap);
    }

    @Override
    public void loop() {
        //BUTTONS
        double mecanumSpeed = -gamepad1.left_stick_y;
        double mecanumTurn = gamepad1.right_stick_x;
        double mecanumStrafe = gamepad1.left_stick_x;

        boolean mecanumSlowStrafe = gamepad1.left_trigger>.7;
        boolean mecanumSlowSpeed = gamepad1.left_trigger>.7;
        boolean mecanumSlowTurn = gamepad1.right_trigger>.7;

        double hangTowerControl = 0.0;
        if (gamepad1.dpad_up) {
            hangTowerControl = 1.0;
        } else if (gamepad1.dpad_down) {
            hangTowerControl = -1.0;
        }
        boolean hangTowerDown=gamepad1.b;
        boolean hangTowerUp=gamepad1.a;

        //double deployTowerControl = gamepad2.left_stick_y;

        boolean collectRotateIn = gamepad2.y;

        boolean collectRotateOut = gamepad2.x;

        double collectSlideControl = gamepad2.right_stick_y;

        double collectSpinSpeed = 0.5;
        if (gamepad2.b)
            collectSpinSpeed = 0.0;
        if (gamepad1.a)
            collectSpinSpeed = 1.0;


        //MAIN DRIVE
        robot.controlMecanumWheels(mecanumSpeed,mecanumTurn,mecanumStrafe,mecanumSlowStrafe,mecanumSlowSpeed,mecanumSlowTurn);

        //HANG TOWER
        robot.controlHangTower(hangTowerControl,hangTowerDown,hangTowerUp);

        // DEPLOY TOWER
//        robot.controlDeployTower(deployTowerControl);

//        telemetry.addData("Deploy Tower", "Starting at %7d",
//                robot.hangTowerPosition());

        // COLLECT ROTATE
        robot.controlCollectRotate(collectRotateIn, collectRotateOut);

        // COLLECT SLIDE
        robot.controlCollectSlide(collectSlideControl);

        // COLECT SPIN
        robot.controlCollectSpin(collectSpinSpeed);

        // TELEMETRY
        robot.displayErrors(telemetry);
    }

    @Override
    public void stop() {
    }
}
