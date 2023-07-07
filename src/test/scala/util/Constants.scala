package util

import java.io.File
import java.nio.file.{Files, Path, Paths}
import java.util.{Base64, UUID}
import scala.util.Properties.envOrElse

object Constants {
  
  val passPortImagePath = Paths.get("src/test/scala/util/Passport_UK_Front.png")
  val idDocumentImagePath = Paths.get("src/test/scala/util/DrivingLicense_US_Front.jpeg")
  val wholeDocumentImagePath = Paths.get("src/test/scala/util/Passport_Other_Front.jpeg")
  val faceFrontImagePath = Paths.get("src/test/scala/util/Face_Front.jpg")

  val passportBase64String = ImageToBase64Converter(passPortImagePath)
  val idDocumentBase64String = ImageToBase64Converter(idDocumentImagePath)
  val wholeScreenBase64String = ImageToBase64Converter(wholeDocumentImagePath)
  val faceFrontBase64String = ImageToBase64Converter(faceFrontImagePath)

  def ImageToBase64Converter(fileName: Path) : String = {
    val imageData = Files.readAllBytes(fileName)
    Base64.getEncoder.encodeToString(imageData)
  }

  // load config
  val requestsPerSecond = envOrElse("REQ_PER_SEC", "1").toInt
  val duration = envOrElse("DURATION_IN_MINUTES", "1").toInt
  val pollTimeout = envOrElse("POLL_TIMEOUT_SECONDS", "600").toInt
  val scenarioNumber = envOrElse("SCENARIO_NUMBER", "2").toInt

  val caseID = "cc3a1163-b8f6-4900-8ec4-12902ecfe8ba"
  val mob_Orch_Url = "https://mobile-orchestration-api.prep-master-ems.xydus.com"
  val pcs_url = "https://api-pcs.paycasso.com"

  val generateToken = "/generateToken"
  val caseInformation = "/caseInformation"
  val transactions = "/api/transactions"
  val document = "/api/transactions/\"#{transactionID}\"/document"
  val face = "/api/transactions/\"#{transactionID}\"/face"
  val face_end = "/api/transactions/\"#{transactionID}\"/face/end"
  val transactionPerformed = "/api/url-service/transaction-performed"
  val caseIdKey = "caseId"

  val appUserId = "na"
  val deviceId = "gatling"
  val transactionRef = UUID.randomUUID()


  val transactionRequestBody =
      """{
     "consumerReference": "externalConsumerId",
     "transactionCheck": "VeriSure",
     "transactionReference": "externalTransactionId",
     "documentsConfiguration": {
       "documents": [
     {
       "acceptedDocuments": {
       "kind": "any"
     },
       "bothSides": false,
       "docCheck": {
       "kind": "all"
     },
       "face": {
       "kind": "all"
     },
       "ocr": {
       "kind": "all"
     },
       "preflight": {
       "kind": "no"
     }
     }
       ]
     },
     "signatureStepRequired": false,
     "callback": {
       "url":"https://dev.clienturl.com/callback3",
       "basicAuth":"Basic QWxhZGRpbjpPcGVuU2VzYW1l"
     }
    }"""

  val tokenKey = "Token"
  val imageKey = "image"
  val paycassoVersionKey = "PaycassoVersion"
  val paycassoVersion = "2.10"
  val xDocShapeKey = "X-Document-Shape"
  val xReCaptureDocumentTypeKey = "X-Recapture-Document-Type"
  val xReCaptureDocumentSideKey = "X-Recapture-Document-Side"
  val xReCaptureAttemptsKey = "X-Recapture-Document-Type"
  val xDocumentNameKey = "X-Document-Name"
  val xMRZLocationKey = "X-MRZ-Location"
  val xBarcodeLocationKey = "X-Barcode-Location"
  val xFaceLocationKey = "X-Face-Location"
  val xIsBothSidesKey = "X-Is-Both-Sides"
  val xEchipPresenceKey = "X-EChip-Presence"
  val xDocCheckKey = "X-Doc-Check"

  val tokenHeader = Map(tokenKey -> "#{emsToken}")

  val faceHeaders = Map(
    paycassoVersionKey -> paycassoVersion,
    tokenKey -> "#{emsToken}"
  )

  val passportHeaders = Map(
    paycassoVersionKey -> paycassoVersion,
    tokenKey -> "#{emsToken}",
    xDocShapeKey -> "PASSPORT",
    xReCaptureDocumentTypeKey -> "PASSPORT",
    xReCaptureDocumentSideKey -> "front",
    xReCaptureAttemptsKey -> "0",
    xDocumentNameKey -> "Passport",
    xMRZLocationKey -> "FRONT",
    xBarcodeLocationKey -> "No",
    xFaceLocationKey -> "FRONT",
    xIsBothSidesKey -> "false",
    xEchipPresenceKey -> "true",
    xDocCheckKey -> "true"
  )

  val identityDocumentHeaders = Map(
    paycassoVersionKey -> paycassoVersion,
    tokenKey -> "#{emsToken}",
    xDocShapeKey -> "ID_CARD",
    xReCaptureDocumentTypeKey -> "ID_CARD",
    xReCaptureDocumentSideKey -> "front",
    xReCaptureAttemptsKey -> "0",
    xDocumentNameKey -> "Identity Card",
    xMRZLocationKey -> "No",
    xBarcodeLocationKey -> "No",
    xFaceLocationKey -> "FRONT",
    xIsBothSidesKey -> "false",
    xEchipPresenceKey -> "true",
    xDocCheckKey -> "true"
  )

  val wholeScreenDocumentHeaders = Map(
    paycassoVersionKey -> paycassoVersion,
    tokenKey -> "#{emsToken}",
    xDocShapeKey -> "WHOLE_SCREEN",
    xReCaptureDocumentTypeKey -> "WHOLE_SCREEN",
    xReCaptureDocumentSideKey -> "front",
    xReCaptureAttemptsKey -> "0",
    xDocumentNameKey -> "Birth Certificate",
    xMRZLocationKey -> "No",
    xBarcodeLocationKey -> "No",
    xFaceLocationKey -> "No",
    xIsBothSidesKey -> "false",
    xEchipPresenceKey -> "false",
    xDocCheckKey -> "false"
  )

  val BASE_URL = "mobile-orchestration-api.inte-master-ems.xydus.com"

  val CREATE_USER_URL = "/config"

  val CASE_ID = "18149b30-d891-4013-ba67-915312163761"

  val CREATE_USER_SCENARIO_NAME = "Create User Scenario"

  val JSON_CONTENT_TYPE = "application/json"

  val CREATE_USER_REQUEST_BODY_PATH = "body/createUserRequest.json"

  val SERVICE_JSON_BASE_PATH = "build/reports/resource"

  val SERVICE_JSON_FILENAME = "/createUserData.json"

  var USER_REQUESTS_PER_SECOND: Double = 4

  var USER_DURATION: Integer = 5

  var RAMP_UP_TIME_SECONDS:Integer = Integer.getInteger(("rampUpUserSeconds"),1)

  if(System.getProperty("USER_REQUESTS_PER_SECOND") != null)
    USER_REQUESTS_PER_SECOND = Integer.parseInt(System.getProperty("USER_REQUESTS_PER_SECOND"))

  if(System.getProperty("USER_DURATION") != null)
    USER_DURATION = Integer.parseInt(System.getProperty("USER_DURATION"))

  if(System.getProperty("RAMP_UP_TIME_SECONDS") != null)
    RAMP_UP_TIME_SECONDS = Integer.parseInt(System.getProperty("RAMP_UP_TIME_SECONDS"))
}
