package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name = "AUTO Blue Vuforia Align Test", group = "AUTO")
@Disabled
public class AUTOBlueVuforiaAlignTest extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        turnDegrees(-0.2, 90 - vuforiaGetDataWIP());
        sleep(6000);
    }

}
