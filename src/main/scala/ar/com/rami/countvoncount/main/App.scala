package ar.com.rami.countvoncount.main

import ar.com.rami.countvoncount.extractor.TwitterStatusExtractor
import ar.com.rami.countvoncount.statistics.WordCounter
import ar.com.rami.countvoncount.util.TwitterConfig
import com.typesafe.config.ConfigFactory
import twitter4j._


object App {

  def main(args: Array[String]) {
    val config = ConfigFactory.load().getConfig("twitter")
    val twitter = new TwitterFactory(TwitterConfig(config)).getInstance()
    val tweets = new TwitterStatusExtractor().extract("FlavioAzzaro",twitter, true)
    val frequencyMap = new WordCounter().count(tweets)
    println(frequencyMap.toSeq.sortBy(_.weight))





  }

}
