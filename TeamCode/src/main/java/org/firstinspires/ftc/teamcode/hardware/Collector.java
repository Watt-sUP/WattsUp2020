package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Collector {

    public DcMotor left, right;

    public Collector (DcMotor _left, DcMotor _right){
       left = _left;
       right = _right;

       left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       left.setDirection(DcMotorSimple.Direction.FORWARD);
       right.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setPower (double power){
        right.setPower(power);
        left.setPower(power);
    }
}
