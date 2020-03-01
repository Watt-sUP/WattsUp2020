package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Mugurel.Hardware.Collector;
import org.firstinspires.ftc.teamcode.Mugurel.Hardware.Mugurel;

import java.util.IllegalFormatCodePointException;
import java.util.RandomAccess;
import java.util.zip.DeflaterOutputStream;

@TeleOp(name="Driver Controled" , group="Linear Opmode")
//@Disabled
public class DriverControled extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Mugurel robot;

    boolean x_press = false;
    boolean b_press = false;
    boolean y_press = false;
    boolean a_press = false;
    boolean rb_press = false;
    boolean lb_press = false;

    boolean dpadUp = false;
    boolean dpadDown = false;
    boolean dpadLeft = false;
    double rotatep = 0.0;


    double ext = 1.0;
    int  runState = 0;
    double power = 0.8;
    int upTicks = 900;
    int downTicks = -900;


    @Override
    public void runOpMode()  {

        robot = new Mugurel(hardwareMap);
       // robot.setOpmode(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        robot.runner.setFace(Math.PI);
        robot.runner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // robot.lift.setTargetPosition(0);
       // robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       // robot.lift.setPower(1.0);

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            if (gamepad1.y) robot.runner.setFace(Math.PI);
            else if (gamepad1.a) robot.runner.setFace(0);
            else if (gamepad1.x) robot.runner.setFace(-Math.PI / 2.0);
            else if (gamepad1.b) robot.runner.setFace( Math.PI / 2.0);

          double modifier = 0.75;
          if (gamepad1.right_trigger > 0.3) modifier = 0.23;
          if (gamepad1.left_trigger > 0.3)  modifier = 0.5;

            final double drive_y = robot.runner.scalePower(gamepad1.left_stick_y);
            final double drive_x = robot.runner.scalePower(gamepad1.left_stick_x);
            final double turn = robot.runner.scalePower(gamepad1.right_stick_x);

             if (gamepad1.dpad_right) robot.runner.moveWithAngle(1,0,0, modifier);
             else if (gamepad1.dpad_left) robot.runner.moveWithAngle(-1,0,0, modifier);
             else robot.runner.moveWithAngle(drive_x, drive_y, turn, modifier);

            /**
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             * ////////////////////////////////////////////////////////////////////////////////////////////////////////
             */

            if (gamepad2.x)
            {
              if (!x_press)
              {
                if(runState == 0)   runState = 1;
                else    runState = 0;

                x_press = true;
              }
            }
            else x_press = false;

            if (gamepad2.b)
            {
              if (!b_press)
              {
                if (runState == -1)   runState = 0;
                else   runState = -1;

                b_press = true;
              }
            }
            else b_press = false;

            robot.collector.setPower(power*(double)runState);

            robot.lift.update();

            double  liftPower = 0.0;

           if (gamepad2.right_trigger > 0.3)  liftPower += 1.0;
           if (gamepad2.left_trigger > 0.3)   liftPower += -1.0;


            /* if (Math.abs(liftPower) < 0.01 && !robot.lift.isBusy())
             {
              /*robot.lift.liftLeft.setTargetPosition(robot.lift.liftLeft.getCurrentPosition());
              robot.lift.liftRight.setTargetPosition(robot.lift.liftRight.getCurrentPosition());

              robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              robot.lift.setPower(1.0);
              robot.lift.stopLift();
              robot.lift.addTicks(robot.lift.getCurrentPosition());
             }*/

             if (Math.abs(liftPower) > 0.01)
             {
               robot.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
               robot.lift.setPower(liftPower);
             }
             else
              if (!robot.lift.liftLeft.isBusy())
             {
              robot.lift.stopLift();
              robot.lift.addTicks(0);
             }

             if (gamepad2.left_bumper)
             {
               if (!lb_press)
               {
                   int leftTicks = robot.lift.liftLeft.getCurrentPosition();
                   int rightTicks = robot.lift.liftRight.getCurrentPosition();

                   robot.lift.stopLift();
                   robot.lift.addTicks(1000);
                   ext = 1.0;
                  // downTicks = -robot.lift.getCurrentPosition();
                   //downTicks += 120;
                   //int zeroPosition = Math.abs(leftTicks - downTicks) + Math.abs(rightTicks - downTicks) / 2;
                   robot.lift.addTicks(-robot.lift.getCurrentPosition());



                /*int leftTicks = robot.lift.liftLeft.getCurrentPosition();
                int rightTicks = robot.lift.liftRight.getCurrentPosition();
                downTicks =  (leftTicks+rightTicks) / 2;
                downTicks += 120;

                robot.lift.liftLeft.setTargetPosition( Math.abs(leftTicks - downTicks) );
                robot.lift.liftRight.setTargetPosition( Math.abs(rightTicks - downTicks) );

                robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.lift.setPower(1.0);

                a_press = true;*/
                lb_press = true;
               }
             }
             else lb_press = false;

           if (gamepad2.right_bumper)
           {
             if (!rb_press)
             {
               if (ext == 0.5)
                ext = 0.0;
               else
                ext = 0.5;
                rb_press = true;
             }

           }
           else rb_press = false;

           robot.stone.extend.setPosition(ext);

           if (gamepad1.left_bumper)
           { robot.stone.grab.setPosition(1); runState = 1;}
           if (gamepad1.right_bumper)
           { robot.stone.grab.setPosition(0); runState = 0; }


           if (gamepad2.dpad_right)
            robot.stone.rotate.setPosition(0.0);

           if (gamepad2.dpad_left)
           {
               if (!dpadLeft)
               {
                   if (robot.stone.rotate.getPosition() == 0.40)
                       robot.stone.rotate.setPosition(1.0);
                   else
                       robot.stone.rotate.setPosition(0.40);
                   dpadLeft = true;
               }

           }
           else dpadLeft = false;


           telemetry.addData("Servo Position", robot.stone.extend.getPosition());
           telemetry.addData("liftPower", liftPower);
           telemetry.addData("collectPower", power);
           telemetry.addData("Mode", robot.lift.liftLeft.getMode());
           telemetry.addData("liftRight", robot.lift.liftRight.getCurrentPosition());
           telemetry.addData("liftLeft", robot.lift.liftLeft.getCurrentPosition());
        }

    }
}
