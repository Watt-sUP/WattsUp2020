package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mugurel {

  public Runner runner;
  public Stone stone;
  public Lift lift;
  public Collector collector;

  public HardwareMap hardwareMap;
  public Telemetry telemetry;
  public LinearOpMode opMode;

  public Mugurel ( )  { }

  public Mugurel (HardwareMap hm){

     hardwareMap = hm;

     runner = new Runner(
             hm.get(DcMotor.class, Config.left_back),
             hm.get(DcMotor.class, Config.left_front),
             hm.get(DcMotor.class, Config.right_back),
             hm.get(DcMotor.class, Config.right_front)
     );

    stone = new Stone(
             hm.get(Servo.class, Config.extend),
             hm.get(Servo.class, Config.rotate),
            hm.get(Servo.class, Config.grab)
     );

     lift = new Lift(
             hm.get(DcMotor.class, Config.liftRight),
             hm.get(DcMotor.class, Config.liftLeft)
     );

     collector = new Collector(
             hm.get(DcMotor.class, Config.rotLeft),
             hm.get(DcMotor.class, Config.rotRight)
     );



  }

    public void setTelemetry (Telemetry _t){
        telemetry = _t;
    }
    public void setOpmode(LinearOpMode _o) { opMode = _o; lift.setOpmode(_o); }


}
