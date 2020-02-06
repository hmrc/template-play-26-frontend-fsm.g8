package $package$.controllers

import play.api.Application
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HeaderCarrier
import $package$.models.$servicenameCamel$FrontendModel
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

  def fakeRequest = FakeRequest().withSession(controller.journeyService.journeyKey -> "fooId")

  "$servicenameCamel$FrontendController" when {

    "GET /" should {

      "display start page" in {
        val result = controller.showStart(fakeRequest)
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("start.title"))
        journeyState.get shouldBe Some((Start, Nil))
      }
    }

    "GET /questions" should {

      "redirect to questions page" in {
        journeyState.set(Questions(), Start :: Nil)
        val result = controller.showQuestions(FakeRequest())
        status(result) shouldBe 303
        redirectLocation(result) shouldBe Some("/$servicenameHyphen$")
      }

      "display questions page" in {
        journeyState.set(Questions(), Start :: Nil)
        val result = controller.showQuestions(fakeRequest)
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("questions.title"))
      }
    }

    "POST /questions" should {
      "redirect to confirmation page" in {
        journeyState.set(Questions(), Start :: Nil)
        val result = controller.submitQuestions(
          fakeRequest.withFormUrlEncodedBody(
            "name"            -> "Henry",
            "postcode"        -> "",
            "telephoneNumber" -> "00000000001",
            "emailAddress"    -> "henry@example.com"))
        status(result) shouldBe 303
        redirectLocation(result) shouldBe Some(
          routes.$servicenameCamel$FrontendController.showConfirmation().url)
      }
    }

    "GET /confirmation" should {
      "display confirmation page" in {
        journeyState.set(
          Confirmation(
            $servicenameCamel$FrontendModel(
              "name",
              Some("postcode"),
              Some("telephone"),
              Some("email"))),
          Questions() :: Start :: Nil)
        val result = controller.showConfirmation(fakeRequest)
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("confirmation.title"))
      }
    }
  }

}
