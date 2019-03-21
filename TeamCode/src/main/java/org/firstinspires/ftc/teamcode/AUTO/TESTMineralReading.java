package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Test Mineral Reading", group = "AUTO")
public class TESTMineralReading extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException {
        startBasicAutoOp();
        driveStraightAndColor(5000, -0.3);
    }
}
