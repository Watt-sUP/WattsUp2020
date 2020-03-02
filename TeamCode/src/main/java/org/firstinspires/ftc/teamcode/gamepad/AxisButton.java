package org.firstinspires.ftc.teamcode.gamepad;

public class AxisButton extends Button {
    AxisButton(double _threshold) {
        threshold = _threshold;
    }
    private double threshold;

    public void update(double value) {
        update(value >= threshold);
    }
}
