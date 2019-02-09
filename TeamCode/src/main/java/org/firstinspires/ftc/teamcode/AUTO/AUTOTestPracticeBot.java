package org.firstinspires.ftc.teamcode.AUTO;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        double speed = 0.8;
        boolean logValues = true;
        int highestValRed =0;
        int greenAtHighestRed =0;
        int blueAtHighestRed =0;

        int highestValGreen =0;
        int redAtHighestGreen =0;
        int blueAtHighestGreen =0;

        int highestValBlue =0;
        int redAtHighestBlue =0;
        int greenAtHighestBlue =0;

        int reading = 0;
        int encoder = 0;
        ElapsedTime eTime = new ElapsedTime();
        double startTime = 0.0;
        double endTime = 0.0;

        boolean twoSensors = false;

        waitForStart();

        while (opModeIsActive()) {
            while (opModeIsActive() && gamepad1.start == false)
            {
                if (gamepad1.a) speed -= 0.00005;
                if (gamepad1.b) speed += 0.00005;
                if (gamepad1.x) logValues = true;
                if (gamepad1.y) logValues = false;
                if (gamepad1.right_bumper) twoSensors = true;
                if (gamepad1.left_bumper) twoSensors = false;

                telemetry.addData("speed: ", speed);
                telemetry.addData("Log Values? ", logValues);
                telemetry.addData("Two Sensors? ", twoSensors);
                telemetry.addData("Press Start to run test.", "");
                telemetry.update();
            }
            if (logValues)
            {
                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);

                startTime = eTime.milliseconds();

                while (opModeIsActive() && gamepad1.b == false){// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();
                    encoder = motorBackLeft.getCurrentPosition();
                    reading++;
                    int red2 = 0;
                    int green2 = 0;
                    int blue2 = 0;

                    Log.i("isColorYellow", "Reading: " + reading + " Encoder: " + encoder + " Time(ms): " + eTime.milliseconds());
                    Log.i("isColorYellow", "Red1: " + red1 + " Green1: " + green1 + " Blue1: " + blue1);

                    if (twoSensors) {
                        red2 = sensorColor2.red();
                        green2 = sensorColor2.green();
                        blue2 = sensorColor2.blue();

                        Log.i("isColorYellow", "Red2: " + red2 + " Green2: " + green2 + " Blue2: " + blue2);
                    }
                    //int red3 = sensorColor3.red();
                    //int green3 = sensorColor3.green();
                    //int blue3 = sensorColor3.blue();

                    //Log.i("isColorYellow", "Red3: " + red3 + " Green3: " + green3 + " Blue3: " + blue3);

                    /*
                    if (sensorColor.red() > highestValRed) {
                        highestValRed = sensorColor.red();
                        greenAtHighestRed = sensorColor.green();
                        blueAtHighestRed = sensorColor.blue();
                    }
                    if (sensorColor.green() > highestValGreen) {
                        highestValGreen = sensorColor.green();
                        redAtHighestGreen = sensorColor.red();
                        blueAtHighestGreen = sensorColor.blue();
                    }
                    if (sensorColor.blue() > highestValBlue)
                    {
                        highestValBlue = sensorColor.blue();
                        redAtHighestBlue = sensorColor.red();
                        greenAtHighestBlue = sensorColor.green();
                    }
                    */
                }

                endTime = eTime.milliseconds();

                motorBackLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
            }
            else
            {
                motorBackLeft.setPower(-speed);
                motorBackRight.setPower(speed);
                motorFrontRight.setPower(speed);
                motorFrontLeft.setPower(-speed);
                while (opModeIsActive() && gamepad1.b == false && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
                }
                motorBackLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
            }
            while (opModeIsActive() && gamepad1.a == false) {
                /*
                telemetry.addData("Highest Red: ", highestValRed);
                telemetry.addData("Green @ Highest Red: ", greenAtHighestRed);
                telemetry.addData("Blue @ Highest Red: ", blueAtHighestRed);
                telemetry.addLine();
                telemetry.addData("Highest Green: ", highestValGreen);
                telemetry.addData("Red @ Highest Green: ", redAtHighestGreen);
                telemetry.addData("Blue @ Highest Green: ", blueAtHighestGreen);
                telemetry.addLine();
                telemetry.addData("Highest Blue: ", highestValBlue);
                telemetry.addData("Red @ Highest Blue: ", redAtHighestBlue);
                telemetry.addData("Green @ Highest Blue: ",greenAtHighestBlue);
                */
                telemetry.addData("Encoder units per reading", "" + encoder / reading);
                telemetry.addData("Milliseconds per reading", (endTime - startTime) / reading);
                telemetry.update();
            }
        }
    }
}