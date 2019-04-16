package $package$.controllers

import play.api.Application
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HeaderCarrier
import $package$.support.BaseISpec

import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$FrontendControllerISpec extends BaseISpec {

  private lazy val controller: $servicenameCamel$FrontendController =
    app.injector.instanceOf[$servicenameCamel$FrontendController]

  implicit val hc: HeaderCarrier = HeaderCarrier()
  override implicit lazy val app: Application = appBuilder
    .overrides(new TestAgentInvitationJourneyModule)
    .build()

  lazy val journeyState = app.injector.instanceOf[Test$servicenameCamel$FrontendJourneyService]

  import journeyState.model.State._

  "NewShinyService26FrontendController" when {

    "GET /start" should {
      "display start page" in {
        journeyState.set(Start, Nil)
        val result = controller.showStart(FakeRequest())
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("start.title"))
      }
    }

    "POST /start" should {
      "redirect to end page" in {
        journeyState.set(Start, Nil)
        val result = controller.submitStart(
          FakeRequest().withFormUrlEncodedBody(
            "name"            -> "Henry",
            "postcode"        -> "",
            "telephoneNumber" -> "00000000001",
            "emailAddress"    -> "henry@example.com"))
        status(result) shouldBe 303
        redirectLocation(result) shouldBe Some(routes.$servicenameCamel$FrontendController.showEnd().url)
      }
    }

    "GET /end" should {
      "display start page" in {
        journeyState.set(End("name", Some("postcode"), Some("telephone"), Some("email")), Nil)
        val result = controller.showEnd(FakeRequest())
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("end.title"))
      }
    }
  }

}
