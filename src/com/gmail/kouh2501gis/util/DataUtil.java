package com.gmail.kouh2501gis.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
    /**
     * 曜日を文字列で返す
     * @param cal
     * @return
     */
    static public String getDayOfWeekString(Calendar cal)
    {
    	String result = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
        	result = "日";
        	break;
        case Calendar.MONDAY:
        	result = "月";
        	break;
        case Calendar.TUESDAY:
        	result = "火";
        	break;
        case Calendar.WEDNESDAY:
        	result = "水";
        	break;
        case Calendar.THURSDAY:
        	result = "木";
        	break;
        case Calendar.FRIDAY:
        	result = "金";
        	break;
        case Calendar.SATURDAY:
        	result = "土";
        	break;
        }
        return result;
    }
    
    /**
     * 日付の文字列を返す
     * @param cal
     * @return 書式：YYYY/MM/DD
     */
    static public String getDayForString(Calendar cal)
    {
    	return kDayFormat.format(cal.getTime());
    }
    
    /**
     * 時間の文字列を返す
     * @param cal
     * @return 書式：HH:MM
     */
    static public String getTimeForString(Date date)
    {
    	return kTimeFormat.format(date);
    }
    
    private static final SimpleDateFormat kTimeFormat = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat kDayFormat = new SimpleDateFormat("yyyy/MM/dd");
}
