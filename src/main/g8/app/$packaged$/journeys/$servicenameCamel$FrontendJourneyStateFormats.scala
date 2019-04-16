package $package$.journeys
import play.api.libs.json._
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{End, Start}
import uk.gov.hmrc.play.fsm.JsonStateFormats

object $servicenameCamel$FrontendJourneyStateFormats extends JsonStateFormats[State] {

  val EndFormat = Json.format[End]

  override val serializeStateProperties: PartialFunction[State, JsValue] = {
    case s: End => EndFormat.writes(s)
  }

  override def deserializeState(stateName: String, properties: JsValue): JsResult[State] =
    stateName match {
      case "Start" => JsSuccess(Start)
      case "End"   => EndFormat.reads(properties)
      case _       => JsError(s"Unknown state name \$stateName")
    }
}
