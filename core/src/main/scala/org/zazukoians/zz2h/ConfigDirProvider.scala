package org.zazukoians.zz2h

import java.io.File
import org.wymiwyg.commons.util.dirbrowser.PathNode
import org.wymiwyg.commons.util.dirbrowser.PathNodeFactory
import org.wymiwyg.commons.util.dirbrowser.PathNameFilter
import org.wymiwyg.commons.util.dirbrowser.FilePathNode
import org.wymiwyg.commons.util.dirbrowser.MultiPathNode

object ConfigDirProvider {

  private val cwd = new File(System.getProperty("user.dir"));
  private val zz2hDir = {
    val zz2hProperty = System.getenv().get("ZZ2H");//System.getProperty("zz2h.dir");
    if (zz2hProperty == null) {
      new File(cwd, "zz2h/")
    } else {
      new File(zz2hProperty)
    }
  }
  
  def configDir = new File(zz2hDir, "config/");

  private def systemConfigDir = new File("/etc/fusepool-p3/");
  
  def configNode = {
    val nodes = new scala.collection.mutable.ListBuffer[PathNode]
    if (!configDir.exists()) {
      configDir.mkdirs();
    }
    val userConfigNode: PathNode =new FilePathNode(configDir);
    nodes += userConfigNode;
    /*if (systemConfigDir.exists()) {
      val systemConfigNode: PathNode =new FilePathNode(systemConfigDir);
      nodes += systemConfigNode;
    }*/
    new MultiPathNode(nodes.toList:_*);
  }
}