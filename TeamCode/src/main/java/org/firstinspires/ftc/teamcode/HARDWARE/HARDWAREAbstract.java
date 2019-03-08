package org.firstinspires.ftc.teamcode.HARDWARE;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.telecom.TelecomManager;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.robotcore.util.Range.scale;


public abstract class HARDWAREAbstract implements SensorEventListener{

    /*

    RULES FOR NAMING VARIABLES:
    1. If the variable is an hardware device, like a motor or a servo or a color sensor,
    always lead with the type of hardware device it is.
    servoJewelFlicker is correct.
    jewelFlickerServo or jewelFlicker are not correct

    2. After the hardware device type, the next part of the name should be the assembly
    or goal that the variable is used in. The only exception to this rule are our main
    mecanum wheels. For example:
    motorGlyphTower is correct.
    motorTowerGlyph is incorrect.
    motorFrontLeft normally should be motorMecanumFrontLeft, but it is an exception.
    This goes for variables that aren't hardware devices as well. For example:
    relicArmPosition, a variable for storing the position of the relic arm servo, is
    correct.
    GLBPosition, a variable for storing the position of the left-bottom glyph servo,
    is incorrect. it should be written as glyphLBPosition.
    Ignore this rule if the variable is not tied to a specific assembly or goal, E.G.
    the hardwareClass variable.

    3. Have fun!

    */


    final public static double MECANUM_MAX_SPEED = 1.0;
    final public static double SERVO_DEPLOY_GATE_RIGHT_OPEN = 0.58;
    final public static double SERVO_DEPLOY_GATE_RIGHT_CLOSED = 0.16;
    final public static double SERVO_DEPLOY_GATE_LEFT_OPEN = 0.17;
    final public static double SERVO_DEPLOY_GATE_LEFT_CLOSED = 0.63;

    final public static double SERVO_DEPLOY_POUR_UNPOURED = 0.25;
    final public static double SERVO_DEPLOY_POUR_PRE_DOWN = 0.3;
    final public static double SERVO_DEPLOY_POUR_POURED = .65;

    final double SERVO_MARKER_DELIVERY_DOWN = 0.6;
    final double SERVO_MARKER_DELIVERY_UP_TELE = 0.1;
    final double SERVO_MARKER_DELIVERY_UP_INIT = 0;

    final double SLOW_STRAFE_FACTOR = 1.4;
    final double SLOW_TURN_FACTOR = 1.20;
    final double SLOW_SPEED_FACTOR = 1.4;

    final public static double SERVO_COLLECT_DELIVERY_GATE_CLOSED = 1.0;
    final public static double SERVO_COLLECT_DELIVERY_GATE_OPEN = 0.5;

    final public static double SERVO_MINERAL_ARM_UP = 0.8;
    final public static double SERVO_MINERAL_ROTATE_IN = 0.1;

    final public static double  SERVO_MARKER_SHOULDER_START_POS = 0.05;
    final public static double  SERVO_MARKER_ELBOW_START_POS = 1.0;
    final public static double  SERVO_MARKER_WRIST_START_POS = 0.0;

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor motorHangTower;
    int hangTowerTargetPosition = 0;
    int hangTowerTimer=0;

    Servo servoMarkerDelivery;

    Servo servoMineralArm;
    Servo servoMineralRotate;

    Servo servoMarkerShoulder;
    Servo servoMarkerElbow;
    Servo servoMarkerWrist;

    //KOMODO
    DcMotor motorDeployTower;
    Servo servoDeployPour;
    Servo servoDeployGateLeft;
    Servo servoDeployGateRight;
    int deployTowerTargetPosition = 0;
    int deployTowerTimer=0;
    int deployGateLeftToggleTimer = 0;
    int deployGateRightToggleTimer = 0;
    boolean deployGateAutoclosed = false;

    DcMotor motorCollectSlide;
    DcMotor motorCollectRotate;
    Servo servoCollectSpin1;
    Servo servoCollectSpin2;
    int collectSlideTargetPosition = 0;
    int collectSlideTimer=0;

