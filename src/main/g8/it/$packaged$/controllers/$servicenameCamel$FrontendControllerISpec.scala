package $package$.controllers

import play.api.Application
import play.api.inject.bind
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HeaderCarrier
import $package$.models.$servicenameCamel$FrontendModel
import $package$.services.$servicenameCamel$FrontendJourneyServiceWithHeaderCarrier
import $package$.support.{AppISpec, InMemoryJourneyService, TestJourneyService}

import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$FrontendControllerISpec
    extends $servicenameCamel$FrontendControllerISpecSetup {

  import journey.model.State._

  "$servicenameCamel$FrontendController" when {

    "GET /" should {

      "display start page" in {
        val result = controller.showStart(fakeRequest)
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("start.title"))
        journey.get shouldBe Some((Start, Nil))
      }
    }

    "GET /questions" should {

      "redirect to questions page" in {
        journey.set(Questions(), Start :: Nil)
        val result = controller.showQuestions(FakeRequest())
        status(result) shouldBe 303
        redirectLocation(result) shouldBe Some("/$servicenameHyphen$")
      }

      "display questions page" in {
        journey.set(Questions(), Start :: Nil)
        val result = controller.showQuestions(fakeRequest)
        status(result) shouldBe 200
        checkHtmlResultWithBodyText(result, htmlEscapedMessage("questions.title"))
      }
    }

    "POST /questions" should {
      "redirect to confirmation page" in {
        journey.set(Questions(), Start :: Nil)
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
        journey.set(
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

class TestInMemory$servicenameCamel$FrontendJourneyService
    extends $servicenameCamel$FrontendJourneyServiceWithHeaderCarrier
    with InMemoryJourneyService[HeaderCarrier] with TestJourneyService[HeaderCarrier]

trait $servicenameCamel$FrontendControllerISpecSetup extends AppISpec {

  override def fakeApplication: Application =
    appBuilder
      .overrides(
        bind(classOf[$servicenameCamel$FrontendJourneyServiceWithHeaderCarrier])
          .to(classOf[TestInMemory$servicenameCamel$FrontendJourneyService]))
      .build()

  lazy val controller: $servicenameCamel$FrontendController =
    app.injector.instanceOf[$servicenameCamel$FrontendController]

  lazy val journey: TestInMemory$servicenameCamel$FrontendJourneyService =
    controller.journeyService
      .asInstanceOf[TestInMemory$servicenameCamel$FrontendJourneyService]

  def fakeRequest = FakeRequest().withSession(controller.journeyService.journeyKey -> "fooId")

}
