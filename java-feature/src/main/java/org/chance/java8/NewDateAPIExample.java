package org.chance.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * Created by gengchao on 16/6/18.
 * 新的时间及日期API位于java.time包中.不可变且线程安全的
 * <ul>
 * <li>{@link Instant} 瞬时时间,时间戳 </li>
 * <li>{@link LocalDate} 不包含具体时间的日期</li>
 * <li>{@link LocalTime} 它代表的是不含日期的时间</li>
 * <li>{@link LocalDateTime} ——它包含了日期及时间，不过还是没有偏移信息或者说时区。</li>
 * <li>{@link ZonedDateTime} 这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。</li>
 * <li>{@link ZoneId} 某个特定的时区 </li>
 * <li>{@link ZoneOffset} 时区偏移 </li>
 * <li>{@link DateTimeFormatter} 格式化时间</li>
 * <li>{@link Duration} 持续时间 </li>
 * <li>{@link MonthDay} 月日</li>
 * <li>{@link YearMonth} 年月</li>
 * <li>{@link Period} 计算两个给定的日期之间包含多少天，多少周或者多少年</li>
 * </ul>
 */
public class NewDateAPIExample {

    private static class ClockClient{
        public static void main(String[] args) {

            Clock clock = Clock.systemDefaultZone();
            long millis = clock.millis();
            System.out.println("millis = " + millis);
            System.out.println("System.currentTimeMillis() = " +
                    System.currentTimeMillis());

            System.out.println("----------------------------------------");
            Instant instant = clock.instant();
            Date dateFromInstant = Date.from(instant);
            int nanoTime = instant.getNano();
            System.out.println("instant = " + instant.getEpochSecond());
            System.out.println("nanoTime = " + nanoTime);

            // ZoneId
            System.out.println("--------------------------------------------");
            System.out.println(ZoneId.getAvailableZoneIds());
            ZoneId zone1 = ZoneId.of("Europe/Berlin");
            ZoneId zone2 = ZoneId.of("Brazil/East");
            System.out.println("zone1 = " + zone1.getRules());
            System.out.println("zone2 = " + zone2.getRules());

            // LocalTime
            System.out.println("--------------------------------------------");
            LocalTime now1 = LocalTime.now(zone1);
            LocalTime now2 = LocalTime.now(zone2);
            LocalTime now3 = LocalTime.now();
            System.out.println("now1 = " + now1);
            System.out.println("now2 = " + now2);
            System.out.println("now3 = " + now3);
            System.out.println(now1.isBefore(now2));

            long hoursBetween = ChronoUnit.HOURS.between(now1,now2);
            long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
            System.out.println("hoursBetween = " + hoursBetween);
            System.out.println("minutesBetween = " + minutesBetween);

            LocalTime late = LocalTime.of(23, 59, 59);
            System.out.println("late = " + late);

            DateTimeFormatter germanFormatter =
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                    .withLocale(Locale.GERMAN);
            LocalTime leetTime = LocalTime.parse("12:37", germanFormatter);
            System.out.println("leetTime = " + leetTime);

            // LocalDate
            System.out.println("--------------------------------------------");
            LocalDate today = LocalDate.now();
            System.out.println("today = " + today.plus(1,ChronoUnit.YEARS));

            today.getYear();
            today.getMonth();
            LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
            LocalDate yesterday = tomorrow.minusDays(2);
            LocalDate indenpendenceDay =
                    LocalDate.of(2014, Month.JULY, 4);
            DayOfWeek dayOfWeek = indenpendenceDay.getDayOfWeek();
            System.out.println("dayOfWeek = " + dayOfWeek);
            boolean b = indenpendenceDay.isEqual(LocalDate.of(2014, Month.JULY,
                    4));
            System.out.println("b = " + b);

            // DateTimeFormatter
            DateTimeFormatter dateTimeFormatter
                    =DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .withLocale(Locale.GERMAN);
            LocalDate localDate = LocalDate.parse("24.12.2014", dateTimeFormatter);
            System.out.println("localDate = " + localDate);

            // LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.of(2014,Month.DECEMBER,31,23,59,59);
            DayOfWeek dayOfWeek1 = localDateTime.getDayOfWeek();
            System.out.println("dayOfWeek1 = " + dayOfWeek1);

            Month month = localDateTime.getMonth();
            System.out.println("month = " + month);

            long minuteOfDay = localDateTime.getLong(ChronoField.MINUTE_OF_DAY);
            System.out.println("minuteOfDay = " + minuteOfDay);


            // Instant
            Instant instant1 = localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant();

            // Instant to Date
            Date date = Date.from(instant1);
            System.out.println("date = " + date);

            //DateTimeFormatter
            DateTimeFormatter formatter
                    = DateTimeFormatter
                    .ofPattern("MM dd, yyyy - HH:mm");
            LocalDateTime parsed = LocalDateTime.parse("11 03, 2014 - 07:13",
                    formatter);
            String string = formatter.format(parsed);
            System.out.println("string = " + string);

            System.out.println("----------------------------------------");
            YearMonth currentYm = YearMonth.now();
            System.out.println("currentYm = " + currentYm);
            System.out.println("currentYm.lengthOfMonth() = "
                    + currentYm.lengthOfMonth());

            System.out.println("----------------------------------------");
            Period period = Period.between(today,LocalDate.of(2014,4,14));
            System.out.println("period = " + period.getMonths());

            //OffsetDateTime ZoneOffset ZoneDateTime
            System.out.println("----------------------------------------");
            LocalDateTime dateTime = LocalDateTime.now();
            ZoneOffset offset = ZoneOffset.of("+05:30");
            OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime,offset);
            System.out.println("offsetDateTime = " + offsetDateTime);

            // Instant
            System.out.println("----------------------------------------");
            Instant timestamp = Instant.now();
            System.out.println("timestamp = " + timestamp);
            Date d = Date.from(timestamp);
            Instant timestamp1 = d.toInstant();
            System.out.println("timestamp1 = " + timestamp1);

            // DateTimeFormatter
            System.out.println("----------------------------------------");
            System.out.println("LocalDate.parse() = " + LocalDate.parse
                    ("20160908",DateTimeFormatter.BASIC_ISO_DATE));
            System.out.println("LocalDate.parse() = " + LocalDate.parse(
                    "九月 18 2014",DateTimeFormatter.ofPattern("MMM dd yyyy")));

            System.out.printf("Arriving at: %s %n",LocalDateTime.now().format
                    (DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a")));

        }
    }
}
