package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Stone {

   public Servo extend;
   public Servo grab;
   public Servo rotate;


    public Stone (Servo ext, Servo rot, Servo _grab){
        extend = ext;
        rotate = rot;
        grab = _grab;

    }

    public void grabStone (){
       if (grab.getPosition() == 1)
         grab.setPosition(0);
       else
          grab.setPosition(1);
    }

    public void flip (){
        if(rotate.getPosition() == 1)
          rotate.setPosition(0);
        else
          rotate.setPosition(1);
    }

    public void extend (){
       if (extend.getPosition() == 1)
           extend.setPosition(0);
       else
           extend.setPosition(1);
    }










}
