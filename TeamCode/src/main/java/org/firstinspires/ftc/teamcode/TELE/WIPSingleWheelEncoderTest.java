package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREAbstract;
import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREKomodo;
import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREPracticeBot;

import static com.qualcomm.robotcore.util.Range.scale;

@TeleOp(name="SINGLE WHEEL ENCODER TEST", group="WIP")
public class WIPSingleWheelEncoderTest extends OpMode {
    HARDWAREPracticeBot robot = new HARDWAREPracticeBot();
    @Override
    public void init() {
        robot.initializeSingleWheelEncoderTestBot(hardwareMap);
    }

    @Override
    public void loop() {
        robot.controlSingleWheelEncoderTest(telemetry);
    }

    @Override
    public void stop() {
    }

}