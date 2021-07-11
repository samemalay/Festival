package code.model

import org.joda.time.DateTime
import play.api.libs.json._

case class Plan(
                  band: String,
                  start: DateTime,
                  finish: DateTime
               )

object Plan {
  implicit val dateFormatter: Format[DateTime] = DateFormatUtil.formatter
  implicit val formatter: OFormat[Plan] = Json.format[Plan]
}
