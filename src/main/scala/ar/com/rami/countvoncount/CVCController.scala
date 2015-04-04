package ar.com.rami.countvoncount

import ar.com.rami.countvoncount.counter.TwitterCounter
import ar.com.rami.countvoncount.model.TwitterUser
import com.typesafe.scalalogging.LazyLogging
import org.json4s.Formats
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import scalate.ScalateSupport
import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.JsonMethods._
import org.json4s._
import org.json4s.jackson.Serialization.{read, write}
import org.scalatra.json._


class CVCController(val counter: TwitterCounter) extends CountVonCountStack with JacksonJsonSupport with LazyLogging {

  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/") {
    contentType = "text/html"
    ssp("/main","title"->"title")
  }

  post("/count") {
    contentType = formats("json")
    val words = counter.count(parsedBody.extract[TwitterUser].nick)
    words
  }

  get("/:user") {
    contentType = "text/html"
    val words = write(counter.count(params("user")).get("words"))
    val mentions = write(counter.count(params("user")).get("mentions"))
    ssp("/landing", ("words" -> words),("mentions" -> mentions))
  }

}
