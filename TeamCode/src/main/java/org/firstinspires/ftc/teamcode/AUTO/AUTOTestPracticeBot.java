package org.firstinspires.ftc.teamcode.AUTO;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PreviousYear.AUTOMecanumAbstractCayenne;



@Autonomous(name="AUTO Alex's Scanning Test", group="AUTO")
@Disabled
public class AUTOTestPracticeBot extends AUTOMecanumAbstractPracticeBot {
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
        double speed = 0.8;
        boolean logValues = true;

        int reading = 1;
        int encoder = 1;
        int numBlocks = 0;
        boolean onBlock = false;
        ElapsedTime eTime = new ElapsedTime();
        double startTime = 0.0;
        double endTime = 0.0;


        boolean ballMode = false;

        waitForStart();

        while (opModeIsActive()) {
            int redHighest = 0;
            int blueAtRedHighest = 0;
            int greenAtRedHighest = 0;

            int blueHighest = 0;
            int redAtBlueHighest = 0;
            int greenAtBlueHighest = 0;

            int greenHighest = 0;
            int redAtGreenHighest = 0;
            int blueAtGreenHighest = 0;

            int redLowestBlock = 0;
            int blueAtRedLowest = 0;
            int greenAtRedLowest = 0;

            int blueLowestBlock = 0;
            int redAtBlueLowest = 0;
            int greenAtBlueLowest = 0;

            int greenLowestBlock = 0;
            int redAtGreenLowest = 0;
            int blueAtGrenLowest = 0;

            while (opModeIsActive() && gamepad1.start == false)
            {
                if (gamepad1.a) speed -= 0.00001;
                if (gamepad1.b) speed += 0.00001;
                if (gamepad1.x) logValues = true;
                if (gamepad1.y) logValues = false;
                if (gamepad1.right_bumper) ballMode = true;
                if (gamepad1.left_bumper) ballMode = false;

                telemetry.addData("speed: ", speed);
                telemetry.addData("Log Values with Alex's Method? ", logValues);
                telemetry.addData("Two Sensors? ", ballMode);
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
                numBlocks = 0;
                onBlock=false;
                if (!ballMode) while (opModeIsActive() && gamepad1.b == false){// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();

                    if (red1>redHighest)
                    {
                        redHighest=red1;
                        blueAtRedHighest=blue1;
                        greenAtRedHighest=green1;
                    }

                    if (green1>greenHighest)
                    {
                        greenHighest=green1;
                        redAtGreenHighest=red1;
                        blueAtGreenHighest=blue1;
                    }

                    if (blue1>blueHighest)
                    {
                        blueHighest=blue1;
                        redAtBlueHighest=red1;
                        greenAtBlueHighest=blue1;
                    }



                    boolean isYellow = isColorYellow(sensorColor, "sensorColor", 0);
                    if (!onBlock && isYellow)
                    {
                        numBlocks++;
                        onBlock = true;
                    }
                    else if (!isYellow)
                    {
                        onBlock = false;
                    }
                }

                if (ballMode) while (opModeIsActive() && gamepad1.b == false){
                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();

                    if (red1>redHighest)
                    {
                        redHighest=red1;
                        blueAtRedHighest=blue1;
                        greenAtRedHighest=green1;
                    }

                    if (green1>greenHighest)
                    {
                        greenHighest=green1;
                        redAtGreenHighest=red1;
                        blueAtGreenHighest=blue1;
                    }

                    if (blue1>blueHighest)
                    {
                        blueHighest=blue1;
                        redAtBlueHighest=red1;
                        greenAtBlueHighest=blue1;
                    }

                    if (red1>90)
                    {

                    }

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
                while (opModeIsActive() && gamepad1.b == false && !isColorYellow(sensorColor, "sensor_mineral_color", motorFrontLeft.getCurrentPosition()) && !isColorYellow(sensorColor2, "sensor_mineral_color2", motorFrontLeft.getCurrentPosition()) && !isColorYellow(sensorColor3, "sensor_mineral_color3", motorFrontLeft.getCurrentPosition())) {
                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();
                    encoder = motorBackLeft.getCurrentPosition();
                    reading++;

                    Log.i("isColorYellow", "Reading: " + reading + " Encoder: " + encoder + " Time(ms): " + eTime.milliseconds());
                    Log.i("isColorYellow", "Red1: " + red1 + " Green1: " + green1 + " Blue1: " + blue1);
                }
                motorBackLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                motorBackRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontRight.setPower(speed/2 * -(speed / Math.abs(speed)));
                motorFrontLeft.setPower(speed/2 * (speed / Math.abs(speed)));
                sleep(300);
                stopMotors();
            }

            Log.i("isColorYellow", "Encoder units per reading: " + encoder / reading);
            Log.i("isColorYellow", "Milliseconds per reading: " + (endTime - startTime) / reading);
            while (opModeIsActive() && gamepad1.a == false) {
                /*
                telemetry.addData("Encoder units per reading", "" + encoder / reading);
                telemetry.addData("Milliseconds per reading", (endTime - startTime) / reading);*/
                telemetry.addData("Number of Blocks", numBlocks);
                if (logValues) {
                    telemetry.addData("Red Highest", redHighest);
                    telemetry.addData("Blue At Red Highest", blueAtRedHighest);
                    telemetry.addData("Green At Red Highest", greenAtRedHighest);
                    telemetry.addData("Blue Highest", blueHighest);
                    telemetry.addData("Red At Blue Highest", redAtBlueHighest);
                    telemetry.addData("Green At Blue Highest", greenAtBlueHighest);
                    telemetry.addData("Green Highest", greenHighest);
                    telemetry.addData("Red At Green Highest", redAtGreenHighest);
                    telemetry.addData("Blue At Green Highest", blueAtGreenHighest);
                    if (ballMode)
                    {

                    }
                }
                telemetry.update();
            }
        }
    }
}