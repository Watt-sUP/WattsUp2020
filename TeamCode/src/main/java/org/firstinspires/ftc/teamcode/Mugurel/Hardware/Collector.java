package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Collector {

  public DcMotor rotLeft, rotRight;
  public Servo servo;
  public double defaultPower = 0.6;

    public Collector (DcMotor _rotLeft, DcMotor _rotRight, Servo _servo){
       rotLeft = _rotLeft;
       rotRight = _rotRight;
       servo = _servo;

       rotLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       rotRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       rotLeft.setDirection(DcMotorSimple.Direction.FORWARD);
       rotRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void grabStone (){

       if (servo.getPosition() == 1)
         servo.setPosition(0);
       else servo.setPosition(1);

    }

    public double powerUp (double power){
      if(power <= 1.0)
          power += 0.10;
        return power ;
    }

    public double powerDown (double power){
      if(power >= 0.0)
        power -= 0.10;
      return power;
    }

    public void collect (int state){
      defaultPower *= (double)state;
     rotLeft.setPower(defaultPower);
     rotRight.setPower(defaultPower);
    }

    public void setPower (double power){
      rotRight.setPower(power);
      rotLeft.setPower(power);
    }




}
