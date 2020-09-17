A [Giter8](http://www.foundweekends.org/giter8/) template for creating HMRC Digital Scala Play 2.6 Stateful Frontend
What's inside?
==

Stateful frontend microservice seed build with:

* [playframework 2.6](https://www.playframework.com/documentation/2.6.x/Home)
* [bootstrap-play](https://github.com/hmrc/bootstrap-play)
* [play-fsm](https://github.com/hmrc/play-fsm)
* [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk)
* [mongodb](https://www.mongodb.com)



How to create a new project based on the template?
==

* Install g8 commandline tool (http://www.foundweekends.org/giter8/setup.html)
* Go to the directory where you want to create the template
* Decide your service name (the hardest part :))
* Run the command

    `g8 {GITHUB_USER}/template-play26-frontend-fsm.g8 --servicename="New Shiny Service" --serviceTargetPort="9999" --serviceUrlPrefix="shiny-url-prefix" --package="uk.gov.hmrc.newshinyservice" -o new-shiny-service`
    
and then
    
    cd new-shiny-service
    git init
	git add .
	git commit -m start
  
* Test generated project using command 

    `sbt test it:test`
    

How to test the template and generate an example project?
==

* Run `./test.sh` 

An example project will be then created and tested in `target/sandbox/new-shiny-service`

How to modify the template?
==

 * review template sources in `/src/main/g8`
 * modify files as you need, but be careful about placeholders, paths and so on
 * run `./test.sh` in template root to validate your changes
 
or (safer) ...

* run `./test.sh` first
* open `target/sandbox/new-shiny-service` in your preferred IDE, 
* modify the generated example project as you wish, 
* build and test it as usual, you can run `sbt test it:test`,
* when you are done switch back to the template root
* run `./update-g8.sh` in order to port your changes back to the template.
* run `./test.sh` again to validate your changes

What is in the template?
==

Assuming the command above 
the template will supply the following values for the placeholders:

    $packaged$ -> uk/gov/hmrc/newshinyservice
	$package$ -> uk.gov.hmrc.newshinyservice
	$servicenameCamel$ -> NewShinyService
	$servicenamecamel$ -> newShinyService
	$servicenameNoSpaceLowercase$ -> newshinyservice
	$servicenameNoSpaceUppercase$ -> NEWSHINYSERVICE
	$servicenamesnake$ -> new_shiny_service
	$servicenameSnake$ -> NEW_SHINY_SERVICE
	$servicenamePackage$ -> New.Shiny.Service
	$servicenamePackageLowercase$ -> new.shiny.service
	$servicenamePackaged$ -> New/Shiny/Service
	$servicenamePackagedLowercase$ -> new/shiny/service
	$servicenameHyphen$ -> new-shiny-service
	$servicenameLowercase$ -> new shiny service
	$servicenameUppercase$ -> NEW SHINY SERVICE
	$servicename$ -> New Shiny Service
	$serviceUrlPrefixCamel$ -> ShinyUrlPrefix
	$serviceUrlPrefixcamel$ -> shinyUrlPrefix
	$serviceUrlPrefixNoSpaceLowercase$ -> shinyurlprefix
	$serviceUrlPrefixNoSpaceUppercase$ -> SHINYURLPREFIX
	$serviceUrlPrefixsnake$ -> shiny_url_prefix
	$serviceUrlPrefixSnake$ -> SHINY_URL_PREFIX
	$serviceUrlPrefixPackage$ -> shiny.url.prefix
	$serviceUrlPrefixPackageLowercase$ -> shiny.url.prefix
	$serviceUrlPrefixPackaged$ -> shiny/url/prefix
	$serviceUrlPrefixPackagedLowercase$ -> shiny/url/prefix
	$serviceUrlPrefixHyphen$ -> shiny-url-prefix
	$serviceUrlPrefixLowercase$ -> shiny url prefix
	$serviceUrlPrefixUppercase$ -> SHINY URL PREFIX
	$serviceUrlPrefix$ -> shiny-url-prefix
	$serviceTargetPort$ -> 9999

and produce the folders and files as shown below:

    ├── .description
	├── .gitignore
	├── .scalafmt.conf
	├── app
	│   ├── assets
	│   │   ├── javascripts
	│   │   │   ├── app.js
	│   │   │   ├── autocomplete.js
	│   │   │   ├── jquery.min.js
	│   │   │   ├── researchBanner.js
	│   │   │   └── timeout
	│   │   │       └── timeoutDialog.js
	│   │   │
	│   │   └── stylesheets
	│   │       ├── _autocomplete.scss
	│   │       ├── _currency-input.scss
	│   │       ├── _js-hidden.scss
	│   │       ├── _language-toggle.scss
	│   │       ├── _panel.scss
	│   │       ├── _percentage-input.scss
	│   │       ├── _print-preview.scss
	│   │       ├── _research-banner.scss
	│   │       ├── _responsive-table.scss
	│   │       ├── _summary-list.scss
	│   │       ├── _task-list.scss
	│   │       ├── _timeout-dialog.scss
	│   │       ├── application.scss
	│   │       ├── print-styles
	│   │       │   ├── _print-claim-details.scss
	│   │       │   └── _print.scss
	│   │       │
	│   │       └── print.scss
	│   │
	│   ├── FrontendModule.scala
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice
	│                   ├── connectors
	│                   │   ├── ApiError.scala
	│                   │   ├── FrontendAuthConnector.scala
	│                   │   ├── NewShinyServiceApiConnector.scala
	│                   │   ├── NewShinyServiceApiRequest.scala
	│                   │   └── NewShinyServiceApiResponse.scala
	│                   │
	│                   ├── controllers
	│                   │   ├── AccessibilityStatementController.scala
	│                   │   ├── AuthActions.scala
	│                   │   ├── DateFieldHelper.scala
	│                   │   ├── FormFieldMappings.scala
	│                   │   ├── LanguageSwitchController.scala
	│                   │   ├── NewShinyServiceFrontendController.scala
	│                   │   ├── SignOutController.scala
	│                   │   └── ValidateHelper.scala
	│                   │
	│                   ├── filters
	│                   │   ├── AuditFilter.scala
	│                   │   └── CustomFrontendFilters.scala
	│                   │
	│                   ├── journeys
	│                   │   ├── NewShinyServiceFrontendJourneyModel.scala
	│                   │   └── NewShinyServiceFrontendJourneyStateFormats.scala
	│                   │
	│                   ├── models
	│                   │   └── NewShinyServiceModel.scala
	│                   │
	│                   ├── repository
	│                   │   └── JourneyCacheRepository.scala
	│                   │
	│                   ├── services
	│                   │   ├── AuditService.scala
	│                   │   ├── MongoDBCachedJourneyService.scala
	│                   │   ├── NewShinyServiceFrontendJourneyService.scala
	│                   │   └── SessionCache.scala
	│                   │
	│                   ├── support
	│                   │   └── CallOps.scala
	│                   │
	│                   ├── views
	│                   │   ├── AccessibilityStatementView.scala.html
	│                   │   ├── components
	│                   │   │   ├── autocomplete.scala.html
	│                   │   │   ├── bullets.scala.html
	│                   │   │   ├── button.scala.html
	│                   │   │   ├── errorSummary.scala.html
	│                   │   │   ├── fieldset.scala.html
	│                   │   │   ├── FooterLinks.scala
	│                   │   │   ├── h1.scala.html
	│                   │   │   ├── h1NoMargin.scala.html
	│                   │   │   ├── h2.scala.html
	│                   │   │   ├── h3.scala.html
	│                   │   │   ├── inputCheckboxes.scala.html
	│                   │   │   ├── inputCurrency.scala.html
	│                   │   │   ├── inputDate.scala.html
	│                   │   │   ├── inputHidden.scala.html
	│                   │   │   ├── inputNumber.scala.html
	│                   │   │   ├── inputNumberSubHeading.scala.html
	│                   │   │   ├── inputPercentage.scala.html
	│                   │   │   ├── inputRadio.scala.html
	│                   │   │   ├── inputText.scala.html
	│                   │   │   ├── languageSelection.scala.html
	│                   │   │   ├── link.scala.html
	│                   │   │   ├── multiFieldError.scala.html
	│                   │   │   ├── multiLineSummary.scala.html
	│                   │   │   ├── multiLineSummaryWithoutAction.scala.html
	│                   │   │   ├── orderedList.scala.html
	│                   │   │   ├── p.scala.html
	│                   │   │   ├── pageHeading.scala.html
	│                   │   │   ├── panelIndent.scala.html
	│                   │   │   ├── phaseBanner.scala.html
	│                   │   │   ├── researchBanner.scala.html
	│                   │   │   ├── simpleInputText.scala.html
	│                   │   │   ├── siteHeader.scala.html
	│                   │   │   ├── strong.scala.html
	│                   │   │   ├── subheading.scala.html
	│                   │   │   ├── subheadingP.scala.html
	│                   │   │   ├── warningText.scala.html
	│                   │   │   ├── yesNoRadio.scala.html
	│                   │   │   └── yesNoRadioSubHeading.scala.html
	│                   │   │
	│                   │   ├── StartView.scala.html
	│                   │   └── templates
	│                   │       ├── ErrorTemplate.scala.html
	│                   │       ├── GovukLayoutWrapper.scala.html
	│                   │       └── GtmSnippet.scala.html
	│                   │
	│                   └── wiring
	│                       ├── AppConfig.scala
	│                       └── ErrorHandler.scala
	│
	├── build.sbt
	├── conf
	│   ├── app.routes
	│   ├── application-json-logger.xml
	│   ├── application.conf
	│   ├── logback.xml
	│   ├── messages.cy
	│   ├── messages.en
	│   └── prod.routes
	│
	├── it
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice
	│                   ├── connectors
	│                   │   └── NewShinyServiceConnectorISpec.scala
	│                   │
	│                   ├── controllers
	│                   │   ├── AccessibilityStatementControllerISpec.scala
	│                   │   ├── AuthActionsISpec.scala
	│                   │   ├── NewShinyServiceFrontendControllerISpec.scala
	│                   │   └── NewShinyServiceFrontendISpec.scala
	│                   │
	│                   ├── stubs
	│                   │   ├── AuthStubs.scala
	│                   │   ├── DataStreamStubs.scala
	│                   │   ├── JourneyTestData.scala
	│                   │   └── NewShinyServiceStubs.scala
	│                   │
	│                   └── support
	│                       ├── AppISpec.scala
	│                       ├── BaseISpec.scala
	│                       ├── InMemoryJourneyService.scala
	│                       ├── MetricsTestSupport.scala
	│                       ├── NonAuthPageISpec.scala
	│                       ├── ServerISpec.scala
	│                       ├── TestAppConfig.scala
	│                       ├── TestJourneyService.scala
	│                       └── WireMockSupport.scala
	│
	├── LICENSE
	├── project
	│   ├── build.properties
	│   └── plugins.sbt
	│
	├── README.md
	├── repository.yaml
	└── test
	    └── uk
	        └── gov
	            └── hmrc
	                └── newshinyservice
	                    ├── controllers
	                    │   ├── DateFieldHelperSpec.scala
	                    │   ├── FormFieldMappingsSpec.scala
	                    │   └── NewShinyServiceFormSpec.scala
	                    │
	                    ├── journey
	                    │   ├── NewShinyServiceFrontendFormatSpec.scala
	                    │   └── NewShinyServiceFrontendModelSpec.scala
	                    │
	                    └── support
	                        ├── CallOpsSpec.scala
	                        ├── InMemoryStore.scala
	                        └── StateMatchers.scala