/* Copyright (c) 2017 FIRST. All rights reserved.
        *
        * Redistribution and use in source and binary forms, with or without modification,
        * are permitted (subject to the limitations in the disclaimer below) provided that
        * the following conditions are met:
        *
        * Redistributions of source code must retain the above copyright notice, this list
        * of conditions and the following disclaimer.
        *
        * Redistributions in binary form must reproduce the above copyright notice, this
        * list of conditions and the following disclaimer in the documentation and/or
        * other materials provided with the distribution.
        *
        * Neither the name of FIRST nor the names of its contributors may be used to endorse or
        * promote products derived from this software without specific prior written permission.
        *
        * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
        * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
        * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
        * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
        * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
        * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
        * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
        * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
        * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
        * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
        * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
        */

        package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.Magura;


@TeleOp(name="TeleOp", group="Linear Opmode")
@Disabled
public class OpMode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private Magura robot;

    public boolean x_press = false;
    private boolean a_press = false;
    private boolean y_press = false;
    private boolean b_press = false;
    public double rotPower = 0.0;

   // private double modifier = 1.0;
    private double driveSpeed = 1.0;

    private int Turn = 120;

    @Override
    public void runOpMode() {

       robot = new Magura(hardwareMap);

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()) {

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            double modifier = 1.0;
            if (gamepad1.right_trigger > 0.3) modifier = 0.5;
            if (gamepad1.left_trigger > 0.3)  modifier = 0.3;

            final double drive_y = robot.motors.scalePower(gamepad1.left_stick_y);
            final double drive_x = robot.motors.scalePower(gamepad1.left_stick_x);
            final double turn = robot.motors.scalePower(gamepad1.right_stick_x);

            robot.motors.moveWithAngle(drive_x, drive_y, turn, modifier);




            
           /* double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            if (gamepad1.a) {
                if (!a_press && modifier > 0.0)
                    modifier *= -1.0;
                a_press = true;
            } else
                a_press = false;

            if (gamepad1.y) {
                if (!y_press && modifier < 0.0)
                    modifier = Math.abs(modifier);
                y_press = true;
            } else
                y_press = false;


            drive *= modifier;

            if(gamepad1.right_trigger>0.3)
                driveSpeed=0.5;
            else
                driveSpeed=1.0;

            robot.motors.move(drive,turn,driveSpeed);

            if(robot.sensors.rotTouch.isPressed())
            {  robot.collector.stopRotation(); rotPower = 0.0;  }

            if (gamepad1.x){

              if(!x_press){
               // robot.collector.stopRotation();
                if(rotPower==0.0)
                {
                  rotPower=0.35;
                  robot.collector.addPower(rotPower);
                }
                else {
                    rotPower=0.0;
                    robot.collector.addPower(rotPower);
                }
                  x_press=true;
              }

            }
            else
                x_press=false;

             if (gamepad1.b)
             {
                if(!b_press)
                {
                  robot.collector.stopRotation();
                  robot.collector.addTicksWithPower(Turn,0.3);
                  b_press = true;
                }

             }
             else
                 b_press=false;

            if(robot.collector.isMotorStopped())
                robot.collector.stopRotation();

*/



        }
    }
}