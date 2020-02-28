package $package$.support

import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import uk.gov.hmrc.http.HeaderCarrier

abstract class AppISpec extends BaseISpec with GuiceOneAppPerSuite {

  implicit val hc: HeaderCarrier = HeaderCarrier()

}