    //TABASCO
    DcMotor motorCollectionDeliveryAngle;
    DcMotor motorCollectionDeliverySlide;
    DcMotor motorCollectionDeliverySpin;
    Servo servoCollectionDeliveryGate;
    double collectionDeliveryGatePosition = SERVO_COLLECT_DELIVERY_GATE_CLOSED;
    int collectionDeliveryAngleState = 0;

    //TEST BOT
    DcMotor motorSingleWheelEncoderTest;


    //GENERIC
    ElapsedTime matchTimer = new ElapsedTime();

    String errors ="";

    List<Float> gyroEventValues = new ArrayList<>();
    float gyroCurrentHeading = 0.0f;
    public float offset = 0.0f;

    HardwareMap hardwareMap  = null;

    protected void initializePhoneGyro() {
        SensorManager sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gameRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void deregisterPhoneGyro() {
    }

    protected void offsetPhoneGyro() {
        offset+=gyroCurrentHeading;
    }

    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        gyroCurrentHeading = (float) ((Math.toDegrees(orientation[0]) - offset)% 360 - 360) % 360;
        if (Math.abs(gyroCurrentHeading) > 180) gyroCurrentHeading+=360;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void initializeMecanum()
    {
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void initializeHang()
    {
        motorHangTower = hardwareMap.dcMotor.get("motorHangTower");
    }

    public void initializeDeploy()
    {
        motorDeployTower = hardwareMap.dcMotor.get("motorDeployTower");
        motorDeployTower.setDirection(DcMotorSimple.Direction.FORWARD);
        motorDeployTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDeployTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorDeployTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDeployTower.setPower(1);

        servoDeployPour = hardwareMap.servo.get("servoDeployPour");
        servoDeployGateLeft = hardwareMap.servo.get("servoDeployGateLeft");
        servoDeployGateRight = hardwareMap.servo.get("servoDeployGateRight");

        servoDeployPour.setPosition(SERVO_DEPLOY_POUR_UNPOURED);
        servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_CLOSED);
        servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_CLOSED);
    }

    public void initializeCollect()
    {
        motorCollectSlide = hardwareMap.dcMotor.get("motorCollectSlide");
        motorCollectSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        motorCollectRotate = hardwareMap.dcMotor.get("motorCollectRotate");
        motorCollectRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        motorCollectRotate.setPower(0.0);

        servoCollectSpin1 = hardwareMap.servo.get("servoCollectSpin1");
        servoCollectSpin2 = hardwareMap.servo.get("servoCollectSpin2");
        servoCollectSpin1.setPosition(0.5);
        servoCollectSpin2.setPosition(0.5);

    }

    public void  initializeCollectionDelivery () {
        motorCollectionDeliveryAngle = hardwareMap.dcMotor.get("motorCollectionDeliveryAngle");
        motorCollectionDeliveryAngle.setDirection(DcMotorSimple.Direction.REVERSE);
        motorCollectionDeliveryAngle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorCollectionDeliveryAngle.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorCollectionDeliverySlide = hardwareMap.dcMotor.get("motorCollectionDeliverySlide");
        motorCollectionDeliverySpin = hardwareMap.dcMotor.get("motorCollectionDeliverySpin");
        motorCollectionDeliverySlide.setDirection(DcMotorSimple.Direction.FORWARD);
        motorCollectionDeliverySpin.setDirection(DcMotorSimple.Direction.FORWARD);


        servoCollectionDeliveryGate = hardwareMap.servo.get("servoCollectionDeliveryGate");
        servoCollectionDeliveryGate.setPosition(SERVO_COLLECT_DELIVERY_GATE_CLOSED);
    }

    public void initializeMarkerDelivery()
    {
        servoMarkerDelivery = hardwareMap.servo.get("servoMarkerDelivery");
        servoMarkerDelivery.setPosition(SERVO_MARKER_DELIVERY_UP_INIT);
    }

    public void initializeMarkerDeliveryKomodo() {
        servoMarkerShoulder = hardwareMap.get(Servo.class, "servoMarkerShoulder");
        servoMarkerElbow = hardwareMap.get(Servo.class, "servoMarkerElbow");
        servoMarkerWrist = hardwareMap.get(Servo.class, "servoMarkerWrist");

        servoMarkerShoulder.setPosition(SERVO_MARKER_SHOULDER_START_POS);
        servoMarkerWrist.setPosition(SERVO_MARKER_WRIST_START_POS);

    }

