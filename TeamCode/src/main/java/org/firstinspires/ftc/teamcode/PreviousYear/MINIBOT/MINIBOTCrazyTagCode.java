package org.firstinspires.ftc.teamcode.PreviousYear.MINIBOT;


import android.media.AudioManager;
import android.media.SoundPool;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.lang.Math;
import java.util.ArrayList;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TELEOP MINIBOT crazy code", group="MINIBOT")
@Disabled
public class MINIBOTCrazyTagCode extends OpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    int randomLUp;
    int randomLDown;
    int randomRUp;
    int randomRDown;
    boolean first = true;
    public SoundPool mySound;
    public int beepID;

    ElapsedTime elapsedTime = new ElapsedTime();

    ArrayList<Boolean> controls = new ArrayList<Boolean>();

    @Override
    public void init() {
        mySound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // PSM
        //beepID = mySound.load(hardwareMap.appContext, R.raw.beep, 1); // PSM
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        controls.add(gamepad1.left_stick_y>= 0.7);

        controls.add(gamepad1.left_stick_y<= -0.7);

        controls.add(gamepad1.right_stick_y>= 0.7);
        controls.add(gamepad1.right_stick_y<= -0.7);

        controls.add(gamepad1.left_trigger>=.7);
        controls.add(gamepad1.right_trigger>=.7);

        controls.add(gamepad1.left_bumper);
        controls.add(gamepad1.right_bumper);

        controls.add(gamepad1.x);
        controls.add(gamepad1.y);
        controls.add(gamepad1.a);
        controls.add(gamepad1.b);

        controls.add(gamepad1.dpad_up);
        controls.add(gamepad1.dpad_down);
        controls.add(gamepad1.dpad_left);
        controls.add(gamepad1.dpad_right);

        controls.add(gamepad1.start);
        controls.add(gamepad1.back);
        controls.add(gamepad1.atRest());
    }

    @Override
    public void loop() {
        if (first)
        {
            first=false;
            elapsedTime.reset();
            elapsedTime.startTime();
            randomRUp = (int) (Math.random() * 19);
            randomRDown = (int) (Math.random() * 19);
            while (randomRUp == randomRDown) {
                randomRDown = (int) (Math.random() * 19);
            }

            randomLUp = (int) (Math.random() * 19);

            while (randomLUp == randomRDown || randomLUp == randomRUp) {
                randomLUp = (int) (Math.random() * 19);
            }

            randomLDown = (int) (Math.random() * 19);

            while (randomLDown == randomLUp || randomLDown == randomRUp || randomLDown == randomRDown) {
                randomLDown = (int) (Math.random() * 19);
            }
            mySound.play(beepID,1,1,1,0,1);
        }

        controls.set(0, gamepad1.left_stick_y >= 0.7);

        controls.set(1, gamepad1.left_stick_y <= -0.7);

        controls.set(2, gamepad1.right_stick_y >= 0.7);
        controls.set(3, gamepad1.right_stick_y <= -0.7);

        controls.set(4, gamepad1.left_trigger >= .7);
        controls.set(5, gamepad1.right_trigger >= .7);

        controls.set(6, gamepad1.left_bumper);
        controls.set(7, gamepad1.right_bumper);

        controls.set(8, gamepad1.x);
        controls.set(9, gamepad1.y);
        controls.set(10, gamepad1.a);
        controls.set(11, gamepad1.b);

        controls.set(12, gamepad1.dpad_up);
        controls.set(13, gamepad1.dpad_down);
        controls.set(14, gamepad1.dpad_left);
        controls.set(15, gamepad1.dpad_right);

        controls.set(16, gamepad1.start);
        controls.set(17, gamepad1.back);
        controls.set(18, gamepad1.atRest());

        if (elapsedTime.time()>15) {
            mySound.play(beepID,1,1,1,0,1);
            elapsedTime.reset();
            elapsedTime.startTime();

            randomRUp = (int) (Math.random() * 19);
            randomRDown = (int) (Math.random() * 19);
            while (randomRUp == randomRDown) {
                randomRDown = (int) (Math.random() * 19);
            }

            randomLUp = (int) (Math.random() * 19);

            while (randomLUp == randomRDown || randomLUp == randomRUp) {
                randomLUp = (int) (Math.random() * 19);
            }

            randomLDown = (int) (Math.random() * 19);

            while (randomLDown == randomLUp || randomLDown == randomRUp || randomLDown == randomRDown) {
                randomLDown = (int) (Math.random() * 19);
            }
        }
        setLeftPower(randomLUp,randomLDown);
        setRightPower(randomRUp,randomRDown);

    }


    @Override
    public void stop() {
    }

    void setLeftPower(int LUp, int LDown) {
        if (controls.get(LUp)==true)
        {
            motorLeft.setPower(0.8);
        } else if (controls.get(LDown)==true)
        {
            motorLeft.setPower(-0.8);
        } else
        {
            motorLeft.setPower(0);
        }
    }

    void setRightPower(int RUp, int RDown) {
        if (controls.get(RUp)==true)
        {
            motorRight.setPower(0.8);
        } else if (controls.get(RDown)==true)
        {
            motorRight.setPower(-0.8);
        } else
        {
            motorRight.setPower(0);
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
}