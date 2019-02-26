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

        int reading = 1;
        int encoder = 1;
        int numBlocks = 0;
        boolean onBlock = false;
        ElapsedTime eTime = new ElapsedTime();
        double startTime = 0.0;
        double endTime = 0.0;

        boolean twoSensors = false;

        waitForStart();

        while (opModeIsActive()) {
            while (opModeIsActive() && gamepad1.start == false)
            {
                if (gamepad1.a) speed -= 0.00001;
                if (gamepad1.b) speed += 0.00001;
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
                numBlocks = 0;
                onBlock=false;
                if (!twoSensors) while (opModeIsActive() && gamepad1.b == false){// && !isColorYellow(sensorColor) && !isColorYellow(sensorColor2) && !isColorYellow(sensorColor3)) {
/*                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();
                    encoder = motorBackLeft.getCurrentPosition();
                    reading++;
                    Log.i("isColorYellow", "Reading: " + reading + " Encoder: " + encoder + " Time(ms): " + eTime.milliseconds());
                    Log.i("isColorYellow", "Red1: " + red1 + " Green1: " + green1 + " Blue1: " + blue1);

                    */
                    boolean isYellow = isColorYellow(sensorColor);
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
                if (twoSensors) while (opModeIsActive() && gamepad1.b == false){
                    int red1 = sensorColor.red();
                    int green1 = sensorColor.green();
                    int blue1 = sensorColor.blue();
                    encoder = motorBackLeft.getCurrentPosition();
                    reading++;
                    int red2 = sensorColor2.red();
                    int green2 = sensorColor2.green();
                    int blue2 = sensorColor2.blue();

                    Log.i("isColorYellow", "Reading: " + reading + " Encoder: " + encoder + " Time(ms): " + eTime.milliseconds());
                    Log.i("isColorYellow", "Red1: " + red1 + " Green1: " + green1 + " Blue1: " + blue1);
                    Log.i("isColorYellow", "Red2: " + red2 + " Green2: " + green2 + " Blue2: " + blue2);
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

            Log.i("isColorYellow", "Encoder units per reading: " + encoder / reading);
            Log.i("isColorYellow", "Milliseconds per reading: " + (endTime - startTime) / reading);
            while (opModeIsActive() && gamepad1.a == false) {
                /*
                telemetry.addData("Encoder units per reading", "" + encoder / reading);
                telemetry.addData("Milliseconds per reading", (endTime - startTime) / reading);*/
                telemetry.addData("Number of Blocks", numBlocks);
                telemetry.update();
            }
        }
    }
}