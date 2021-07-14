package code.model

import play.api.libs.json._

import scala.io.Source
import scala.util.{Failure, Success, Try}


object JsonUtil {

  def showFrom(fileName: String): List[Show] = {

    val tryToReadStringFromFile = Try(Source.fromFile(fileName).getLines.mkString)

    tryToReadStringFromFile map { mayBeJsonString =>
      Json.parse(mayBeJsonString).validate[List[Show]] match {
        case s: JsSuccess[List[Show]] => {
          Some(s.get)
        }
        case e: JsError => {
          println(s"Error parsing Shows : JsError ${e}")
          None
        }
      }
    } match {
      case Success(showOpt) =>
        showOpt.get
      case Failure(f) =>
        println(s"Possibly invalid Json for Shows : failure ${f}")
        Nil
    }
  }

  def planFrom(fileName: String): List[Plan] = {

    val tryToReadStringFromFile = Try(Source.fromFile(fileName).getLines.mkString)

    tryToReadStringFromFile map { mayBeJsonString =>
      Json.parse(mayBeJsonString).validate[List[Plan]] match {
        case s: JsSuccess[List[Plan]] => {
          Some(s.get)
        }
        case e: JsError => {
          println(s"Error parsing Plans : JsError ${e}")
          None
        }
      }
    } match {
      case Success(planOpt) =>
        planOpt.get
      case Failure(f) =>
        println(s"Possibly invalid Json for Plans : failure ${f}")
        Nil
    }
  }


}
