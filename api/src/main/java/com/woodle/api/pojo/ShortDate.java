package com.woodle.api.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 精度为天的数据类型. 特点: 内存小,性能高, 精确到天. 完整,可信.
 * 此类不仅作用于性能敏感场合, 更重要的是约定日期的精度.
 *
 * User: wuqingchao
 * Time: 14-5-18 下午6:38
 */
public class ShortDate implements Serializable, Comparable<ShortDate> {

    private static final long serialVersionUID = -223324363802995L;

    /* yyyyMMdd 的数字表现形式.*/
    private int intValue;

    /**
     * 用于json序列化初始化, 严禁调用.
     */
    @Deprecated
    public ShortDate() {
    }

    public ShortDate(int intValue) {
        this.intValue = intValue;
    }

    /**
     * 返回 yyyyMMdd 的数字表现形式.
     *
     */
    public int getIntValue() {
        return intValue;
    }

    public int getMonthOfYear() {
        return intValue / 100 % 100;
    }

    @SuppressWarnings("deprecation")
    public int getDayOfWeek() {
        return toDate().getDate();
    }


    /**
     * 返回 yyyy-MM-dd 的字符串表现形式
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return (int) (Math.floor(intValue / 10000)) + "-" + fill((int) Math.floor(intValue / 100 % 100)) + "-"
                + fill((int) Math.floor(intValue % 100));
    }

    /**
     * 返回 yyyy-MM-dd hh:mm:ss 的字符串表现形式
     *
     * @see java.lang.Object#toString()
     */
    public String toTimeString() {
        return (int) (Math.floor(intValue / 10000)) + "-" + fill((int) Math.floor(intValue / 100 % 100)) + "-"
                + fill((int) Math.floor(intValue % 100)) +" 00:00:00";
    }

    /**
     * 返回Date类型
     */
    public Date toDate() {
        return parseDate(intValue);
    }

    @Override
    public int hashCode() {
        return intValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShortDate) {
            return intValue == ((ShortDate) obj).intValue;
        }
        return false;
    }


    public static ShortDate today(){
        return valueOf(new Date());
    }

    /**
     * 使用 yyyy-MM-dd 格式转义
     *
     * @param str
     * @return
     */
    public static ShortDate valueOf(String str) {
        if (str == null)
            throw new IllegalArgumentException("null string");
        if (str.length() != 10)
            throw new IllegalArgumentException("QunarDate format must be yyyy-MM-dd: " + str);
        int year = Integer.parseInt(str.substring(0, 4));
        int month = Integer.parseInt(str.substring(5, 7));
        int day = Integer.parseInt(str.substring(8, 10));

        return valueOf(year * 10000 + month * 100 + day);
    }

    /**
     * 从Date转义
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static ShortDate valueOf(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();

        return new ShortDate(year * 10000 + month * 100 + day);
    }

    /**
     * @param numDate
     * @return
     */
    public static ShortDate valueOf(int numDate) {
        return new ShortDate(normalize(numDate));
    }

    @SuppressWarnings("deprecation")
    public static int normalize(int numDate) {
        Date date = parseDate(numDate);
        return (date.getYear() + 1900) * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
    }

    @Override
    public int compareTo(ShortDate o) {
        int thisVal = this.intValue;
        int anotherVal = o.intValue;
        return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
    }

    public boolean isAfter(ShortDate o) {
        return this.intValue > o.intValue;
    }

    public boolean isBefore(ShortDate o) {
        return this.intValue < o.intValue;
    }

    public ShortDate addMonths(int amount) {
        return add(toDate().getTime(), Calendar.MONTH, amount);
    }

    public ShortDate addWeeks(int amount) {
        return add(toDate().getTime(), Calendar.WEEK_OF_YEAR, amount);
    }

    public ShortDate addDays(int amount) {
        return add(toDate().getTime(), Calendar.DAY_OF_MONTH, amount);
    }

    private static ShortDate add(long date, int calendarField, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        c.add(calendarField, amount);
        return valueOf(c.getTime());
    }

    @SuppressWarnings("deprecation")
    // 这个实现使用了废弃的方法,性能需要.总之此处较特殊,切勿模仿.
    public static Date parseDate(int numDate) {
        return new Date((int) (Math.floor(numDate / 10000)) - 1900, (int) Math.floor(numDate / 100 % 100) - 1,
                (int) Math.floor(numDate % 100));
    }

    private static String fill(int i) {
        if (i < 10)
            return "0" + i;
        else
            return Integer.toString(i, 10);
    }


}
