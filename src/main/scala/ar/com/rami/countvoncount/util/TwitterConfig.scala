package ar.com.rami.countvoncount.util

import com.typesafe.config.Config
import twitter4j.conf.Configuration

object TwitterConfig {

  def apply(configuration: Config): Configuration = {
    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(configuration.getString("consumerKey"))
      .setOAuthConsumerSecret(configuration.getString("consumerSecret"))
      .setOAuthAccessToken(configuration.getString("accessToken"))
      .setOAuthAccessTokenSecret(configuration.getString("accessTokenSecret"))
      .setRestBaseURL("https://api.twitter.com/1.1/")
      .setDebugEnabled(false)
      .build
  }
}