    public void initializeMineral()
    {
        servoMineralArm = hardwareMap.servo.get("servo_mineral_arm"); //todo FIX THE CONFIG. DAVID'S NAMES ARE TERRIBLE. WE USE CAMEL CASE, NOT UNDERSCORES
        servoMineralRotate = hardwareMap.servo.get("servo_mineral_rotate");
        servoMineralArm.setPosition(SERVO_MINERAL_ARM_UP);
        servoMineralRotate.setPosition(SERVO_MINERAL_ROTATE_IN);
    }

    public void initializeSingleWheelEncoderTest()
    {
        motorSingleWheelEncoderTest = hardwareMap.dcMotor.get("motorSingleWheelEncoderTest");
        motorSingleWheelEncoderTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSingleWheelEncoderTest.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorSingleWheelEncoderTest.setPower(1);
        int pos = (motorSingleWheelEncoderTest.getCurrentPosition())%1220;

    }

    //END OF ASSEMBLY INITIALIZATION METHODS

    public void initializeJalepeño(HardwareMap hwMap){
        this.hardwareMap = hwMap;
        try {
            initializeMecanum();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING MECANUM");
        }

        try {
            initializeHang();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING HANG");
        }

        try {
            initializeDeploy();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING DEPLOY");
        }

        try {
            initializeCollect();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING COLLECT");
        }

        try {
            initializePhoneGyro();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING PHONE GYRO");
        }

        try {
            initializeMarkerDelivery();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING MARKER DELIVERY");
        }

        try {
            initializePhoneGyro();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING PHONE GYRO");
        }

        try {
            initializeMineral();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING MINERAL");
        }
    }

    public void initializeKomodo(HardwareMap hwMap){
        this.hardwareMap = hwMap;
        try {
            initializeMecanum();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING MECANUM");
        }
        try {
            initializeCollectionDelivery();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING COLLECTION DELIVERY");
        }
        try {
            initializeHang();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING HANG");
        }
        try {
            initializeMarkerDeliveryKomodo();
        } catch (IllegalArgumentException e) {
            addErrors("ERROR INITIALIZING MARKER DELIVERY");
        }

//        motorCollectionDeliverySlide.setTargetPosition(0);
//        motorCollectionDeliverySlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorCollectionDeliverySlide.setPower(0.65);

    }

    public void initializeSingleWheelEncoderTestBot(HardwareMap hwMap){
        this.hardwareMap = hwMap;
        initializeSingleWheelEncoderTest();
    }

        public void controlMarkerDelivery(boolean j) {
        if (j)
        {
            setMarkerDeliveryPosition(SERVO_MARKER_DELIVERY_DOWN);
        }
        else setMarkerDeliveryPosition(SERVO_MARKER_DELIVERY_UP_TELE);

    }



    public double getGyroPos0360()
    {
        double gyroPos0360=gyroCurrentHeading;
        if (gyroCurrentHeading<0)
        {
            gyroPos0360=gyroCurrentHeading+360;
        }
        return gyroPos0360;
    }

