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
import org.apache.clerezza.rdf.ontologies._

@Component(service = Array(classOf[Object]), property = Array("javax.ws.rs=true"))
@Path("zz2h")
class EntryPage {

  val bblfishModulus = """
    9D ☮ 79 ☮ BF ☮ E2 ☮ F4 ☮ 98 ☮ BC ☮ 79 ☮ 6D ☮ AB ☮ 73 ☮ E2 ☮ 8B ☮ 39 ☮ 4D ☮ B5 26 ✜ 68 ✜ 49 ✜ EE ✜ 71 ✜ 87 ✜
    06 ✜ 32 ✜ C9 ✜ 9F ✜ 3F ✜ 94 ✜ E5 ✜ CB ✜ 4D ✜ B5 12 ☮ 35 ☮ 13 ☮ 69 ☮ 60 ☮ 81 ☮ 58 ☮ 79 ☮ 66 ☮ F3 ☮ 79 ☮ 20 ☮
    91 ☮ 6A ☮ 3F ☮ 42 5A ✜ F6 ✜ 54 ✜ 42 ✜ 88 ✜ B2 ✜ E9 ✜ 19 ✜ 4A ✜ 79 ✜ 87 ✜ 2E ✜ 62 ✜ 44 ✜ 2D ✜ 7C 06 ☽ 78 ☽ F8
    ☽ FD ☽ 52 ☽ 92 ☽ 6D ☽ CD ☽ D6 ☽ F3 ☽ 28 ☽ 6B ☽ 1F ☽ DB ☽ CB ☽ D3 F2 ☮ 08 ☮ 34 ☮ 72 ☮ A2 ☮ 12 ☮ 75 ☮ AE ☮ D1
    ☮ 09 ☮ 17 ☮ D0 ☮ 88 ☮ 4C ☮ 04 ☮ 8E 04 ☾ E5 ☾ BF ☾ D1 ☾ 41 ☾ 64 ☾ D1 ☾ F7 ☾ 89 ☾ 6D ☾ 8B ☾ B2 ☾ F2 ☾ 46 ☾ C0
    ☾ 56 87 ☮ 8D ☮ B8 ☮ 7C ☮ C6 ☮ FE ☮ E9 ☮ 61 ☮ 88 ☮ 08 ☮ 61 ☮ DD ☮ E3 ☮ B8 ☮ B5 ☮ 47 ♥
    """

  /**import some references in order to reduce dependencies */

  final val hex: IRI = new IRI("http://www.w3.org/ns/auth/cert#hex")
  final val identity: IRI = new IRI("http://www.w3.org/ns/auth/cert#identity")
  final val RSAPublicKey: IRI = new IRI("http://www.w3.org/ns/auth/rsa#RSAPublicKey")
  final val modulus: IRI = new IRI("http://www.w3.org/ns/auth/rsa#modulus")
  final val public_exponent: IRI = new IRI("http://www.w3.org/ns/auth/rsa#public_exponent")

  val henryUri: String = "http://bblfish.net/#hjs"
  val retoUri: String = "http://farewellutopia.com/reto/#me"
  val danbriUri: String = "http://danbri.org/foaf.rdf#danbri"

  @GET
  def hello(@Context uriInfo: UriInfo) =
    {
      val resource = uriInfo.getRequestUri().toString().iri;
      val g = new EzGraph() {
        (
          resource.a(FOAF.Person) -- FOAF.name --> "Reto Gmür".lang("rm")
          -- FOAF.title --> "Mr"
          -- FOAF.currentProject --> "http://clerezza.org/".iri
          -- FOAF.knows --> (
            "http://bblfish.net/#hjs".iri.a(FOAF.Person)
            -- FOAF.name --> "Henry Story"
            -- FOAF.currentProject --> "http://webid.info/".iri
            -- FOAF.knows -->> List(b_("reto"), b_("danny"))
            //one need to list properties before inverse properties, or use brackets
            <-- identity -- (
              bnode.a(RSAPublicKey) //. notation because of precedence of operators
              -- modulus --> 65537
              -- public_exponent --> (bblfishModulus ^^ hex) // brackets needed due to precedence
            )
          )
              -- FOAF.knows --> (
                b_("danny").a(FOAF.Person)
                -- FOAF.name --> "Danny Ayers".lang("en")
                -- FOAF.knows --> "http://bblfish.net/#hjs".iri //knows
                -- FOAF.knows --> b_("reto")
              )
        )
      }
      new GraphNode(resource,g)
    }

  //"hello "+uriInfo.getRequestUri().toString();
}
