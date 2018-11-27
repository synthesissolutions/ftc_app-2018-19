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

@TeleOp(name="TEST Motor Multiplier", group="TEST")

@Disabled
public class TESTMotorAdjusterAdjuster extends OpMode {

    final static double GRIPPER_LEFT_OPEN = 0.89;
    final static double GRIPPER_RIGHT_OPEN = 0.10;
    final static double GRIPPER_LEFT_CLOSED = 0.53;
    final static double GRIPPER_RIGHT_CLOSED = 0.47;
    final static double GRIPPER_LEFT_MID = 0.68;
    final static double GRIPPER_RIGHT_MID = 0.32;

    //Should we have scope on these vars?
    DcMotor motorLeftFront;
    DcMotor motorRightFront;
    DcMotor motorRightBack;
    DcMotor motorLeftBack;
    int slowTimer=100;
    boolean slowed=false;
    DcMotor motorTowerGlyph;
    DcMotor motorTowerRelic;
    Servo servoGlyphLeft;
    Servo servoGlyphRight;
    Servo servoJewelArm;
    Servo servoJewelFlicker;
    Servo servoRelicHand;
    double servoGLposition=GRIPPER_LEFT_OPEN; //naming on these two vars
    double servoGRposition=GRIPPER_RIGHT_OPEN;
    ColorSensor sensorJewelColor;
    Double multiplierLeftFront,multiplierRightFront,multiplierLeftBack,multiplierRightBack=1.0;
    int timerX,timerY,timerA,timerB,timerLeft,timerRight,timerUp,timerDown=0;

    @Override
    public void init() {
        timerX=timerY=timerA=timerB=timerLeft=timerRight=timerUp=timerDown;
        multiplierLeftFront=multiplierRightFront=multiplierLeftBack=multiplierRightBack;
        motorLeftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRightFront = hardwareMap.dcMotor.get("motorFrontRight");
        motorRightBack = hardwareMap.dcMotor.get("motorBackRight");
        motorLeftBack = hardwareMap.dcMotor.get("motorBackLeft");
        motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorGlyphTower = hardwareMap.dcMotor.get("motorGlyphTower");
        //motorTowerRelic = hardwareMap.dcMotor.get("motorTowerRelic");
        //servoGlyphLeftBot = hardwareMap.servo.get("servoLeftGlyph");
        //servoGlyphRightBot = hardwareMap.servo.get("servoRightGlyph");
        //MIGHT need servoGlyphTouchArm
        //servoJewelArm = hardwareMap.servo.get("servoJewelArm");
        //servoJewelFlicker = hardwareMap.servo.get("servoJewelFlicker");
        //servoRelicHand = hardwareMap.servo.get("servoRelicHand");
        //servoJewelFlicker.setPosition(0.3);
        //servoJewelArm.setPosition(0.05);
        //sensorJewelColor = hardwareMap.get(ColorSensor.class, "sensorJewelColor");
        //servoGlyphLeftBot.setPosition(GRIPPER_LEFT_BOT_OPEN);
        //servoGlyphRightBot.setPosition(GRIPPER_RIGHT_BOT_OPEN);

        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if (timerA==50)
        {
            if (gamepad1.a)
            {
                timerA=0;
                multiplierRightBack-=0.01;
            }
        }
        else
        {
            if (timerA<50)
            {
                timerA++;
            }
        }

        if (timerY==50)
        {
            if (gamepad1.y)
            {
                timerY=0;
                multiplierRightBack+=0.01;
            }
        }
        else
        {
            if (timerY<50)
            {
                timerY++;
            }
        }

        if (timerX==50)
        {
            if (gamepad1.x)
            {
                timerX=0;
                multiplierRightFront+=0.01;
            }
        }
        else
        {
            if (timerX<50)
            {
                timerX++;
            }
        }

        if (timerB==50)
        {
            if (gamepad1.b)
            {
                timerB=0;
                multiplierRightFront-=0.01;
            }
        }
        else
        {
            if (timerB<50)
            {
                timerB++;
            }
        }

        if (timerDown==50)
        {
            if (gamepad1.dpad_down)
            {
                timerDown=0;
                multiplierLeftBack-=0.01;
            }
        }
        else
        {
            if (timerDown<50)
            {
                timerDown++;
            }
        }

        if (timerUp==50)
        {
            if (gamepad1.dpad_up)
            {
                timerUp=0;
                multiplierLeftBack+=0.01;
            }
        }
        else
        {
            if (timerUp<50)
            {
                timerUp++;
            }
        }

        if (timerLeft==50)
        {
            if (gamepad1.dpad_left)
            {
                timerLeft=0;
                multiplierLeftFront+=0.01;
            }
        }
        else
        {
            if (timerLeft<50)
            {
                timerLeft++;
            }
        }

        if (timerRight==50)
        {
            if (gamepad1.dpad_right)
            {
                timerRight=0;
                multiplierLeftFront-=0.01;
            }
        }
        else
        {
            if (timerRight<50)
            {
                timerRight++;
            }
        }
        multiplierRightFront=Range.clip(multiplierRightFront,0.0,1.0);
        multiplierRightBack=Range.clip(multiplierRightBack,0.0,1.0);
        multiplierLeftFront=Range.clip(multiplierLeftFront,0.0,1.0);
        multiplierLeftBack=Range.clip(multiplierLeftBack,0.0,1.0);
        if(gamepad1.right_bumper)
        {
            motorLeftBack.setPower(.5*multiplierLeftBack);
            motorRightFront.setPower(.5*multiplierRightFront);
            motorRightBack.setPower(-.5*multiplierRightBack);
            motorLeftFront.setPower(-.5*multiplierLeftFront);
        }
        else if (gamepad1.left_bumper)
        {
            motorLeftBack.setPower(-.5*multiplierLeftBack);
            motorRightFront.setPower(-.5*multiplierRightFront);
            motorRightBack.setPower(.5*multiplierRightBack);
            motorLeftFront.setPower(.5*multiplierLeftFront);
        }
        else
        {
            motorLeftBack.setPower(0);
            motorRightFront.setPower(0);
            motorRightBack.setPower(0);
            motorLeftFront.setPower(0);
        }
        telemetry.addData("multiplier right front (X B):        ",multiplierRightFront);
        telemetry.addData("multiplier right back  (Y A):        ",multiplierRightBack);
        telemetry.addData("multiplier left front  (LEFT RIGHT): ",multiplierLeftFront);
        telemetry.addData("multiplier left back  (UP DOWN):     ",multiplierLeftBack);


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
