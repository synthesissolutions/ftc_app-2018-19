package org.firstinspires.ftc.teamcode.PreviousYear;

import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.TouchSensor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.robotcore.util.Range.scale;

@TeleOp(name="Mecanum Teleop Cayenne Old", group="TELE")
@Disabled
public class TELEMecanumCayenne extends OpMode implements SensorEventListener {

    final static double GRIPPER_LEFT_BOT_OPEN = 0.60;
    final static double GRIPPER_RIGHT_BOT_OPEN = 0.65;
    final static double GRIPPER_LEFT_BOT_CLOSED = 0.0;
    final static double GRIPPER_RIGHT_BOT_CLOSED = 1.0;
    final static double GRIPPER_LEFT_BOT_MID = 0.37;
    final static double GRIPPER_RIGHT_BOT_MID = 0.75;
    final static double JEWEL_FLICKER_START = 0.13;
    final static double JEWEL_FLICKER_MID = 0.63;
    final static double JEWEL_ARM_IN = 0.90;
    final static double RELIC_ARM_START = 0.33;
    final static double RELIC_HAND_CLOSED = 1;
    final static double RELIC_HAND_OPEN = .3;
    final static double GLYPH_TOUCH_ARM_UP = 0.70;
    final static double GLYPH_TOUCH_ARM_DOWN = 0.15;
    final static double MAX_SPEED = 1.0;

    DcMotor motorLeftFront;
    DcMotor motorRightFront;
    DcMotor motorRightBack;
    DcMotor motorLeftBack;
    DcMotor motorGlyphTower;
    DcMotor motorRelicTower;
    //Servo servoGlyphLeftTop;
    //Servo servoGlyphRightTop;
    Servo servoGlyphLeftBot;
    Servo servoGlyphRightBot;
    Servo servoJewelArm;
    Servo servoJewelFlicker;
    Servo servoGlyphTouchArm;
    Servo servoRelicHand;
    Servo servoRelicArm;
    ColorSensor sensorJewelColorFlicker;
    ColorSensor sensorJewelColorArm;
    TouchSensor sensorGlyphTouch;

    double GLBPosition = GRIPPER_LEFT_BOT_OPEN; //naming on these two vars
    double GRBPosition = GRIPPER_RIGHT_BOT_OPEN;
    //double GLTPosition = GRIPPER_LEFT_TOP_OPEN;
    //double GRTPosition = GRIPPER_RIGHT_TOP_OPEN;
    double relicArmPosition = RELIC_ARM_START;
    double relicHandPosition = RELIC_HAND_CLOSED;
    double startOfStrafeHeading=0.0;
    double turnAmount=0.0;
    int targetTowerPosition = 0;
    int turnMultiplier=1;
    boolean inStrafe=false;
    boolean firstTime=true;
    List<Float> gyroEventValues = new ArrayList<>();
    public float currentHeading = 0.0f;

