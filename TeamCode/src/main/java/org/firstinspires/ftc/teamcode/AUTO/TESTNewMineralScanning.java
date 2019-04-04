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
        int numReadings = 0;

        /*ElapsedTime eTime = new ElapsedTime();
        double startTime = 0.0;
        double endTime = 0.0;*/

        int numSensors = 1;

        waitForStart();

        while (opModeIsActive()) {
            while (opModeIsActive() && gamepad1.start == false) {
                if (gamepad1.a) speed -= 0.00001;
                if (gamepad1.b) speed += 0.00001;
                if (gamepad1.x) numSensors = 1;
                if (gamepad1.y) numSensors = 2;
                if (gamepad1.dpad_down) numSensors = 3;

                telemetry.addData("speed: ", speed);
                telemetry.addData("num sensors: ", numSensors);
                telemetry.addData("Press Start to run test.", "");
                telemetry.update();
            }

            boolean onMineral = false;
            boolean definitivelyYellow = false;
            boolean scannedWhite = false;
            boolean scannedYellow = false;

            int unitsTraveled = 0;

            numBlocks = 0;
            numReadings = 0;

            if (numSensors == 1) {
                int red = 0;
                int green = 0;
                int blue = 0;
                unitsTraveled = motorBackLeft.getCurrentPosition();
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
                    numReadings++;
                }

                motorBackLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
                unitsTraveled = Math.max(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()))-Math.min(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()));
                if (definitivelyYellow) numBlocks = 1;
            } else if (numSensors == 3) {
                int red1 = 0;
                int green1 = 0;
                int blue1 = 0;

                int red2 = 0;
                int green2 = 0;
                int blue2 = 0;

                int red3 = 0;
                int green3 = 0;
                int blue3 = 0;

                unitsTraveled = motorBackLeft.getCurrentPosition();

                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);

                while (opModeIsActive() && !definitivelyYellow && gamepad1.b == false) {// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {

                    red1 = sensorColor.red();
                    blue1 = sensorColor.blue();
                    green1 = sensorColor.green();

                    red2 = sensorColor2.red();
                    blue2 = sensorColor2.blue();
                    green2 = sensorColor2.green();

                    red3 = sensorColor3.red();
                    blue3 = sensorColor3.blue();
                    green3 = sensorColor3.green();

                    if (onMineral && (onMat(red1,blue1,green1) && onMat(red2,blue2,green2) && onMat(red3,blue3,green3))) {
                        onMineral = false;
                        definitivelyYellow = !scannedWhite && scannedYellow;
                    }
                    else if (!onMineral && !(onMat(red1,blue1,green1) && onMat(red2,blue2,green2) && onMat(red3,blue3,green3))) {
                        onMineral = true;
                        scannedWhite = false;
                        scannedYellow = false;
                    }
                    if (onMineral) {
                        if (!scannedWhite) scannedWhite = onWhite(red1, blue1, green1) || onWhite(red2, blue2, green2) || onWhite(red3, blue3, green3);
                        if (!scannedYellow) scannedYellow = onYellow(red1,blue1,green1) || onYellow(red2,blue2,green2) || onYellow(red3,blue3,green3);
                    }
                    numReadings++;
                }

                motorBackLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
                unitsTraveled = Math.max(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()))-Math.min(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()));
                if (definitivelyYellow) numBlocks = 1;

            } else {
                int red1 = 0;
                int green1 = 0;
                int blue1 = 0;

                int red2 = 0;
                int green2 = 0;
                int blue2 = 0;

                unitsTraveled = motorBackLeft.getCurrentPosition();

                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);

                while (opModeIsActive() && !definitivelyYellow && gamepad1.b == false) {// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {

                    red1 = sensorColor.red();
                    blue1 = sensorColor.blue();
                    green1 = sensorColor.green();

                    red2 = sensorColor2.red();
                    blue2 = sensorColor2.blue();
                    green2 = sensorColor2.green();

                    if (onMineral && (onMat(red1,blue1,green1) && onMat(red2,blue2,green2))) {
                        onMineral = false;
                        definitivelyYellow = !scannedWhite && scannedYellow;
                    }
                    else if (!onMineral && !(onMat(red1,blue1,green1) && onMat(red2,blue2,green2))) {
                        onMineral = true;
                        scannedWhite = false;
                        scannedYellow = false;
                    }
                    if (onMineral) {
                        if (!scannedWhite) scannedWhite = onWhite(red1, blue1, green1) || onWhite(red2, blue2, green2);
                        if (!scannedYellow) scannedYellow = onYellow(red1,blue1,green1) || onYellow(red2,blue2,green2);
                    }
                    numReadings++;
                }

                motorBackLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed / 2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed / 2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
                unitsTraveled = Math.max(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()))-Math.min(Math.abs(unitsTraveled),Math.abs(motorBackLeft.getCurrentPosition()));
                if (definitivelyYellow) numBlocks = 1;
            }


            while (opModeIsActive() && gamepad1.dpad_up == false) {
                telemetry.addData("Number of Blocks", numBlocks);
                telemetry.addData("Number of Sensors", numSensors);
                telemetry.addData("Number of Readings", numReadings);
                telemetry.addData("Speed", speed);
                telemetry.addData("Units Traveled", unitsTraveled);
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
