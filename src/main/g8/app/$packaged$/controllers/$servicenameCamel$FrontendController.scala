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
import play.api.mvc.Results.Redirect
import play.api.mvc._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.domain.Nino
import $package$.connectors.{FrontendAuthConnector, $servicenameCamel$ApiConnector}
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State._
import $package$.models.$servicenameCamel$Model
import $package$.services.$servicenameCamel$FrontendJourneyServiceWithHeaderCarrier
import $package$.wiring.AppConfig
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.play.fsm.{JourneyController, JourneyIdSupport}

import scala.concurrent.ExecutionContext
import scala.util.Success

@Singleton
class $servicenameCamel$FrontendController @Inject() (
  appConfig: AppConfig,
  override val messagesApi: MessagesApi,
  $servicenamecamel$ApiConnector: $servicenameCamel$ApiConnector,
  val authConnector: FrontendAuthConnector,
  val env: Environment,
  override val journeyService: $servicenameCamel$FrontendJourneyServiceWithHeaderCarrier,
  controllerComponents: MessagesControllerComponents,
  startView: $package$.views.html.StartView
)(implicit val config: Configuration, ec: ExecutionContext)
    extends FrontendController(controllerComponents) with I18nSupport with AuthActions
    with JourneyController[HeaderCarrier] with JourneyIdSupport[HeaderCarrier] {

  import $servicenameCamel$FrontendController._
  import $package$.journeys.$servicenameCamel$FrontendJourneyModel._

  val AsStrideUser: WithAuthorised[String] = { implicit request =>
    authorisedWithStrideGroup(appConfig.authorisedStrideGroup)
  }

  val AsEnrolledUser: WithAuthorised[String] = { implicit request =>
    authorisedWithEnrolment(appConfig.authorisedServiceName, appConfig.authorisedIdentifierKey)
  }

  def toSubscriptionJourney(continueUrl: String): Result =
    Redirect(
      appConfig.subscriptionJourneyUrl,
      Map(
        "continue" -> Seq(continueUrl)
      )
    )

  // GET /
  val showStart: Action[AnyContent] =
    action { implicit request =>
      whenAuthorised(AsEnrolledUser)(Transitions.start)(display)
        .andThen {
          // reset navigation history
          case Success(_) => journeyService.cleanBreadcrumbs()
        }
    }

  /**
    * Function from the `State` to the `Call` (route),
    * used by play-fsm internally to create redirects.
    */
  override def getCallFor(state: State)(implicit request: Request[_]): Call = state match {
    case Start =>
      routes.$servicenameCamel$FrontendController.showStart()
  }

  import uk.gov.hmrc.play.fsm.OptionalFormOps._

  /**
    * Function from the `State` to the `Result`,
    * used by play-fsm internally to render the actual content.
    */
  override def renderState(state: State, breadcrumbs: List[State], formWithErrors: Option[Form[_]])(implicit
    request: Request[_]
  ): Result = state match {

    case Start =>
      Ok(startView())

  }

  override implicit def context(implicit rh: RequestHeader): HeaderCarrier =
    appendJourneyId(super.hc)

  override def amendContext(headerCarrier: HeaderCarrier)(key: String, value: String): HeaderCarrier =
    headerCarrier.withExtraHeaders(key -> value)
}

object $servicenameCamel$FrontendController {

  import FormFieldMappings._

  val $servicenameCamel$Form = Form[$servicenameCamel$Model](
    mapping(
      "nino" -> uppercaseNormalizedText
        .verifying(validNino())
        .transform(Nino.apply, (n: Nino) => n.toString),
      "givenName"   -> trimmedName.verifying(validName("givenName", 1)),
      "familyName"  -> trimmedName.verifying(validName("familyName", 2)),
      "dateOfBirth" -> dateOfBirthMapping
    )($servicenameCamel$Model.apply)($servicenameCamel$Model.unapply)
  )
}
