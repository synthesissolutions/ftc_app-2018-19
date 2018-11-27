package org.firstinspires.ftc.teamcode.PreviousYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TEST Arm", group="TEST")

@Disabled
public class TESTArmTeleop extends OpMode {

	Servo servoJoint1;
	Servo servoJoint2;
	Servo servoContinuous;

	double servoJoint1pos=0.5;
	double servoJoint2pos=0;
	double servoContinuousSpeed=0.5;

	int timer = 0;




	@Override
	public void init() {
		servoJoint1pos=0.5;
		servoJoint2pos=0;
		servoContinuousSpeed=0.5;
		timer = 0;


		servoJoint1 = hardwareMap.servo.get("servoJoint1");

		servoJoint2 = hardwareMap.servo.get("servoJoint2");

		servoContinuous = hardwareMap.servo.get("servoContinuous");
		servoJoint1.setPosition(servoJoint1pos);
		servoJoint2.setPosition(servoJoint2pos);
		servoContinuous.setPosition(servoContinuousSpeed);
	}

	@Override
	public void loop() {


		if (gamepad1.a)
		{
			servoJoint1pos-=0.008;
		}
		if (gamepad1.b)
		{
			servoJoint1pos+=0.008;
		}
		if (gamepad1.x)
		{
			servoJoint2pos=0;
		}
		if (gamepad1.y)
		{
			servoJoint2pos=1;
		}

		if (gamepad1.dpad_up)
		{
			servoContinuousSpeed=0;
		}
		if (gamepad1.dpad_down)
		{
			servoContinuousSpeed=1;
		}
		if (gamepad1.dpad_left||gamepad1.dpad_right)
		{
			servoContinuousSpeed=0.5;
		}
		servoJoint1pos=Range.clip(servoJoint1pos,.3,.9);
		servoJoint1.setPosition(servoJoint1pos);
		servoJoint2.setPosition(servoJoint2pos);
		servoContinuous.setPosition(servoContinuousSpeed);
	}
	@Override
	public void stop() {

	}


}