    @Override
    public void init() {
        motorGlyphTower = hardwareMap.dcMotor.get("motorGlyphTower");
        motorRelicTower = hardwareMap.dcMotor.get("motorRelicTower");
        motorLeftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRightFront = hardwareMap.dcMotor.get("motorFrontRight");
        motorRightBack = hardwareMap.dcMotor.get("motorBackRight");
        motorLeftBack = hardwareMap.dcMotor.get("motorBackLeft");
        motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorGlyphTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlyphTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servoGlyphLeftBot = hardwareMap.servo.get("servoGlyphLeftBot");
        servoGlyphRightBot = hardwareMap.servo.get("servoGlyphRightBot");
        //servoGlyphLeftTop = hardwareMap.servo.get("servoGlyphLeftTop");
        //servoGlyphRightTop = hardwareMap.servo.get("servoGlyphRightTop");
        servoJewelArm = hardwareMap.servo.get("servoJewelArm");
        servoJewelFlicker = hardwareMap.servo.get("servoJewelFlicker");
        servoGlyphTouchArm = hardwareMap.servo.get("servoGlyphTouchArm");
        servoRelicHand = hardwareMap.servo.get("servoRelicHand");
        servoRelicArm = hardwareMap.servo.get("servoRelicArm");
        servoGlyphLeftBot.setPosition(GLBPosition);
        servoGlyphRightBot.setPosition(GRBPosition);
        //servoGlyphRightTop.setPosition(GRTPosition);
        //servoGlyphLeftTop.setPosition(GLTPosition);
        servoJewelFlicker.setPosition(JEWEL_FLICKER_START);
        servoJewelArm.setPosition(JEWEL_ARM_IN);
        servoGlyphTouchArm.setPosition(GLYPH_TOUCH_ARM_UP);
        servoRelicHand.setPosition(relicHandPosition);
        servoRelicArm.setPosition(relicArmPosition);
        sensorJewelColorArm = hardwareMap.get(ColorSensor.class, "sensorJewelColorArm");
        sensorJewelColorArm.enableLed(true);
        sensorJewelColorFlicker = hardwareMap.get(ColorSensor.class, "sensorJewelColorFlicker");
        sensorJewelColorFlicker.setI2cAddress(I2cAddr.create8bit(0x44));
        sensorJewelColorFlicker.enableLed(true);

        motorGlyphTower.setTargetPosition(0);
        motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlyphTower.setPower(0.65);

        initializePhoneGyro();
    }

    @Override
    public void loop() {
        if (firstTime)
        {
            firstTime=false;
            relicArmPosition= .22;
        }
        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID);

        boolean relicArmMoving = false;

        //MAIN DRIVE
        double speed = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;
        /*if (Math.abs(strafe)>.2)
        {
            //speed=0;
            turn=0;
            if (!inStrafe) {
                startOfStrafeHeading = getGyroPos();
                inStrafe=true;
            }
            if (startOfStrafeHeading!=getGyroPos())
            {
                if (turnLeftOrRight(getGyroPos(),startOfStrafeHeading)=="left")
                {
                    turnMultiplier=1;
                }
                else
                {
                    turnMultiplier=-1;
                }
                turnAmount=1*(Math.min(Math.abs(getGyroPos()-startOfStrafeHeading),Math.abs(360+getGyroPos()-startOfStrafeHeading))/30); //TODO: try this number higher

                turnAmount=Range.clip(turnAmount,-1,1);
                turn=turnAmount*turnMultiplier;
            }
        }
        else
        {
            inStrafe=false;
        }*/

        if (gamepad1.right_trigger>.7) {
            speed=speed/3.0;
            strafe=strafe/2.0;
        }
        if (gamepad1.left_trigger>.7)
        {
            turn=turn/1.50;
        }
        holonomic(speed, turn, strafe, MAX_SPEED );
        //RELIC TOWER GAMEPAD 1 CONTROLS
        /*if (gamepad1.dpad_up) {
            motorRelicTower.setPower(.5);
        } else if (gamepad1.dpad_down) {
            motorRelicTower.setPower(-.5);
        }
        else
        {
            motorRelicTower.setPower(0);
        }*/
        //RELIC TOWER GAMEPAD 2 CONTROLS
        motorRelicTower.setPower(gamepad2.right_stick_y/2.0);

        //RELIC ARM GAMEPAD 1 CONTROLS
        /*if (gamepad1.left_bumper) {
            relicArmPosition -= 0.001;
        } else if (gamepad1.right_bumper) {
            relicArmPosition += 0.001;
        }*/
        //RELIC ARM GAMEPAD 2 CONTROLS
        if (gamepad2.left_bumper) {
            relicArmMoving = true;
            relicArmPosition -= 0.005;
        } else if (gamepad2.right_bumper) {
            relicArmMoving = true;
            relicArmPosition += 0.005;
        } else if (relicArmMoving) {
            // Hold the current position and stop moving someone just released the button
            relicArmMoving = false;
            relicArmPosition = servoRelicArm.getPosition();
        }

