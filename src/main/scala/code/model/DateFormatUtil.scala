package code.model

import java.util.TimeZone

import org.joda.time._
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Format, JsPath, JsString, Json, Writes}

object DateFormatUtil {
  val FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

  private val dateFmt = DateTimeFormat.forPattern(FORMAT)

  implicit val formatter =
    Format(
      JsPath.read[JsString].map { json =>
        dateFmt.parseDateTime(json.value)
      },

      Writes[DateTime] { dt =>
        Json.toJson(dateFmt.print(dt))
      }
    )
}
