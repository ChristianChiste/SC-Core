package at.uibk.dps.sc.core.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import at.uibk.dps.ee.model.graph.SpecificationProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * The {@link SchedulerOrdered} picks a mapping in a specified order.
 * 
 * @author Christian Chisté
 */
@Singleton
public class SchedulerOrdered extends SchedulerAbstract {

  /**
   * The injection constructor; Same as parent.
   * 
   * @param specProvider
   */
  @Inject
  public SchedulerOrdered(final SpecificationProvider specProvider) {
    super(specProvider);
  }

  @Override
  protected Set<Mapping<Task, Resource>> chooseMappingSubset(final Task task,
      final Set<Mapping<Task, Resource>> mappingOptions) {
    List<Mapping<Task, Resource>> mappingList = new ArrayList<>();
    mappingList.add(mappingOptions.stream().sorted(new MappingComparator()).collect(Collectors.toList()).get(0));
    return new HashSet<>(mappingList);
  }

  class MappingComparator implements Comparator<Mapping<Task, Resource>> {

    public int compare(Mapping<Task, Resource> m1, Mapping<Task, Resource> m2) {
      return PropertyServiceMapping.getRank(m1.getTarget()) - PropertyServiceMapping.getRank(m2.getTarget());
    }
  }
}
