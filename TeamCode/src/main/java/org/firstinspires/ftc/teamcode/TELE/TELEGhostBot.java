package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREGhostBot;
import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREKomodo;


@TeleOp(name="TELE Ghost", group="TELE")

public class TELEGhostBot extends OpMode {
    int collectionDeliverySlidePosition = 0;

    HARDWAREKomodo robot = new HARDWAREKomodo();
    int heldSpinTimer = 0;

    @Override
    public void init() {
        robot.initializeKomodo(hardwareMap);



        // TELEMETRY
        robot.displayErrors(telemetry);
    }

    @Override
    public void loop() {

        boolean collectionDeliverySlideMoving = false;

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


        double collectionDeliveryAnglePower = gamepad2.left_stick_y;

        /*if (gamepad2.right_stick_y > 0) {
            collectionDeliverySlideMoving = true;
            collectionDeliverySlidePosition += (gamepad2.right_stick_y * 20);
        } else if (gamepad2.right_stick_y < 0) {
            collectionDeliverySlideMoving = true;
            collectionDeliverySlidePosition += (gamepad2.right_stick_y * 20);
        } else if (collectionDeliverySlideMoving) {
            // Hold the current position and stop moving someone just released the button
            collectionDeliverySlideMoving = false;
            collectionDeliverySlidePosition = robot.motorCollectionDeliverySlideGetPosition();
        }*/
        double collectionDeliverySlidePower = gamepad2.right_stick_y;

        boolean collectionDeliverySpinOut = gamepad2.left_trigger>.7;
        boolean collectionDeliverySpinIn = gamepad2.right_trigger>.7;

        //boolean collectionDeliveryGateOut = gamepad2.x;
        //boolean collectionDeliveryGateIn = gamepad2.y;

        if (gamepad2.x) {
            robot.openCollectionGate();
        }
        else if (gamepad2.y) {
            robot.openCollectionGateGhost();
        }

        //MAIN DRIVE
        robot.controlMecanumWheels(mecanumSpeed,mecanumTurn,mecanumStrafe,mecanumSlowStrafe,mecanumSlowSpeed,mecanumSlowTurn);

        //COLLECTION DELIVERY
        robot.controlCollectionDeliveryAngle(collectionDeliveryAnglePower, telemetry);

        //robot.controlCollectionDeliverySlidePosition(collectionDeliverySlidePosition);
        robot.controlCollectionDeliverySlide(collectionDeliverySlidePower);

        robot.controlCollectionDeliverySpinGhost(collectionDeliverySpinOut, collectionDeliverySpinIn);

        //robot.controlCollectionDeliveryGate(collectionDeliveryGateOut,collectionDeliveryGateIn);

        robot.controlHangTower(hangTowerControl);
        robot.retractMineralElbow();
        // TELEMETRY
        //robot.displayErrors(telemetry);
        //telemetry.addData("TOWER POS:", robot.deployTowerPosition());
        //telemetry.addData("SLIDE POS:", robot.collectSlidePosition());
        //telemetry.update();

    }

    @Override
    public void stop() {
    }
}
