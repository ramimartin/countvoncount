import ar.com.rami.countvoncount._
import ar.com.rami.countvoncount.counter.TwitterCounter
import ar.com.rami.countvoncount.extractor.TwitterStatusExtractor
import ar.com.rami.countvoncount.statistics.WordCounter
import ar.com.rami.countvoncount.util.TwitterConfig
import com.typesafe.config.ConfigFactory
import org.scalatra._
import javax.servlet.ServletContext

import twitter4j.TwitterFactory

class ScalatraBootstrap extends LifeCycle {
  val config = ConfigFactory.load().getConfig("twitter")
  val twitter = new TwitterFactory(TwitterConfig(config)).getInstance()

  override def init(context: ServletContext) {
    context.mount(new CVCController(new TwitterCounter(twitter, new TwitterStatusExtractor, new WordCounter)), "/countvoncount")
  }
}
