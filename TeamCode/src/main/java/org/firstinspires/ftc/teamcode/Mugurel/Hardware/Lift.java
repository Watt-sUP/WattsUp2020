package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift {

   public DcMotor liftRight;
   public DcMotor liftLeft;

    public double liftTicks;
    public double defaultPower = 0.5;

    public Lift (DcMotor lifr, DcMotor lifl){
       liftRight = lifr;
       liftLeft = lifl;

       liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setMode (DcMotor.RunMode mode){
      liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      liftLeft.setMode(mode);
      liftRight.setMode(mode);
    }

    public void setPower (double power){
     liftLeft.setPower(power);
     liftLeft.setPower(power);

    }

    public void holdPosition (double power){
        if(power != 0) return;
        liftLeft.setTargetPosition(0);
        liftRight.setTargetPosition(0);

        setPower(0.8);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


}
