package ar.com.rami.countvoncount.statistics

import ar.com.rami.countvoncount.model.WordMetadata
import com.typesafe.scalalogging.LazyLogging

class WordCounter extends LazyLogging {


  def count(tweets: List[String]) = {
    val everyWord = WordFilter(tweets.flatMap(_.toLowerCase().split(" ")))
    logger.info(s"Processing ${everyWord.length} words in ${tweets.length} tweets")
    val wordsGroups = everyWord.groupBy { word => WordNormalizer(word) }
    wordsGroups.map(p => WordMetadata(p._1, p._2.size))
  }

}

object WordNormalizer {
  def apply(word: String): String = {
    word.replace(".", "").replace(",", "").replace(":", "").replace(";", "").replace("\"", "").replace("?", "")
      .replace("'", "").replace("!", "").replace("¿", "").replace("¡", "").replace("(", "").replace(")", "")

  }
}

object WordFilter {

  val exclusions: List[String]= List("de","rt","el","que","a","la","el","y","en","no","un","los","es","con","se","lo",
    "es","del","para","por","me","al","una","si","las","le","","te","más","q","pero","como","mi")


  def apply(words: List[String]): List[String] = {
    words.filter {
      !exclusions.contains(_)
    }
  }
}
