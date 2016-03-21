package org.zazukoians.zz2h

import org.apache.clerezza.commons.rdf._
import org.apache.clerezza.rdf.utils.GraphNode
import org.osgi.service.component.annotations._
import javax.ws.rs._
import javax.ws.rs.core._
import org.apache.clerezza.rdf.core._
import org.apache.clerezza.rdf.scala.utils._
//import org.apache.clerezza.rdf.scala.utils.impl._
import Preamble._
import org.apache.clerezza.rdf.core.access.NoSuchEntityException
import org.apache.clerezza.rdf.core.access.TcManager
import org.apache.clerezza.rdf.core.serializedform.Parser
import org.apache.clerezza.rdf.core.serializedform.SupportedFormat
import org.apache.clerezza.rdf.ontologies._
import org.apache.clerezza.jaxrs.utils.TrailingSlash

@Component(service = Array(classOf[Object]), property = Array("javax.ws.rs=true"))
@Path("zz2h/matchers")
class Matchers {

  var tcManager: TcManager = null;
  
  var parser: Parser = null;

  @GET
  def getGraph(@Context uriInfo: UriInfo) =
    {
      TrailingSlash.enforceNotPresent(uriInfo)
      val graphUri = uriInfo.getRequestUri.toString.iri
      try {
        tcManager.getGraph(graphUri)
      } catch {
        case e: NoSuchEntityException => {
            synchronized {
              //only create graph if its still not there
              try {
                tcManager.getGraph(graphUri)
              } catch {
                case e: NoSuchEntityException => {
                    val in = getClass.getResourceAsStream("matchers.ttl");
                    val defaultGraph = parser.parse(in, SupportedFormat.TURTLE, (graphUri.getUnicodeString+"/").iri)
                    val newG = tcManager.createGraph(graphUri)
                    newG.addAll(defaultGraph)
                    newG
                }
              }
            }
        }
      }
    }

  @Reference(
    cardinality = ReferenceCardinality.MANDATORY,
    policy = ReferencePolicy.STATIC,
    unbind = "unsetTcManager"
  )
  def setTcManager(tcManager: TcManager) {
    this.tcManager = tcManager;
  }

  def unsetTcManager(tcManager: TcManager) {
    this.tcManager = null;
  }
  
  @Reference(
    cardinality = ReferenceCardinality.MANDATORY,
    policy = ReferencePolicy.STATIC,
    unbind = "unsetParser"
  )
  def setParser(parser: Parser) {
    this.parser = parser;
  }

  def unsetParser(parser: Parser) {
    this.parser = null;
  }
}
