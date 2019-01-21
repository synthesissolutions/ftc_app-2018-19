package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREKomodo;

@TeleOp(name="Komodo Teleop", group="TELE")
public class TELEKomodo extends OpMode  {

    HARDWAREKomodo robot = new HARDWAREKomodo();
    int heldSpinTimer = 0;

    @Override
    public void init() {
        robot.initializeJalepeño(hardwareMap);

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

        double collectionDeliveryAnglePower = gamepad2.left_stick_y;

        double collectionDeliverySlidePower = gamepad2.right_stick_y;

        boolean collectionDeliverySpinOut = gamepad2.left_trigger>.7;
        boolean collectionDeliverySpinIn = gamepad2.right_trigger>.7;

        boolean collectionDeliveryGateOut = gamepad2.x;
        boolean collectionDeliveryGateIn = gamepad2.y;

        //MAIN DRIVE
        robot.controlMecanumWheels(mecanumSpeed,mecanumTurn,mecanumStrafe,mecanumSlowStrafe,mecanumSlowSpeed,mecanumSlowTurn);

        //COLLECTION DELIVERY
        robot.controlCollectionDeliveryAngle(collectionDeliveryAnglePower);

        robot.controlCollectionDeliverySlide(collectionDeliverySlidePower);

        robot.controlCollectionDeliverySpin(collectionDeliverySpinOut, collectionDeliverySpinIn);

        robot.controlCollectionDeliveryGate(collectionDeliveryGateOut,collectionDeliveryGateIn);

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
