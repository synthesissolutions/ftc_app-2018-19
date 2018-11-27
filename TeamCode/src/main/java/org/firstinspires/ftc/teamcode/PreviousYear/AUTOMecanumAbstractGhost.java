package org.firstinspires.ftc.teamcode.PreviousYear;

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
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static com.qualcomm.robotcore.util.Range.scale;

public abstract class AUTOMecanumAbstractGhost extends LinearOpMode implements SensorEventListener {
    // Constants
    public static final int ONE_WHEEL_ROTATION = 1220;

//    final static double GLYPH_GRIPPER_LEFT_BOT_OPEN = .85;
//    final static double GLYPH_GRIPPER_RIGHT_BOT_OPEN = 0.24;
//    final static double GLYPH_GRIPPER_LEFT_BOT_CLOSED = 0.15;
//    final static double GLYPH_GRIPPER_RIGHT_BOT_CLOSED = 0.9;
//    final static double GLYPH_GRIPPER_LEFT_BOT_MID = 0.35;
//    final static double GLYPH_GRIPPER_RIGHT_BOT_MID = 0.65;
//    final static double GLYPH_GRIPPER_LEFT_TOP_OPEN = 0.23;
//    final static double GLYPH_GRIPPER_RIGHT_TOP_OPEN = 0.92;
//    final static double GLYPH_GRIPPER_LEFT_TOP_CLOSED = 0.68;
//    final static double GLYPH_GRIPPER_RIGHT_TOP_CLOSED = 0.23;
//    final static double GLYPH_GRIPPER_LEFT_TOP_MID = 0.55;
//    final static double GLYPH_GRIPPER_RIGHT_TOP_MID = 0.4;
//    final static double SPINNER_DOUBLE_TOP = 0.533625;
//    final static double SPINNER_DOUBLE_BOT = 0.447625;

    final static double GLYPH_GRIPPER_SINGLE_TOP_OPEN = 1.0;
    final static double GLYPH_GRIPPER_SINGLE_TOP_MID = 0.45;
    final static double GLYPH_GRIPPER_SINGLE_TOP_CLOSED = 0.10;
    final static double GLYPH_GRIPPER_SINGLE_BOT_OPEN = 1.0;
    final static double GLYPH_GRIPPER_SINGLE_BOT_MID = 0.50;
    final static double GLYPH_GRIPPER_SINGLE_BOT_CLOSED = 0.1;
    final static double SPINNER_SINGLE_TOP = 0.49;
    final static double SPINNER_SINGLE_BOT = 0.41;

    final static double JEWEL_FLICKER_START = .90;
    final static double JEWEL_FLICKER_MID_BLUE = 0.48;
    final static double JEWEL_FLICKER_MID = 0.40;
    final static double JEWEL_ARM_IN = 0.90;
    final static double JEWEL_GATE_CLOSED=0.7;
    final static double JEWEL_GATE_OPEN=0;

    final static double RELIC_ARM_START = 0.58;
    final static double RELIC_ARM_GRAB = 0.545;
    final static double RELIC_ARM_HOLD = 0.47;
    final double RELIC_ARM_PRE_GRAB = 0.535;
    final static double RELIC_HAND_CLOSED = .5;
    final static double RELIC_HAND_OPEN = .35;

    final public static double MECANUM_MAX_SPEED = 1.0;

    //Initialize variables necessary for methods and auto code
    ElapsedTime eTime = new ElapsedTime();    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorGlyphTower;
    DcMotor motorRelicTower;
    Servo servoGlyphSingleTop;
    Servo servoGlyphSingleBot;
    Servo servoGlyphSpinner;
    Servo servoJewelArm;
    Servo servoJewelFlicker;
    Servo servoJewelGate;
    Servo servoRelicHand;
    Servo servoRelicArm;
    ColorSensor sensorJewelColorFlicker;
    ColorSensor sensorJewelColorArm;
    int encoderatstart=0;
    double Turn;
    double startOfStrafeHeading;
    double turnMultiplier;
    double turnAmount;
    boolean inStrafe;
    public static final String TAG = "Mecanum Abstract";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;

