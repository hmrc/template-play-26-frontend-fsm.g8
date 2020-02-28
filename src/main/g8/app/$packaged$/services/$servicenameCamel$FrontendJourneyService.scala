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

import com.google.inject.ImplementedBy
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Format
import uk.gov.hmrc.cache.repository.CacheMongoRepository
import uk.gov.hmrc.crypto.ApplicationCrypto
import uk.gov.hmrc.http.HeaderCarrier
import $package$.journeys.{$servicenameCamel$FrontendJourneyModel, $servicenameCamel$FrontendJourneyStateFormats}
import uk.gov.hmrc.play.fsm.PersistentJourneyService

@ImplementedBy(classOf[MongoDBCached$servicenameCamel$FrontendJourneyService])
trait $servicenameCamel$FrontendJourneyService[RequestContext]
    extends PersistentJourneyService[RequestContext] {

  val journeyKey = "$servicenameCamel$Journey"

  override val model = $servicenameCamel$FrontendJourneyModel

  // do not keep errors in the journey history
  override val breadcrumbsRetentionStrategy: Breadcrumbs => Breadcrumbs =
    _.filterNot(_.isInstanceOf[model.IsError])
}

trait $servicenameCamel$FrontendJourneyServiceWithHeaderCarrier
    extends $servicenameCamel$FrontendJourneyService[HeaderCarrier]

@Singleton
case class MongoDBCached$servicenameCamel$FrontendJourneyService @Inject()(
  cacheMongoRepository: CacheMongoRepository,
  applicationCrypto: ApplicationCrypto)
    extends MongoDBCachedJourneyService[HeaderCarrier]
    with $servicenameCamel$FrontendJourneyServiceWithHeaderCarrier {

  override val stateFormats: Format[model.State] =
    $servicenameCamel$FrontendJourneyStateFormats.formats

  override def getJourneyId(hc: HeaderCarrier): Option[String] =
    hc.extraHeaders.find(_._1 == journeyKey).map(_._2)

}
