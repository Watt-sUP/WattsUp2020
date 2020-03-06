package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.LinkedList;
import java.util.Queue;

public class Lift {

    public DcMotor right;
    public DcMotor left;

    public final int MAX_LEVEL = 14;
    public final int error = 20;

    public int level = 0;
    public int[] levelsPositions = new int[MAX_LEVEL + 1];

    public boolean usingLevels = false;

    public Lift (DcMotor lifr, DcMotor lifl){
        right = lifr;
        left = lifl;

        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setDirection(DcMotorSimple.Direction.FORWARD);
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        levelsPositions[0] = 0;
        levelsPositions[1] = 0;
        levelsPositions[2] = 0;
        levelsPositions[3] = 0;
        levelsPositions[4] = 0;
        levelsPositions[5] = 0;
        levelsPositions[6] = 0;
        levelsPositions[7] = 0;
        levelsPositions[8] = 0;
        levelsPositions[9] = 0;
        levelsPositions[10] = 0;
        levelsPositions[11] = 0;
        levelsPositions[12] = 0;
        levelsPositions[13] = 0;
        levelsPositions[14] = 0;
    }

    public void setMode(DcMotor.RunMode mode){
        left.setMode(mode);
        right.setMode(mode);
    }

    public void setPower(double power){
        left.setPower(power);
        right.setPower(power);
    }

    public int getPosition() {
        return (left.getCurrentPosition() + right.getCurrentPosition()) / 2;
    }

    public void goToPosition(int position, double speed) {
       // left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setTargetPosition(position);
        right.setTargetPosition(position);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setPower(speed);
        right.setPower(speed);
    }

    public void goToPosition(int position) {
        goToPosition(position, 0.5);
    }

    public void setManualPower(double power) {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(power);
        usingLevels = false;
    }

    public void stop() {
        goToPosition(getPosition(), 1);
        usingLevels = false;
    }

    public void hold() {

        if(isBusy())   return;
        stop();
    }

    public void levelUp() {
        if(usingLevels) {
            level += 1;
            level = Math.min(level, MAX_LEVEL);
        }
        else {
            level = MAX_LEVEL;
            for (int i = 0; i < MAX_LEVEL; i++)
                if (levelsPositions[i] - error >= getPosition()) {
                    level = i;
                    break;
                }
            usingLevels = true;
        }
        goToPosition(levelsPositions[level]);
    }

    public void levelDown() {
        if(usingLevels) {
            level -= 1;
            level = Math.max(level, 0);
        }
        else {
            level = 0;
            for (int i = MAX_LEVEL; i > 0; i--)
                if (levelsPositions[i] - error >= getPosition()) {
                    level = i;
                    break;
                }
            usingLevels = true;
        }
        goToPosition(levelsPositions[level]);
    }

    public void addTicks(int ticks) {
        addTicks(ticks, 1.0);
    }

    public void addTicks(int ticks, double speed) {
        goToPosition(left.getTargetPosition() + ticks, speed);
    }

    public void goDown() {
        goToPosition(levelsPositions[0]);
    }

    public boolean isBusy() {
        return left.isBusy() || right.isBusy();
    }
}
