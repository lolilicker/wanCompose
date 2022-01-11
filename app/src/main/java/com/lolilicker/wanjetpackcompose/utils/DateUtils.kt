package com.lolilicker.wanjetpackcompose.utils

import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 日期时间相关的工具类。
 */
object DateUtils {
    /**
     * 豆瓣阅读史前时代的开始点。用于增量数据更新的默认起始时间。
     * 定为 2012年5月7日吧。
     */
    val START_OF_ARK_ERA = Date(1336320000000L)

    /**
     * 2038问题判断的点。在应用里需要用到「长期有效」的时间判断的时候，可能会遇到2038problem
     * 这里设定 2038年1月1日作为判断是否过了 2038 的点。如果超过这个点，可以认为是「永久有效」（例如礼券）。
     *
     *
     * 当然到了2038年的话大概这里会成为一个bug。。
     */
    val DATE_OVERFLOW_ISSUE_POINT = Date(2145888000000L)
    const val ONE_SECOND_TIME: Long = 1000 // 一秒
    const val ONE_MINUTE_TIME = (60 * 1000 // 一分钟
            ).toLong()
    const val ONE_HOUR_TIME = 60 * ONE_MINUTE_TIME // 一小时
    const val ONE_DAY_TIME = 24 * ONE_HOUR_TIME // 一天
    const val ONE_MONTH_TIME = 30 * ONE_DAY_TIME // 一个月
    const val ONE_WEEK_TIME = 7 * ONE_DAY_TIME // 一个月
    private val TAG = DateUtils::class.java.simpleName
    private val sISO8601Format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
    private val sYearMonthFormat = SimpleDateFormat("yyyy年MM月")
    private val sMonthDayFormat = SimpleDateFormat("M月d日")
    private val sDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val sDateFormatWithoutYear = SimpleDateFormat("MM-dd")
    private val sDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    private val sDateFormatWithUnit = SimpleDateFormat("yyyy年MM月dd日")
    private val sDateTimeFormatWithUnit = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
    private val sTimeFormat = SimpleDateFormat("HH:mm:ss")
    private val sTimeFormatWithoutSecond = SimpleDateFormat("HH:mm")
    private val sTimeFormatWithoutHour = SimpleDateFormat("mm:ss")

    /**
     * 将ISO8601格式的字符串转换为Date
     *
     * @param timeStr ISO8601格式的字符串
     * @return date Date对象
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun parseIso8601(timeStr: String?): Date? {
        var date: Date? = null
        if (timeStr?.isNotEmpty() == true) {
            date = sISO8601Format.parse(timeStr)
        }
        return date
    }

    /**
     * 时间戳转化为ISO8601字符串形式
     *
     * @param timestamp 时间戳
     * @return ISO8601格式的的字符串
     */
    fun formatIso8601(timestamp: Long): String {
        return formatIso8601(Date(timestamp))
    }

    /**
     * 转化为ISO8601字符串形式
     *
     * @param date 时间
     * @return ISO8601格式的的字符串
     */
    fun formatIso8601(date: Date?): String {
        return sISO8601Format.format(date)
    }

