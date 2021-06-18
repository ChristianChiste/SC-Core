package at.uibk.dps.sc.core.modules;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;

import at.uibk.dps.ee.core.ExecutionData;
import at.uibk.dps.ee.guice.modules.EeModule;
import at.uibk.dps.sc.core.interpreter.ScheduleInterpreterUser;
import at.uibk.dps.sc.core.interpreter.ScheduleInterpreterUserMultiple;
import at.uibk.dps.sc.core.interpreter.ScheduleInterpreterUserSingle;
import at.uibk.dps.sc.core.scheduler.Scheduler;
import at.uibk.dps.sc.core.scheduler.SchedulerAllOptions;
import at.uibk.dps.sc.core.scheduler.SchedulerOrdered;
import at.uibk.dps.sc.core.scheduler.SchedulerRandom;
import at.uibk.dps.sc.core.scheduler.SchedulerSingleOption;
import at.uibk.dps.sc.core.scheduler.Scheduling;
import at.uibk.dps.sc.core.scheduler.Scheduling.SchedulingOption;

/**
 * The {@link SchedulerModule} configures the binding of the scheduling-related
 * interfaces.
 * 
 * @author Fedor Smirnov
 *
 */
public class SchedulerModule extends EeModule {

  public enum SchedulingType {
    /**
     * Execution on single scheduled resource
     */
    Dynamic,
    /**
     * Execution on single resource
     */
    SingleStatic,
    /**
     * Execution on every resource
     */
    RedundantStatic
  }

  /**
   * Enum defining different scheduling modes.
   * 
   * @author Fedor Smirnov
   */
  public enum SchedulingMode {
    /**
     * Expects a single scheduling option
     */
    SingleOption,
    /**
     * Expects all scheduling options
     */
    AllOptions,
    /**
     * Random scheduling
     */
    Random,
    /**
     * Ordered scheduling
     */
    Ordered
  }

  @Order(1)
  @Info("The type of scheduling for user tasks.")
  protected static SchedulingType schedulingType = SchedulingType.SingleStatic;

  @Order(2)
  @Info("The mode used to schedule user tasks.")
  @Required(property = "schedulingType", elements = "Dynamic")
  public SchedulingMode schedulingMode;

  @Order(3)
  @Info("The number of mappings to pick for each user task.")
  @Constant(namespace = SchedulerRandom.class, value = "mappingsToPick")
  @Required(property = "schedulingMode", elements = "Random")
  public int mappingsToPick = 1;

  @Override
  protected void config() {
    if (schedulingType.equals(SchedulingType.Dynamic)) {
      Scheduling.schedulingOption = SchedulingOption.Dynamic;
      bind(ScheduleInterpreterUser.class).to(ScheduleInterpreterUserSingle.class);
      if (schedulingMode.equals(SchedulingMode.Random)) {
        bind(Scheduler.class).to(SchedulerRandom.class);
      } else if (schedulingMode.equals(SchedulingMode.Ordered)) {
        bind(Scheduler.class).to(SchedulerOrdered.class);
      }
    } else {
      Scheduling.schedulingOption = SchedulingOption.Static;
      if (schedulingType.equals(SchedulingType.SingleStatic)) {
        bind(ScheduleInterpreterUser.class).to(ScheduleInterpreterUserSingle.class);
        bind(Scheduler.class).to(SchedulerSingleOption.class);
      } else if (schedulingType.equals(SchedulingType.RedundantStatic)) {
        bind(ScheduleInterpreterUser.class).to(ScheduleInterpreterUserMultiple.class);
        bind(Scheduler.class).to(SchedulerAllOptions.class);
      }
    }
  }

  public SchedulingType getSchedulingType() {
    return schedulingType;
  }

  public void setSchedulingType(final SchedulingType schedulingType) {
    this.schedulingType = schedulingType;
  }

  public SchedulingMode getSchedulingMode() {
    return schedulingMode;
  }

  public void setSchedulingMode(final SchedulingMode schedulingMode) {
    this.schedulingMode = schedulingMode;
  }

  public int getMappingsToPick() {
    return mappingsToPick;
  }

  public void setMappingsToPick(final int mappingsToPick) {
    this.mappingsToPick = mappingsToPick;
  }
}
