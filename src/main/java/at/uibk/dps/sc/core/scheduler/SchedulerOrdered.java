package at.uibk.dps.sc.core.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.opt4j.core.start.Constant;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import at.uibk.dps.ee.model.graph.SpecificationProvider;
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

  protected int index = 0;

  /**
   * The injection constructor.
   * 
   * @param specProvider the specification provider
   * @param random the random number generator
   * @param mappingsToPick the number of mappings to pick
   */
  @Inject
  public SchedulerOrdered(final SpecificationProvider specProvider) {
    super(specProvider);
  }

  @Override
  protected Set<Mapping<Task, Resource>> chooseMappingSubset(final Task task,
      final Set<Mapping<Task, Resource>> mappingOptions) {
    final List<Mapping<Task, Resource>> mappingList = new ArrayList<>(mappingOptions);
    List<Mapping<Task, Resource>> mappingList2 = new ArrayList<>();
    for(Mapping<Task, Resource> mapping : mappingOptions) {
    	if(mapping.getTarget().getAttribute("rank")==String.valueOf(index))
    		mappingList2.add(mapping);
    }
    //mappingList2.add(mappingList.get(nextIndex(mappingList.size())).getTarget().getAttribute("Id"));
    return new HashSet<>(mappingList2);
  }
  
  protected int nextIndex(int size) {
	return 0; //index++ % size;
	  
  }
}
