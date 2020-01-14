A [Giter8](http://www.foundweekends.org/giter8/) template for creating HMRC Digital Scala Play 2.6 Stateful Frontend
What's inside?
==

Stateful frontend microservice seed build with:

* [playframework 2.6](https://www.playframework.com/documentation/2.6.x/Home)
* [play-fsm](https://github.com/hmrc/play-fsm)
* [assets-frontend](http://hmrc.github.io/assets-frontend/)
* [mongodb](https://www.mongodb.com/)



How to create a new project based on the template?
==

* Install g8 commandline tool (http://www.foundweekends.org/giter8/setup.html)
* Go to the directory where you want to create the template
* Decide your service name (the hardest part :))
* Run the command

    `g8 hmrc/template-play-26-frontend-fsm.g8 --package=uk.gov.hmrc.newshinyservice26fsmfrontend --servicename="New Shiny Service 26 FSM" --serviceTargetPort="9999"`
    
and then
    
    cd new-shiny-service-26-fsm
    git init
	git add .
	git commit -m start
  
* Test generated project using command 

    `sbt test it:test`
    

How to test the template and generate an example project?
==

* Run `./test.sh` 

An example project will be then created and tested in `target/sandbox/new-shiny-service-26-fsm`

How to modify the template?
==

 * review template sources in `/src/main/g8`
 * modify files as you need, but be careful about placeholders, paths and so on
 * run `./test.sh` in template root to validate your changes
 
or (safer) ...

* run `./test.sh` first
* open `target/sandbox/new-shiny-service-26-fsm` in your preferred IDE, 
* modify the generated example project as you wish, 
* build and test it as usual, you can run `sbt test it:test`,
* when you are done switch back to the template root
* run `./update-g8.sh` in order to port your changes back to the template.
* run `./test.sh` again to validate your changes

What is in the template?
==

Assuming the command above 
the template will supply the following values for the placeholders:

    $packaged$ -> uk/gov/hmrc/newshinyservice26fsmfrontend
	$package$ -> uk.gov.hmrc.newshinyservice26fsmfrontend
	$servicenameCamel$ -> NewShinyService26Fsm
	$servicenamecamel$ -> newShinyService26Fsm
	$servicenameSnake$ -> NEW_SHINY_SERVICE_26_FSM
	$servicenamePackage$ -> New.Shiny.Service.26.FSM
	$servicenamePackageLowercase$ -> new.shiny.service.26.fsm
	$servicenamePackaged$ -> New/Shiny/Service/26/FSM
	$servicenamePackagedLowercase$ -> new/shiny/service/26/fsm
	$servicenameHyphen$ -> new-shiny-service-26-fsm
	$servicenameLowercase$ -> new shiny service 26 fsm
	$servicenameUppercase$ -> NEW SHINY SERVICE 26 FSM
	$servicename$ -> New Shiny Service 26 FSM
	$serviceTargetPort$ -> 9999

and produce the folders and files as shown below:

    ├── .description
	├── .gitignore
	├── .scalafmt.conf
	├── app
	│   ├── ErrorHandler.scala
	│   ├── FrontendModule.scala
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice26fsmfrontend
	│                   ├── connectors
	│                   │   ├── FrontendAuthConnector.scala
	│                   │   └── NewShinyService26FsmConnector.scala
	│                   │
	│                   ├── controllers
	│                   │   ├── AuthActions.scala
	│                   │   ├── NewShinyService26FsmFrontendController.scala
	│                   │   └── package.scala
	│                   │
	│                   ├── journeys
	│                   │   ├── NewShinyService26FsmFrontendJourneyModel.scala
	│                   │   ├── NewShinyService26FsmFrontendJourneyService.scala
	│                   │   └── NewShinyService26FsmFrontendJourneyStateFormats.scala
	│                   │
	│                   ├── models
	│                   │   └── NewShinyService26FsmFrontendModel.scala
	│                   │
	│                   ├── repository
	│                   │   ├── MongoSessionStore.scala
	│                   │   ├── SessionCache.scala
	│                   │   └── SessionCacheRepository.scala
	│                   │
	│                   ├── services
	│                   │   └── AuditService.scala
	│                   │
	│                   ├── views
	│                   │   ├── end.scala.html
	│                   │   ├── error_prefix.scala.html
	│                   │   ├── error_template.scala.html
	│                   │   ├── govuk_wrapper.scala.html
	│                   │   ├── main_template.scala.html
	│                   │   └── start_page.scala.html
	│                   │
	│                   └── wiring
	│                       └── AppConfig.scala
	│
	├── build.sbt
	├── conf
	│   ├── app.routes
	│   ├── application-json-logger.xml
	│   ├── application.conf
	│   ├── logback.xml
	│   ├── messages
	│   └── prod.routes
	│
	├── it
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice26fsmfrontend
	│                   ├── connectors
	│                   │   ├── NewShinyService26FsmConnectorISpec.scala
	│                   │   └── TestAppConfig.scala
	│                   │
	│                   ├── controllers
	│                   │   ├── AuthActionsISpec.scala
	│                   │   ├── NewShinyService26FsmFrontendControllerISpec.scala
	│                   │   └── TestNewShinyService26FsmJourneyService.scala
	│                   │
	│                   ├── stubs
	│                   │   ├── AuthStubs.scala
	│                   │   └── DataStreamStubs.scala
	│                   │
	│                   └── support
	│                       ├── BaseISpec.scala
	│                       ├── MetricsTestSupport.scala
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
	                └── newshinyservice26fsmfrontend
	                    ├── controllers
	                    │   └── NewShinyService26FsmFrontendFormSpec.scala
	                    │
	                    ├── journey
	                    │   ├── NewShinyService26FsmFrontendFormatSpec.scala
	                    │   ├── NewShinyService26FsmFrontendModelSpec.scala
	                    │   ├── StateMatchers.scala
	                    │   └── TestStorage.scala
	                    │
	                    ├── services
	                    │   └── AuditServiceSpec.scala
	                    │
	                    └── views
	                        └── ViewsSpec.scala