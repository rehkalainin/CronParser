

case class CronParser(cronString: String) {
  def run() = {
    val parsMap = parseCronString(cronString)

    val minuteValue = parsMap.getOrElse("minute", "---")
    val minutes = minuteEncoder(minuteValue)

    val hourValue = parsMap.getOrElse("hour", "---")
    val hours = hourEncoder(hourValue)

    val dayOfMonthValue = parsMap.getOrElse("dayOfMonth", "---")
    val daysOfMonth = dayOfMonthEncoder(dayOfMonthValue)

    val monthValue = parsMap.getOrElse("month", "---")
    val months = monthEncoder(monthValue)

    val dayOfWeekValue = parsMap.getOrElse("dayOfWeek", "---")
    val dayOfWeek = dayOfWeekEncoder(dayOfWeekValue)

    val command = parsMap.getOrElse("command", "---")

    println {
      s"""
         |minute        ${minutes.mkString(" ")}\n
         |hour          ${hours.mkString(" ")} \n
         |dayOfMonth    ${daysOfMonth.mkString(" ")} \n
         |month         ${months.mkString(" ")} \n
         |dayOfWeek     ${dayOfWeek.mkString(" ")} \n
         |command       $command
      """.stripMargin
    }
  }


  def parseCronString(inputCron: String) = {

    val tokens = inputCron.split(" ").toList
    val names = List("minute", "hour", "dayOfMonth", "month", "dayOfWeek", "command")
    val resMap: Map[String, String] = names.zip(tokens).toMap
    resMap
  }


  def minuteEncoder(cronMinute: String) = {
    cronMinute match {
      case "*" => Range(0, 60, 1).toList
      case cron if cron.contains("/") => {
        val step = cron.split("/").toList.last.toInt
        Range(0, 60, step).toList
      }
      case _ => cronMinute.split(",").toList.map { el: String =>
        el match {
          case cron if cron.contains("-") =>
            val startEnd = cron.split("-").toList
            val start = startEnd.head.toInt
            val end = startEnd.last.toInt
            if (start >= 0 && start < 60 && end > start && end < 60) (start to end).toList.mkString(" ")
            else throw new MinuteException

          case _ =>
            val number = el.toInt
            if (number >= 0 && number < 60) number
            else throw new MinuteException
        }
      }
    }
  }

  def hourEncoder(cronHour: String) = {
    cronHour match {
      case "*" => Range(0, 24, 1).toList
      case cron if cron.contains("/") => {
        val step = cron.split("/").toList.last.toInt
        Range(0, 24, step).toList
      }
      case _ => cronHour.split(",").toList.map { el: String =>
        el match {
          case cron if cron.contains("-") =>
            val startEnd = cron.split("-").toList
            val start = startEnd.head.toInt
            val end = startEnd.last.toInt
            if (start >= 0 && start < 24 && end > start && end < 24) (start to end).toList.mkString(" ")
            else throw new HourException

          case _ =>
            val number = el.toInt
            if (number >= 0 && number < 24) number
            else throw new HourException

        }
      }
    }
  }

  def dayOfMonthEncoder(cronDayOfMonth: String) = {
    cronDayOfMonth match {
      case "*" => Range(1, 32, 1).toList
      case cron if cron.contains("/") => {
        val step = cron.split("/").toList.last.toInt
        Range(0, 32, step).toList
      }
      case _ => cronDayOfMonth.split(",").toList.map { el: String =>
        el match {
          case cron if cron.contains("-") =>
            val startEnd = cron.split("-").toList
            val start = startEnd.head.toInt
            val end = startEnd.last.toInt
            if (start >= 1 && start < 32 && end > start && end < 32) (start to end).toList.mkString(" ")
            else throw new DayOfMonthException

          case _ =>
            val number = el.toInt
            if (number >= 1 && number < 32) number
            else throw new DayOfMonthException

        }
      }
    }

  }

  def monthEncoder(cronMonth: String) = {
    cronMonth match {
      case "*" => Range(1, 13, 1).toList
      case cron if cron.contains("/") => {
        val step = cron.split("/").toList.last.toInt
        Range(1, 13, step).toList
      }
      case _ => cronMonth.split(",").toList.map { el: String =>
        el match {
          case cron if cron.contains("-") =>
            val startEnd = cron.split("-").toList
            val start = startEnd.head.toInt
            val end = startEnd.last.toInt
            if (start >= 1 && start < 13 && end > start && end < 13) (start to end).toList.mkString(" ")
            else throw new MonthException

          case _ =>
            val number = el.toInt
            if (number >= 1 && number < 13) number
            else throw new MonthException
        }
      }
    }
  }


  def dayOfWeekEncoder(cronDayOfWeek: String) = {
    cronDayOfWeek match {
      case "*" => Range(1, 8, 1).toList
      case cron if cron.contains("/") => {
        val step = cron.split("/").toList.last.toInt
        Range(1, 8, step).toList
      }
      case _ => cronDayOfWeek.split(",").toList.map { el =>
        el match {
          case cron if cron.contains("-") =>
            val startEnd = cron.split("-").toList
            val start = startEnd.head.toInt
            val end = startEnd.last.toInt
            if (start >= 1 && start < 8 && end > start && end < 8) (start to end).toList.mkString(" ")
            else throw new DayOfWeekException

          case _ =>
            val number = el.toInt
            if (number >= 1 && number < 8) number
            else throw new DayOfWeekException
        }
      }
    }

  }
}
