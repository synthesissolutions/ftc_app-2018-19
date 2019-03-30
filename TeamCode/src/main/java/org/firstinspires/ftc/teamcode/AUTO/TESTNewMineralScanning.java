package org.firstinspires.ftc.teamcode.AUTO;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="TEST New Mineral Scanning", group="TEST")
public class TESTNewMineralScanning extends AUTOMecanumAbstractPracticeBot {
    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        servoMarkerShoulder = hardwareMap.get(Servo.class, "servoMarkerShoulder");
        servoMarkerShoulder.setPosition(0.5);

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
        double speed = -0.8;

        //int reading = 1;
        //int encoder = 1;
        int numBlocks = 0;
        /*ElapsedTime eTime = new ElapsedTime();
        double startTime = 0.0;
        double endTime = 0.0;*/

        boolean autoStop = true;

        waitForStart();

        while (opModeIsActive()) {
            while (opModeIsActive() && gamepad1.start == false) {
                if (gamepad1.a) speed -= 0.00001;
                if (gamepad1.b) speed += 0.00001;
                if (gamepad1.x) autoStop = false;
                if (gamepad1.y) autoStop = true;

                telemetry.addData("speed: ", speed);
                telemetry.addData("autostop: ", autoStop);
                telemetry.addData("Press Start to run test.", "");
                telemetry.update();
            }

            boolean onMineral = false;
            boolean definitivelyYellow = false;
            boolean scannedWhite = false;
            boolean scannedYellow = false;
            int red = 0;
            int green = 0;
            int blue = 0;
            numBlocks = 0;

            if (autoStop) {
                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);

                while (opModeIsActive() && !definitivelyYellow && gamepad1.b == false) {// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
                    red = sensorColor.red();
                    blue = sensorColor.blue();
                    green = sensorColor.green();

                    if (onMineral && onMat(red,blue,green)) {
                        onMineral = false;
                        definitivelyYellow = !scannedWhite && scannedYellow;
                    }
                    else if (!onMineral && !onMat(red,blue,green)) {
                        onMineral = true;
                        scannedWhite = false;
                        scannedYellow = false;
                    }
                    if (onMineral) {
                        if (!scannedWhite) scannedWhite = onWhite(red, blue, green);
                        if (!scannedYellow) scannedYellow = onYellow(red,blue,green);
                    }
                }

                motorBackLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
                if (definitivelyYellow) numBlocks = 1;
            } else {
                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);

                while (opModeIsActive() && gamepad1.b == false) {// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
                    red = sensorColor.red();
                    blue = sensorColor.blue();
                    green = sensorColor.green();

                    if (definitivelyYellow)
                    {
                        //todo this, caue its not done
                    }

                    if (onMineral && onMat(red,blue,green)) {
                        onMineral = false;
                        definitivelyYellow = !scannedWhite && scannedYellow;
                    }
                    else if (!onMineral && !onMat(red,blue,green)) {
                        onMineral = true;
                        scannedWhite = false;
                        scannedYellow = false;
                    }
                    if (onMineral) {
                        if (!scannedWhite) scannedWhite = onWhite(red, blue, green);
                        if (!scannedYellow) scannedYellow = onYellow(red,blue,green);
                    }
                }

                motorBackLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
                if (definitivelyYellow) numBlocks = 1;

            }
            while (opModeIsActive() && gamepad1.a == false) {
                telemetry.addData("Number of Blocks", numBlocks);

            telemetry.update();
            }
        }
    }

    boolean onMat(int r, int b, int g)
    {
        return r+b+g < 140;
    }

    boolean onWhite(int r, int b, int g)
    {
        return r > 300 || b > 300 || g > 300;
    }

    boolean onYellow(int r, int b, int g)
    {
        return 90 < r && r < 170 && 35 < b && b < 75 && 80 < g && g < 125 && r > g && g > b;
    }
}
