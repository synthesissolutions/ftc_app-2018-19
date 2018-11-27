package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import java.util.List;

@TeleOp(name="TEST Nathan", group="TEST")
@Disabled
public class TESTNathan extends LinearOpMode {

    ColorSensor colorSensor;
    //DcMotor motor = hardwareMap.dcMotor.get("motor1");
    DcMotor motor;
    Servo servo;


    @Override
    public void runOpMode() {

        servo = hardwareMap.servo.get("servo2");
        motor = hardwareMap.dcMotor.get("motor1");

        List<HardwareDevice> devices = hardwareMap.getAll(HardwareDevice.class);

        for(int i = 0; i < devices.size(); i++){
            telemetry.addData("Device Name: ", devices.get(i).getDeviceName());
            telemetry.addLine("-Connection Info: ");
            telemetry.addLine(devices.get(i).getConnectionInfo());
            telemetry.addLine("-----------------------");
            telemetry.update();
        }

        waitForStart();;

        telemetry.clear();

        boolean forward = true; //Are we going forward or backward
        double increment = 0.01; //how much to increment per loop
        double maxSpeed = .6; //max motor speed in either direction
        double power = 0; //starting power
        int rampSpeed = 1; // 1, 2, or 3

        for(int i = 0; i < 5; i++) {
            telemetry.addData("Iteration: ", i + 1);

            while(opModeIsActive()){

                //change power
                power += forward ? increment : -increment;

                //flip direction
                if(power >= maxSpeed) {
                    forward = false;
                } else if (power <= -maxSpeed) {
                    forward = true;
                }

                //GO!
                motor.setPower(power);
                telemetry.addData("Motor Power: ", power*100);
                telemetry.update();
                sleep(rampSpeed == 1 ? 50 : rampSpeed == 2 ? 30 : 10);
            }
        }



        /*

            sleep(2000);

            colorSensor = hardwareMap.get(ColorSensor.class, "sensorJewelColor");
            int blue = colorSensor.blue();
            int red = colorSensor.red();

            if (red > blue) {
                // detected red knock it off
                flickerServo.setPosition(1);
            } else if (blue > red) {
                // detected blue - go the opposite direction
                flickerServo.setPosition(0.0);
            }
        telemetry.addData("red:", red);
        telemetry.addData("blue:", blue);
        telemetry.update();

            sleep(5000);
        armServo.setPosition(0.05);
            */
    }

    private void wag(int count){
        double wagSize;
        int waitTime;

        for(int i = 0; i < count; i++) {
            wagSize = randInterval(.05 , .4);
            waitTime = wagSize > .8 ? 400 : wagSize > .6 ? 300 : wagSize > .4 ? 200 : wagSize > .2 ? 100 : 50;

            //servo.setPosition(wagSize);
            sleep(waitTime);

            //servo.setPosition(wagSize);
            sleep(waitTime);
        }

    }

    private double randInterval(double low, double high) {
        java.util.Random rand = new java.util.Random();

        return rand.nextDouble();
    }
}
