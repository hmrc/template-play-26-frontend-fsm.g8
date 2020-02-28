package $package$.controllers

import java.util.UUID

import play.api.Application
import play.api.libs.json.Format
import play.api.libs.ws.WSClient
import play.api.mvc.{Cookies, Session, SessionCookieBaker}
import uk.gov.hmrc.cache.repository.CacheMongoRepository
import uk.gov.hmrc.crypto.ApplicationCrypto
import $package$.journeys.$servicenameCamel$FrontendJourneyStateFormats
import $package$.services.{MongoDBCachedJourneyService, $servicenameCamel$FrontendJourneyService}
import $package$.support.{ServerISpec, TestJourneyService}
import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$FrontendISpec extends $servicenameCamel$FrontendISpecSetup {

  import journey.model.State._

  "$servicenameCamel$FrontendController" when {

    "GET /" should {

      "display the start page" in {
        implicit val journeyId: JourneyId = JourneyId()

        val result = await(request("/").get())

        result.status shouldBe 200
        result.body should include(htmlEscapedMessage("start.title"))
        journey.getState shouldBe Start
      }
    }
  }

}

trait $servicenameCamel$FrontendISpecSetup extends ServerISpec {

  val baseUrl: String = s"http://localhost:\$port/$servicenameHyphen$"

  override def fakeApplication: Application = appBuilder.build()

  // define test service capable of manipulating journey state
  lazy val journey = new TestJourneyService[JourneyId]
  with $servicenameCamel$FrontendJourneyService[JourneyId]
  with MongoDBCachedJourneyService[JourneyId] {

    override lazy val cacheMongoRepository = app.injector.instanceOf[CacheMongoRepository]
    override lazy val applicationCrypto = app.injector.instanceOf[ApplicationCrypto]

    override val stateFormats: Format[model.State] =
      $servicenameCamel$FrontendJourneyStateFormats.formats

    override def getJourneyId(journeyId: JourneyId): Option[String] = Some(journeyId.value)
  }

  def request(path: String)(implicit journeyId: JourneyId) =
    wsClient
      .url(s"\$baseUrl\$path")
      .withHttpHeaders(
        play.api.http.HeaderNames.COOKIE -> Cookies.encodeCookieHeader(
          Seq(
            sessionCookieBaker.encodeAsCookie(Session(Map(journey.journeyKey -> journeyId.value)))
          )))

}
