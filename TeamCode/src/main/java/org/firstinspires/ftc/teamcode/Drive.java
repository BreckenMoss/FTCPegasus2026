package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import java.text.SimpleDateFormat;
import java.util.Date;

@TeleOp(name = "Drive")

public class Drive extends OpMode {
    
    private DcMotor frontLeft, frontRight, backRight, backLeft, flyWheel;
    private CRServo feeder;
    
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flyWheel = hardwareMap.get(DcMotor.class, "flyWheel");
        feeder     = hardwareMap.get(CRServo.class, "feeder");
        
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
    }

    @Override

    public void start() {
        flyWheel.setPower(0.6);
    }

    public void loop() {
        telemetry.update();

        double ly = -gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1.0);

        frontRight.setPower((ly - rx + lx) / denominator);
        backRight.setPower((ly - rx - lx) / denominator);
        frontLeft.setPower((ly + rx + lx) / denominator);
        backLeft.setPower((ly + rx - lx) / denominator);

        if (gamepad1.right_trigger > 0.5) {
            feeder.setPower(0.6);
        } else {
            feeder.setPower(0);
        }
    }
    @Override
    public void stop() {
        flyWheel.setPower(0);
        feeder.setPower(0);
    }
}