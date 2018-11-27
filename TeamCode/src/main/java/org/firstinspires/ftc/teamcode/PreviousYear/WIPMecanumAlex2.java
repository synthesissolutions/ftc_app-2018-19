package org.firstinspires.ftc.teamcode.PreviousYear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

@TeleOp(name="WIP Mecanum Alex 2", group="WIP")

@Disabled
public class WIPMecanumAlex2 extends OpMode implements SensorEventListener{
    DcMotor motorLeftBack;
    DcMotor motorRightBack;
    DcMotor motorRightFront;
    DcMotor motorLeftFront;
    List<Float> gyroEventValues = new ArrayList<>();
    public float currentHeading = 0.0f;

    double adjustedAngle=0.0;
    double rightFront=0.0;
    double rightBack=0.0;
    double leftFront=0.0;
    double leftBack=0.0;
    double leftTurn=0.0;
    double rightTurn=0.0;

    double adjustedLeftStick=0.0;
    boolean closeEnough=false;
    double topEdge=0.0;
    double botEdge=0.0;
    double gyroPos;
    String leftOrRight;

    @Override
    public void init() {

        motorLeftBack = hardwareMap.dcMotor.get("motorBackLeft");
        motorRightBack = hardwareMap.dcMotor.get("motorBackRight");
        motorRightFront = hardwareMap.dcMotor.get("motorFrontRight");
        motorLeftFront = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);

