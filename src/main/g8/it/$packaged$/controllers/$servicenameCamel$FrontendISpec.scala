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
import $package$.stubs.{JourneyTestData, $servicenameCamel$Stubs}
import $package$.support.{ServerISpec, TestJourneyService}

import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$FrontendISpec
    extends $servicenameCamel$FrontendISpecSetup with $servicenameCamel$Stubs with JourneyTestData {

  import journey.model.State._

  "$servicenameCamel$Frontend" when {

    "GET /$serviceUrlPrefixHyphen$/" should {
      "show the start page" in {
        implicit val journeyId: JourneyId = JourneyId()
        givenAuthorisedForEnrolment(Enrolment("HMRC-XYZ", "EORINumber", "foo"))

        val result = await(request("/").get())

        result.status shouldBe 200
        result.body should include(htmlEscapedMessage("view.start.title"))
        journey.getState shouldBe Start
      }
    }

    "GET /$serviceUrlPrefixHyphen$/foo" should {
      "return an error page not found" in {
        implicit val journeyId: JourneyId = JourneyId()
        givenAuthorisedForEnrolment(Enrolment("HMRC-XYZ", "EORINumber", "foo"))

        val result = await(request("/foo").get())

        result.status shouldBe 404
        result.body should include("This page can’t be found")
        journey.get shouldBe None
      }
    }
  }

}

trait $servicenameCamel$FrontendISpecSetup extends ServerISpec {

  override def fakeApplication: Application = appBuilder.build()

  lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]
  lazy val sessionCookieBaker: SessionCookieBaker = app.injector.instanceOf[SessionCookieBaker]

  case class JourneyId(value: String = UUID.randomUUID().toString)

  // define test service capable of manipulating journey state
  lazy val journey = new TestJourneyService[JourneyId]
    with $servicenameCamel$FrontendJourneyService[JourneyId] with MongoDBCachedJourneyService[JourneyId] {

    override lazy val cacheMongoRepository = app.injector.instanceOf[CacheMongoRepository]
    override lazy val applicationCrypto = app.injector.instanceOf[ApplicationCrypto]

    override val stateFormats: Format[model.State] =
      $servicenameCamel$FrontendJourneyStateFormats.formats

    override def getJourneyId(journeyId: JourneyId): Option[String] = Some(journeyId.value)
  }

  val baseUrl: String = s"http://localhost:\$port/$serviceUrlPrefixHyphen$"

  def request(path: String)(implicit journeyId: JourneyId) =
    wsClient
      .url(s"\$baseUrl\$path")
      .withHttpHeaders(
        play.api.http.HeaderNames.COOKIE -> Cookies.encodeCookieHeader(
          Seq(
            sessionCookieBaker.encodeAsCookie(Session(Map(journey.journeyKey -> journeyId.value)))
          )
        )
      )

}
