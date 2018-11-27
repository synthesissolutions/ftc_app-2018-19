package org.firstinspires.ftc.teamcode.WIP;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="WIP Gyro Tele INITS ON COMMAND", group="WIP")
@Disabled
public class WIPGyroTele3 extends OpMode implements SensorEventListener{
    List<Float> gyroEventValues = new ArrayList<>();
    public float currentHeading = 0.0f;
    SensorManager sensorManager;

    @Override
    public void init() {
        initializePhoneGyro();
    }

    @Override
    public void loop() {
        if (gamepad1.a)
        {
            initializePhoneGyro();
        }
        if (gamepad1.b)
        {
            deregisterPhoneGyro();
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
        sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gameRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void deregisterPhoneGyro() {
        sensorManager.unregisterListener(this);
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
