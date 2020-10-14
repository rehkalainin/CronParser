import org.scalatest.flatspec.AnyFlatSpec

class CronParserTest extends AnyFlatSpec {

  behavior of "Cron Parser"

  val cronString = "*/15 0 1,15 * 1-5 /usr/bin/find"

  val cronParser = CronParser(cronString)

  it should "correct parsing from input to Map" in {
    assert(cronParser.parseCronString(cronString) === Map("minute" -> "*/15",
      "hour" -> "0",
      "dayOfMonth" -> "1,15",
      "month" -> "*",
      "dayOfWeek" -> "1-5",
      "command" -> "/usr/bin/find")
    )
  }

  it should "have correct parsing values to time " in {
    assert(cronParser.minuteEncoder("*/15") === List(0, 15, 30, 45))
    assert(cronParser.hourEncoder("0") === List(0))
    assert(cronParser.dayOfMonthEncoder("1,15") === List(1, 15))
    assert(cronParser.monthEncoder("*") === List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
    assert(cronParser.dayOfWeekEncoder("1-5") === List("1 2 3 4 5"))
  }

  behavior of "MinuteEncoder"

  it should "have sequence minutes" in {
    assert(cronParser.minuteEncoder("*/15") === List(0, 15, 30, 45))
    assert(cronParser.minuteEncoder("1,15") === List(1, 15))
    assert(cronParser.minuteEncoder("1-5") === List("1 2 3 4 5"))
    assert(cronParser.minuteEncoder("0") === List(0))
  }

  it should "have MinuteException" in {
    assertThrows[MinuteException] {
      cronParser.minuteEncoder("60")
    }
    assertThrows[MinuteException] {
      cronParser.minuteEncoder("1-70")
    }
    assertThrows[MinuteException] {
      cronParser.minuteEncoder("25-24")
    }
  }

  it should "have NumberFormatException" in {
    assertThrows[NumberFormatException] {
      cronParser.minuteEncoder(" ")
    }
    assertThrows[NumberFormatException] {
      cronParser.minuteEncoder("a")
    }
    assertThrows[NumberFormatException] {
      cronParser.minuteEncoder("1-b")
    }
  }
}


