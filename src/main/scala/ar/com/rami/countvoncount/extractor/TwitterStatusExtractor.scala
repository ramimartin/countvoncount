package ar.com.rami.countvoncount.extractor

import com.typesafe.scalalogging.LazyLogging
import twitter4j.{Status, Paging, Twitter}
import scala.collection.JavaConversions._
import scalacache._
import memoization._
import scalacache.guava.GuavaCache
import scala.concurrent.duration._


class TwitterStatusExtractor extends LazyLogging {

  implicit val scalaCache = ScalaCache(GuavaCache())

  def extract(user: String, twitter: Twitter, includeRetweets: Boolean = false):List[String] = memoize(60 minutes) {
    //val tweetCount = twitter.showUser(user).getStatusesCount
    logger.info(s"Extracting $user's tweets")
    val tweetCount = 200

    val pagings = for {
      page <- List.range(1, (tweetCount / 100) + 1)
    } yield {
        new Paging(page, 100)
      }

    pagings.flatMap { paging =>
      twitter.getUserTimeline(user, paging) filter TwitterStatusExtractor.getFilter(includeRetweets) map { tuit =>
        tuit.getText
      }
    }
  }

}

object TwitterStatusExtractor {
  val excludeRetweetsFilter: (Status => Boolean) = {
    !_.isRetweet
  }
  val includeRetweetsFilter: (Status => Boolean) = { status =>
    true
  }

  def getFilter(includeRetweets: Boolean) = if (includeRetweets) includeRetweetsFilter else excludeRetweetsFilter
}