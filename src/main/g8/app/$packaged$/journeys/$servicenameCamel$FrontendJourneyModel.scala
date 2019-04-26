package $package$.journeys
import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.fsm.JourneyModel

object $servicenameCamel$FrontendJourneyModel extends JourneyModel {

  sealed trait State
  sealed trait IsError

  override val root: State = State.Start

  object State {
    case object Start extends State
    case class End(name: String, postcode: Option[String], telephone: Option[String], emailAddress: Option[String])
        extends State
    case object SomeError extends State with IsError
  }

  object Transitions {
    import State._

    def start = Transition {
      case _ => goto(Start)
    }

    def submitStart(humanId: String)(formData: $servicenameCamel$FrontendModel) = Transition {
      case Start => goto(End(formData.name, formData.postcode, formData.telephoneNumber, formData.emailAddress))
    }
  }

}
