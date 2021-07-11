package code

import code.model._

import scala.io.Source
import java.io._

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Festival extends App {

  //Source.fromFile(args(0)).mkString
  //writeJson(args(0), "abcd\nefgh")

  def makePlans(shows: Set[Show]): List[Plan] = {
    if (shows.isEmpty)
      Nil
    else {
      shows.tail.foldLeft(Set(shows.head)){ (a, b) =>
        applyShow(a, b)
      }.toList.map(_.toPlan)
    }
  }

  def applyShow(schedules: Set[Show], show: Show): Set[Show] = {
    if (schedules.isEmpty)
      Set(show)
    else {
      val startTime = show.start.getMillis
      val endTime = show.finish.getMillis

      val scheds = schedules.map{ sched =>
        val schedStart = sched.start.getMillis
        val schedEnd = sched.finish.getMillis

        if (schedStart >= endTime || schedEnd <= startTime) //no overlap
          Set(sched, show)
        else if (schedStart > startTime) {
          if (endTime > schedStart && endTime < schedEnd) {
            val show1 = Show(sched.band, sched.start, show.start, sched.priority)
            val show3 = Show(show.band, sched.finish, show.finish, show.priority)
            val show2 = if (show.priority > sched.priority)
              Show(show.band, show.start, sched.finish, show.priority)
            else
              Show(sched.band, show.start, sched.finish, sched.priority)
            Set(show1, show2, show3)
          }

        } else if (schedStart == startTime) {

        } else { //schedStart < startTime

        }
      }

      //TODO
      Set()
    }
  }

  def writeJson(fileName: String, str: String): Unit = {
    val file = new File(fileName.replaceFirst(".json", ".optimal.json"))
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(str)
    bw.close()
  }

}
