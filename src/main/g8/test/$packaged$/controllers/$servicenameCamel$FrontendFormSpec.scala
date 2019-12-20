package $package$.controllers

import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.test.UnitSpec

class $servicenameCamel$FrontendFormSpec extends UnitSpec {

  "$servicenameCamel$FrontendForm" should {

    "bind some input fields and return $servicenameCamel$FrontendModel and fill it back" in {
      val form = $servicenameCamel$FrontendController.$servicenameCamel$FrontendForm

      val value = $servicenameCamel$FrontendModel(
        name = "SomeValue",
        postcode = None,
        telephoneNumber = None,
        emailAddress = None)

      val fieldValues = Map("name" -> "SomeValue")

      form.bind(fieldValues).value shouldBe Some(value)
      form.fill(value).data shouldBe fieldValues
    }

    "bind all input fields and return $servicenameCamel$FrontendModel and fill it back" in {
      val form = $servicenameCamel$FrontendController.$servicenameCamel$FrontendForm

      val value = $servicenameCamel$FrontendModel(
        name = "SomeValue",
        postcode = Some("AA1 1AA"),
        telephoneNumber = Some("098765321"),
        emailAddress = Some("foo@bar.com"))

      val fieldValues = Map(
        "name"            -> "SomeValue",
        "postcode"        -> "AA1 1AA",
        "telephoneNumber" -> "098765321",
        "emailAddress"    -> "foo@bar.com")

      form.bind(fieldValues).value shouldBe Some(value)
      form.fill(value).data shouldBe fieldValues
    }
  }
}
