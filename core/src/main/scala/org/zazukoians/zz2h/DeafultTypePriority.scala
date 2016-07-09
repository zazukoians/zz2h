package eu.fusepool.p3.entry

import org.apache.clerezza.commons.rdf.Graph
import org.apache.clerezza.commons.rdf.impl.utils.simple.SimpleGraph
import org.apache.clerezza.platform.typepriority.TypePrioritizer
import org.osgi.service.component.annotations.Component
//remove if not needed
import scala.collection.JavaConversions._

//The typerendeing mechanisms needs such a service
@Component(service = Array(classOf[TypePrioritizer]), immediate = true)
class DefalutTypePriority extends TypePrioritizer {

  bindSystemGraph(new SimpleGraph())

  protected override def unbindSystemGraph(arg0: Graph) {
  }
}
