package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.util.Range.scale;

@TeleOp(name="WIP Mecanum Teleop Alt", group="WIP")
@Disabled
public class WIPMecanumTeleopAlt extends OpMode {

    final static double GRIPPER_LEFT_BOT_OPEN = 0.77;
    final static double GRIPPER_RIGHT_BOT_OPEN = 0.51;
    final static double GRIPPER_LEFT_BOT_MID = 0.58;
    final static double GRIPPER_RIGHT_BOT_MID = 0.66;
    final static double GRIPPER_LEFT_BOT_CLOSED = 0.305;
    final static double GRIPPER_RIGHT_BOT_CLOSED = .933; //.94
    final static double GRIPPER_LEFT_TOP_OPEN = 0.82;
    final static double GRIPPER_RIGHT_TOP_OPEN = 0.0;
    final static double GRIPPER_LEFT_TOP_CLOSED = 0.3;
    final static double GRIPPER_RIGHT_TOP_CLOSED = 0.4; //.4
    final static double GRIPPER_LEFT_TOP_MID = 0.51;
    final static double GRIPPER_RIGHT_TOP_MID = 0.2;


    //Should we have scope on these vars?
    DcMotor motorLeftFront;
    DcMotor motorRightFront;
    DcMotor motorRightBack;
    DcMotor motorLeftBack;
    int slowTimer=100;
    boolean slowed=false;
    DcMotor motorTowerGlyph;
    DcMotor motorTowerRelic;
    Servo servoGlyphLeftTop;
    Servo servoGlyphRightTop;
    Servo servoGlyphLeftBot;
    Servo servoGlyphRightBot;
    Servo servoJewelArm;
    Servo servoJewelFlicker;
    Servo servoRelicHand;
    Servo servoRelicContinuous;

    double GLBposition = GRIPPER_LEFT_BOT_OPEN; //naming on these two vars
    double GRBposition = GRIPPER_RIGHT_BOT_OPEN;
    double GLTposition = GRIPPER_LEFT_TOP_OPEN;
    double GRTposition = GRIPPER_RIGHT_TOP_OPEN;
    ColorSensor sensorJewelColor;


    @Override
    public void init() {
        motorTowerGlyph = hardwareMap.dcMotor.get("motorGlyphTower");
        motorLeftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRightFront = hardwareMap.dcMotor.get("motorFrontRight");
        motorRightBack = hardwareMap.dcMotor.get("motorBackRight");
        motorLeftBack = hardwareMap.dcMotor.get("motorBackLeft");
        motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        servoGlyphLeftBot = hardwareMap.servo.get("servoGlyphLeftBot");
        servoGlyphRightBot = hardwareMap.servo.get("servoGlyphRightBot");
        servoGlyphLeftTop = hardwareMap.servo.get("servoGlyphLeftTop");
        servoGlyphRightTop = hardwareMap.servo.get("servoGlyphRightTop");
        servoJewelArm = hardwareMap.servo.get("servoJewelArm");
        servoJewelFlicker = hardwareMap.servo.get("servoJewelFlicker");
        servoRelicHand = hardwareMap.servo.get("servoRelicHand");
        servoRelicContinuous = hardwareMap.servo.get("servoRelicContinuous");
        servoGlyphLeftBot.setPosition(GRIPPER_LEFT_BOT_OPEN);
        servoGlyphRightBot.setPosition(GRIPPER_RIGHT_BOT_OPEN);
        servoGlyphRightTop.setPosition(GRIPPER_RIGHT_TOP_OPEN);
        servoGlyphLeftTop.setPosition(GRIPPER_LEFT_TOP_OPEN);
        servoJewelFlicker.setPosition(0.15);
        servoRelicHand.setPosition(.9);
        servoJewelArm.setPosition(0.15);
        servoRelicContinuous.setPosition(0.5);

        sensorJewelColor = hardwareMap.get(ColorSensor.class, "sensorJewelColor");

    }

