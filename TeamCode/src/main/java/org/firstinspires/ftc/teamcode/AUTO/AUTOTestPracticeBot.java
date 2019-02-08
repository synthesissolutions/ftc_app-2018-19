package org.firstinspires.ftc.teamcode.AUTO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.PreviousYear.AUTOMecanumAbstractCayenne;



@Autonomous(name="AUTO Test", group="AUTO")
//@Disabled
public class AUTOTestPracticeBot extends AUTOMecanumAbstractPracticeBot {
    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_mineral_color");
        sensorColor2 = hardwareMap.get(ColorSensor.class, "sensor_mineral_color2");
        sensorColor3 = hardwareMap.get(ColorSensor.class, "sensor_mineral_color3");
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        double speed = 1;

        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(speed);
        motorFrontRight.setPower(speed);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {

            telemetry.addData("R", sensorColor2.red());
            telemetry.addData("G", sensorColor2.green());
            telemetry.addData("B", sensorColor2.blue());
            telemetry.update();
        }

        motorBackLeft.setPower(0.6*(speed/Math.abs(speed)));
        motorBackRight.setPower(0.6*-(speed/Math.abs(speed)));
        motorFrontRight.setPower(0.6*-(speed/Math.abs(speed)));
        motorFrontLeft.setPower(0.6*(speed/Math.abs(speed)));
        sleep(300);
        stopMotors();
    }
}