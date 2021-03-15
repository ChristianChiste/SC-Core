package at.uibk.dps.sc.core.scheduler;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.model.graph.SpecificationProvider;

import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

@Singleton
public class SchedulerAllOptions extends SchedulerAbstract {

  /**
   * The injection constructor; Same as parent.
   * 
   * @param specProvider
   */
  @Inject
  public SchedulerAllOptions(final SpecificationProvider specProvider) {
    super(specProvider);
  }

  @Override
  protected Set<Mapping<Task, Resource>> chooseMappingSubset(final Task task,
      final Set<Mapping<Task, Resource>> mappingOptions) {
    return mappingOptions;
  }
}
