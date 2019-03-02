package org.firstinspires.ftc.teamcode.AUTO;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//import com.vuforia.CameraDevice;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.robotcore.util.Range.scale;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;


public abstract class AUTOMecanumAbstractPracticeBot extends LinearOpMode implements SensorEventListener {
    // Constants
    public static final int ONE_WHEEL_ROTATION = 1220;

    final public static double MECANUM_MAX_SPEED = 1.0;

    //Initialize variables necessary for methods and auto code
    ElapsedTime eTime = new ElapsedTime();    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorHangTower;
    DcMotor motorCollectRotate;
    Servo servoMarkerDelivery;
    Servo cameraWiper;
    Servo servoMineralArm;
    Servo servoMineralRotate;
    Servo servoMarkerShoulder;
    Servo servoMarkerElbow;
    Servo servoMarkerWrist;
    ColorSensor sensorColor;
    ColorSensor sensorColor2;
    ColorSensor sensorColor3;



    int encoderAtStart =0;
    double turn;
    double startOfStrafeHeading;
    double turnMultiplier;
    double turnAmount;
    boolean inStrafe;

    public static final String TAG = "Mecanum Abstract";

    SensorManager sensorManager;
    Sensor gameRotationSensor;


    List<Float> gyroEventValues = new ArrayList<>();
    public float gyroCurrentHeading = 0.0f;
    public float offset = 0.0f;

    //WIP VUFORIA STUFF
    private static final String VUFORIA_KEY = "AXNGJw//////AAAAGYtqot53p0ULmjqVWBBEdR1T7XzT7DmorNidxCIAXp9/o1sLPS+rc57z9m4f/381DeFLCz2AdUiOMOHhnO4zkZXm5W8S/Z5+C6jrGp18HJOwoTyIL9JbDiPADw9pDFoU5LL79g79Crd17x4V9o/rvPO1TAWJ3MPqaUb3U+/uPBF0XvguKTtZpnTHtgvSRsxW3f1Y90JiTfAH3k2A5vUsX9qcf0fb+/xG1EFtvunElevmUB3CmVzfkb3AEJxq86SzVxIgDw7gz/U3BdpPafCAb6jozBC6u0t7H4EUkl1Ro08O15vLs9Eah4ZN15aJci7fkTzEZ7HkEGN3HNkMUEkKRtxsOYU/gD5QsDpqBBpmOESf";
    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;
    private static final float mmTargetHeight   = (6) * mmPerInch;

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    VuforiaLocalizer vuforia2;
    int cameraMonitorViewId;

    final int CAMERA_FORWARD_DISPLACEMENT  = 64;   // eg: Camera is 110 mm in front of robot center
    final int CAMERA_VERTICAL_DISPLACEMENT = 425;   // eg: Camera is 200 mm above ground
    final int CAMERA_LEFT_DISPLACEMENT     = 108;     // eg: Camera is ON the robot's center line

    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";



    //end of variable initilization


    protected void initializeEncoders() throws InterruptedException {
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorHangTower.setDirection(DcMotorSimple.Direction.REVERSE);
        motorHangTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorHangTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorHangTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorHangTower.setPower(1.0);



        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        Thread.sleep(200);
        Log.d("RHTP","wheel encoder settings set");
    }

    protected void initializeColor() throws InterruptedException {
        /*sensorColorSample = hardwareMap.get(ColorSensor.class, "sensorColorSample");
        sensorColorSample.setI2cAddress(I2cAddr.create8bit(0x44));

        sensorColorSample.enableLed(true);*/
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_mineral_color");
        sensorColor2 = hardwareMap.get(ColorSensor.class, "sensor_mineral_color2");
        //sensorColor3 = hardwareMap.get(ColorSensor.class, "sensor_mineral_color3");

    }

    protected void initializeTouch() throws InterruptedException {

    }

