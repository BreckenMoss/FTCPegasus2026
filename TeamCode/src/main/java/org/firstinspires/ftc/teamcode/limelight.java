package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "limelight", group = "TeleOp")

public class Limelight extends OpMode {
    private Limelight3A limelight;
    private DcMotor backRight;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        
        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop() {

            LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx();
            double ty = result.getTy();
        
            telemetry.addData("TX", tx);
            telemetry.addData("TY", ty);

            backRight.setPower(tx/100);
        } else {
            telemetry.addData("Limelight", "No Targets Visible");
            backRight.setPower(0);
        }
    }
