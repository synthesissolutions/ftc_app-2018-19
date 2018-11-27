package org.firstinspires.ftc.teamcode.WIP;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="WIP Gyro Tele TEST RESET GYRO", group="WIP")

public class WIPGyroTele4 extends OpMode implements SensorEventListener{
    List<Float> gyroEventValues = new ArrayList<>();
    public float currentHeading = 0.0f;
    public float offset = 0.0f;

    @Override
    public void init() {
        initializePhoneGyro();
    }

    @Override
    public void loop() {
        if (gamepad1.a)
        {
            offsetPhoneGyro();
        }

        telemetry.addData("Gyro Pos", currentHeading);
        telemetry.update();
    }

    @Override
    public void stop() {
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

    protected void offsetPhoneGyro() {
        offset+=currentHeading;
    }

    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        currentHeading = (float) ((Math.toDegrees(orientation[0]) - offset)% 360 - 360) % 360;
        if (Math.abs(currentHeading) > 180)
            currentHeading+=360;

/*      gyroCurrentHeading = (float) Math.toDegrees(orientation[0]) - offset;
        if (Math.abs((int) gyroCurrentHeading / 180)%2==1)
            gyroCurrentHeading = gyroCurrentHeading % 180 - 180 * Math.abs(gyroCurrentHeading)/gyroCurrentHeading;
        else gyroCurrentHeading = gyroCurrentHeading % 180;*/
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    /******************************************************************************************************************/
}
