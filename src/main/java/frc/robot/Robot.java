package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//for teleop mode
import edu.wpi.first.wpilibj.XboxController;




// Auto
import edu.wpi.first.wpilibj.xrp.XRPMotor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.xrp.XRPServo;
//Teleop
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // My private info
  private final XRPMotor leftDrive = new XRPMotor(0); 
  private final XRPMotor rightDrive = new XRPMotor(1);
  private final DifferentialDrive mDrive = new DifferentialDrive(leftDrive, rightDrive);
  private final Timer mTimer = new Timer();
  private final XRPServo mServo = new XRPServo(4);

  private final XboxController  mController = new XboxController(0);

  //This variable controls the max speed of the robot
  double driveSpeed = 1;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    rightDrive.setInverted(true);
  }

  @Override
  public void robotPeriodic() {}

  
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    mTimer.start();
    mTimer.reset();
  }


  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
      if(mTimer.get()>0){
        mServo.setPosition(0.25);
      }
      if(mTimer.get() < 8){
          // Step 1 - Drive forward 45 inch
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 9.7){
         // Step 2 - 90* Turn
          mDrive.tankDrive(-0.6,0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 10.6){
          //Step 4 - Go to middle
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 11.9){
          //Step 6 - Turn 90*
          mDrive.tankDrive(-0.6,0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 23){
          //Step 7 - Cross pass
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 23.2){
          //Step 8 - Turn 90*
          mDrive.tankDrive(0.6,-0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 26){
         //Step 9- Go to ramp 
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(-0.7);
        }
        else if(mTimer.get() < 26){
          //Step 10 - Stop
          mDrive.tankDrive(0, 0);
          mServo.setPosition(0.7);
        }
          else if(mTimer.get() < 27){
            //Step 11 - Get on  at bottom of ramp
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(0.7);
        }
         else if(mTimer.get() < 27.7){
          //Step 12 - Turn to face ramp
          mDrive.tankDrive(0.6,-0.6);
          mServo.setPosition(-0.7);
        }
         else if(mTimer.get() < 28.5){
          // Step 13 - On ramp
          mDrive.tankDrive(0.6, 0.6);
          mServo.setPosition(0.7);
        }
        
        else{
          //End
          mDrive.tankDrive(0, 0);
        }
        break;
      }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    if(mController.getRightBumper()){
      driveSpeed = 1;
    }
    else if(mController.getLeftBumper()){
      driveSpeed = 0.6;
    }
    //mDrive.tankDrive(-mController.getLeftY(), -mController.getRightY());  
    mDrive.arcadeDrive(-mController.getLeftY() * driveSpeed, -mController.getRightX() * driveSpeed);

    if(mController.getAButton() == true){
      mServo.setPosition(1);
    }
    else if(mController.getXButton()){
      mServo.setPosition(0.5);
    }
    else if(mController.getYButton()){
      mServo.setPosition(0.75);
    }
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
}
