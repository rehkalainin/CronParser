
    Cron Expression Parser takes a string as input (line must consist of 6 expressions separated by spaces)
and parses a cron string for expanding each field to show the times at which it will run.

example for starting App:
~$ sbt
> run "* * * * * /usr/bin/find"


* * * * * /usr/bin/find
| | | | | |
| | | | | +--- command
| | | | +----- dayOfWeek   (diapason: 1-7)
| | | +------- months      (diapason: 1-12)
| | +--------- daysOfMonth (diapason: 1-31)
| +----------- hours       (diapason: 0-23)
+------------- minutes     (diapason: 0-59)

You can use:
*         - all diapason,
*/x (*/2) - all values from diapason with step number (2), x:Int
x-y (1-3) - all values from diapason from x to y (from 1 to 3), x,y: Int
x,y,z (1,3,7)    - sequence numbers which contain in diapason, x,y,z:Int

you can combine

x,y-z

example:
~$ sbt
> run "*/15 0 1,15 * 1-5 /usr/bin/find"

output:

minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find



