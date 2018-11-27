package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by admin on 11/2/17.
 */

@TeleOp(name="TEST Nate Sensors", group="TEST")

@Disabled
public class TESTNateSensor extends LinearOpMode {

    private Servo servo;
    private DcMotor motor;
    private ColorSensor color;
    private GyroSensor gyro;
    private TouchSensor touch;
    private DistanceSensor distance;

    @Override
    public void runOpMode() throws InterruptedException {
        //Init hardware
        servo = hardwareMap.servo.get("servo2");
        motor = hardwareMap.dcMotor.get("motor1");
        color = hardwareMap.colorSensor.get("color");
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        touch = hardwareMap.touchSensor.get("touch");
        distance = hardwareMap.get(DistanceSensor.class, "range");

        waitForStart();

        boolean isStopped = true;
        boolean mode = true;
        boolean alreadyFlipped = false;

        double theoreticalPower = 0, actualPower = 0;

        int sensorExceptionCount = 0;
        int longPressIterationCount = 1000;
        int heldIterationCount = 0;

        //loop
        while(opModeIsActive()){
            telemetry.addData("Mode: ", mode ? "distance knob" : "gyro knob");
            telemetry.addData("Theoretical power:", theoreticalPower);
            telemetry.addData("Motor Speed: ", motor.getPower());
            telemetry.addData("Held Iterations: ", heldIterationCount);
            telemetry.addData("Color: ", rgbString(color));
            telemetry.addData("Gyro: ", "heading: " + gyro.getHeading() /*+ " rotation: " + gyro.getRotationFraction()*/);
            telemetry.addData("Touch: ", touch.isPressed() + " (" + touch.getValue() + ")");
            telemetry.addData("Range: ", distance.getDistance(DistanceUnit.CM));
            telemetry.addData("Sensor Exceptions: ", sensorExceptionCount);
            telemetry.update();

            //stop/start motor each time button is released and then pressed.
            if(touch.isPressed()){
                if(!alreadyFlipped){
                    isStopped = !isStopped;
                    alreadyFlipped = true;
                }

                //detect long press to change motor speed mode
                heldIterationCount++;
                if(heldIterationCount >= longPressIterationCount){
                    mode = !mode;
                }
            } else {
                alreadyFlipped = false;
                heldIterationCount = 0;
            }

            //determine theoretical motor speed based on selected mode
            if(mode) {
                try {
                    //speed based on distance from sensor
                    double d = distance.getDistance(DistanceUnit.CM);
                    theoreticalPower = limit(d / 200, 1);
                } catch (Exception e) {
                    sensorExceptionCount++;
                    //the distance sensor is wrong, no update this time
                }
            } else {
                try {
                    //gyro knob
                    int h = gyro.getHeading();
                    theoreticalPower = limit((360 - h) / 360, .8);
                } catch (Exception e) {
                    sensorExceptionCount++;
                }
            }//end if(mode)


            //set actual speed depending if motor is stopped or nah
            if(isStopped) {
                actualPower = 0;
            } else {
                actualPower = theoreticalPower;
            }


            //set power. Wrapped in try/catch in case one of the calculated values for speed is null/imaginary.
            try {
                motor.setPower(actualPower);
            } catch (Exception e) {
                sensorExceptionCount++;
            }



        }//end runOpMode


    }

    /**
     * Limit a number 'num' to number 'max'
     * @param num
     * @param max
     * @return double higher of the two numbers
     */
    private double limit(double num, double max) {
        return num > max ? max : num;
    }

    private String rgbString(ColorSensor s) {
        return "(" + s.red() * 8 + ", " + s.green() * 8 + ", " + s.blue() * 8 + ")";
    }
}
