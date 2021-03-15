package at.uibk.dps.sc.core.interpreter;

import java.util.Iterator;
import java.util.Set;

import com.google.inject.Inject;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.enactables.local.Composite;
import at.uibk.dps.ee.enactables.local.calculation.FunctionFactoryLocal;
import at.uibk.dps.ee.enactables.serverless.FunctionFactoryServerless;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * The {@link ScheduleInterpreterUserMultiple} expects to get a schedule with
 * atleast one mapping.
 * 
 * @author Christian Chisté
 *
 */
public class ScheduleInterpreterUserMultiple extends ScheduleInterpreterUser {

  /**
   * Injection constructor.
   * 
   * @param localFunctionFactory the factory used for the creation of
   *        {@link EnactmentFunction} used for the local calculation.
   * @param functionFactorySl the factory creating the serverless functions
   */
  @Inject
  public ScheduleInterpreterUserMultiple(final FunctionFactoryLocal localFunctionFactory,
      final FunctionFactoryServerless functionFactorySl) {
    super(localFunctionFactory, functionFactorySl);
  }

  @Override
  protected EnactmentFunction interpretScheduleUser(final Task task,
      final Set<Mapping<Task, Resource>> scheduleModel) {
    Composite composite = new Composite();
    Iterator<Mapping<Task, Resource>> iterator = scheduleModel.iterator();
    while(iterator.hasNext())
      composite.addFunction(getFunctionForMapping(iterator.next()));
    return composite;
  }
}
