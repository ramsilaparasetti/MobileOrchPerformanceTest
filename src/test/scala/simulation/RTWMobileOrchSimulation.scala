package simulation

import jdk.nashorn.internal.runtime.ScriptingFunctions.exec

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import util.{Constants, PropertiesReader}
import util.Constants._

import java.util.UUID
import scala.concurrent.duration._
import scala.util.Properties._
import java.nio.file.{Files, Paths}
import java.util.Base64
import scala.language.postfixOps



class RTWMobileOrchSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl(mob_Orch_Url)
    .acceptHeader(HttpHeaderValues.ApplicationJson)
    .contentTypeHeader(HttpHeaderValues.ApplicationJson)

  val getEmsToken = exec(
    httpProtocol("Get login token")
      .post(generateToken)
      .body(StringBody(s"""{"${caseIdKey}": "${caseID}"}""".stripMargin))
      .check(
        status is 200,
        jsonPath("$..token").exists.saveAs("emsToken")
      )
  ).exitHereIfFailed

  val getCaseInformation = exec(
    httpProtocol("Get case information")
      .get(caseInformation)
      .headers(tokenHeader)
      .check(
        status.is(200)
      )
   )

  val postTransactions = exec(
    httpProtocol("get transaction id")
      .post(transactions)
      .headers(tokenHeader)
      .body(StringBody(transactionRequestBody.stripMargin
      ))
      .check(
        status.is(200),
        jsonPath("$..transactionId").exists.saveAs("transactionID")
      )
  ).exitHereIfFailed

  val postPassportDocument = exec(
    httpProtocol("post passport documents")
      .post(document)
      .headers(passportHeaders)
      .body(StringBody(s"""{"${imageKey}": "${passportBase64String}"}""".stripMargin))
      .check(
        status.is(200)
      )
  )

  val postIDDocument = exec(
    httpProtocol("post ID documents")
      .post(document)
      .headers(identityDocumentHeaders)
      .body(StringBody(s"""{"${imageKey}": "${idDocumentBase64String}"}""".stripMargin))
      .check(
        status.is(200)
      )
  )

  val wholeScreenDocument = exec(
    http("Whole screen documents")
      .post(document)
      .headers(wholeScreenDocumentHeaders)
      .body(StringBody(s"""{"${imageKey}": "${wholeScreenBase64String}"}""".stripMargin))
      .check(
        status.is(200)
      )
  )

  val postFace = exec(
    httpProtocol("post face capture")
      .post(face)
      .headers(faceHeaders)
      .body(StringBody(s"""{"${imageKey}": "${faceFrontBase64String}"}""".stripMargin))
      .check(
        status.is(200)
      )
  )

  val postFaceEnd = exec(
    httpProtocol("post face end capture")
      .post(face_end)
      .headers(faceHeaders)
      .check(
        status.is(200)
      )
  )

  val postTransactionPerformed = exec(
    httpProtocol("Post Transaction Performed")
      .post(transactionPerformed)
      .body(StringBody(s"""{"${caseIdKey}": "${caseID}"}""".stripMargin))
      .check(
        status.is(200)
      )
  )

  val scenarioBuilder = scenario(s"""Doc check at $requestsPerSecond tps - poll timeout ${pollTimeout}s""")

    .exec(
      getEmsToken,
      getCaseInformation,
      postTransactions,
      postPassportDocument,
      postIDDocument,
      wholeScreenDocument,
      postFace,
      postFaceEnd,
      postTransactionPerformed
    )
  setUp(scenarioBuilder.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}