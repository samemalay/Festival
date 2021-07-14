package code

import java.io._

import code.model._
import play.api.libs.json.Json

object Festival extends App {

  val inputFile = args(0)
  writeJson(inputFile, Json.prettyPrint(Json.toJson(
    makePlans(JsonUtil.showFrom(inputFile).toSet)
  )))

  def makePlans(shows: Set[Show]): List[Plan] = {
    if (shows.isEmpty)
      Nil
    else {
      shows.tail.foldLeft(Set(shows.head)){ (a, b) =>
        applyShow(a, b)
      }.toList.map(_.toPlan).sortBy(_.start.getMillis)
    }
  }

  def applyShow(schedules: Set[Show], show: Show): Set[Show] = {
    if (schedules.isEmpty)
      Set(show)
    else {
      val showStart = show.start.getMillis
      val showEnd = show.finish.getMillis

      schedules.flatMap{ sched =>
        val schedStart = sched.start.getMillis
        val schedEnd = sched.finish.getMillis

        if (schedStart >= showEnd || schedEnd <= showStart) //no overlap
          Set(sched, show)
        else if (schedStart < showStart) {
          if (schedEnd < showEnd) {
            if (show.priority > sched.priority)
              Set(
                Show(sched.band, sched.start, show.start, sched.priority),
                show
              )
            else
              Set(
                sched,
                Show(show.band, sched.finish, show.finish, show.priority)
              )
          } else if (schedEnd == showEnd) {
            if (show.priority > sched.priority)
              Set(
                Show(sched.band, sched.start, show.start, sched.priority),
                show
              )
            else
              Set(
                sched
              )
          } else {
            if (show.priority > sched.priority)
              Set(
                Show(sched.band, sched.start, show.start, sched.priority),
                show,
                Show(sched.band, show.finish, sched.finish, sched.priority)
              )
            else
              Set(
                sched
              )
          }
        } else if (schedStart == showStart) {
          if (schedEnd < showEnd) {
            if (show.priority > sched.priority)
              Set(
                show
              )
            else
              Set(
                sched,
                Show(show.band, sched.finish, show.finish, show.priority)
              )
          } else if (schedEnd == showEnd) {
            if (show.priority > sched.priority)
              Set(
                show
              )
            else
              Set(
                sched
              )
          } else {
            if (show.priority > sched.priority)
              Set(
                show,
                Show(sched.band, show.finish, sched.finish, sched.priority)
              )
            else
              Set(
                sched
              )
          }
        } else { //schedStart > startTime
          if (schedEnd < showEnd) {
            if (show.priority > sched.priority)
              Set(
                show
              )
            else
              Set(
                Show(show.band, show.start, sched.start, show.priority),
                sched,
                Show(show.band, sched.finish, show.finish, show.priority)
              )
          } else if (schedEnd == showEnd) {
            if (show.priority > sched.priority)
              Set(
                show
              )
            else
              Set(
                Show(show.band, show.start, sched.start, show.priority),
                sched
              )
          } else {
            if (show.priority > sched.priority)
              Set(
                show,
                Show(sched.band, show.finish, sched.finish, sched.priority)
              )
            else
              Set(
                Show(show.band, show.start, sched.start, show.priority),
                sched
              )
          }

        }
      }
    }
  }

  def writeJson(fileName: String, str: String): Unit = {
    val file = new File(fileName.replaceFirst(".json", ".optimal.json"))
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(str)
    bw.close()
  }

}
/* cases


    show
           |                |
sched
|  | X
      |   | X
                            |  | X
                              |   | X
9 cases
    |          |
    |                       |
    |                                  |
          |      |
          |                |
          |                    |
                  |    |
                  |         |
                  |              |

 */


