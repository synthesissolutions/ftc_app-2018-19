package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Testing New Turn Method", group = "AUTO")

public class AUTOTestingNewTurnMethod extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        turnDegrees( -0.3, 45);
        sleep(3000);

        turnDegrees(-0.2, 90);
        sleep(3000);

        turnDegrees(-0.2, 45);
        sleep(3000);

        turnDegrees(0.2, 20);
        sleep(3000);
        turnDegrees(0.3, 90);
        sleep(3000);
        turnDegrees(-0.2, 179);
        sleep(3000);

    }

}
