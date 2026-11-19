package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

@TeleOp(name = "ConnyRed")
public class ConnyRed extends OpMode {
    private static final int[] TARGET_IDS = {30, 31, 34, 35};

    private boolean isTarget(int id) {
        for (int t : TARGET_IDS) {
            if (id == t) return true;
        }
        return false;
    }

    private DcMotor frontLeft, frontRight, backRight, backLeft, flyWheel, intake;
    private CRServo feeder;
    private Servo extractor;
    private Limelight3A limelight;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flyWheel = hardwareMap.get(DcMotor.class, "flyWheel");
        feeder = hardwareMap.get(CRServo.class, "feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");
        extractor = hardwareMap.get(Servo.class, "extractor");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");

        limelight.setPollRateHz(100);
        limelight.start();
    }

    @Override
    public void start() {
        flyWheel.setPower(0.43);
    }

    @Override
    public void loop() {
        // Limelight
        double tx = 0;
        boolean seesTarget = false;
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                if (isTarget(tag.getFiducialId())) {
                    tx = tag.getTargetXDegrees();
                    seesTarget = true;
                    break;
                }
            }
        }

        // Driver
        double ly = -gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1.0);

        if (!gamepad1.b) {
            frontRight.setPower((ly - rx - lx) / denominator);
            backRight.setPower((ly - rx + lx) / denominator);
            frontLeft.setPower((ly + rx + lx) / denominator);
            backLeft.setPower((ly + rx - lx) / denominator);
        }

        if (gamepad1.y) {
            flyWheel.setPower(0.45);
        } else {
            flyWheel.setPower(0.43);
        }

        // Auto-aim
        if (gamepad1.b) {
            double turn = 0;
            if (seesTarget) {
                double error = tx + 0.5;
                if (Math.abs(error) > 1.0) {
                    turn = error * 0.02;
                    turn = Math.max(-0.25, Math.min(0.25, turn));
                }
            }
            frontRight.setPower(-turn);
            backRight.setPower(-turn);
            frontLeft.setPower(turn);
            backLeft.setPower(turn);
        }

        // Manipulator
        if (gamepad2.b) {
            feeder.setPower(1);
        } else {
            feeder.setPower(0);
        }

        if (gamepad2.right_trigger > 0.5) {
            intake.setPower(1);
        } else if (gamepad2.left_trigger > 0.5) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        flyWheel.setPower(0);
        feeder.setPower(0);
    }
}