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

package $package$.journeys
import $package$.models.$servicenameCamel$FrontendModel
import uk.gov.hmrc.play.fsm.JourneyModel

object $servicenameCamel$FrontendJourneyModel extends JourneyModel {

  sealed trait State
  sealed trait IsError

  override val root: State = State.Questions()

  object State {

    case object Start extends State

    case class Questions(maybeFormData: Option[$servicenameCamel$FrontendModel] = None)
        extends State

    case class Confirmation(formData: $servicenameCamel$FrontendModel) extends State

    case object SomeError extends State with IsError
  }

  object Transitions {
    import State._

    val start = Transition {
      case _ => goto(Start)
    }

    def gotoQuestions(humanId: String) = Transition {
      case Confirmation(formData) => goto(Questions(Some(formData)))
      case _                      => goto(Questions())
    }

    def gotoConfirmation(humanId: String)(formData: $servicenameCamel$FrontendModel) =
      Transition {
        case Questions(_) =>
          goto(Confirmation(formData))
      }
  }

}
