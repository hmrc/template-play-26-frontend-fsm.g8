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

package $package$.journey
import play.api.libs.json.{Format, Json}
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{Confirmation, Questions, Start}
import $package$.journeys.$servicenameCamel$FrontendJourneyStateFormats
import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.test.UnitSpec

class $servicenameCamel$FrontendFormatSpec extends UnitSpec {

  implicit val formats: Format[State] = $servicenameCamel$FrontendJourneyStateFormats.formats

  "$servicenameCamel$FrontendJourneyStateFormats" should {
    "serialize and deserialize state" when {
      "Start" in {
        val state = Start

        val json = Json.parse("""{"state":"Start"}""")
        Json.toJson(state) shouldBe json
        json.as[State] shouldBe state
      }
      "Questions" in {
        val state = Questions(None)

        val json = Json.parse("""{"state":"Questions","properties":{}}""")
        Json.toJson(state) shouldBe json
        json.as[State] shouldBe state
      }
      "Summary" in {
        val state = Confirmation(
          $servicenameCamel$FrontendModel(
            "Henry",
            Some("BN12 6BX"),
            Some("00000000001"),
            Some("henry@example.com")))

        val json = Json.parse("""{"state":"Confirmation",
                                |"properties":{"formData":{
                                | "name":"Henry",
                                | "postcode":"BN12 6BX",
                                | "telephoneNumber":"00000000001",
                                | "emailAddress": "henry@example.com"
                                |}}}""".stripMargin)
        Json.toJson(state) shouldBe json
        json.as[State] shouldBe state
      }
    }

  }
}
