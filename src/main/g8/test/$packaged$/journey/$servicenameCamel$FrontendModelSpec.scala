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
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.State.{Confirmation, Questions}
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.Transitions._
import $package$.journeys.$servicenameCamel$FrontendJourneyModel.{State, Transition}
import $package$.models.$servicenameCamel$FrontendModel
import $package$.services.$servicenameCamel$FrontendJourneyService
import $package$.support.{InMemoryStore, StateMatchers}
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$FrontendModelSpec extends UnitSpec with StateMatchers[State] {

  // dummy journey context
  case class DummyContext()
  implicit val dummyContext: DummyContext = DummyContext()

  "$servicenameCamel$FrontendModel" when {
    "at state Questions" should {
      "go to Confirmation when provided with a form data" in {
        given(Questions()) when gotoConfirmation("001.H")(
          $servicenameCamel$FrontendModel("Henry", None, None, None)) should thenGo(
          Confirmation($servicenameCamel$FrontendModel("Henry", None, None, None)))
      }
    }
  }

  // TEST UTILITIES

  case class given(initialState: State)
      extends $servicenameCamel$FrontendJourneyService[DummyContext]
      with InMemoryStore[(State, List[State]), DummyContext] {

    await(save((initialState, Nil)))

    def when(transition: Transition): (State, List[State]) =
      await(super.apply(transition))
  }
}