    /**
     * 转化为输出年和月的字符串
     * 如：2015年11月
     *
     * @param date 时间
     * @return simpleFormat yyyy-MM-dd
     */
    fun formatYearMonth(date: Date?): String {
        var dateString = ""
        try {
            dateString = sYearMonthFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateString
    }

    /**
     * 转化为输出月和日的字符串
     * 如：5月29日
     *
     * @param date 时间
     * @return simpleFormat MM-dd
     */
    fun formatMonthDay(date: Date?): String {
        var dateString = ""
        try {
            dateString = sMonthDayFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return dateString
    }

    /**
     * 转化输出时间字符串
     *
     * @param date 时间
     * @return simpleFormat yyyy-MM-dd
     */
    fun formatPrettyDate(date: Date?): String {
        val timeSpendInMill = millisElapsed(date)
        return if (timeSpendInMill < ONE_HOUR_TIME) {
            getMinutesSince(date).toString() + " 分钟前"
        } else if (timeSpendInMill <= ONE_DAY_TIME) {
            getHoursSince(date).toString() + " 小时前"
        } else if (timeSpendInMill <= ONE_MONTH_TIME) {
            formatMonthDay(date)
        } else {
            formatDate(date)
        }
    }

    fun formatPrettyDateTime(date: Date?): String {
        val timeSpendInMill = millisElapsed(date)
        return if (timeSpendInMill < ONE_HOUR_TIME) {
            getMinutesSince(date).toString() + " 分钟前"
        } else if (timeSpendInMill <= ONE_DAY_TIME) {
            getHoursSince(date).toString() + " 小时前"
        } else if (timeSpendInMill <= ONE_MONTH_TIME) {
            getDaysSince(date).toString() + " 天前"
        } else if (isSameYear(
                date,
                Date(System.currentTimeMillis())
            )
        ) {
            formatDateWithoutYear(date)
        } else {
            formatDate(date)
        }
    }

    /**
     * 转化输出时间字符串
     *
     * @param date 时间
     * @return simpleFormat yyyy-MM-dd
     */
    fun formatDate(date: Date?): String {
        var timeString = ""
        try {
            timeString = sDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    fun formatDateWithoutYear(date: Date?): String {
        var timeString = ""
        try {
            timeString = sDateFormatWithoutYear.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    /**
     * 转化输出时间字符串
     *
     * @param date 时间
     * @return formatDateWithUnit yyyy年MM月dd日
     */
    fun formatDateWithUnit(date: Date?): String {
        var dateString = ""
        try {
            dateString = sDateFormatWithUnit.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return dateString
    }

    /**
     * 转化输出时间字符串
     *
     * @param date 时间
     * @return formatDateWithUnit yyyy年MM月dd日 HH:mm
     */
    fun formatDateTimeWithUnit(date: Date?): String {
        var dateString = ""
        try {
            dateString = sDateTimeFormatWithUnit.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return dateString
    }

    /**
     * 时间戳转化为字符串
     *
     * @param timeStamp 时间戳
     * @return simpleFormat yyyy-MM-dd
     */
    fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        return formatDate(date)
    }

    /**
     * 时间字符转化
     *
     * @param timeStr format yyyy-MM-dd'T'HH:mm:ssZZZZZ
     * @return simpleFormat yyyy-MM-dd
     */
    @Throws(ParseException::class)
    fun formatDate(timeStr: String?): String {
        val date = parseIso8601(timeStr)
        return formatDate(date)
    }

    /**
     * 时间戳转化为字符串
     *
     * @param timeStamp 时间戳
     * @return simpleFormat yyyy-MM-dd HH:mm
     */
    fun formatDateTime(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        return formatDateTime(date)
    }

    /**
     * 时间转化为字符串
     *
     * @param date 时间
     * @return simpleFormat yyyy-MM-dd HH:mm
     */
    fun formatDateTime(date: Date?): String {
        var timeString = ""
        try {
            timeString = sDateTimeFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return timeString
    }

    /**
     * 时间转化为字符串
     *
     * @param millis 时间
     * @return simpleFormat yyyy-MM-dd HH:mm
     */
    fun formatTime(millis: Long?): String {
        var timeString = ""
        try {
            sTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
            //            timeString = sTimeFormat.format(new Date(Math.abs(millis)));
            timeString = "%02d:%02d:%02d".format(
                TimeUnit.MILLISECONDS.toHours(
                    millis!!
                ),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
            )
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    fun formatTime(date: Date?): String {
        var timeString = ""
        try {
            timeString = sTimeFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    fun formatTimeWithoutSecond(date: Date?): String {
        var timeString = ""
        try {
            timeString = sTimeFormatWithoutSecond.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    fun formatTimeWithoutHour(date: Date?): String {
        var timeString = ""
        try {
            timeString = sTimeFormatWithoutHour.format(date)
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return timeString
    }

    /**
     * 取得指定的Date与现在相差多少天
     *
     * @param date 指定的Date
     */
    fun getDaysSince(date: Date?): Long {
        if (date == null) {
            return 0
        }
        val durationInMillis = System.currentTimeMillis() - date.time
        //float to int 直接取整数，相当于Math.floor
        return Math.abs(durationInMillis / ONE_DAY_TIME)
    }

    fun getHoursSince(date: Date?): Long {
        if (date == null) {
            return 0
        }
        val durationInMillis = System.currentTimeMillis() - date.time
        return durationInMillis / ONE_HOUR_TIME
    }

    fun getHoursBetween(date1: Date?, date2: Date?): Long {
        if (date1 == null || date2 == null) {
            return 0
        }
        val durationInMillis = date1.time - date2.time
        return durationInMillis / ONE_HOUR_TIME
    }

    fun getWeeksBetween(date1: Date?, date2: Date?): String {
        if (date1 == null || date2 == null) {
            return ""
        }
        val durationInMillis = date1.time - date2.time
        val days = durationInMillis / ONE_DAY_TIME
        val week = days / 7
        val modDays = days % 7
        return if (modDays == 0L) {
            "整 $week 周"
        } else {
            "$week 周零 $modDays 天"
        }
    }

    /**
     * 取得从指定的Date到现在经过了多少分钟
     *
     * @param date 指定的Date
     */
    fun getMinutesSince(date: Date?): Long {
        if (date == null) {
            return 0
        }
        val durationInMillis = System.currentTimeMillis() - date.time
        return durationInMillis / (60 * 1000)
    }

    fun getMinutesBetween(date1: Date?, date2: Date?): Long {
        if (date1 == null || date2 == null) {
            return 0
        }
        val durationInMillis = date1.time - date2.time
        return durationInMillis / (60 * 1000)
    }

    /**
     * 取得指定的Date与现在相差多少毫秒
     *
     * @param date 指定的Date
     */
    fun getMilliSecondsSince(date: Date?): Long {
        return if (date == null) {
            0
        } else System.currentTimeMillis() - date.time
        //float to int 直接取整数，相当于Math.floor
    }

    fun getHourMilliSeconds(hour: Int): Long {
        return hour * ONE_HOUR_TIME
    }

    /**
     * 判断当前时间是否是在制定时间范围之内。
     *
     * @param start 范围开始
     * @param end   范围结束
     * @return 如果在制定时间范围之内，返回true，否则返回false。
     */
    fun isInRange(start: Date?, end: Date?): Boolean {
        if (start == null || end == null) {
            return false
        }
        val current = Date()
        return current.after(start) && current.before(end)
    }

    /**
     * 判断当前时间是否比给定的时间更靠前。
     *
     * @param date 给定的时间
     * @return 如果当前时间比给定的时间更靠前，返回true，否则返回false。
     */
    fun isBefore(date: Date?): Boolean {
        if (date == null) {
            return false
        }
        val current = Date()
        return current.before(date)
    }

    /**
     * 给定两个时间点，返回两个时间点经过了多少毫秒。
     *
     * @param start 开始时刻
     * @param end   结束时刻
     * @return 两个时刻之间经过的毫秒数。
     */
    fun millisBetween(start: Date?, end: Date?): Long {
        return if (start == null || end == null) {
            Long.MAX_VALUE
        } else end.time - start.time
    }

    /**
     * 给定两个时间点，返回两个时间点经过了多少分钟。
     *
     * @param start
     * @param end
     * @return 两个时刻之间经过的分钟数
     */
    fun minuteBetween(start: Date?, end: Date?): Long {
        return if (start == null || end == null) {
            Int.MAX_VALUE.toLong()
        } else (end.time - start.time) / ONE_MINUTE_TIME
    }

    fun secondsBetween(start: Date?, end: Date?): Long {
        return if (start == null || end == null) {
            Int.MAX_VALUE.toLong()
        } else (end.time - start.time) / ONE_SECOND_TIME
    }

    /**
     * 给定一个时间点，返回到当前时间经过了多少毫秒。
     *
     * @param date 给定的时间点。
     * @return 从给定的时间点到当前时间经过的毫秒数。
     */
    fun millisElapsed(date: Date?): Long {
        return if (date == null) {
            Long.MAX_VALUE
        } else millisBetween(
            date,
            Date()
        )
    }

    /**
     * 给定一段时间，返回这段时间约等于多少小时多少分钟。
     *
     * @param durationSeconds 注意这里给的 duration 单位是秒
     * @return
     */
    fun hourMinute(durationSeconds: Int): String {
        //此处将 duration * 1000 换算成 毫秒 int 可能会溢出，造成错误
        val duration = durationSeconds.toLong()
        if (duration > 0 && duration * 1000 < ONE_MINUTE_TIME) {
            return "1 分钟"
        }
        val totalHours = duration * 1000 / ONE_HOUR_TIME
        val remainMinute = (duration * 1000 - totalHours * ONE_HOUR_TIME) / ONE_MINUTE_TIME
        val result = StringBuilder()
        if (totalHours > 0) {
            result.append(totalHours).append(" 小时 ")
        }
        result.append(remainMinute).append(" 分钟")
        return result.toString()
    }

    /**
     * 对给定的Date进行整理，如果为空或者比 [.START_OF_ARK_ERA] 更早，
     * 则 round 成 [.START_OF_ARK_ERA]。
     *
     * @param date 要round的Date。
     * @return round后的Date。
     */
    fun round(date: Date?): Date {
        return if (date == null || date.before(START_OF_ARK_ERA)) {
            START_OF_ARK_ERA
        } else {
            date
        }
    }

    fun isSameDay(date1: Date?, date2: Date?): Boolean {
        if (date1 == null || date2 == null) {
            return false
        }
        val fmt = SimpleDateFormat("yyyyMMdd")
        return fmt.format(date1) == fmt.format(date2)
    }

    fun isSameYear(date1: Date?, date2: Date?): Boolean {
        if (date1 == null || date2 == null) {
            return false
        }
        val fmt = SimpleDateFormat("yyyy")
        return fmt.format(date1) == fmt.format(date2)
    }

    /**
     * 转化输出时间字符串
     *
     * @param date
     * @return 大于三天：  x 天 x 时 x 分，
     * 小于三天：hh-mm-ss
     */
    fun formatDateTimeAboveThreeDaysSince(date: Date?): String {
        return if (getDaysSince(date) > 2) {
            //大于三天
            val daysLeft =
                Math.abs(getDaysSince(date))
            val dateAfterThreeDays =
                Date(System.currentTimeMillis() + ONE_DAY_TIME * 3)
            val hoursLeft = Math.abs(
                getHoursBetween(
                    date,
                    dateAfterThreeDays
                )
            )
            val dateAfterThreeDaysHours =
                Date(System.currentTimeMillis() + ONE_DAY_TIME * 3 + hoursLeft * ONE_HOUR_TIME)
            val minutesLeft = Math.abs(
                getMinutesBetween(
                    date,
                    dateAfterThreeDaysHours
                )
            )
            "$daysLeft 天 $hoursLeft 时 $minutesLeft 分"
        } else {
            formatTime(
                Math.abs(
                    getMilliSecondsSince(
                        date
                    )
                )
            )
        }
    }
}