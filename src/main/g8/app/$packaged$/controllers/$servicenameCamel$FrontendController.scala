/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
  $servicenameCamel$Connector: $servicenameCamel$Connector,
  val authConnector: FrontendAuthConnector,
  val env: Environment,
  input: Input,
  form: FormWithCSRF,
  errorSummary: ErrorSummary,
  mainTemplate: main_template,
  override val journeyService: $servicenameCamel$FrontendJourneyService,
  controllerComponents: MessagesControllerComponents)(
  implicit val configuration: Configuration,
  ec: ExecutionContext)
    extends FrontendController(controllerComponents) with I18nSupport with AuthActions
    with JourneyController[HeaderCarrier] with JourneyIdSupport[HeaderCarrier] {

  import $servicenameCamel$FrontendController._
  import $package$.journeys.$servicenameCamel$FrontendJourneyModel._

  val AsHuman: WithAuthorised[String] = { implicit request =>
    withAuthorisedAsHuman(_)
  }

  val showStart = actionShowStateWhenAuthorised(AsHuman) {
    case Start =>
  }

  val submitStart = action { implicit request =>
    whenAuthorisedWithForm(AsHuman)($servicenameCamel$FrontendForm)(Transitions.submitStart)
  }

  val showEnd = action { implicit request =>
    showStateWhenAuthorised(AsHuman) {
      case _: End =>
    }.andThen {
      case Success(_) => journeyService.cleanBreadcrumbs()
    }
  }

  override def getCallFor(state: State)(implicit request: Request[_]): Call = state match {
    case Start  => routes.$servicenameCamel$FrontendController.showStart()
    case _: End => routes.$servicenameCamel$FrontendController.showEnd()
  }

  override def renderState(state: State, breadcrumbs: List[State], formWithErrors: Option[Form[_]])(
    implicit request: Request[_]): Result = state match {
    case Start =>
      Ok(new start_page(mainTemplate, input, form, errorSummary)($servicenameCamel$FrontendForm))
    case _: End => Ok(new end(mainTemplate)($servicenameCamel$FrontendForm))
  }

  override implicit def context(implicit rh: RequestHeader): HeaderCarrier =
    appendJourneyId(super.hc)

  override def amendContext(
    headerCarrier: HeaderCarrier)(key: String, value: String): HeaderCarrier =
    headerCarrier.withExtraHeaders(key -> value)
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
