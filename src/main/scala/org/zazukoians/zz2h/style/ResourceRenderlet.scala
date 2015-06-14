package org.zazukoians.zz2h.style

import java.net.URLEncoder
import javax.ws.rs.core.MediaType
import org.apache.clerezza.platform.typerendering._
import org.apache.clerezza.rdf.scala.utils.RichGraphNode
import org.apache.clerezza.rdf.utils.GraphNode
import org.apache.clerezza.rdf.ontologies._
import org.apache.clerezza.rdf.core._
import org.apache.clerezza.rdf.utils._
import org.apache.clerezza.rdf.scala.utils.Preamble._
import org.apache.clerezza.platform.typerendering.scala._
import org.apache.clerezza.rdf.ontologies.DC
import org.apache.clerezza.commons.rdf.IRI
import org.osgi.service.component.annotations._

/**
 * A Renderlet for the menu
 */
@Component(service = Array(classOf[TypeRenderlet]))
class ResourceRenderlet extends SRenderlet {

  val getRdfType = RDFS.Resource

  override def getModePattern = "(?!.*naked)(?!.*menu).*"

  protected def defaultTitle(res: RichGraphNode) = "A Resource" + (res*)

  override def renderedPage(arguments: XmlResult.Arguments) = {
    new XmlResult(arguments) {
      def menuLink(href: String, label: String) =
        if ((res*).endsWith(href) || (res*).endsWith(href + "index")) {
          <a href={ href } class="active">{ label }</a>
        } else {
          <a href={ href }>{ label }</a>
        }
      override def content = {
        
        println("rendering "+(res*)+": "+mode);
        /*resultDocModifier.addStyleSheet("/style/style.css");
        resultDocModifier.addScriptReference("/jquery/jquery-1.3.2.min.js");
        resultDocModifier.addScriptReference("/scripts/modification-status.js");
        resultDocModifier.addScriptReference("/scripts/status-message.js");*/
        <html xmlns="http://www.w3.org/1999/xhtml" class="fetch" resource="">
          <head>
            <title>{"A Resource" + (res*)}</title>
                    <link rel="matchers" href="/rdf2h/matchers.ttl" type="text/turtle" />
        <script src="/js/ld2h/js/libs/rdf2h/rdf2h.js"></script>
        <script src="/js/ld2h/js/libs/jquery/jquery.min.js"></script>
        <script src="/js/ld2h/js/ld2h.js"></script>        
        <script type="text/javascript">
$(function () {{
   var store = new rdf.LdpStore({{parsers: {{
       //'application/ld+json': rdf.parseJsonLd,
       //'application/n-triples': rdf.parseTurtle,
       'text/turtle': rdf.parseTurtle
   }}}});
   LD2h.store = store;
   LD2h.expand();
}});
        </script>
    </head>
    <body>
This will be replaced by rendered RDF.
    </body>
        </html>
      }
    }
  }

}
