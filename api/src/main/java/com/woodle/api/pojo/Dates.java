package com.woodle.api.pojo;

import java.util.Date;

/**
 * 日期类型各种形式的字符串输出
 * User: wuqingchao
 * Time: 14-5-23 下午10:58
 */
public class Dates {

    private static final String[] MONTH = {"January","February","March","April","May","June","July","August","September","October","November","December"};

    /* 周日作为一周第一天 */
    private static final String[] WEEK = {"Sunday","Monday","Tuesday","Wednesday","Thursday", "Friday", "Saturday"};


    private int showMonthLenght = 3;

    private int showWeekLength = 2;

    private boolean showMonthAllLetterUppercase = true;

    private boolean showMonthFirstLetterUppercase = false;

    private boolean showWeekAllLetterUppercase = true;

    private boolean showWeekFirstLetterUppercase = false;

    private int showOrderOfMonth = 3;

    private int showOrderOfDay = 2;

    private int showOrderOfWeek = 1;




    //private static final String[] WEEK_SHORT = {"SU","MO","TU","WE","TH","FR","SA"};








}
