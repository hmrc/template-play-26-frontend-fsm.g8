package $package$.controllers

import java.util.concurrent.atomic.AtomicReference

import com.google.inject.AbstractModule
import javax.inject.Singleton
import uk.gov.hmrc.http.HeaderCarrier
import $package$.journeys.$servicenameCamel$FrontendJourneyService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class Test$servicenameCamel$FrontendJourneyService extends $servicenameCamel$FrontendJourneyService {

  private val stateCache = new AtomicReference[Option[StateAndBreadcrumbs]](None)

  def set(state: model.State, breadcrumbs: List[model.State])(
    implicit headerCarrier: HeaderCarrier,
    timeout: Duration,
    ec: ExecutionContext): Unit =
    Await.result(save((state, breadcrumbs)), timeout)

  def get(implicit headerCarrier: HeaderCarrier, timeout: Duration, ec: ExecutionContext): Option[StateAndBreadcrumbs] =
    Await.result(fetch, timeout)

  override protected def fetch(
    implicit hc: HeaderCarrier,
    ec: ExecutionContext): Future[Option[(model.State, List[model.State])]] = Future.successful(
    stateCache.get()
  )

  override protected def save(state: (model.State, List[model.State]))(
    implicit hc: HeaderCarrier,
    ec: ExecutionContext): Future[(model.State, List[model.State])] =
    Future {
      stateCache.set(Some(state))
      state
    }
}

private class TestAgentInvitationJourneyModule extends AbstractModule {
  override def configure(): Unit =
    bind(classOf[$servicenameCamel$FrontendJourneyService]).to(classOf[Test$servicenameCamel$FrontendJourneyService])
}
