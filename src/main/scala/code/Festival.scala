package code

import code.model._

import scala.io.Source
import java.io._

import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.{Failure, Success, Try}

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

        //TODO check obvious contiguous cases

        if (schedStart >= showEnd || schedEnd <= showStart) //no overlap
          Set(sched, show)
        else if (schedStart < showStart) {

          if (schedEnd < showEnd) {
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show3 = Show(show.band, sched.finish, show.finish, show.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, sched.finish, show.priority)
            else
              Show(sched.band, show.start, sched.finish, sched.priority)
            Set(show1, show2, show3)
          } else if (schedEnd == showEnd) {
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, sched.finish, show.priority)
            else
              Show(sched.band, show.start, sched.finish, sched.priority)
            Set(show1, show2)
          } else {
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show3 = Show(sched.band, show.finish, sched.finish, sched.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, show.finish, show.priority)
            else
              Show(sched.band, show.start, show.finish, sched.priority)
            Set(show1, show2, show3)
          }
        } else if (schedStart == showStart) {
          //TODO total 3 cases
          if (schedEnd < showEnd) {
            val show1 = Show(show.band, sched.finish, show.finish, show.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, sched.finish, show.finish, show.priority)
            else
              Show(sched.band, sched.finish, show.finish, sched.priority)
            Set(show1, show2)
          } else if (schedEnd == showEnd) {
            val show1 = if (show.priority > sched.priority)
              Show(show.band, show.start, show.finish, show.priority)
            else
              Show(sched.band, show.start, show.finish, sched.priority)
            Set(show1)
          } else {
            val show1 = Show(sched.band, show.finish, sched.finish, sched.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, show.finish, show.priority)
            else //TODO in this case shouldn't it be continuous ?
              Show(sched.band, sched.start, show.finish, sched.priority)
            Set(show1, show2)
          }
        } else { //schedStart > startTime
          //TODO total 3 cases
          if (schedEnd < showEnd) {
            //TODO need to check / change
            val show1 = Show(show.band, show.start, sched.start, show.priority)
            val show3 = Show(show.band, sched.finish, show.finish, show.priority)
            //TODO may be contiguous (all 3 1 show)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, sched.start, sched.finish, show.priority)
            else
              Show(sched.band, sched.start, sched.finish, sched.priority)
            Set(show1, show2, show3)
          } else if (schedEnd == showEnd) {
            //TODO need to check / change
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, sched.finish, show.priority)
            else
              Show(sched.band, show.start, sched.finish, sched.priority)
            Set(show1, show2)
          } else {
            //TODO need to check / change
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show3 = Show(show.band, sched.finish, show.finish, show.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, sched.finish, show.priority)
            else
              Show(sched.band, show.start, sched.finish, sched.priority)
            Set(show1, show2, show3)
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


