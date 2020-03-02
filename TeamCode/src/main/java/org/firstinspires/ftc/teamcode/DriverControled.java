package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mugurel.Hardware.Mugurel;
import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.gamepad.Button;
import org.firstinspires.ftc.teamcode.gamepad.GamepadExt;

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

    GamepadExt gaju, duta;


    @Override
    public void runOpMode()  {

        robot = new Mugurel(hardwareMap);
        gaju = new GamepadExt(gamepad1);
        duta = new GamepadExt(gamepad2);

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
            gaju.update();
            duta.update();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            setFace(gaju.y, gaju.a, gaju.x, gaju.b);
            move(gaju.left_x, gaju.left_y, gaju.right_y, gaju.left_trigger.toButton(0.3), gaju.right_trigger.toButton(0.3), gaju.dpad_left, gaju.dpad_right);


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

            setCollector(duta.x, duta.b);

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

    private void setFace(Button front, Button back, Button left, Button right) {
        if (front.pressed()) robot.runner.setFace(Math.PI);
        else if (back.pressed()) robot.runner.setFace(0);
        else if (left.pressed()) robot.runner.setFace(-Math.PI / 2.0);
        else if (right.pressed()) robot.runner.setFace( Math.PI / 2.0);
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower, Button dl, Button dr) {
        double modifier = 0.75;
        if (smallPower != null && smallPower.raw) modifier = 0.23;
        if (mediumPower != null && mediumPower.raw)  modifier = 0.5;

        final double drive_y = robot.runner.scalePower(ly.raw);
        final double drive_x = robot.runner.scalePower(lx.raw);
        final double turn = robot.runner.scalePower(rx.raw);

        if (dr != null && dr.raw) robot.runner.moveWithAngle(1,0,0, modifier);
        else if (dl != null && dl.raw) robot.runner.moveWithAngle(-1,0,0, modifier);
        else robot.runner.moveWithAngle(drive_x, drive_y, turn, modifier);
    }

    private void setCollector(Button in, Button out) {
        if (in.pressed()) {
            if(runState == 0)   runState = 1;
            else    runState = 0;
        }

        if (out.pressed()) {
            if (runState == -1)   runState = 0;
            else   runState = -1;
        }
        robot.collector.setPower(power*(double)runState);
    }
}
