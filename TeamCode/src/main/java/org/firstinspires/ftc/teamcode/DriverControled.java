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

    boolean dpadUp = false;
    boolean dpadDown = false;


    double servop = 0.0;
    int  runState = 0;
    double power = 0.6;
    int upTicks = 900;
    int downTicks = -900;


    @Override
    public void runOpMode()  {

        robot = new Mugurel(hardwareMap);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        robot.runner.setFace(Math.PI);
        robot.runner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //robot.collect.afterStartInit();
       // robot.lift.afterStartInit();

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            if (gamepad1.y) robot.runner.setFace(Math.PI);
            else if (gamepad1.a) robot.runner.setFace(0);
            else if (gamepad1.x) robot.runner.setFace(-Math.PI / 2.0);
            else if (gamepad1.b) robot.runner.setFace( Math.PI / 2.0);

          double modifier = 1.0;
          if (gamepad1.right_trigger > 0.3) modifier = 0.5;
          if (gamepad1.left_trigger > 0.3)  modifier = 0.3;

            final double drive_y = robot.runner.scalePower(gamepad1.left_stick_y);
            final double drive_x = robot.runner.scalePower(gamepad1.left_stick_x);
            final double turn = robot.runner.scalePower(gamepad1.right_stick_x);

             if (gamepad1.right_bumper) robot.runner.moveWithAngle(1,0,0, modifier);
             else if (gamepad1.left_bumper) robot.runner.moveWithAngle(-1,0,0, modifier);
             else robot.runner.moveWithAngle(drive_x, drive_y, turn, modifier);

            if (gamepad2.y)
            {
                if (!y_press)
                    power = robot.collector.powerUp(power);
                y_press = true;
            }
            else y_press = false;

            if (gamepad2.a)
            {
                if (!a_press)
                  power = robot.collector.powerDown(power);
                a_press = true;
            }
            else a_press = false;


            if (gamepad2.x)
            {
              if (!x_press)
              {
                if(runState == 0)
                 runState = 1;
                else runState = 0;
              }
              x_press = true;
            }
            else x_press = false;

            if (gamepad2.b)
            {
              if (!b_press)
              {
                if (runState == -1)
                 runState = 0;
                else runState = -1;
              }
              b_press = true;
            }
            else b_press = false;

            robot.collector.setPower(power*(double)runState);

           if (gamepad2.dpad_up)
           {
             if (!dpadUp)
             {
                 robot.collector.grabStone();
                 dpadUp = true;
             }
           }
           else dpadUp = false;



        }

    }
}
