package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Stone {

   public DcMotor rotRight;
   public DcMotor rotLeft;

   public double power = 1.0;

    public Stone (DcMotor rr, DcMotor rl){
       rotRight = rr;
       rotLeft = rl;

       rotLeft.setDirection(DcMotorSimple.Direction.REVERSE);

       rotRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       rotLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void grab ( ) {
        rotLeft.setPower(power);
        rotRight.setPower(power);
    }

    public void stop ( ) {
        rotRight.setPower(0);
        rotLeft.setPower(0);
    }





}
