package $package$.models

import play.api.libs.json.Json

case class $servicenameCamel$FrontendModel(
  name: String,
  postcode: Option[String],
  telephoneNumber: Option[String],
  emailAddress: Option[String])

object $servicenameCamel$FrontendModel {
  implicit val modelFormat = Json.format[$servicenameCamel$FrontendModel]
}
