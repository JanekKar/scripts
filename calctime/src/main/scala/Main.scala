import oshi.SystemInfo
import scala.concurrent.duration._
import scala.util.Try

object Main extends App {
  val uptimeLong = new SystemInfo().getOperatingSystem().getSystemUptime()
  val uptime = TimeDuration(uptimeLong)

  args.headOption.fold{
    println(uptime)
    }{ arg =>
      if(TimeDuration.isVaidDurationString(arg)){
        val timeFromArgs = TimeDuration(arg)
        val timeDiff = TimeDuration.diff(uptime, timeFromArgs)
        println(timeDiff)
      } else {
        println("Wrong arg format")
      }
  }

}

case class TimeDuration(timestamp: Long) {
  val hours: Long = timestamp / 3600
  val minutes: Long = (timestamp / 60) % 60

  override def toString = {
    val hoursString = if(hours > 0)
     s"${hours}h"
    else
      ""

    hoursString + s"${minutes}m"
  }

}

object TimeDuration{

  def apply(durationString: String): TimeDuration = {
    val timestamp = durationStringToLong(durationString)

    TimeDuration(timestamp)
  }

  def durationStringToLong(durationString: String): Long = {
    val hours = Try(durationString.split("h").headOption.map(_.toLong).getOrElse(0L)).getOrElse(0L)
    val minutes = Try(durationString.split("h").last.split("m").headOption.map(_.toLong).getOrElse(0L)).getOrElse(0L)

    (hours * 3600) + (minutes * 60)
  }

  def diff(timeDuration1: TimeDuration, timeDuration2: TimeDuration): TimeDuration = {
    val difference = timeDuration1.timestamp - timeDuration2.timestamp

    TimeDuration(difference)
  }


  def isVaidDurationString(string: String): Boolean = {

    val regex = "([0-9]{1,2}h)?([0-9]{1,2}m)?".r

    regex.matches(string)
  }

}
