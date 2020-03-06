package org.firstinspires.ftc.teamcode.hardware;

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

     public void init() {
        runner = new Runner(
                hardwareMap.get(DcMotor.class, Config.left_back),
                hardwareMap.get(DcMotor.class, Config.left_front),
                hardwareMap.get(DcMotor.class, Config.right_back),
                hardwareMap.get(DcMotor.class, Config.right_front)
        );

        stone = new Stone(
                hardwareMap.get(Servo.class, Config.extend),
                hardwareMap.get(Servo.class, Config.rotate),
                hardwareMap.get(Servo.class, Config.grab)
        );

        lift = new Lift(
                hardwareMap.get(DcMotor.class, Config.liftRight),
                hardwareMap.get(DcMotor.class, Config.liftLeft)
        );

        collector = new Collector(
                hardwareMap.get(DcMotor.class, Config.rotLeft),
                hardwareMap.get(DcMotor.class, Config.rotRight)
        );
    }

    public Mugurel () {}

    public Mugurel (HardwareMap hm){
        hardwareMap = hm;
        init();
    }

    public Mugurel(HardwareMap hm, Telemetry t, LinearOpMode opmode) {
        hardwareMap = hm;
        telemetry = t;
        opMode = opmode;
        init();
    }

    public void setTelemetry (Telemetry _t) { telemetry = _t; }
    public void setOpmode(LinearOpMode _o) { opMode = _o; }
}