        initializePhoneGyro();
    }

    @Override
    public void loop() {
        adjustedAngle=getAdjustedAngle();
        if (adjustedAngle >= 0.0 && adjustedAngle <= 90.0)
        {
            rightFront=1.0;
            leftBack=1.0;
            leftFront=-1.0+((adjustedAngle/90.0)*2.0);
            rightBack=-1.0+((adjustedAngle/90.0)*2.0);
        }
        else if (adjustedAngle >= 90.0 && adjustedAngle <= 180.0)
        {
            leftFront=1.0;
            rightBack=1.0;
            leftBack=1.0-(((adjustedAngle-90.0)/90.0)*2.0);
            rightFront=1.0-(((adjustedAngle-90.0)/90.0)*2.0);
        }
        else if (adjustedAngle >= 180.0 && adjustedAngle <= 270.0)
        {
            rightFront=-1.0;
            leftBack=-1.0;
            leftFront=1.0-(((adjustedAngle-180.0)/90.0)*2.0);
            rightBack=1.0-(((adjustedAngle-180.0)/90.0)*2.0);
        }
        else if (adjustedAngle >= 270.0 && adjustedAngle <= 360.0)
        {
            leftFront=-1.0;
            rightBack=-1.0;
            leftBack=-1.0+(((adjustedAngle-270.0)/90.0)*2.0);
            rightFront=-1.0+(((adjustedAngle-270.0)/90.0)*2.0);
        }
        leftFront*=getRightStickRadians();
        rightFront*=getRightStickRadians();
        leftBack*=getRightStickRadians();
        rightBack*=getRightStickRadians();
        leftFront=Range.clip(leftFront,-1.0,1.0);
        rightFront=Range.clip(rightFront,-1.0,1.0);
        leftBack=Range.clip(leftBack,-1.0,1.0);
        rightBack=Range.clip(rightBack,-1.0,1.0);


        if (getLeftStickRadians()>=.7)
        {
            adjustedLeftStick = getLeftStickTheta() - 90.0;
            while (adjustedLeftStick < 0.0) {
                adjustedLeftStick =  adjustedLeftStick+360;
            }
            //adjustedLeftStick-=180.0;
            while (adjustedLeftStick > 360.0) {
                adjustedLeftStick = adjustedLeftStick-360;
            }
        }

        closeEnough=false;
        topEdge=adjustedLeftStick+10;
        botEdge=adjustedLeftStick-10;
        gyroPos=getGyroPosition();
        if (!(topEdge>360.0 || botEdge<0.0))
        {
            if (gyroPos<topEdge && gyroPos>botEdge)
                closeEnough=true;
        }
        else if (topEdge>360.0)
        {
            if ((gyroPos<topEdge||gyroPos<topEdge-360.0)&&gyroPos>botEdge)
                closeEnough=true;
        }
        else if (botEdge<0.0)
        {
            if (gyroPos<topEdge&&(gyroPos>botEdge||gyroPos>botEdge+360.0))
                closeEnough=true;
        }
        leftOrRight=turnLeftOrRight(gyroPos,adjustedLeftStick);
        if (leftOrRight=="right" && closeEnough==false)
        {
            rightTurn=0.5;
            leftTurn=-0.5;
        }
        else if (leftOrRight=="left" && closeEnough==false)
        {
            rightTurn=-0.5;
            leftTurn=0.5;
        }
        else
        {
           rightTurn=0.0;
           leftTurn=0.0;
        }

        motorLeftBack.setPower(Range.scale(leftFront+leftTurn,-(abs(leftFront)+abs(leftTurn)),(abs(leftFront)+abs(leftTurn)),-1.0,1.0));
        motorRightBack.setPower(Range.scale(rightFront+rightTurn,-(abs(rightFront)+abs(rightTurn)),(abs(rightFront)+abs(rightTurn)),-1.0,1.0));
        motorLeftFront.setPower(Range.scale(leftBack+leftTurn,-(abs(leftBack)+abs(leftTurn)),(abs(leftBack)+abs(leftTurn)),-1.0,1.0));
        motorRightFront.setPower(Range.scale(rightBack+rightTurn,-(abs(rightBack)+abs(rightTurn)),(abs(rightBack)+abs(rightTurn)),-1.0,1.0));
        telemetry.addData("closeEnough",closeEnough);
        telemetry.addData("leftOrRight",leftOrRight);
        telemetry.addData("leftStickAdjusted",adjustedLeftStick);
        telemetry.addData("leftStickActual",getLeftStickTheta());
        telemetry.addData("leftStickRadians",getLeftStickRadians());

        telemetry.addData("Move Towards", adjustedAngle);
        telemetry.addData("Gyro Pos", getGyroPosition());
        telemetry.addData("RightStick", getRightStickTheta());
        telemetry.update();

    }

    @Override
    public void stop() {
    }

    String turnLeftOrRight(double gyroPos, double adjustedLeftStick)
    {
        double math=0.0;
        String returner="left";
        if (gyroPos<=180.0 && adjustedLeftStick<=180.0)
        {
            math=gyroPos-adjustedLeftStick;
            if (math>0.0)
            {
                returner = "right";
            }
            else
            {
                returner = "left";
            }
        }
        else if (gyroPos>=180.0&&adjustedLeftStick<=180.0)
        {
            math = gyroPos - adjustedLeftStick;
            if (math > 180.0)
            {
                returner = "left";
            } else
            {
                returner = "right";
            }
        }
        else if (gyroPos<=180.0&&adjustedLeftStick>=180.0)
        {
            math = gyroPos - adjustedLeftStick;
            if (math > -180.0)
            {
                returner = "left";
            } else
            {
                returner = "right";
            }
        }
        else if (gyroPos>=180.0&&adjustedLeftStick>=180.0)
        {
            math = gyroPos - adjustedLeftStick;
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


    double getAdjustedAngle() {
        double offset = 0.0;
        double adjustedAngle = 0.0;
        if (getGyroPosition() > 180.0) {
            offset = 360.0 - getGyroPosition();
        } else {
            offset = 0.0 - getGyroPosition();
        }
        adjustedAngle = getRightStickTheta() + offset;
        adjustedAngle+=180;
        if (adjustedAngle < 0.0) {
            adjustedAngle += 360;
        }
        if (adjustedAngle >= 360)
        {
            adjustedAngle -= 360;
        }
        return adjustedAngle;
    }


    double getGyroPosition()
    {
        double gyroPosition=0.0d;
        if (currentHeading<=0.0)
        {
            gyroPosition=0.0-currentHeading;
        }
        else
        {
            gyroPosition=360.0-currentHeading;
        }
        return gyroPosition;
    }


    double getLeftStickRadians()
    {
        double leftStickRadians= sqrt((gamepad1.left_stick_x*gamepad1.left_stick_x)+(gamepad1.left_stick_y*gamepad1.left_stick_y));
        leftStickRadians = Range.clip(leftStickRadians, -1.0, 1.0);
        return leftStickRadians;
    }

    double getLeftStickTheta()
    {
        double leftstickTheta=0.0;

        leftstickTheta=atan2(gamepad1.left_stick_y,gamepad1.left_stick_x);
        leftstickTheta=leftstickTheta*(180.0/((double) PI));
        if (leftstickTheta<=0)
        {
            leftstickTheta=0.0-leftstickTheta;
        }
        else
        {
            leftstickTheta=360.0-leftstickTheta;
        }
        return leftstickTheta;
    }

    double getRightStickRadians()
    {
        double rightStickRadians= sqrt((gamepad1.right_stick_x*gamepad1.right_stick_x)+(gamepad1.right_stick_y*gamepad1.right_stick_y));
        rightStickRadians = Range.clip(rightStickRadians, -1.0, 1.0);
        return rightStickRadians;
    }

    double getRightStickTheta()
    {
        double rightstickTheta=0.0;

        rightstickTheta=atan2(gamepad1.right_stick_y,gamepad1.right_stick_x);
        rightstickTheta=rightstickTheta*(180.0/((double) PI));
        if (rightstickTheta<=0)
        {
            rightstickTheta=0-rightstickTheta;
        }
        else
        {
            rightstickTheta=360-rightstickTheta;
        }
        return rightstickTheta;
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
