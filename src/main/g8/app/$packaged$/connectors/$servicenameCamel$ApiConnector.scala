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

package $package$.connectors

import java.net.URL
import java.util.UUID

import com.codahale.metrics.MetricRegistry
import com.kenshoo.play.metrics.Metrics
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Json, Writes}
import uk.gov.hmrc.agent.kenshoo.monitoring.HttpAPIMonitor
import $package$.connectors.$servicenameCamel$ApiConnector.extractResponseBody
import $package$.wiring.AppConfig
import uk.gov.hmrc.http._
import uk.gov.hmrc.http.HttpReads.Implicits._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class $servicenameCamel$ApiConnector @Inject()(appConfig: AppConfig, http: HttpGet with HttpPost, metrics: Metrics)
    extends HttpAPIMonitor {

  val HEADER_X_CORRELATION_ID = "X-Correlation-Id"

  val baseUrl: String = appConfig.$servicenamecamel$ApiBaseUrl
  val someApiPath = "/v1/some-api"

  override val kenshooRegistry: MetricRegistry = metrics.defaultRegistry

  def someApi(request: $servicenameCamel$ApiRequest)(
    implicit hc: HeaderCarrier,
    ec: ExecutionContext): Future[$servicenameCamel$ApiResponse] =
    monitor(s"ConsumedAPI-$servicenameHyphen$-some-api-POST") {
      http
        .POST[$servicenameCamel$ApiRequest, $servicenameCamel$ApiResponse](
          new URL(baseUrl + someApiPath).toExternalForm,
          request)(
          implicitly[Writes[$servicenameCamel$ApiRequest]],
          implicitly[HttpReads[$servicenameCamel$ApiResponse]],
          hc.withExtraHeaders(HEADER_X_CORRELATION_ID -> UUID.randomUUID().toString),
          implicitly[ExecutionContext]
        )
        .recover {
          case UpstreamErrorResponse.Upstream4xxResponse(e) if e.statusCode == 400 =>
            Json.parse(extractResponseBody(e.message, "Response body: '")).as[$servicenameCamel$ApiResponse]
          case UpstreamErrorResponse.Upstream4xxResponse(e) if e.statusCode == 404 =>
            Json.parse(extractResponseBody(e.message, "Response body: '")).as[$servicenameCamel$ApiResponse]
          case UpstreamErrorResponse.Upstream4xxResponse(e) if e.statusCode == 409 =>
            Json.parse(extractResponseBody(e.message, "Response body: '")).as[$servicenameCamel$ApiResponse]
        }
        .recoverWith {
          case e: Throwable => Future.failed($servicenameCamel$ProxyError(e))
        }
    }

}

object $servicenameCamel$ApiConnector {

  def extractResponseBody(message: String, prefix: String): String = {
    val pos = message.indexOf(prefix)
    val body =
      if (pos >= 0) message.substring(pos + prefix.length, message.length - 1)
      else s"""{"error":{"errCode":"\$message"}}"""
    body
  }
}

case class $servicenameCamel$ProxyError(e: Throwable) extends RuntimeException(e)
