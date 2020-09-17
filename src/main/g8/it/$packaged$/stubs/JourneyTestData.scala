package $package$.stubs

import java.time.LocalDate

import uk.gov.hmrc.domain.Nino
import $package$.models._

trait JourneyTestData {

  val correlationId: String = scala.util.Random.alphanumeric.take(64).mkString

  val validModel =
    $servicenameCamel$Model(Nino("RJ301829A"), "Doe", "Jane", "2001-01-31")

}
