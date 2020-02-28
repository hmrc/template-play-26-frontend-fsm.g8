package $package$.support

import java.util.UUID

import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.ws.WSClient
import play.api.mvc.SessionCookieBaker

abstract class ServerISpec extends BaseISpec with GuiceOneServerPerSuite {

  lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]
  lazy val sessionCookieBaker: SessionCookieBaker = app.injector.instanceOf[SessionCookieBaker]

  case class JourneyId(value: String = UUID.randomUUID().toString)

}
