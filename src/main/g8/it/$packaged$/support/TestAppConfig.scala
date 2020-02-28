package $package$.support

import $package$.wiring.AppConfig

case class TestAppConfig(wireMockBaseUrl: String, wireMockPort: Int) extends AppConfig {

  override val appName: String = "$servicenameHyphen$-frontend"
  override val authBaseUrl: String = wireMockBaseUrl
  override val mongoSessionExpiryTime: Int = 3600
  override val someInt: Int = 0
  override val someString: String = ""
  override val someBoolean: Boolean = false
  override val serviceBaseUrl: String = wireMockBaseUrl
}
