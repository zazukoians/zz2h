package org.zazukoians.zz2h

import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import org.apache.clerezza.commons.rdf.IRI
import org.apache.clerezza.commons.rdf._
import org.apache.clerezza.rdf.utils.graphnodeprovider.GraphNodeProvider
import org.osgi.service.component.annotations._
import javax.ws.rs._
import javax.ws.rs.core._

//@Component(service = Array(classOf[Object]), property = Array("org.apache.clerezza.platform.typehandler=true"))
//@SupportedTypes(types = { Array("http://www.w3.org/2000/01/rdf-schema#Resource") }, prioritize = false)
@Component(service = Array(classOf[Object]), property = Array("javax.ws.rs=true"))
@Path("")
class RootResource {

  var gnp: GraphNodeProvider = null
  
  @Reference(
    cardinality = ReferenceCardinality.MANDATORY,
    policy = ReferencePolicy.STATIC,
    unbind = "unsetGraphNodeProvider"
  )
  def setGraphNodeProvider(gnp: GraphNodeProvider) {
    this.gnp = gnp;
  }

  def unsetGraphNodeProvider(gnp: GraphNodeProvider) {
    this.gnp = null;
  }
  @GET
  @Path("{path: .*}")
  def get(@Context uriInfo: UriInfo) : Response = {
    /*val static = ConfigDirProvider.publicNode.getSubPath(path); 
    if (static.exists) {
      throw new RuntimeException("hooray: "+path);
    }*/
    //set vary: Accept header
    Response.ok({
        val iri : IRI = new IRI(uriInfo.getAbsolutePath().toString());
        val result = gnp.getLocal(iri)
        result
    }).header("Vary", "Accept").build();
    
  }
  
}
