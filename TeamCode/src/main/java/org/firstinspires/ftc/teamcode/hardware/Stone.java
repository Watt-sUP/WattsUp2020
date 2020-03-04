package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class Stone {

   public Servo extendServo;
   public Servo grabServo;
   public Servo rotateServo;


    public Stone (Servo ext, Servo rot, Servo _grab){
        extendServo = ext;
        rotateServo = rot;
        grabServo = _grab;
    }

    /// Grabber
    public void grab() {
        grabServo.setPosition(1);
    }
    public void release() {
        grabServo.setPosition(0);
    }

    /// Extender
    public void extend() {
        extendServo.setPosition(1);
    }
    public void retract() {
        extendServo.setPosition(0);
    }

    /// Rotator
    public void rotate0() {
        rotateServo.setPosition(0);
    }
    public void rotate90() {
        rotateServo.setPosition(0.5);
    }
    public void rotate180() {
        rotateServo.setPosition(1);
    }
}
