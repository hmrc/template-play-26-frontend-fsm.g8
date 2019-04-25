package $package$.journeys

import com.google.inject.ImplementedBy
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.cache.repository.CacheRepository
import uk.gov.hmrc.http.HeaderCarrier
import $package$.repository.{SessionCache, SessionCacheRepository}
import uk.gov.hmrc.play.fsm.PersistentJourneyService

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[MongoDBCached$servicenameCamel$FrontendJourneyService])
trait $servicenameCamel$FrontendJourneyService extends PersistentJourneyService {

  val journeyKey = "$servicenameCamel$Journey"

  override val model = $servicenameCamel$FrontendJourneyModel
}

@Singleton
class MongoDBCached$servicenameCamel$FrontendJourneyService @Inject()(_cacheRepository: SessionCacheRepository)
    extends $servicenameCamel$FrontendJourneyService {

  case class PersistentState(state: model.State, breadcrumbs: List[model.State])

  implicit val formats1: Format[model.State] = $servicenameCamel$FrontendJourneyStateFormats.formats
  implicit val formats2: Format[PersistentState] = Json.format[PersistentState]

  final val cache = new SessionCache[PersistentState] {

    override val sessionName: String = journeyKey
    override val cacheRepository: CacheRepository = _cacheRepository

    override def getSessionId(implicit hc: HeaderCarrier): Option[String] =
      hc.extraHeaders.find(_._1 == journeyKey).map(_._2)
  }

  override protected def fetch(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Option[StateAndBreadcrumbs]] =
    cache.fetch.map(_.map(ps => (ps.state, ps.breadcrumbs)))

  override protected def save(
    state: StateAndBreadcrumbs)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[StateAndBreadcrumbs] =
    cache.save(PersistentState(state._1, state._2)).map(_ => state)

  override def clear(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Unit] =
    cache.delete().map(_ => ())

}
