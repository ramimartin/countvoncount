package ar.com.rami.countvoncount.counter

import ar.com.rami.countvoncount.extractor.TwitterStatusExtractor
import ar.com.rami.countvoncount.statistics.WordCounter
import twitter4j.Twitter

class TwitterCounter(twitter: Twitter, extractor: TwitterStatusExtractor, wordCounter: WordCounter) {

  import TwitterCounter._

  def count(nick: String) = {
    def tweets = extractor.extract(nick, twitter, true)
    def wordOccurrences = wordCounter.count(tweets).filter {
      word => word.weight >= minOccurrences && !word.text.contains(nick.toLowerCase())
    }
    wordOccurrences.groupBy(_.text.startsWith("@")).map {
      case (true, list) => ("mentions", list)
      case (false, list) => ("words", list)
    }
  }

}

object TwitterCounter {
  val minOccurrences = 2
}
