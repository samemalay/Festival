package code.model

import org.joda.time.DateTime
import play.api.libs.json._

case class Show(
                  band: String,
                  start: DateTime,
                  finish: DateTime,
                  priority: Int
               ) {
  def toPlan = Plan(band, start, finish)
}

object Show {
  implicit val dateFormatter: Format[DateTime] = DateFormatUtil.formatter
  implicit val formatter: OFormat[Show] = Json.format[Show]
}