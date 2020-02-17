package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OpMode;

public class Collector {

    public DcMotor lift , collect ;
    public Servo servoRight, servoLeft;

   public Collector (DcMotor rr, DcMotor rl, Servo _s1, Servo _s2){
      lift =rl;
      collect = rr;
     servoRight = _s1;
     servoLeft = _s2;

       lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       collect.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

   }



   public void lift (double power){
    lift.setPower(power);
   }

   public void collect (double power){
     collect.setPower(power);
   }

   /*public void stopRotation (){
     rotLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     rotRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

     rotLeft.setPower(0.0);
     rotRight.setPower(0.0);

   }

   public void addPower ( double power){
     rotLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     rotRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

     rotRight.setPower(-power);
     rotLeft.setPower(power);

   }

  public void addTicksWithPower (int ticks, double power){

   rotLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
   rotRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

   rotRight.setTargetPosition(Math.abs(rotRight.getCurrentPosition()-ticks));
   rotLeft.setTargetPosition(rotLeft.getCurrentPosition()-ticks);

   rotRight.setPower(power);
   rotLeft.setPower(power);

  }

  public  boolean isMotorStopped(){
       if(rotLeft.isBusy()&&rotRight.isBusy())
           return false;
       else
           return  true;

  }
*/

}
