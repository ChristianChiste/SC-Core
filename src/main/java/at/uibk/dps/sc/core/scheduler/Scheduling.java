package at.uibk.dps.sc.core.scheduler;

/**
 * Class used to store the information if either a static or dynamic scheduling variant was chosen 
 * 
 * @author Christian Chisté
 *
 */
public final class Scheduling {
  
  public static SchedulingOption schedulingOption;
  
  public enum SchedulingOption {
    Dynamic,
    Static
  }
}