        //RELIC HAND GAMEPAD 1 CONTROLS
        /*relicHandPosition=Range.clip(relicHandPosition, RELIC_HAND_OPEN,RELIC_HAND_CLOSED);

        if (gamepad1.dpad_right)
        {
            if (relicHandPosition!=RELIC_HAND_CLOSED)
            {
                relicHandPosition +=.03;
            }
            else
            {
                relicHandPosition=1.0;
            }
        }
        if (gamepad1.dpad_left)
        {
            relicHandPosition -=.03;
        }
        relicHandPosition=Range.clip(relicHandPosition, RELIC_HAND_OPEN,1.0);*/
        //RELIC HAND GAMEPAD 1 CONTROLS
        relicHandPosition=Range.clip(relicHandPosition, RELIC_HAND_OPEN,RELIC_HAND_CLOSED);

        if (gamepad2.left_trigger>.7)
        {
            if (relicHandPosition!=RELIC_HAND_CLOSED)
            {
                relicHandPosition +=.03;
            }
            else
            {
                relicHandPosition=1.0;
            }
        }
        if (gamepad2.right_trigger>.7)
        {
            relicHandPosition -=.03;
        }
        relicHandPosition=Range.clip(relicHandPosition, RELIC_HAND_OPEN,1.0);

        //GLYPH TOWER
        if (Math.abs(gamepad2.left_stick_y) > 0.1) {
            targetTowerPosition += gamepad2.left_stick_y * 20;
        }

        if (gamepad2.dpad_down) {
            targetTowerPosition = 0;
            motorGlyphTower.setTargetPosition(0);
            motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorGlyphTower.setPower(0.65);
        }

        if (gamepad2.dpad_left) {
            targetTowerPosition = -300;
            motorGlyphTower.setTargetPosition(-300);
            motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorGlyphTower.setPower(0.65);
        }

        if (gamepad2.dpad_up) {
            targetTowerPosition = -1100;
            motorGlyphTower.setTargetPosition(-1100);
            motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorGlyphTower.setPower(0.65);
        }

        if (gamepad2.dpad_right) {
            targetTowerPosition = -2050;
        }

        motorGlyphTower.setTargetPosition(targetTowerPosition);

        //GLYPH SERVOS
                /*if (gamepad2.dpad_left)
        {
            GLBPosition +=(GRIPPER_LEFT_BOT_OPEN-GRIPPER_LEFT_BOT_CLOSED)/33.3;
            GRBPosition +=(GRIPPER_RIGHT_BOT_OPEN-GRIPPER_RIGHT_BOT_CLOSED)/33.3;
            GLTPosition +=(GRIPPER_LEFT_TOP_OPEN-GRIPPER_LEFT_TOP_CLOSED)/33.3;
            GRTPosition +=(GRIPPER_RIGHT_TOP_OPEN-GRIPPER_RIGHT_TOP_CLOSED)/33.3;
        }
        if (gamepad2.dpad_right)
        {
            GLBPosition -=(GRIPPER_LEFT_BOT_OPEN-GRIPPER_LEFT_BOT_CLOSED)/33.3;
            GRBPosition -=(GRIPPER_RIGHT_BOT_OPEN-GRIPPER_RIGHT_BOT_CLOSED)/33.3;
            GLTPosition -=(GRIPPER_LEFT_TOP_OPEN-GRIPPER_LEFT_TOP_CLOSED)/33.3;
            GRTPosition -=(GRIPPER_RIGHT_TOP_OPEN-GRIPPER_RIGHT_TOP_CLOSED)/33.3;
        }*/

        if (gamepad2.x) {
            GLBPosition = GRIPPER_LEFT_BOT_OPEN;
            GRBPosition = GRIPPER_RIGHT_BOT_OPEN;
            //GLTposition = GRIPPER_LEFT_TOP_OPEN;
            //GRTposition = GRIPPER_RIGHT_TOP_OPEN;
        }
        if ( gamepad2.y) {
            GLBPosition = GRIPPER_LEFT_BOT_MID;
            GRBPosition = GRIPPER_RIGHT_BOT_MID;
            //GLTposition = GRIPPER_LEFT_TOP_MID;
            //GRTposition = GRIPPER_RIGHT_TOP_MID;
        }
        if (gamepad2.b) {
            GLBPosition = GRIPPER_LEFT_BOT_CLOSED;
            GRBPosition = GRIPPER_RIGHT_BOT_CLOSED;
            //GLTposition = GRIPPER_LEFT_TOP_CLOSED;
            //GRTposition = GRIPPER_RIGHT_TOP_CLOSED;
        }