    SensorManager sensorManager;
    Sensor gameRotationSensor;

    Servo servoGlyphTouchArm;


    List<Float> gyroEventValues = new ArrayList<>();
    public float currentHeading = 0.0f;
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
        motorGlyphTower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlyphTower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        Thread.sleep(200);
        Log.d("RHTP","wheel encoder settings set");
    }

    protected void initializeColors() throws InterruptedException {
        sensorJewelColorArm = hardwareMap.get(ColorSensor.class, "sensorJewelColorArm");
        sensorJewelColorArm.setI2cAddress(I2cAddr.create8bit(0x44));

        sensorJewelColorArm.enableLed(true);
        sensorJewelColorFlicker = hardwareMap.get(ColorSensor.class, "sensorJewelColorFlicker");
        sensorJewelColorFlicker.setI2cAddress(I2cAddr.create8bit(0x42));
        sensorJewelColorFlicker.enableLed(true);
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
        encoderatstart= motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(0);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount)) {
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount-1000))
            {
                motorFrontLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                motorBackRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
            }*/
        }
        stopMotors();
    }

    protected void drive135(int moveamount,double speed)
    {
        encoderatstart= motorBackLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(0);

        while (opModeIsActive() && checkDistance(motorBackLeft.getCurrentPosition(), encoderatstart, moveamount)) {
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount-1000))
            {
                motorFrontLeft.setPower(0);
                motorFrontRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorBackLeft.getCurrentPosition(), encoderatstart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorBackLeft.getCurrentPosition(), encoderatstart, moveamount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackRight.setPower(0);
            }*/
        }
        stopMotors();
    }

    protected void drive0(int moveamount,double speed)
    {

        encoderatstart= motorFrontLeft.getCurrentPosition();
        /*motorBackLeft.setPower(-speed);
        motorBackRight.setPower(speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(speed);*/
        inStrafe=false;
        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount)) {
            Turn=0;
            if (!inStrafe) {
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
                Turn = turnAmount * turnMultiplier;
                telemetry.addData("turn",Turn);
                telemetry.update();
            }
            holonomic(0,Turn,speed,1);
        }
        stopMotors();
    }

    protected void driveStraight(int moveAmount, double speed)
    {
        encoderatstart= motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(-speed);

        while (opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount)) {

            telemetry.addData("encoders:", motorFrontLeft.getCurrentPosition());
            telemetry.update();
            /*if (!checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount-1000))
            {
                motorFrontLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorFrontRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackLeft.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
                motorBackRight.setPower((-speed-(.1*speed/Math.abs(speed)))
                        *(distanceLeft(motorFrontLeft.getCurrentPosition(), encoderatstart, moveAmount)/1000)
                        -.1*speed/Math.abs(speed));
            }*/
        }
        stopMotors();
    }
    protected void driveStraightOrTime(int moveamount, double speed, double starttime, double stoptime) {
        encoderatstart= motorFrontLeft.getCurrentPosition();
        motorBackLeft.setPower(-speed);
        motorBackRight.setPower(-speed);
        motorFrontRight.setPower(-speed);
        motorFrontLeft.setPower(-speed);
        //move forward until correct distance is reached
        while(opModeIsActive() && checkDistance(motorFrontLeft.getCurrentPosition(), encoderatstart, moveamount) && (eTime.seconds()-starttime)<stoptime) {
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
        servoJewelArm.setPosition(JEWEL_ARM_IN);
        servoJewelFlicker.setPosition(JEWEL_FLICKER_START);
        servoGlyphSingleTop.setPosition(GLYPH_GRIPPER_SINGLE_TOP_OPEN);
        servoGlyphSingleBot.setPosition(GLYPH_GRIPPER_SINGLE_BOT_OPEN);
        servoRelicHand.setPosition(RELIC_HAND_CLOSED);
        servoRelicArm.setPosition(RELIC_ARM_START);
        servoGlyphSpinner.setPosition(SPINNER_SINGLE_TOP);
        servoJewelGate.setPosition(JEWEL_GATE_CLOSED);
    }

    protected void releaseGlyph() {
        servoGlyphSingleBot.setPosition(GLYPH_GRIPPER_SINGLE_BOT_MID);
        servoGlyphSingleTop.setPosition(GLYPH_GRIPPER_SINGLE_TOP_MID);
    }

    protected void grabGlyph(){
        servoGlyphSingleBot.setPosition(GLYPH_GRIPPER_SINGLE_BOT_CLOSED);
        servoGlyphSingleTop.setPosition(GLYPH_GRIPPER_SINGLE_TOP_CLOSED);
        sleep(500);
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

    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        currentHeading = (float) Math.toDegrees(orientation[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void turnDegrees(double maxSpeed, double degrees) {
        //deregisterPhoneGyro();
        //initializePhoneGyro();
        double minSpeed=maxSpeed-(.10*(Math.abs(maxSpeed)/maxSpeed));
        float startingHeading = currentHeading;
        double degreesTurned = Math.abs(startingHeading - currentHeading);
        motorFrontLeft.setPower(-maxSpeed);
        motorBackLeft.setPower(-maxSpeed);
        motorFrontRight.setPower(maxSpeed);
        motorBackRight.setPower(maxSpeed);
        while ((degreesTurned < degrees)&&opModeIsActive()) {
            telemetry.addData("Seconds",eTime.seconds()+" "+eTime.time()+" "+eTime.milliseconds());
            telemetry.addData("Turn", "baby turn");
            telemetry.addData("Starting", "" + startingHeading + " degrees");
            telemetry.addData("Heading", "" + currentHeading + " degrees");
            telemetry.addData("Degrees Turned: ", degreesTurned);
            telemetry.update();
            motorFrontLeft.setPower(-(maxSpeed - (Math.abs(startingHeading - currentHeading) / degrees) * (maxSpeed - minSpeed)));
            motorBackLeft.setPower(-(maxSpeed - (Math.abs(startingHeading - currentHeading) / degrees) * (maxSpeed - minSpeed)));
            motorFrontRight.setPower((maxSpeed - (Math.abs(startingHeading - currentHeading) / degrees) * (maxSpeed - minSpeed)));
            motorBackRight.setPower((maxSpeed - (Math.abs(startingHeading - currentHeading) / degrees) * (maxSpeed - minSpeed)));
            degreesTurned = Math.abs(startingHeading - currentHeading);

        }
    }

    public void initializeVuforia()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXNGJw//////AAAAGYtqot53p0ULmjqVWBBEdR1T7XzT7DmorNidxCIAXp9/o1sLPS+rc57z9m4f/381DeFLCz2AdUiOMOHhnO4zkZXm5W8S/Z5+C6jrGp18HJOwoTyIL9JbDiPADw9pDFoU5LL79g79Crd17x4V9o/rvPO1TAWJ3MPqaUb3U+/uPBF0XvguKTtZpnTHtgvSRsxW3f1Y90JiTfAH3k2A5vUsX9qcf0fb+/xG1EFtvunElevmUB3CmVzfkb3AEJxq86SzVxIgDw7gz/U3BdpPafCAb6jozBC6u0t7H4EUkl1Ro08O15vLs9Eah4ZN15aJci7fkTzEZ7HkEGN3HNkMUEkKRtxsOYU/gD5QsDpqBBpmOESf";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
    }

    public String checkRelicVuforia()
    {
        //CameraDevice.getInstance().setFlashTorchMode(true);
        String cryptoboxPos= "";
        double startTime=eTime.seconds();
        while (opModeIsActive() && (eTime.seconds()-startTime)<5.0 && cryptoboxPos.equals(""))
        {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                cryptoboxPos=vuMark.name();
            }
        }
        //CameraDevice.getInstance().setFlashTorchMode(false);
        return cryptoboxPos;

    }

    protected void startAutoOp() throws InterruptedException {
        telemetry.addData("INIT STATUS:","NOT READY!");
        telemetry.update();
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorGlyphTower = hardwareMap.dcMotor.get("motorGlyphTower");
        servoJewelArm = hardwareMap.servo.get("servoJewelArm");
        servoJewelFlicker = hardwareMap.servo.get("servoJewelFlicker");
        servoJewelGate = hardwareMap.servo.get("servoJewelGate");
        servoGlyphSingleTop = hardwareMap.servo.get("servoGlyphSingleTop");
        servoGlyphSingleBot = hardwareMap.servo.get("servoGlyphSingleBot");
//        servoGlyphLeftBot = hardwareMap.servo.get("servoGlyphLeftBot");
//        servoGlyphRightBot = hardwareMap.servo.get("servoGlyphRightBot");
//        servoGlyphLeftTop = hardwareMap.servo.get("servoGlyphLeftTop");
//        servoGlyphRightTop = hardwareMap.servo.get("servoGlyphRightTop");
        servoRelicHand = hardwareMap.servo.get("servoRelicHand");
        servoRelicArm = hardwareMap.servo.get("servoRelicArm");
        servoGlyphSpinner = hardwareMap.servo.get("servoGlyphSpinner");
        Log.d("RHTP","initializing");
        initializeColors();
        Log.d("RHTP","initialized colors");

        initializeServos();
        Log.d("RHTP","initialized servos");

        initializeTouch();
        Log.d("RHTP","initialized touch");

        initializeEncoders();
        Log.d("RHTP","initialized encoders");

        initializeVuforia();
        Log.d("RHTP","initialized vuforia");

        initializePhoneGyro ();
        Log.d("RHTP","initialized phone gyro");

        telemetry.addData("Staring Phone Gyro Angle", currentHeading);
        telemetry.update();
        Log.d("RHTP", "Starting Phone Gyro Angle: " + currentHeading);

        waitForStart();

        relicTrackables.activate();
        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID);


        Log.d("RHTP","started");
        eTime.reset();
        eTime.startTime();
        Log.d("RHTP","etime reset");
    }

    protected void raiseTowerGlyph() {
        sleep(500);
        motorGlyphTower.setTargetPosition(-1100);
        motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlyphTower.setPower(0.65);
        sleep(500);
    }

    protected void lowerTowerGlyph() {
        sleep(500);
        motorGlyphTower.setTargetPosition(0);
        motorGlyphTower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlyphTower.setPower(0.4);
        sleep(3000);
    }


    protected void lowerJewel() {
        servoJewelGate.setPosition(JEWEL_GATE_OPEN);
        sleep(500);
        servoJewelArm.setPosition(.24);
    }
    protected void jewelPressBlue() {
        servoJewelGate.setPosition(JEWEL_GATE_OPEN);
        sleep(500);
        int redArm, redFlicker, blueArm, blueFlicker;
        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID_BLUE);
        servoJewelArm.setPosition(.24);
        sleep(1500);

        sensorJewelColorArm.enableLed(true);
        redArm = sensorJewelColorArm.red();
        blueArm = sensorJewelColorArm.blue();

        sensorJewelColorFlicker.enableLed(true);
        redFlicker = sensorJewelColorFlicker.red();
        blueFlicker = sensorJewelColorFlicker.blue();

        if (redArm==0 && blueArm==0 && redFlicker==0 && blueFlicker==0)
        {
            servoJewelFlicker.setPosition(JEWEL_FLICKER_MID_BLUE-.03);
            sensorJewelColorArm.enableLed(true);
            redArm = sensorJewelColorArm.red();
            blueArm = sensorJewelColorArm.blue();

            sensorJewelColorFlicker.enableLed(true);
            redFlicker = sensorJewelColorFlicker.red();
            blueFlicker = sensorJewelColorFlicker.blue();
            if (redArm==0 && blueArm==0 && redFlicker==0 && blueFlicker==0) {
                servoJewelFlicker.setPosition(JEWEL_FLICKER_MID_BLUE+.03);
                sensorJewelColorArm.enableLed(true);
                redArm = sensorJewelColorArm.red();
                blueArm = sensorJewelColorArm.blue();

                sensorJewelColorFlicker.enableLed(true);
                redFlicker = sensorJewelColorFlicker.red();
                blueFlicker = sensorJewelColorFlicker.blue();
            }
        }

        //This conditional will fail if the value of red and blue are the same
        if (opModeIsActive() && (redArm < blueArm)) {
            servoJewelFlicker.setPosition(1.00);
        }
        else if (opModeIsActive() && (redArm > blueArm))
        {
            servoJewelFlicker.setPosition(0);
        }
        else if (opModeIsActive() && (redFlicker > blueFlicker))
        {
            servoJewelFlicker.setPosition(1);
        }
        else if (opModeIsActive() && (redFlicker < blueFlicker))
        {
            servoJewelFlicker.setPosition(0);
        }


        telemetry.addData("redArm:",redArm);
        telemetry.addData("blueArm:",blueArm);
        telemetry.addData("redFlicker:",redFlicker);
        telemetry.addData("blueFlicker:",blueFlicker);
        telemetry.update();
        sleep(500);

        servoJewelArm.setPosition(JEWEL_ARM_IN);
        sleep(500);
        servoJewelGate.setPosition(JEWEL_GATE_CLOSED);

        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID_BLUE);
    }

    protected void jewelPressRed() {
        servoJewelGate.setPosition(JEWEL_GATE_OPEN);
        sleep(500);
        int redArm, redFlicker, blueArm, blueFlicker;
        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID);
        servoJewelArm.setPosition(.24);
        sleep(1500);
        sensorJewelColorArm.enableLed(true);
        redArm = sensorJewelColorArm.red();
        blueArm = sensorJewelColorArm.blue();

        sensorJewelColorFlicker.enableLed(true);
        redFlicker = sensorJewelColorFlicker.red();
        blueFlicker = sensorJewelColorFlicker.blue();
        if (redArm==0 && blueArm==0 && redFlicker==0 && blueFlicker==0)
        {
            servoJewelFlicker.setPosition(JEWEL_FLICKER_MID-.03);
            sensorJewelColorArm.enableLed(true);
            redArm = sensorJewelColorArm.red();
            blueArm = sensorJewelColorArm.blue();

            sensorJewelColorFlicker.enableLed(true);
            redFlicker = sensorJewelColorFlicker.red();
            blueFlicker = sensorJewelColorFlicker.blue();
            if (redArm==0 && blueArm==0 && redFlicker==0 && blueFlicker==0) {
                servoJewelFlicker.setPosition(JEWEL_FLICKER_MID+.03);
                sensorJewelColorArm.enableLed(true);
                redArm = sensorJewelColorArm.red();
                blueArm = sensorJewelColorArm.blue();

                sensorJewelColorFlicker.enableLed(true);
                redFlicker = sensorJewelColorFlicker.red();
                blueFlicker = sensorJewelColorFlicker.blue();
            }
        }
        if (opModeIsActive() && (redArm < blueArm)) {
            servoJewelFlicker.setPosition(0.00);
        }
        else if (opModeIsActive() && (redArm > blueArm))
        {
            servoJewelFlicker.setPosition(1);
        }
        else if (opModeIsActive() && (redFlicker > blueFlicker))
        {
            servoJewelFlicker.setPosition(0);
        }
        else if (opModeIsActive() && (redFlicker < blueFlicker))
        {
            servoJewelFlicker.setPosition(1);
        }
        telemetry.addData("redArm:",redArm);
        telemetry.addData("blueArm:",blueArm);
        telemetry.addData("redFlicker:",redFlicker);
        telemetry.addData("blueFlicker:",blueFlicker);
        telemetry.update();
        sleep(500);
        servoJewelArm.setPosition(JEWEL_ARM_IN);
        sleep(500);
        servoJewelGate.setPosition(JEWEL_GATE_CLOSED);
        servoJewelFlicker.setPosition(JEWEL_FLICKER_MID);
    }

    public double getGyroPos0360()
    {
        double gyroPos=currentHeading;
        if (currentHeading<0)
        {
            gyroPos=currentHeading+360;
        }
        return gyroPos;
    }

    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        //variables should not start with capital letter
        double Magnitude = Math.abs(Speed) + Math.abs(Turn) + Math.abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        motorFrontLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (motorBackLeft != null) {
            motorBackLeft.setPower(-scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        motorFrontRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
        if (motorBackRight != null) {
            motorBackRight.setPower(-(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED)));
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
}
