package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Disabled
public class Auto extends Mugurel {

    public final double wheelDiameter = 100.0;
    public final double wheelCircumference = wheelDiameter * Math.PI;
    public double ticksPerRevolution = 560;

    public enum AutonomusMoveType {FORWARD, BACKWARD, LEFT, RIGHT}

    public class Autonomous {
    public BNO055IMU imu;
    public double Angle;
    public Servo servo1, servo2;



    Autonomous() {}

    public void init () {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.mode = BNO055IMU.SensorMode.IMU;

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");

        servo2.setPosition(0.0);
        servo1.setPosition(0.0);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        Angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while(!imu.isGyroCalibrated() && timer.milliseconds()<1000)
        {
          telemetry.addData("Gyro", "Calibrating.....");
          telemetry.update();
          if (!opMode.opModeIsActive())
             return;
        }
        //ticksPerRevolution = 560; //runner.left_front.getMotorType().getTicksPerRev();

        telemetry.addData("Gyro","Calibrated");
        telemetry.update();
    }

    public int distanceToTicks (double dist){
       double ticks = (dist * ticksPerRevolution)/  wheelCircumference;
      return (int) ticks;
    }

    public void moveForwardBackward (int distance, AutonomusMoveType type ){
     if (type != AutonomusMoveType.FORWARD && type != AutonomusMoveType.BACKWARD)
        return;
      int ticks = distanceToTicks(distance);
      if (type == AutonomusMoveType.BACKWARD)
       ticks = -ticks;
        runner.setTargetPosition(ticks, ticks, ticks, ticks);
       runner.reset(DcMotor.RunMode.RUN_TO_POSITION);
       move();
       // while (runner.isBusy()){
         //   runner.setPower(0.2);
        //}
        //runner.stop();


    }

    public void moveLeftRight (double distance, AutonomusMoveType type){
      if (type != AutonomusMoveType.LEFT && type != AutonomusMoveType.RIGHT)
       return;
      int ticks = distanceToTicks(distance);
      if(type == AutonomusMoveType.LEFT)
        ticks = -ticks;
      runner.setTargetPosition(ticks, -ticks, -ticks, ticks);
        runner.reset(DcMotor.RunMode.RUN_TO_POSITION);
        move();


    }

    public void move (){
      double finalPower = 0.0;
      double acceptedDifference= 0.1;
      int startTicks = (int) (1.1 * ticksPerRevolution);
       while (runner.isBusy()){
          double power = 0.0;
          int distanceTicks = runner.getTicksDistance();
          if (distanceTicks < startTicks)
           power = (double) distanceTicks / (double) startTicks;
          else
           power = 0.5;

         power = Math.min(power, finalPower + acceptedDifference);
         power = Math.max(power, finalPower - acceptedDifference);
         power = Math.max(power, 0.05);
         finalPower = power;

         runner.setPower(power);

        if(!opMode.opModeIsActive()){
          runner.stop();
          return;
        }

       }

      runner.stop();


    }

    public void grabTray () {
        servo1.setPosition(0.80);
        servo2.setPosition(0.80);
    }

    public void leaveTray () {
        servo1.setPosition(0.0);
        servo2.setPosition(0.0);
    }







    }

  public Autonomous autonomous;

  public Auto (HardwareMap hm, Telemetry _t, LinearOpMode _o) {
      hardwareMap = hm;
      runner = new Runner(
              hm.get(DcMotor.class, Config.left_front),
              hm.get(DcMotor.class, Config.right_front),
              hm.get(DcMotor.class, Config.left_back),
              hm.get(DcMotor.class, Config.right_back)
      );

      autonomous = new Autonomous();

      setTelemetry(_t);
      setOpmode(_o);

      autonomous.init();
  }

}
