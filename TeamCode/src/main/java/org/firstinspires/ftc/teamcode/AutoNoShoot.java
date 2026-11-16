package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name = "AutoNoShoot")

public class AutoNoShoot extends LinearOpMode {
    
    private DcMotor frontLeft, frontRight, backRight, backLeft, flyWheel, intake;
    private CRServo feeder;
    private Servo extractor;

    public void runOpMode() {
        waitForStart();
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flyWheel = hardwareMap.get(DcMotor.class, "flyWheel");
        feeder = hardwareMap.get(CRServo.class, "feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");
        extractor = hardwareMap.get(Servo.class, "extractor");
        
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(1);
        backLeft.setPower(1);
        sleep(750);
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }
}