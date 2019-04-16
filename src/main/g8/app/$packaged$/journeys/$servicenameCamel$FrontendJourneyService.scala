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

  override val model = $servicenameCamel$FrontendJourneyModel
}

@Singleton
class MongoDBCached$servicenameCamel$FrontendJourneyService @Inject()(_cacheRepository: SessionCacheRepository)
    extends $servicenameCamel$FrontendJourneyService {

  val id = "neShineyService26FsmJourney"

  case class PersistentState(state: model.State, breadcrumbs: List[model.State])

  implicit val formats1: Format[model.State] = $servicenameCamel$FrontendJourneyStateFormats.formats
  implicit val formats2: Format[PersistentState] = Json.format[PersistentState]

  final val cache = new SessionCache[PersistentState] {
    override val sessionName: String = id
    override val cacheRepository: CacheRepository = _cacheRepository
  }

  protected def fetch(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Option[StateAndBreadcrumbs]] =
    cache.fetch.map(_.map(ps => (ps.state, ps.breadcrumbs)))

  protected def save(
    state: StateAndBreadcrumbs)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[StateAndBreadcrumbs] =
    cache.save(PersistentState(state._1, state._2)).map(_ => state)
}