    public void holonomic(double mecanumSpeed, double mecanumTurn, double mecanumStrafe, double mecanumMaxSpeed)
    {
        //Left Front = +mecanumSpeed + mecanumTurn - mecanumStrafe      Right Front = +mecanumSpeed - mecanumTurn + mecanumStrafe
        //Left Rear  = +mecanumSpeed + mecanumTurn + mecanumStrafe      Right Rear  = +mecanumSpeed - mecanumTurn - mecanumStrafe

        double mecanumMagnitude = Math.abs(mecanumSpeed) + Math.abs(mecanumTurn) + Math.abs(mecanumStrafe);
        mecanumMagnitude = (mecanumMagnitude > 1) ? mecanumMagnitude : 1; //Set scaling to keep -1,+1 range
        final double MECANNUM_BACK_WHEEL_MULTIPLIER = 0.95;

        if (motorFrontLeft != null)
        {
            motorFrontLeft.setPower(-scale((scaleInput(mecanumSpeed) + scaleInput(mecanumTurn) - scaleInput(mecanumStrafe)),
                    -mecanumMagnitude, +mecanumMagnitude, -mecanumMaxSpeed, +mecanumMaxSpeed));
        }
        else
        {
            addErrors("MOTOR FRONT LEFT IS NULL");
        }
        if (motorBackLeft != null)
        {
            motorBackLeft.setPower(-scale((scaleInput(mecanumSpeed) + scaleInput(mecanumTurn) + scaleInput(mecanumStrafe* MECANNUM_BACK_WHEEL_MULTIPLIER)),
                    -mecanumMagnitude, +mecanumMagnitude, -mecanumMaxSpeed, +mecanumMaxSpeed));
        }
        else
        {
            addErrors("MOTOR BACK LEFT IS NULL");
        }
        if (motorFrontRight != null) {
            motorFrontRight.setPower(-(scale((scaleInput(mecanumSpeed) - scaleInput(mecanumTurn) + scaleInput(mecanumStrafe)),
                    -mecanumMagnitude, +mecanumMagnitude, -mecanumMaxSpeed, +mecanumMaxSpeed)));
        }
        else
        {
            addErrors("MOTOR FRONT RIGHT IS NULL");
        }
        if (motorBackLeft != null) {
            motorBackRight.setPower(-(scale((scaleInput(mecanumSpeed) - scaleInput(mecanumTurn) - scaleInput(mecanumStrafe* MECANNUM_BACK_WHEEL_MULTIPLIER)),
                    -mecanumMagnitude, +mecanumMagnitude, -mecanumMaxSpeed, +mecanumMaxSpeed)));
        }
        else
        {
            addErrors("MOTOR BACK LEFT IS NULL");
        }
    }


    double scaleInput(double dVal)
    {
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

    String turnLeftOrRight(double gyroPos, double gyroPosGoal)
    {
        double gyroDifference=0.0;
        String gyroLeftRight="left";
        if (gyroPos<=180.0 && gyroPosGoal<=180.0)
        {
            gyroDifference=gyroPos-gyroPosGoal;
            if (gyroDifference>0.0)
            {
                gyroLeftRight = "right";
            }
            else
            {
                gyroLeftRight = "left";
            }
        }
        else if (gyroPos>=180.0&&gyroPosGoal<=180.0)
        {
            gyroDifference = gyroPos - gyroPosGoal;
            if (gyroDifference > 180.0)
            {
                gyroLeftRight = "left";
            } else
            {
                gyroLeftRight = "right";
            }
        }
        else if (gyroPos<=180.0&&gyroPosGoal>=180.0)
        {
            gyroDifference = gyroPos - gyroPosGoal;
            if (gyroDifference > -180.0)
            {
                gyroLeftRight = "left";
            } else
            {
                gyroLeftRight = "right";
            }
        }
        else if (gyroPos>=180.0&&gyroPosGoal>=180.0)
        {
            gyroDifference = gyroPos - gyroPosGoal;
            if (gyroDifference > 0.0)
            {
                gyroLeftRight = "right";
            } else
            {
                gyroLeftRight = "left";
            }
        }
        return gyroLeftRight;
    }

    void addErrors(String errorMessage) {
        if (!errors.contains(errorMessage)) {
            errors += "\n" + errorMessage;
        }
    }

    String getErrors()
    {
        return errors;
    }

    void clearErrors(String errorMessage)
    {
        errors="";
    }

    public void displayErrors(Telemetry telemetry)
    {
        telemetry.addData("ERROR MESSAGES:",getErrors());
        telemetry.update();
    }

    public double slowSpeed(boolean active, double speed)
    {
        if (active) {
            speed=speed/ SLOW_SPEED_FACTOR;
            speed=speed/ SLOW_SPEED_FACTOR;
        }
        return speed;
    }

    public double slowStrafe(boolean active, double strafe)
    {
        if (active) {
            strafe=strafe/ SLOW_STRAFE_FACTOR;
        }
        return strafe;
    }

    public double slowTurn(boolean active, double turn)
    {
        if (active)
        {
            turn=turn/ SLOW_TURN_FACTOR;
        }
        return turn;
    }

    public void controlMecanumWheels(double sp,double tu, double st, boolean slowSt, boolean slowSp, boolean slowTu)
    {
        double speed = sp;
        double turn = tu;
        double strafe = st;

        strafe = slowStrafe(slowSt, strafe);

        speed = slowSpeed(slowSp, speed);

        turn = slowTurn(slowTu, turn);

        holonomic(speed, turn, strafe, MECANUM_MAX_SPEED);
    }

    public void controlHangTower(double speed) {
        motorHangTower.setPower(speed);
    }

    public void controlDeployTower(double vc) {
        if (vc > 0.9) {
            motorDeployTower.setPower(0.6);
            deployTowerTargetPosition =0;
        }
        if (vc < -0.9)
        {
            motorDeployTower.setPower(1);
            deployTowerTargetPosition = -2420;
        }

        if (deployTowerTimer>12) {
            setDeployTowerPosition(deployTowerTargetPosition);
            deployTowerTimer=0;
        }
        deployTowerTimer++;
    }

    public void setDeployTowerPosition(int v) {
        if (motorDeployTower != null) {
            motorDeployTower.setTargetPosition(v);
        }
        else
        {
            addErrors("MOTOR DEPLOY TOWER IS NULL");
        }
    }

    public void controlDeployDumper (double d, boolean l, boolean r, boolean b)
    {
        if (deployTowerPosition() < -1000) {
            servoDeployPour.setPosition(SERVO_DEPLOY_POUR_UNPOURED + (SERVO_DEPLOY_POUR_POURED - SERVO_DEPLOY_POUR_UNPOURED) * d);
        }
        else if (deployTowerPosition() > -100)
        {
            servoDeployPour.setPosition(SERVO_DEPLOY_POUR_UNPOURED);
        }
        else
        {
            servoDeployPour.setPosition(SERVO_DEPLOY_POUR_PRE_DOWN);
        }
        if (d == 0)
        {
            servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_OPEN);
            servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_OPEN);
            deployGateLeftToggleTimer = 0;
            deployGateRightToggleTimer = 0;
            deployGateAutoclosed = false;
        }

