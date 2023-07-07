//package simulation
//
//
//import io.gatling.core.Predef._
//import io.gatling.http.Predef._
//import util.{Constants, PropertiesReader}
//
//import java.util.UUID
//import scala.concurrent.duration._
//import scala.util.Properties._
//
//
//class I9MobileOrchSimulation extends Simulation {
//
//  // load config
//  val requestsPerSecond = envOrElse("REQ_PER_SEC", "1").toInt
//  val duration = envOrElse("DURATION_IN_MINUTES", "1").toInt
//  val pollTimeout = envOrElse("POLL_TIMEOUT_SECONDS", "600").toInt
//  val scenarioNumber = envOrElse("SCENARIO_NUMBER", "2").toInt
//
//  val caseID = "18149b30-d891-4013-ba67-915312163761"
//  val mob_Orch_Url = "https://mobile-orchestration-api.inte-master-ems.xydus.com"
//
//  val appUserId = "na"
//  val deviceId = "gatling"
//  val transactionRef = UUID.randomUUID()
//
//  val httpProtocol = http
//    .baseUrl(mob_Orch_Url)
//    .acceptHeader("application/json")
//    .header("Content-Type", "application/json")
//
//
//  val getEmsToken = exec(
//    http("get login token")
//      .post("/generateToken")
//      .body(StringBody(
//        """
//          |{
//          |    "caseId": "18149b30-d891-4013-ba67-915312163761"
//          |}""".stripMargin
//      ))
//      .check(
//        status is 200,
//        jsonPath("$..token").exists.saveAs("emsToken")
//      )
//  ).exitHereIfFailed
//
//  val getCaseInformation = exec(
//    http("get case information")
//      .get("/caseInformation")
//      .header("Token", "#{emsToken}")
//      .check(
//        status.is(200)
//      )
//  )
//
//  val getConfig = exec(
//    http("get configuration")
//      .get("/config")
//      .header("Token", "#{emsToken}")
//      .check(
//        status.is(200)
//      )
//.check(bodyString.saveAs("responseBody"))
//  )
//.exec { session =>
//  val responseBody = session("responseBody").as[String] // Retrieve the response body from the session
//  println(s"Response Body: $responseBody") // Print the response body
//  session // Return the session
//}
//
//  val scenarioBuilder = scenario(s"""Doc check at $requestsPerSecond tps - poll timeout ${pollTimeout}s""")
//
//    .exec(
//      getEmsToken,
//      getCaseInformation
//      //getConfig
//    )
//
//      setUp(
//        scenarioBuilder.inject(
//          nothingFor(5.seconds), // 1
//          atOnceUsers(3), // 2
//          rampUsers(requestsPerSecond / 10).during(1.minutes), // 3
//          constantUsersPerSec(requestsPerSecond).during(duration.minutes) // 4
//        ).protocols(httpProtocol)
//      )
//}
