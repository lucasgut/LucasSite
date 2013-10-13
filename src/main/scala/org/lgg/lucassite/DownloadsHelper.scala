package org.lgg.lucassite

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

import org.lgg.lucassite.model.download.Download

object DownloadsHelper {
    def sortedDownloadTitles(downloads: java.util.List[Download]): java.util.List[String] = 
	  seqAsJavaList(downloads.asScala.toList.map(d => d.getSubject).sorted)
}
