package org.firstinspires.ftc.teamcode.AUTO;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AUTO Drop Only", group = "AUTO")

public class AUTODropOnly extends AUTOMecanumAbstractPracticeBot {
    public void runOpMode() throws InterruptedException{
        startAutoOp();
        if (opModeIsActive()) {
            tfodActivate();
        }
        //shutdownTfod();
        //double angle = 0;
        setHangTowerPosition(-8200);
        sleep(6000);
        sleep(1000);
        }

}
