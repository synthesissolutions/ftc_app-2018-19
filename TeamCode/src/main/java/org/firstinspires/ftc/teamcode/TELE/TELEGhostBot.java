package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREGhostBot;

@TeleOp(name="TELE Ghost", group="TELE")

public class TELEGhostBot extends OpMode {
    HARDWAREGhostBot robot = new HARDWAREGhostBot();
    @Override
    public void init() {
        robot.initializeGhost(hardwareMap);
        robot.displayErrors(telemetry);
    }
    @Override
    public void loop() {
        double mecanumSpeed = -gamepad1.left_stick_y;
        double mecanumTurn = gamepad1.right_stick_x;
        double mecanumStrafe = -gamepad1.left_stick_x;

        boolean mecanumSlowStrafe = gamepad1.left_trigger>.7;
        boolean mecanumSlowSpeed = gamepad1.left_trigger>.7;
        boolean mecanumSlowTurn = gamepad1.right_trigger>.7;


        //MAIN DRIVE
        robot.controlMecanumWheels(mecanumSpeed,mecanumTurn,mecanumStrafe,mecanumSlowStrafe,mecanumSlowSpeed,mecanumSlowTurn);

    }
    @Override
    public void stop() {
    }
}
