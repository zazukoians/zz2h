package org.zazukoians.zz2h.style

import org.apache.clerezza.platform.typerendering._
import org.apache.clerezza.rdf.scala.utils.RichGraphNode
import org.apache.clerezza.rdf.ontologies._
import org.apache.clerezza.rdf.core._
import org.apache.clerezza.rdf.utils._
import org.apache.clerezza.rdf.scala.utils.Preamble._
import org.apache.clerezza.platform.typerendering.scala._
import org.osgi.service.component.annotations._

/**
 * Renders resources using ld2h on the client.
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
        <html xmlns="http://www.w3.org/1999/xhtml" class="fetch" resource="" context="http://zz2h.zazukoinas.org/modes/FullPage">
          <head>
            <title>{"A Resource" + (res*)}</title>
            <link rel="matchers" href="/zz2h/matchers" type="text/turtle" />
            <script src="//cdn.rawgit.com/rdf2h/rdf2h/v0.2.1/dist/rdf-ext.js"></script>
            <!-- <script src="//cdn.rawgit.com/retog/clownface-browser/v0.3.0-rc2/dist/clownface.js"></script> -->
            <script src="//cdn.rawgit.com/rdf2h/rdf2h/v0.3.0/dist/rdf2h.js"></script>
            <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
            <script src="//cdn.rawgit.com/retog/rdf-store-ldp-browser/v0.3.0-rc2f/dist/rdf-store-ldp.js"></script>
            <script src="//cdn.rawgit.com/rdf2h/ld2h/v0.2.0/dist/ld2h.js"></script>      
            <script type="text/javascript">
$(function () {{
   /*var store = new LdpStore({{parsers: {{
       //'application/ld+json': rdf.parseJsonLd,
       //'application/n-triples': rdf.parseTurtle,
       'text/turtle': rdf.parseTurtle
   }}}});
   LD2h.store = store;*/
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
