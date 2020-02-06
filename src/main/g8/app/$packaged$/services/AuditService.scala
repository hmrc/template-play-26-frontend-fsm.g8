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

package $package$.services

import com.google.inject.Singleton
import javax.inject.Inject
import play.api.mvc.Request
import uk.gov.hmrc.agentmtdidentifiers.model.Arn
import uk.gov.hmrc.http.HeaderCarrier
import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.audit.AuditExtensions._
import uk.gov.hmrc.play.audit.http.connector.AuditConnector
import uk.gov.hmrc.play.audit.model.DataEvent

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object $servicenameCamel$FrontendEvent extends Enumeration {
  val $servicenameCamel$FrontendSomethingHappened = Value
  type $servicenameCamel$FrontendEvent = Value
}

@Singleton
class AuditService @Inject()(val auditConnector: AuditConnector) {

  import $servicenameCamel$FrontendEvent._

  def send$servicenameCamel$FrontendSomethingHappened(
    model: $servicenameCamel$FrontendModel,
    agentReference: Arn)(
    implicit hc: HeaderCarrier,
    request: Request[Any],
    ec: ExecutionContext): Unit =
    auditEvent(
      $servicenameCamel$FrontendEvent.$servicenameCamel$FrontendSomethingHappened,
      "$servicenameHyphen$-frontend-something-happened",
      Seq(
        "agentReference"  -> agentReference.value,
        "name"            -> model.name,
        "telephoneNumber" -> model.telephoneNumber.getOrElse(""),
        "emailAddress"    -> model.emailAddress.getOrElse("")
      )
    )

  private[services] def auditEvent(
    event: $servicenameCamel$FrontendEvent,
    transactionName: String,
    details: Seq[(String, Any)] = Seq.empty)(
    implicit hc: HeaderCarrier,
    request: Request[Any],
    ec: ExecutionContext): Future[Unit] =
    send(createEvent(event, transactionName, details: _*))

  private[services] def createEvent(
    event: $servicenameCamel$FrontendEvent,
    transactionName: String,
    details: (String, Any)*)(
    implicit hc: HeaderCarrier,
    request: Request[Any],
    ec: ExecutionContext): DataEvent = {

    val detail = hc.toAuditDetails(details.map(pair => pair._1 -> pair._2.toString): _*)
    val tags = hc.toAuditTags(transactionName, request.path)
    DataEvent(
      auditSource = "$servicenameHyphen$-frontend",
      auditType = event.toString,
      tags = tags,
      detail = detail)
  }

  private[services] def send(
    events: DataEvent*)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Unit] =
    Future {
      events.foreach { event =>
        Try(auditConnector.sendEvent(event))
      }
    }

}
