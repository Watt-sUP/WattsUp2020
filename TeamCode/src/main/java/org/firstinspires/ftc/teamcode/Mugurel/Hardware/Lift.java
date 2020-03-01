package org.firstinspires.ftc.teamcode.Mugurel.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.LinkedList;
import java.util.Queue;

public class Lift {

    class LiftMovement {
     public int ticks = 0;

     public LiftMovement () {}
     public LiftMovement (int _ticks) { ticks = _ticks; }

    }

    Queue<LiftMovement> queue;

    public DcMotor liftRight;
    public DcMotor liftLeft;
    public LinearOpMode opMode;

    public double liftTicks;
    public double defaultPower = 0.5;

    public LiftMovement nowPlaying = new LiftMovement();

    public Lift (DcMotor lifr, DcMotor lifl){
       liftRight = lifr;
       liftLeft = lifl;

       liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

       liftRight.setDirection(DcMotorSimple.Direction.FORWARD);
       liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

       queue = new LinkedList<LiftMovement>();

    }

     public int getCurrentPosition ()
     {
         int leftPosition = liftLeft.getCurrentPosition();
         int rightPosition = liftRight.getCurrentPosition();
         int currentPosition = (leftPosition + rightPosition)/2;

         return currentPosition;
     }

     public void setTargetPosition (int ticks)
     {
         liftLeft.setTargetPosition(ticks);
         liftRight.setTargetPosition(ticks);
     }

     public int getDistance ()
     {
         int sum = 0;
         sum += Math.abs(liftLeft.getCurrentPosition() - liftLeft.getTargetPosition());
         sum += Math.abs(liftRight.getCurrentPosition() - liftRight.getTargetPosition());

         return sum/2;
     }

     public boolean isBusy ()
     {
         int limit = 10;
         if ( getDistance() <= limit)
          return false;
         return true;
     }

     public void update ()
     {
        if (isBusy())
        {
           int ticksLimit = 7000;
           int distance = getCurrentPosition() ;
           if (distance > ticksLimit){ setTargetPosition(ticksLimit); return;}

           return;
        }
        if (queue.isEmpty())  return;
        LiftMovement op = queue.peek();
        queue.remove();
        goToPosition(op);

     }


     public void addTicks (int ticks)
     {
         queue.add(new LiftMovement(ticks));
     }

     public void goToPosition (LiftMovement op)
     {
      nowPlaying = op;
      int ticks = op.ticks;
      double power = 1.0;

      setTargetPosition(getCurrentPosition() + ticks);
      setMode(DcMotor.RunMode.RUN_TO_POSITION);
      setPower(power);
     }

     public void stopLift ()
     {
        setTargetPosition(getCurrentPosition());
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (!queue.isEmpty()) queue.remove();
     }

    public void setMode (DcMotor.RunMode mode){
     // liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     // liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      liftLeft.setMode(mode);
      liftRight.setMode(mode);
    }

    public void setPower (double power){
   //   setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      liftLeft.setPower(power);
      liftRight.setPower(power);
     //holdPosition(power);
    }

    public void holdPosition (double power){
        if(power != 0) return;
        liftLeft.setTargetPosition(0);
        liftRight.setTargetPosition(0);

        setPower(0.8);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setOpmode (LinearOpMode _o) {opMode = _o;}

}