        //GLBPosition= Range.clip(GLBPosition,Math.min(GRIPPER_LEFT_BOT_CLOSED,GRIPPER_LEFT_BOT_OPEN),Math.max(GRIPPER_LEFT_BOT_CLOSED,GRIPPER_LEFT_BOT_OPEN));
        //GLTPosition= Range.clip(GLTPosition,Math.min(GRIPPER_LEFT_TOP_CLOSED,GRIPPER_LEFT_TOP_OPEN),Math.max(GRIPPER_LEFT_TOP_CLOSED,GRIPPER_LEFT_TOP_OPEN));
        //GRBPosition= Range.clip(GRBPosition,Math.min(GRIPPER_RIGHT_BOT_CLOSED,GRIPPER_RIGHT_BOT_OPEN),Math.max(GRIPPER_RIGHT_BOT_CLOSED,GRIPPER_RIGHT_BOT_OPEN));
        //GRTPosition= Range.clip(GRTPosition,Math.min(GRIPPER_RIGHT_TOP_CLOSED,GRIPPER_RIGHT_TOP_OPEN),Math.max(GRIPPER_RIGHT_TOP_CLOSED,GRIPPER_RIGHT_TOP_OPEN));
        servoGlyphLeftBot.setPosition(GLBPosition);
        servoGlyphRightBot.setPosition(GRBPosition);

        servoRelicArm.setPosition(relicArmPosition);
        servoRelicHand.setPosition(relicHandPosition);

        //TELEMETRY
        telemetry.addData("Teleop Prime Version", "1.3");
        telemetry.addData("turn",turn);
        telemetry.addData("Teleop Current Heading", getGyroPos());
        telemetry.addData("Teleop Start Heading", startOfStrafeHeading);
        telemetry.addData("Teleop In strafe?", inStrafe);
        telemetry.addData("Relic arm position", relicArmPosition);

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

    public double getGyroPos()
    {
        double gyroPos=currentHeading;
        if (currentHeading<0)
        {
            gyroPos=currentHeading+360;
        }
        return gyroPos;
    }
    String turnLeftOrRight(double ourGyroPos, double goalGyroPos)
    {
        double math=0.0;
        String returner="left";
        if (ourGyroPos<=180.0 && goalGyroPos<=180.0)
        {
            math=ourGyroPos-goalGyroPos;
            if (math>0.0)
            {
                returner = "right";
            }
            else
            {
                returner = "left";
            }
        }
        else if (ourGyroPos>=180.0&&goalGyroPos<=180.0)
        {
            math = ourGyroPos - goalGyroPos;
            if (math > 180.0)
            {
                returner = "left";
            } else
            {
                returner = "right";
            }
        }
        else if (ourGyroPos<=180.0&&goalGyroPos>=180.0)
        {
            math = ourGyroPos - goalGyroPos;
            if (math > -180.0)
            {
                returner = "left";
            } else
            {
                returner = "right";
            }
        }
        else if (ourGyroPos>=180.0&&goalGyroPos>=180.0)
        {
            math = ourGyroPos - goalGyroPos;
            if (math > 0.0)
            {
                returner = "right";
            } else
            {
                returner = "left";
            }
        }
        return returner;
    }
    /******************************************************************************************************************/
    //PHONE GYRO
    protected void initializePhoneGyro() {
        SensorManager sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gameRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void deregisterPhoneGyro() {
    }

    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        currentHeading = (float) Math.toDegrees(orientation[0]);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    /******************************************************************************************************************/
}