package $package$.controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.http.HeaderCarrier
import $package$.connectors.{FrontendAuthConnector, $servicenameCamel$Connector}
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{End, Start}
import $package$.journeys.$servicenameCamel$FrontendJourneyService
import $package$.models.$servicenameCamel$FrontendModel
import $package$.views.html.{main_template, _}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import uk.gov.hmrc.play.fsm.{JourneyController, JourneyIdSupport}
import uk.gov.hmrc.play.views.html.helpers.{ErrorSummary, FormWithCSRF, Input}

import scala.concurrent.ExecutionContext
import scala.util.Success

@Singleton
class $servicenameCamel$FrontendController @Inject()(
  override val messagesApi: MessagesApi,
  newShinyService26Connector: $servicenameCamel$Connector,
  val authConnector: FrontendAuthConnector,
  val env: Environment,
  input: Input,
  form: FormWithCSRF,
  errorSummary: ErrorSummary,
  mainTemplate: main_template,
  override val journeyService: $servicenameCamel$FrontendJourneyService,
  controllerComponents: MessagesControllerComponents)(implicit val configuration: Configuration, ec: ExecutionContext)
    extends FrontendController(controllerComponents) with I18nSupport with AuthActions with JourneyController
    with JourneyIdSupport {

  import $servicenameCamel$FrontendController._
  import $package$.journeys.$servicenameCamel$FrontendJourneyModel._

  override implicit def hc(implicit rh: RequestHeader): HeaderCarrier =
    appendJourneyId(super.hc)

  val AsHuman: WithAuthorised[String] = { implicit request =>
    withAuthorisedAsHuman(_)
  }

  val showStart = showCurrentStateWhenAuthorised(AsHuman) {
    case Start =>
  }

  val submitStart = action { implicit request =>
    authorisedWithForm(AsHuman)($servicenameCamel$FrontendForm)(Transitions.submitStart)
  }

  val showEnd = action { implicit request =>
    whenAuthorised(AsHuman) {
      case _: End =>
    }(display)
      .andThen {
        case Success(_) => journeyService.clear
      }
  }

  override def getCallFor(state: State)(implicit request: Request[_]): Call = state match {
    case Start  => routes.$servicenameCamel$FrontendController.showStart()
    case _: End => routes.$servicenameCamel$FrontendController.showEnd()
  }

  override def renderState(state: State, breadcrumbs: List[State], formWithErrors: Option[Form[_]])(
    implicit request: Request[_]): Result = state match {
    case Start  => Ok(new start_page(mainTemplate, input, form, errorSummary)($servicenameCamel$FrontendForm))
    case _: End => Ok(new end(mainTemplate)($servicenameCamel$FrontendForm))
  }
}

object $servicenameCamel$FrontendController {

  import $package$.controllers.FieldMappings._

  val $servicenameCamel$FrontendForm = Form[$servicenameCamel$FrontendModel](
    mapping(
      "name"            -> validName,
      "postcode"        -> optional(postcode),
      "telephoneNumber" -> telephoneNumber,
      "emailAddress"    -> emailAddress)($servicenameCamel$FrontendModel.apply)(
      $servicenameCamel$FrontendModel.unapply))
}
