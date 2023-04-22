// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PrintCommand;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private XboxController xboxController;
  private MecanumDrive mecanumDrive;

  private WPI_TalonSRX frontLeftMotor;
  private WPI_TalonSRX backLeftMotor;
  private WPI_TalonSRX frontRightMotor;
  private WPI_TalonSRX backRightMotor;

  private final double deadzone = 0.35;

  private final AHRS navx = new AHRS(SPI.Port.kMXP);

  public static final double LY_DEADBAND = 0.10; // left stick, y-axis
  public static final double LX_DEADBAND = 0.10; // left stick, y-axis
  public static final double RX_DEADBAND = 0.10; // right stick, x-axis
  public static final double RY_DEADBAND = 0.10; // right stick, y-axis
  

  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    navx.reset();

    frontLeftMotor = new WPI_TalonSRX(0);
    backLeftMotor = new WPI_TalonSRX(2);
    frontRightMotor = new WPI_TalonSRX(3);
    backRightMotor = new WPI_TalonSRX(1);

    //frontLeftMotor.setInverted(true);
    //frontRightMotor.setInverted(true);

    MotorControllerGroup leftMotors = new MotorControllerGroup(frontLeftMotor, backLeftMotor);
    MotorControllerGroup rightMotors = new MotorControllerGroup(frontRightMotor, backRightMotor);

    mecanumDrive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

    xboxController = new XboxController(0);

    
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double xAxis = xboxController.getLeftX();
    double yAxis = xboxController.getLeftY();
    double zRotation = xboxController.getRightX();
    
    

    
    //double heading = navx.getYaw();
    //double cosA = Math.cos(Math.toRadians(heading));
    //double sinA = Math.sin(Math.toRadians(heading));
    //double temp = yAxis * cosA + xAxis * sinA;
    //xAxis = -yAxis * sinA + xAxis * cosA;
    //yAxis = temp;

    if (Math.abs(yAxis) < deadzone) {
      yAxis = 0;
    }

    if (Math.abs(xAxis) < deadzone) {
      xAxis = 0;
    }

    if (Math.abs(zRotation) < deadzone) {
      zRotation = 0;
    }

    mecanumDrive.driveCartesian(zRotation, xAxis, yAxis);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
