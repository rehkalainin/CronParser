
object App {
  def main(args: Array[String]) = {


    val readLineCron = args(0)

    try {
      val cronParser = CronParser(readLineCron)

      val res = cronParser.run()
    } catch {
      case e: MinuteException => println(e)
      case e: HourException => println(e)
      case e: DayOfMonthException => println(e)
      case e: MonthException => println(e)
      case e: DayOfWeekException => println(e)
      case e: Exception => println(e)
    }

  }
}
