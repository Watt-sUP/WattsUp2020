package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Auto;

@Autonomous (name="Autonomous ", group="Linear Opmode")
//@Disabled
public class Autofield  extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Auto robot;
    private int ticks = 560*3;
    private double distance = 1000;

    @Override
    public void runOpMode()  {
       telemetry.addData("Status", "Initialized");
       robot = new Auto(hardwareMap, telemetry, this);

       while (!opModeIsActive()&&!isStopRequested()) { telemetry.addData("Status", "Waiting in Init"); telemetry.update(); }
        runtime.reset();


     robot.autonomous.moveForwardBackward(1000, Auto.AutonomusMoveType.FORWARD);
     robot.autonomous.moveForwardBackward(400, Auto.AutonomusMoveType.BACKWARD);
     robot.autonomous.moveLeftRight(2100, Auto.AutonomusMoveType.RIGHT);
     robot.autonomous.moveForwardBackward(220, Auto.AutonomusMoveType.FORWARD);
     robot.autonomous.grabTray();
     sleep(320);
     robot.autonomous.moveForwardBackward(855, Auto.AutonomusMoveType.BACKWARD);
     robot.autonomous.leaveTray();
     robot.autonomous.moveLeftRight(1200, Auto.AutonomusMoveType.LEFT);


        while (opModeIsActive()){

        }










    }
}
