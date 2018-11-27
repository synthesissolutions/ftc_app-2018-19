package org.firstinspires.ftc.teamcode.TELE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.util.Range.scale;
@TeleOp(name="Mecanum Teleop Practice Bot", group="TELE")
@Disabled
public class TELEMecanumPracticeBot extends OpMode {

    DcMotor motorLeftFront;
    DcMotor motorRightFront;
    DcMotor motorRightBack;
    DcMotor motorLeftBack;

    DcMotor motorHangTower;

    @Override
    public void init() {
        motorLeftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRightFront = hardwareMap.dcMotor.get("motorFrontRight");
        motorRightBack = hardwareMap.dcMotor.get("motorBackRight");
        motorLeftBack = hardwareMap.dcMotor.get("motorBackLeft");
        motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorHangTower = hardwareMap.dcMotor.get("motorHangTower");
        motorHangTower.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {
        double Speed = -gamepad1.left_stick_y;
        double Turn = gamepad1.right_stick_x;
        double Strafe = gamepad1.left_stick_x;
        double MAX_SPEED = 1.0;
        if (gamepad1.right_trigger>.3) {
            Speed=Speed/(gamepad1.right_trigger*3);
            Strafe=Strafe/(gamepad1.right_trigger*2);
        }
        if (gamepad1.left_trigger>.3)
        {
            Turn=Turn/(gamepad1.left_trigger*2.5);
            Speed=Speed/(gamepad1.left_trigger*2.5);
            Strafe=Strafe/(gamepad1.left_trigger*2);
        }
        holonomic(Speed, Turn, Strafe, MAX_SPEED );

        motorHangTower.setPower(scaleInput(gamepad2.left_stick_x));
    }


    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        //variables should not start with capital letter
        double Magnitude = Math.abs(Speed) + Math.abs(Turn) + Math.abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        motorLeftFront.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (motorLeftBack != null) {
            motorLeftBack.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        motorRightFront.setPower(scale((scaleInput(Speed) - scaleInput(Turn) -
                        scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (motorRightBack != null) {
            motorRightBack.setPower(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
    }

    @Override
    public void stop() {
    }







    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}