    @Override
    public void loop() {
        double Speed = -gamepad1.left_stick_y;
        double Turn = gamepad1.right_stick_x;
        double Strafe = -gamepad1.left_stick_x;
        double MAX_SPEED = 1.0;
        if (slowTimer==100)
        {
            if (gamepad1.y)
            {
                slowTimer=0;
                slowed=!slowed;
            }
        }
        else
        {
            slowTimer++;
        }

        if (slowed)
        {Speed=Speed/5.0;
        Turn=Turn/5.0;
        Strafe=Strafe/5.0;}

        holonomic(Speed, Turn, Strafe, MAX_SPEED );

        motorTowerGlyph.setPower(gamepad2.right_stick_y / 2.0);

        if (gamepad2.dpad_left)
        {
            GLBposition +=(GRIPPER_LEFT_BOT_OPEN-GRIPPER_LEFT_BOT_CLOSED)/33.3;
            GRBposition +=(GRIPPER_RIGHT_BOT_OPEN-GRIPPER_RIGHT_BOT_CLOSED)/33.3;
            GLTposition +=(GRIPPER_LEFT_TOP_OPEN-GRIPPER_LEFT_TOP_CLOSED)/33.3;
            GRTposition +=(GRIPPER_RIGHT_TOP_OPEN-GRIPPER_RIGHT_TOP_CLOSED)/33.3;
        }
        if (gamepad2.dpad_right)
        {
            GLBposition -=(GRIPPER_LEFT_BOT_OPEN-GRIPPER_LEFT_BOT_CLOSED)/33.3;
            GRBposition -=(GRIPPER_RIGHT_BOT_OPEN-GRIPPER_RIGHT_BOT_CLOSED)/33.3;
            GLTposition -=(GRIPPER_LEFT_TOP_OPEN-GRIPPER_LEFT_TOP_CLOSED)/33.3;
            GRTposition -=(GRIPPER_RIGHT_TOP_OPEN-GRIPPER_RIGHT_TOP_CLOSED)/33.3;
        }

        if (gamepad2.x) {
            GLBposition = GRIPPER_LEFT_BOT_OPEN;
            GRBposition = GRIPPER_RIGHT_BOT_OPEN;
            GLTposition = GRIPPER_LEFT_TOP_OPEN;
            GRTposition = GRIPPER_RIGHT_TOP_OPEN;
        }
        if (gamepad2.y) {
            GLBposition = GRIPPER_LEFT_BOT_MID;
            GRBposition = GRIPPER_RIGHT_BOT_MID;
            GLTposition = GRIPPER_LEFT_TOP_MID;
            GRTposition = GRIPPER_RIGHT_TOP_MID;
        }
        if (gamepad2.b) {
            GLBposition = GRIPPER_LEFT_BOT_CLOSED;
            GRBposition = GRIPPER_RIGHT_BOT_CLOSED;
            GLTposition = GRIPPER_LEFT_TOP_CLOSED;
            GRTposition = GRIPPER_RIGHT_TOP_CLOSED;
        }
        GLBposition= Range.clip(GLBposition,Math.min(GRIPPER_LEFT_BOT_CLOSED,GRIPPER_LEFT_BOT_OPEN),Math.max(GRIPPER_LEFT_BOT_CLOSED,GRIPPER_LEFT_BOT_OPEN));
        GLTposition= Range.clip(GLTposition,Math.min(GRIPPER_LEFT_TOP_CLOSED,GRIPPER_LEFT_TOP_OPEN),Math.max(GRIPPER_LEFT_TOP_CLOSED,GRIPPER_LEFT_TOP_OPEN));
        GRBposition= Range.clip(GRBposition,Math.min(GRIPPER_RIGHT_BOT_CLOSED,GRIPPER_RIGHT_BOT_OPEN),Math.max(GRIPPER_RIGHT_BOT_CLOSED,GRIPPER_RIGHT_BOT_OPEN));
        GRTposition= Range.clip(GRTposition,Math.min(GRIPPER_RIGHT_TOP_CLOSED,GRIPPER_RIGHT_TOP_OPEN),Math.max(GRIPPER_RIGHT_TOP_CLOSED,GRIPPER_RIGHT_TOP_OPEN));
        servoGlyphLeftBot.setPosition(GLBposition);
        servoGlyphRightBot.setPosition(GRBposition);
        servoGlyphLeftTop.setPosition(GLTposition);
        servoGlyphRightTop.setPosition(GRTposition);

        telemetry.addData("L Pos: ", GLBposition);
        telemetry.addData("R Pos: ", GRBposition);
        telemetry.update();
    }


    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        //variables should not start with capital letter
        double Magnitude = Math.abs(Speed) + Math.abs(Turn) + Math.abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        motorLeftFront.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (motorLeftBack != null) {
            motorLeftBack.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        motorRightFront.setPower(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (motorRightBack != null) {
            motorRightBack.setPower(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
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