        if (d != 0 && deployGateAutoclosed == false)
        {
            servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_CLOSED);
            servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_CLOSED);
            deployGateLeftToggleTimer = 0;
            deployGateRightToggleTimer = 0;
            deployGateAutoclosed = true;
        }

        if (b)
        {
            servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_OPEN);
            servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_OPEN);
            deployGateLeftToggleTimer = 0;
            deployGateRightToggleTimer = 0;
        }

        if (l && deployGateLeftToggleTimer > 70)
        {
            if (servoDeployGateLeft.getPosition() == SERVO_DEPLOY_GATE_LEFT_OPEN)
                servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_CLOSED);
            else
                servoDeployGateLeft.setPosition(SERVO_DEPLOY_GATE_LEFT_OPEN);
            deployGateLeftToggleTimer = 0;
        }
        deployGateLeftToggleTimer++;

        if (r && deployGateRightToggleTimer > 70)
        {
            if (servoDeployGateRight.getPosition() == SERVO_DEPLOY_GATE_RIGHT_OPEN)
                servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_CLOSED);
            else
                servoDeployGateRight.setPosition(SERVO_DEPLOY_GATE_RIGHT_OPEN);
            deployGateRightToggleTimer = 0;
        }
        deployGateRightToggleTimer++;

    }

    public void controlCollectRotate(boolean i, boolean o)
    {
        if (motorCollectRotate != null) {
            if (o)
            {
                motorCollectRotate.setPower(0.5);
            }
            else
            {
                motorCollectRotate.setPower(0);
            }

            if (i)
            {
                motorCollectRotate.setPower(-0.8);
            }
        }
        else
        {
            addErrors("MOTOR COLLECT ROTATE IS NULL");
        }
    }

    public void controlCollectSpin(double p)
    {
        if (servoCollectSpin1 != null || servoCollectSpin2 != null) {
            servoCollectSpin1.setPosition(p);
            servoCollectSpin2.setPosition(Math.abs(p - 1.0));
        }
        else
        {
            addErrors("SERVO COLLECT SPIN 1 OR 2 ARE NULL");
        }
    }

    public void controlCollectSlide(double power) {
        if (power > 0.0) {
            power = power * 0.75 ;
        }
        motorCollectSlide.setPower(power);
    }

    public void controlCollectionDeliveryAngle(double power, Telemetry telemetry)
    {
        if (power > 0.0) {
            power = power * 0.75;
        }
        /*
        set collectionDeliveryAngleState to 0
        Using encoders, determine once the robot has angled the delivery thing past a certain threshold.
        If they are, flag collectionDeliveryAngleState as -1
        When coming back down, once the arm has reached past a certain threshold and state is less than 0,
        change encoder state to 30 and set the motor speed to go up at an extremely small speed,
        such that it stops moving completely instead of slowly angling back up.
        Every loop tick encoder state down by 1
        When encoder state reaches 0 give control of angle back to driver and stop ticking state down
        */
        motorCollectionDeliveryAngle.setPower(power);
        telemetry.addData("Collection Delivery Angle Position", motorCollectionDeliveryAngle.getCurrentPosition());
        telemetry.update();
    }

    public void controlCollectionDeliverySpin(boolean so, boolean si)
    {
        if (motorCollectionDeliverySpin != null) {
            if (so)
            {
                motorCollectionDeliverySpin.setPower(0.8);
            }
            else
            {
                motorCollectionDeliverySpin.setPower(0);
            }

            if (si)
            {
                motorCollectionDeliverySpin.setPower(-0.8);
            }
        }
        else
        {
            addErrors("MOTOR COLLECTION DELIVERY SPIN IS NULL");
        }
    }

    public int motorCollectionDeliverySlideGetPosition() {
        return motorCollectionDeliverySlide.getCurrentPosition();
    }



    public void controlCollectionDeliverySlidePosition(int position) {
        motorCollectionDeliverySlide.setTargetPosition(position);
    }

    public void controlCollectionDeliverySlide(double power)
    {
        if (power > 0.0) {
            power = power * 0.75 ;
        }
        motorCollectionDeliverySlide.setPower(power);
    }

    public void controlCollectionDeliveryGate(boolean go, boolean gc) {
        if (go && collectionDeliveryGatePosition != SERVO_COLLECT_DELIVERY_GATE_OPEN) {
            collectionDeliveryGatePosition -= 0.01;
        }
        if (gc && collectionDeliveryGatePosition != SERVO_COLLECT_DELIVERY_GATE_CLOSED) {
            collectionDeliveryGatePosition += 0.01;
        }
        servoCollectionDeliveryGate.setPosition(collectionDeliveryGatePosition);

    }

    public void closeCollectionGate() {
        servoCollectionDeliveryGate.setPosition(0.42);
    }
    public void openCollectionGate() {
        servoCollectionDeliveryGate.setPosition(SERVO_COLLECT_DELIVERY_GATE_CLOSED);
    }

    public int hangTowerPosition() {
        return motorHangTower.getCurrentPosition();
    }

    public int collectSlidePosition() {
        return motorCollectSlide.getCurrentPosition();
    }

    public int deployTowerPosition() { return  motorDeployTower.getCurrentPosition();}

    public void setMarkerDeliveryPosition(double v)
    {
        if (servoMarkerDelivery != null) {
            servoMarkerDelivery.setPosition(v);
        }
        else
        {
            addErrors("SERVO MARKER DELIVERY IS NULL");
        }
    }

    public void controlSingleWheelEncoderTest(Telemetry telemetry) {
        double loopval=1220.0;
        double position1k = (double) motorSingleWheelEncoderTest.getCurrentPosition()%(loopval+1);
        if (position1k>loopval-loopval*.2 || position1k < loopval*.2)
        {
            motorSingleWheelEncoderTest.setPower(0.5);
        }
        else
        {
            motorSingleWheelEncoderTest.setPower(((double) Math.abs(position1k-loopval/2.0)/((loopval-loopval*.4)/2.0))*.5+.05);
        }
        telemetry.addData("actual / reduced", motorSingleWheelEncoderTest.getCurrentPosition() +" / "+position1k);
        telemetry.update();
    }

    public void retractMineralElbow() {
        servoMarkerElbow.setPosition(1.0);
    }
}