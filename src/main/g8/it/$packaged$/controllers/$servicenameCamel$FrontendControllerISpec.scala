package $package$.controllers

import play.api.Application
import play.api.inject.bind
import play.api.test.FakeRequest
import play.api.test.Helpers._
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{Start}
import $package$.models._
import $package$.services.$servicenameCamel$FrontendJourneyServiceWithHeaderCarrier
import $package$.stubs.{JourneyTestData, $servicenameCamel$Stubs}
import $package$.support.{AppISpec, InMemoryJourneyService, TestJourneyService}
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class $servicenameCamel$FrontendControllerISpec
    extends $servicenameCamel$FrontendControllerISpecSetup with $servicenameCamel$Stubs with JourneyStateHelpers {

  import journey.model.State._

  "$servicenameCamel$FrontendController" when {

    "GET /" should {

      "redirect to the start page" in {
        givenAuthorisedForEnrolment(Enrolment("HMRC-XYZ", "EORINumber", "foo"))
        val result = controller.showStart(fakeRequest)
        status(result) shouldBe 200
        journey.get shouldBe Some((Start, Nil))
      }
    }
  }

}

trait JourneyStateHelpers extends JourneyTestData {

  def journey: TestInMemory$servicenameCamel$FrontendJourneyService

}

class TestInMemory$servicenameCamel$FrontendJourneyService
    extends $servicenameCamel$FrontendJourneyServiceWithHeaderCarrier with InMemoryJourneyService[HeaderCarrier]
    with TestJourneyService[HeaderCarrier]

trait $servicenameCamel$FrontendControllerISpecSetup extends AppISpec {

  implicit val hc: HeaderCarrier = HeaderCarrier()

  override def fakeApplication: Application =
    appBuilder
      .overrides(
        bind(classOf[$servicenameCamel$FrontendJourneyServiceWithHeaderCarrier])
          .to(classOf[TestInMemory$servicenameCamel$FrontendJourneyService])
      )
      .build()

  lazy val controller: $servicenameCamel$FrontendController =
    app.injector.instanceOf[$servicenameCamel$FrontendController]

  lazy val journey: TestInMemory$servicenameCamel$FrontendJourneyService =
    controller.journeyService
      .asInstanceOf[TestInMemory$servicenameCamel$FrontendJourneyService]

  def fakeRequest = FakeRequest().withSession(controller.journeyService.journeyKey -> "fooId")

}
