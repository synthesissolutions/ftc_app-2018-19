package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREPracticeBot;

@TeleOp(name="Jalepeno Teleop", group="TELE")
public class TELEMecanumPracticeBotNew extends OpMode  {

    HARDWAREPracticeBot robot = new HARDWAREPracticeBot();
    int heldSpinTimer = 0;

    @Override
    public void init() {
        robot.initializePracticeBot(hardwareMap);

        // TELEMETRY
        robot.displayErrors(telemetry);
    }

    @Override
    public void loop() {
        //BUTTONS
        double mecanumSpeed = -gamepad1.left_stick_y;
        double mecanumTurn = gamepad1.right_stick_x;
        double mecanumStrafe = -gamepad1.left_stick_x;

        boolean mecanumSlowStrafe = gamepad1.left_trigger>.7;
        boolean mecanumSlowSpeed = gamepad1.left_trigger>.7;
        boolean mecanumSlowTurn = gamepad1.right_trigger>.7;

        double hangTowerControl = 0.0;
        if (gamepad1.right_bumper || gamepad1.dpad_up) {
            hangTowerControl = 1.0;
        } else if (gamepad1.left_bumper || gamepad1.dpad_down) {
            hangTowerControl = -1.0;
        }
        boolean hangTowerDown=gamepad1.b;
        boolean hangTowerUp=gamepad1.a;

        boolean markerDeliveryDrop = gamepad1.start;

        double deployTowerControl = gamepad2.right_stick_y;

        double deployDumpSensetive = Math.max(gamepad2.left_trigger,gamepad2.right_trigger);
        boolean deployGateLeft = gamepad2.right_bumper;
        boolean deployGateRight = gamepad2.left_bumper;
        boolean deployGateOpenAll = (gamepad2.right_trigger > 0.1);

        boolean collectRotateIn = gamepad2.y;

        boolean collectRotateOut = gamepad2.x;

        double collectSlideControl = gamepad2.left_stick_y;

        double collectSpinSpeed = 0.5;
        if (gamepad2.b || gamepad2.a)
        {
            if (heldSpinTimer < 5)
                heldSpinTimer++;
        }
        else
        {
            heldSpinTimer=0;
        }

        if (gamepad2.b)
            collectSpinSpeed = 0.5-0.1*heldSpinTimer;
        if (gamepad2.a)
            collectSpinSpeed = 0.5+0.1*heldSpinTimer;

        //MAIN DRIVE
        robot.controlMecanumWheels(mecanumSpeed,mecanumTurn,mecanumStrafe,mecanumSlowStrafe,mecanumSlowSpeed,mecanumSlowTurn);

        //HANG TOWER
//        robot.controlHangTower(hangTowerControl,hangTowerDown,hangTowerUp);
        robot.controlHangTowerSimple(hangTowerControl);

        // DEPLOY TOWER
        robot.controlDeployTower(deployTowerControl);

        // DEPLOY DUMPER
        robot.controlDeployDumper(deployDumpSensetive,deployGateLeft,deployGateRight,deployGateOpenAll);

        // COLLECT ROTATE
        robot.controlCollectRotate(collectRotateIn, collectRotateOut);

        // COLLECT SLIDE
//        robot.controlCollectSlide(collectSlideControl);
        robot.controlCollectSlideSimple(collectSlideControl);

        // COLECT SPIN
        robot.controlCollectSpin(collectSpinSpeed);

        // MARKER DELIVERY
        robot.controlMarkerDelivery(markerDeliveryDrop);

        // TELEMETRY
        //robot.displayErrors(telemetry);
        telemetry.addData("TOWER POS:", robot.deployTowerPosition());
        telemetry.addData("SLIDE POS:", robot.collectSlidePosition());
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
