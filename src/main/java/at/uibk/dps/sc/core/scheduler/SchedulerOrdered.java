package at.uibk.dps.sc.core.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import at.uibk.dps.ee.model.graph.SpecificationProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceResource;
import at.uibk.dps.ee.model.properties.PropertyServiceResource.ResourceType;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
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

  private static int index = 0;

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
      final Set<Mapping<Task, Resource>> mappingOptions){
    List<Mapping<Task, Resource>> mappingList = new ArrayList<>();
    Iterator<Mapping<Task, Resource>> iterator = mappingOptions.iterator();
    while(iterator.hasNext()) {
    	Mapping<Task, Resource> mapping = iterator.next();
    	int rank = Integer.valueOf(PropertyServiceResource.getRank(mapping.getTarget()).toString());
    	if(rank == index)
    		mappingList.add(mapping);
    }
    return new HashSet<>(mappingList);
  }
  
}
