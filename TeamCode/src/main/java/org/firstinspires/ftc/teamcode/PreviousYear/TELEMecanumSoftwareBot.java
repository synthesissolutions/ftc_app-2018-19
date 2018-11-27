package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.util.Range.scale;

@TeleOp(name="Mecanum Teleop Software Bot", group="TELE")
@Disabled
public class TELEMecanumSoftwareBot extends OpMode {

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftBack;

    @Override
    public void init() {

        leftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        rightFront = hardwareMap.dcMotor.get("motorFrontRight");
        rightBack = hardwareMap.dcMotor.get("motorBackRight");
        leftBack = hardwareMap.dcMotor.get("motorBackLeft");
    }

    @Override
    public void loop() {
        double Speed = -gamepad1.left_stick_y;
        double Turn = -gamepad1.right_stick_x;
        double Strafe = -gamepad1.left_stick_x;
        double MAX_SPEED = 1.0;
        holonomic(Speed, Turn, Strafe, MAX_SPEED );
    }


    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        double Magnitude = Math.abs(Speed) + Math.abs(Turn) + Math.abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        leftFront.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (leftBack != null) {
            leftBack.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        rightFront.setPower(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (rightBack != null) {
            rightBack.setPower(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
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
