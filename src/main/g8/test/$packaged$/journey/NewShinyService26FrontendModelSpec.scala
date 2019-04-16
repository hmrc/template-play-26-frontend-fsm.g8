package $package$.journey
import uk.gov.hmrc.http.HeaderCarrier
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{End, Start}
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.Transitions._
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.{State, Transition}
import $package$.journeys.$servicenameCamel$FrontendJourneyService
import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class NewShinyService26FrontendModelSpec extends UnitSpec with StateMatchers[State] {

  implicit val hc: HeaderCarrier = HeaderCarrier()

  case class given(initialState: State)
      extends $servicenameCamel$FrontendJourneyService with TestStorage[(State, List[State])] {
    await(save((initialState, Nil)))

    def when(transition: Transition): (State, List[State]) =
      await(super.apply(transition))
  }

  "NewShinyService26FrontendModel" when {
    "at state Start" should {
      "transition to End when Start submitted a form" in {
        given(Start) when submitStart("001.H")($servicenameCamel$FrontendModel("Henry", None, None, None)) should thenGo(
          End("Henry", None, None, None))
      }
    }
  }
}
