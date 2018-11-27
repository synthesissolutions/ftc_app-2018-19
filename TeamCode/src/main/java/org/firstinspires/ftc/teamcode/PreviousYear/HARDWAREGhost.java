package org.firstinspires.ftc.teamcode.PreviousYear;

import org.firstinspires.ftc.teamcode.HARDWARE.HARDWAREAbstract;

/**
 * Created by itops on 12/21/2017.
 */

public class HARDWAREGhost extends HARDWAREAbstract {
    final double RELIC_ARM_START = 0.59;
    final double RELIC_ARM_GRAB = 0.548;
    final double RELIC_ARM_HOLD = 0.47;
    final double RELIC_ARM_PRE_GRAB = 0.535;


    final static public double JEWEL_FLICKER_START = .95;
    final static public double JEWEL_FLICKER_MID = 0.44;
    final static double JEWEL_ARM_IN = 0.80;
    final static double JEWEL_ARM_DOWN = 0.30;
    final static double JEWEL_GATE_OPEN=0.0;
    final static double JEWEL_GATE_CLOSED=.8;
    final static double RELIC_HAND_CLOSED = .425;
    final static double RELIC_HAND_OPEN = .25;

    public double getJewelGateClosed() {
        return JEWEL_GATE_CLOSED;
    }

    public double getJewelArmIn() {
        return JEWEL_ARM_IN;
    }

    public double getJewelFlickerStart() {
        return JEWEL_FLICKER_START;
    }

    public double getJewelFlickerMid() {
        return JEWEL_FLICKER_MID;
    }

    public double getRelicArmStart() {
        return RELIC_ARM_START;
    }

    public double getRelicArmGrab() {
        return RELIC_ARM_GRAB;
    }

    public double getRelicArmHold() {
        return RELIC_ARM_HOLD;
    }
    public double getRelicArmPreGrab() {
        return RELIC_ARM_PRE_GRAB;
    }
    public double getRelicHandOpen() {
        return RELIC_HAND_OPEN;
    }
    public double getRelicHandClosed() {
        return RELIC_HAND_CLOSED;
    }

}