    protected void stopMotors() {
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    protected void drive45(int moveamount,double speed)
    {
        encoderAtStart = motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(0);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount)) {
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount-1000))
            {
                motorFrontLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                motorBackRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
            }*/
        }
        stopMotors();
    }

    protected void drive135(int moveamount,double speed)
    {
        encoderAtStart = motorBackLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(0);

        while (opModeIsActive() && checkDistance(motorBackLeft.getCurrentPosition(), encoderAtStart, moveamount)) {
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount-1000))
            {
                motorFrontLeft.setPower(0);
                motorFrontRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorBackLeft.getCurrentPosition(), encoderAtStart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorBackLeft.getCurrentPosition(), encoderAtStart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackRight.setPower(0);
            }*/
        }
        stopMotors();
    }

    protected void drive0(int moveamount,double speed)
    {

        encoderAtStart = motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(speed);
        inStrafe=false;
        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount)) {
            telemetry.addData("Left encoders:", motorFrontLeft.getCurrentPosition());
            telemetry.update();
/*            turn =0;
            if (!inStrafe) {
                offsetPhoneGyro();
                startOfStrafeHeading = getGyroPos0360();
                inStrafe=true;
            }
            if (startOfStrafeHeading!=getGyroPos0360()) {
                if (turnLeftOrRight(getGyroPos0360(), startOfStrafeHeading) == "left") {
                    turnMultiplier = 1;
                } else {
                    turnMultiplier = -1;
                }
                turnAmount = 1 * (Math.min(Math.abs(getGyroPos0360() - startOfStrafeHeading), Math.abs(360 + getGyroPos0360() - startOfStrafeHeading)) / 30);

                turnAmount = Range.clip(turnAmount, -1, 1);
                turn = turnAmount * turnMultiplier;
                telemetry.addData("turn", turn);
                telemetry.update();
            }
            holonomic(0, turn,speed,1);*/
        }
        stopMotors();
    }

    protected void driveStraight(int moveAmount, double speed)
    {
        encoderAtStart = motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount)) {

            telemetry.addData("Left encoders:", motorFrontLeft.getCurrentPosition());
            telemetry.update();
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount-1000))
            {
                motorFrontLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorFrontRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
            }*/
        }/*
        while (opModeIsActive() && checkDistance(motorFrontRight.getCurrentPosition(), encoderAtStart, moveAmount)) {

            telemetry.addData("Right encoders:", motorFrontRight.getCurrentPosition());
            telemetry.update();
        }*/
        stopMotors();
    }
    protected double driveStraightAndColor(int moveAmount, double speed) {
        encoderAtStart = motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveAmount) && !isColorYellow(sensorColor, "sensor_mineral_color", motorFrontLeft.getCurrentPosition()) && !isColorYellow(sensorColor2, "sensor_mineral_color2", motorFrontLeft.getCurrentPosition())) { //&& !isColorYellow(sensorColor3)) {

            telemetry.addData("Left encoders:", motorFrontLeft.getCurrentPosition());
            telemetry.update();
            telemetry.addData("R", sensorColor.red());
            telemetry.addData("G", sensorColor.green());
            telemetry.addData("B", sensorColor.blue());
            telemetry.update();


        }
        stopMotors();
        return (motorFrontLeft.getCurrentPosition() - encoderAtStart);
    }

    protected void driveStraightOrTime(int moveamount, double speed, double starttime, double stoptime) {
        encoderAtStart = motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(-speed);
        //move forward until correct distance is reached
        while(opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderAtStart, moveamount) && (eTime.seconds()-starttime)<stoptime) {
        }
        stopMotors();
    }


    protected boolean checkDistance(int startPosition, int currentPosition, int distanceToTravel) {
        return Math.abs(currentPosition-startPosition)<distanceToTravel;
    }
    protected int distanceLeft(int currentPosition, int startPosition, int distanceToTravel) {
        return distanceToTravel-(Math.abs(currentPosition-startPosition));
    }


    protected void initializeServos() {//initialization of servo positions
        /*servoMarkerDelivery = hardwareMap.servo.get("servoMarkerDelivery");
        servoMarkerDelivery.setPosition(0.05);
        cameraWiper = hardwareMap.servo.get("cameraWiper");
        cameraWiper.setPosition(0.0);
        servoMineralArm = hardwareMap.get(Servo.class, "servo_mineral_arm");
        servoMineralRotate = hardwareMap.get(Servo.class, "servo_mineral_rotate");
        servoMineralArm.setPosition(0.8);
        servoMineralRotate.setPosition(0.1);*/
        servoMarkerShoulder = hardwareMap.get(Servo.class, "servoMarkerShoulder");
        servoMarkerElbow = hardwareMap.get(Servo.class, "servoMarkerElbow");
        servoMarkerWrist = hardwareMap.get(Servo.class, "servoMarkerWrist");

        servoMarkerShoulder.setPosition(0.05);
        servoMarkerElbow.setPosition(1.0);
        servoMarkerWrist.setPosition(0.0);

    }
    public void wipeCamera() {

        cameraWiper.setPosition(0.49);
        sleep(1000);
        cameraWiper.setPosition(0.0);
        sleep(500);

    }
    public void setMarkerDeliveryPosition(double v)
    {
        if (servoMarkerDelivery != null) {
            servoMarkerDelivery.setPosition(v);
        }
    }

    public void initializePhoneGyro() {
        sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gameRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void deregisterPhoneGyro() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    protected void offsetPhoneGyro() {

        offset+=getGyroCurrentHeading();


    }

    public float getGyroCurrentHeading()
    {
        float gyroOffsetHeading = ((gyroCurrentHeading - offset)% 360 - 360) % 360;
        if (Math.abs(gyroOffsetHeading) > 180) gyroOffsetHeading+=360;
        return gyroOffsetHeading;
    }

    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        gyroCurrentHeading = (float) Math.toDegrees(orientation[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void turnDegrees(double maxSpeed, double degrees) {

        double minSpeed=maxSpeed/3;
        if (Math.abs(minSpeed) < .3)
        {
            minSpeed = .3 * (Math.abs(minSpeed)/minSpeed);
        }

        if (Math.abs(maxSpeed) < .3)
        {
            maxSpeed = .3 * (Math.abs(maxSpeed/maxSpeed));
        }

        double currentSpeed=maxSpeed;


        degrees = degrees - ((degrees * 0.1) * (degrees/97.0));
        float startingHeading = getGyroCurrentHeading();
        double degreesTurned = Math.abs(startingHeading - getGyroCurrentHeading());

        motorFrontLeft.setPower(-currentSpeed);
        motorBackLeft.setPower(-currentSpeed);
        motorFrontRight.setPower(currentSpeed);
        motorBackRight.setPower(currentSpeed);

        while ((degreesTurned < degrees)&&opModeIsActive()) {

            currentSpeed = maxSpeed - ((Math.abs(startingHeading - getGyroCurrentHeading()) / degrees) * (maxSpeed - minSpeed));
            degreesTurned = Math.abs(startingHeading - getGyroCurrentHeading());

            telemetry.addData("Seconds",eTime.seconds());
            telemetry.addData("Starting", "" + startingHeading + " degrees");
            telemetry.addData("Heading", "" + getGyroCurrentHeading() + " degrees");
            telemetry.addData("Offset", offset);
            telemetry.addData("Raw Gyro", gyroCurrentHeading);
            telemetry.addData("Degrees Turned: ", degreesTurned);
            telemetry.addData("Goal", degrees);
            telemetry.addData("Speed: ", currentSpeed);
            telemetry.addData("Maxspeed", maxSpeed);
            telemetry.addData("Minspeed", minSpeed);
            telemetry.update();

            motorFrontLeft.setPower(-currentSpeed);
            motorBackLeft.setPower(-currentSpeed);
            motorFrontRight.setPower(currentSpeed);
            motorBackRight.setPower(currentSpeed);
        }

        stopMotors();

    }
    public void getGyroReading() {
        telemetry.addData("current gyro: ", getGyroCurrentHeading());
        telemetry.update();
        //sleep(5000);
    }

    protected void startAutoOp() throws InterruptedException {
        telemetry.addData("INIT STATUS:","NOT READY!");
        telemetry.update();
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorHangTower = hardwareMap.dcMotor.get("motorHangTower");


        //motorCollectRotate = hardwareMap.dcMotor.get("motorCollectRotate");
        Log.d("RHTP","initializing");
        initializeColor();
        Log.d("RHTP","initialized colors");

        initializeServos();
        Log.d("RHTP","initialized servos");

        initializeTouch();
        Log.d("RHTP","initialized touch");

        initializeEncoders();
        Log.d("RHTP","initialized encoders");

        initializePhoneGyro ();
        Log.d("RHTP","initialized phone gyro");

        telemetry.addData("Staring Phone Gyro Angle", getGyroCurrentHeading());
        telemetry.update();
        Log.d("RHTP", "Starting Phone Gyro Angle: " + getGyroCurrentHeading());

//        vuforiaInitWIP();
//        tfodInit();

        waitForStart();

        Log.d("RHTP","started");
        eTime.reset();
        eTime.startTime();
        Log.d("RHTP","etime reset");
    }

    public double getGyroPos0360()
    {
        double gyroPos= getGyroCurrentHeading();
        if (getGyroCurrentHeading() <0)
        {
            gyroPos= getGyroCurrentHeading() +360;
        }
        return gyroPos;
    }

    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + turn - Strafe      Right Front = +Speed - turn + Strafe
//      Left Rear  = +Speed + turn + Strafe      Right Rear  = +Speed - turn - Strafe

        //variables should not start with capital letter
        double Magnitude = Math.abs(Speed) + Math.abs(Turn) + Math.abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range
        if (Strafe > 0) {
            motorFrontLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
            if (motorBackLeft != null) {
                motorBackLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe + 0.05)),
                        -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
            }
            motorFrontRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
            if (motorBackRight != null) {
                motorBackRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
                        -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
            }
        }
        else {
            motorFrontLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
            if (motorBackLeft != null) {
                motorBackLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe - 0.05)),
                        -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
            }
            motorFrontRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
            if (motorBackRight != null) {
                motorBackRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
                        -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
            }
        }
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
    public void setHangTowerPosition(int v) {
        motorHangTower.setTargetPosition(v);

    }

    public void vuforiaInitWIP() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        //VuforiaLocalizer.Parameters parameters2 = new VuforiaLocalizer.Parameters();

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY ;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //parameters.vuforiaLicenseKey = VUFORIA_KEY;
        //parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        //vuforia2 = ClassFactory.getInstance().createVuforia(parameters2);

        // Load the data sets that for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.

        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");


        // For convenience, gather together all the trackable objects in one easily-iterable collection
        allTrackables.addAll(targetsRoverRuckus);


        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * This Rover Ruckus sample places a specific target in the middle of each perimeter wall.
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        /**
         * Create a transformation matrix describing where the phone is on the robot.
         *
         * The coordinate frame for the robot looks the same as the field.
         * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         *
         * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
         * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
         * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
         *
         * If using the rear (High Res) camera:
         * We need to rotate the camera around it's long axis to bring the rear camera forward.
         * This requires a negative 90 degree rotation on the Y axis
         *
         * If using the Front (Low Res) camera
         * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
         * This requires a Positive 90 degree rotation on the Y axis
         *
         * Next, translate the camera lens to where it is on the robot.
         * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
         */

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));
//
//        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        /** Start tracking the data sets we care about. */
        targetsRoverRuckus.activate();
    }

    public double vuforiaGetDataWIP(){
        float angle = -135f;
        boolean angleFound = false;
        double startingTime = eTime.seconds();
        while(opModeIsActive() && !angleFound && eTime.seconds() - startingTime <= 3) {
            targetVisible = false;
            float temp = 0;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                angle = rotation.thirdAngle;
                angleFound = true;
                telemetry.addData("IMPORTANT GYRO INFO", angle);
            } else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();

        }
        sleep(1000);
        return ((double)(angle));
    }
    public void hitMineralBlueDepot(boolean left, boolean middle, boolean right) {
        if (right && !middle && !left) {
            double vuforiaData = vuforiaGetDataWIP();
            driveStraight(400, -0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            // driveStraight(ONE_WHEEL_ROTATION/2, -0.3);
            turnDegrees(0.5, 45 + vuforiaGetDataWIP());
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(3660, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (middle && !right && !left) {
            double vuforiaData =vuforiaGetDataWIP();
            // driveStraight(ONE_WHEEL_ROTATION/2, 0.3);
            driveStraight(1100, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 67 + vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(2000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 15);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1400, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (left && !right && !middle) {
            double vuforiaData =vuforiaGetDataWIP();
//            /**/turnDegrees(0.5,10);
//            if (!opModeIsActive()) {
//                stopMotors();
//                return;
//            }
            turnDegrees(0.5, 15);
            driveStraight(2580, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 90 - (45 - vuforiaData));
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1420, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 45);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(2000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        setMarkerDeliveryPosition(0.6);
    }
    public void hitMineralRedDepot(boolean left, boolean middle, boolean right) {

        if (right && !middle && !left) {
            double vuforiaData = vuforiaGetDataWIP();
            driveStraight(500, -0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, (225 + vuforiaData));
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
//            turnDegrees(0.5, 90);
            driveStraight(3000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (middle && !right && !left) {
            double vuforiaData =vuforiaGetDataWIP();
            driveStraight(800, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 245 + vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
//            turnDegrees(0.5, 95);
            driveStraight(3420, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 20);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(420, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (left && !right && !middle) {
            double vuforiaData =vuforiaGetDataWIP();
            turnDegrees(0.5,15);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(2300, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 205 + vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1200, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 55);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(2222, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        setMarkerDeliveryPosition(0.6);
    }
    public void hitMineralRedCrater(boolean left, boolean middle, boolean right) {
        if (right && !middle && !left) {
            double vuforiaData = vuforiaGetDataWIP();
            driveStraight(500, -0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, (245 - vuforiaData));
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (middle && !right && !left) {
            double vuforiaData =vuforiaGetDataWIP();
            turnDegrees(0.3, 23);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(900, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 233 - vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (left && !right && !middle) {
            double vuforiaData =vuforiaGetDataWIP();
            turnDegrees(0.3, 23);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
//            turnDegrees(0.5,15);
            driveStraight(2150, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 215 - vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(800, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }

    }
    public void hitMineralBlueCrater(boolean left, boolean middle, boolean right) {
        if (right && !middle && !left) {
            double vuforiaData = vuforiaGetDataWIP();
            driveStraight(500, -0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, (65 - vuforiaData));
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (middle && !right && !left) {
            double vuforiaData =vuforiaGetDataWIP();
            turnDegrees(0.3, 23);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(900, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 53 - vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(1000, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
        else if (left && !right && !middle) {
            double vuforiaData =vuforiaGetDataWIP();
            turnDegrees(0.3, 23);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
//            turnDegrees(0.5,15);
            driveStraight(2150, 0.3);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            turnDegrees(0.5, 35 - vuforiaData);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
            driveStraight(800, 0.5);
            if (!opModeIsActive()) {
                stopMotors();
                return;
            }
        }
    }
    public void tfodInit() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.80;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
    public int tfodGet() {
        /*
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            Recognition recognition;
            if (updatedRecognitions != null && updatedRecognitions.size() != 0) {
                recognition = updatedRecognitions.get(0);
                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                    return 0;
                } else if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                    return 1;
                }
            }
        }*/
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 1) {

                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            return 0;
                        } else if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                            return 1;
                        }
                    }
                }
                telemetry.update();
            }
        }
        return 0;
    }
    public int tfodGetMultiple() {
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            if (updatedRecognitions.size() == 3) {
                int goldMineralX = -1;
                int silverMineral1X = -1;
                int silverMineral2X = -1;
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                        goldMineralX = (int) recognition.getLeft();
                    } else if (silverMineral1X == -1) {
                        silverMineral1X = (int) recognition.getLeft();
                    } else {
                        silverMineral2X = (int) recognition.getLeft();
                    }
                }
                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                    if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                        return 0;
                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            }
        }
        return 2;
    }

    /*BLOCK FLAT PLANE:
        R: 390-410
        G: 280-300
        B: 135-147

        BLOCK CROSS PLANE:
        R: 200-223
        G: 125-140
        B: 65-70

        BALL:
        R: 1956-2134
        G: 2427-2600
        B: 2203-2315

        all of these values are with the mineral just sitting motionless under a sensor */

    public boolean isColorYellow(ColorSensor sC, String sensorName, int encoder) {
        int red = sC.red();
        int blue = sC.blue();
        int green = sC.green();

        Log.i("isColorYellow", "Sensor:" + sensorName + ", Encoder:" + encoder + ", Red: " + red + ", Green: " + green + ", Blue: " + blue);

        if (red >= 150 && red <= 500 && green >= 80 && green <= 390 && blue >= 40 && blue <= 80 && red > green && green > blue) {
            return true;
        }
        else {
            return false;
        }
    }
    public void detectMineral() {
        while (sensorColor.red() > 100 && sensorColor.green() > 100 && sensorColor.blue() > 100) {
            driveStraight(200, -0.4);
        }
        if (sensorColor.red() >= 50 && sensorColor.green() >= 40 && sensorColor.blue() < 40) {
            servoMineralRotate.setPosition(0.0);
            driveStraight(420, -0.4);
        }

    }
    public void tfodActivate() {
        //this does nothing but i'm too lazy to remove it from the other programs so we keep it for now
    }
    public void deployMineralArm() {
        servoMarkerShoulder.setPosition(0.5);
    }
    public void retractMineralArm() {
        servoMarkerShoulder.setPosition(0.05);
        sleep(500);
        servoMarkerElbow.setPosition(1.0);
    }
    public void deployMarker(int x) {
        if (x < 1600) {
            servoMarkerShoulder.setPosition(0.57);
        }
        else if (x < 2800) {
            servoMarkerShoulder.setPosition(0.5);
        }
        else if (x < 3900) {
            servoMarkerShoulder.setPosition(0.33);
            sleep(1000);
        }
        else {
            servoMarkerShoulder.setPosition(0.33);
        }
        servoMarkerElbow.setPosition(0.0);
        sleep(1000);
        servoMarkerWrist.setPosition(0.9);
        sleep(3000);
        servoMarkerWrist.setPosition(0.0);
    }
}
