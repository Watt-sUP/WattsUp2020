package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Mugurel;
import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.gamepad.Button;
import org.firstinspires.ftc.teamcode.gamepad.GamepadEx;

@TeleOp(name="Driver Controled" , group="Linear Opmode")
//@Disabled
public class DriverControled extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Mugurel robot;
    public int collectorState = 0;
    public boolean inReset = false;

    private GamepadEx gaju, duta;

    @Override
    public void runOpMode()  {

        robot = new Mugurel(hardwareMap);
        gaju = new GamepadEx(gamepad1);
        duta = new GamepadEx(gamepad2);

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

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){
            gaju.update();
            duta.update();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
         //   telemetry.update();

            setFace(gaju.y, gaju.a, gaju.x, gaju.b);
            move(gaju.left_x, gaju.left_y, gaju.right_x, gaju.left_trigger.toButton(0.3), gaju.right_trigger.toButton(0.3), gaju.dpad_left, gaju.dpad_right);

            setCollector(duta.x, duta.b);
            moveLift(duta.left_y, duta.dpad_up, duta.dpad_down, duta.left_trigger.toButton(0.3));
            stoneHandler(duta.right_bumper, duta.left_bumper, duta.right_trigger.toButton(0.3), duta.a, duta.y);

            telemetry.addData("Servo Position", robot.stone.extendServo.getPosition());
            telemetry.addData("Mode", robot.lift.left.getMode());
            telemetry.addData("liftRight", robot.lift.right.getCurrentPosition());
            telemetry.addData("liftLeft", robot.lift.left.getCurrentPosition());
            telemetry.addData("left_y", duta.left_y.raw);
            telemetry.addData("isBusy", robot.lift.left.isBusy());
            telemetry.update();
        }

    }

    private void setFace(Button front, Button back, Button left, Button right) {
        if (front != null && front.pressed()) robot.runner.setFace(Math.PI);
        else if (back != null && back.pressed()) robot.runner.setFace(0);
        else if (left != null && left.pressed()) robot.runner.setFace(-Math.PI / 2.0);
        else if (right != null && right.pressed()) robot.runner.setFace( Math.PI / 2.0);
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower, Button dl, Button dr) {
        double modifier = 1.0;
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
        final double power = 0.8;
        if (in != null && in.pressed()) {
            if(collectorState == 0)   collectorState = 1;
            else    collectorState = 0;
        }

        if (out != null && out.pressed()) {
            if (collectorState == -1)   collectorState = 0;
            else   collectorState = -1;
        }
        robot.collector.setPower(power * collectorState);
    }

    private void moveLift (Axis manual, Button levelUp, Button levelDown, Button stop) {
        if (manual != null && Math.abs(manual.raw) > 0.1)
           robot.lift.setManualPower(-manual.raw);
        else
            robot.lift.hold();

         if (levelUp != null && levelUp.pressed())
            robot.lift.levelUp();
        else if (levelDown != null && levelDown.pressed())
            robot.lift.levelDown();
        else if (stop != null && stop.pressed())
            robot.lift.stop();

    }
    private void stoneHandler(Button extend, Button retract, Button reset, Button grab, Button release) {
        if(extend != null && extend.pressed())
            robot.stone.extend();
        else if(retract != null && retract.pressed())
            robot.stone.retract();

        if(grab != null && grab.pressed()) {
            robot.stone.grab();
            collectorState = 0;
            robot.collector.setPower(0);
        } else if(release != null && release.pressed()) {
            robot.stone.release();
            collectorState = 1;
            robot.collector.setPower(1.0);
        }

        if(reset != null && reset.pressed()) {
            inReset = true;
            robot.lift.levelUp();
        }

        if(inReset && !robot.lift.isBusy()) {
            inReset = false;
            robot.stone.retract();
            robot.lift.goDown();
        }
    }

    // TODO: add rotate servo buttons and buttons for grabbing foundation ( + ruleta :) )
    // TODO: maybe automate stone releasing
}
