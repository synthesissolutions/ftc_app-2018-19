package org.firstinspires.ftc.teamcode.HARDWARE;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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

    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorHangTower;
    DcMotor motorDeployTower;
    DcMotor motorCollectSlide;

    int hangTowerTargetPosition = 0;
    int hangTowerTimer=0;

    int deployTowerTargetPosition = 0;
    int deployTowerTimer=0;

    int collectSlideTargetPosition = 0;
    int collectSlideTimer=0;

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
        motorHangTower.setDirection(DcMotorSimple.Direction.REVERSE);
        motorHangTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorHangTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorHangTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorHangTower.setPower(1.0);

    }

    public void initializeDeploy()
    {
        motorDeployTower = hardwareMap.dcMotor.get("motorDeployTower");
        motorDeployTower.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDeployTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDeployTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorDeployTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDeployTower.setPower(1);
    }

    public void initializeCollect()
    {
        motorCollectSlide = hardwareMap.dcMotor.get("motorCollectSlide");
        motorCollectSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        motorCollectSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorCollectSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorCollectSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCollectSlide.setPower(0.5);
    }
    //END OF INITIALIZATION METHODS

    public void initializePracticeBot(HardwareMap hwMap){
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
            speed=speed/3.0;
        }
        return speed;
    }

    public double slowStrafe(boolean active, double strafe)
    {
        if (active) {
            strafe=strafe/2.0;
        }
        return strafe;
    }

    public double slowTurn(boolean active, double turn)
    {
        if (active)
        {
            turn=turn/1.50;
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

    public void controlHangTower(double vc, boolean d, boolean u)
    {
        if (Math.abs(vc) > 0.1) {
            hangTowerTargetPosition += vc * 6 ;
        }

        if (d) {
            hangTowerTargetPosition = 0;
        }
        if (u) {

            hangTowerTargetPosition = -20000; //test this first
        }

        if (hangTowerTimer>12) {
            setHangTowerPosition(hangTowerTargetPosition);
            hangTowerTimer=0;
        }
        hangTowerTimer++;
    }

    public void setHangTowerPosition(int v) {
        if (motorHangTower != null) {
            motorHangTower.setTargetPosition(v);
        }
        else
        {
            addErrors("MOTOR HANG TOWER IS NULL");
        }
    }

    public void controlDeployTower(double vc) {
        if (Math.abs(vc) > 0.1) {
            deployTowerTargetPosition += vc * 12 ;
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

    public void controlCollectSlide(double vc) {
        if (Math.abs(vc) > 0.1) {
            collectSlideTargetPosition += vc * 6 ;
        }

        if (collectSlideTimer>12) {
            setCollectSlidePosition(collectSlideTargetPosition);
            collectSlideTimer=0;
        }
        collectSlideTimer++;
    }

    public void setCollectSlidePosition(int v) {
        if (motorCollectSlide != null) {
            motorCollectSlide.setTargetPosition(v);
        }
        else
        {
            addErrors("MOTOR COLLECT SLIDE IS NULL");
        }
    }
    public int towerPosition() {
        return motorHangTower.getCurrentPosition();
    }
}