package com.gmail.kouh2501gis.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
    /**
     * �j���𕶎���ŕԂ�
     * @param cal
     * @return
     */
    static public String getDayOfWeekString(Calendar cal)
    {
    	String result = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
        	result = "��";
        	break;
        case Calendar.MONDAY:
        	result = "��";
        	break;
        case Calendar.TUESDAY:
        	result = "��";
        	break;
        case Calendar.WEDNESDAY:
        	result = "��";
        	break;
        case Calendar.THURSDAY:
        	result = "��";
        	break;
        case Calendar.FRIDAY:
        	result = "��";
        	break;
        case Calendar.SATURDAY:
        	result = "�y";
        	break;
        }
        return result;
    }
    
    /**
     * ���t�̕������Ԃ�
     * @param cal
     * @return �����FYYYY/MM/DD
     */
    static public String getDayForString(Calendar cal)
    {
    	return kDayFormat.format(cal.getTime());
    }
    
    /**
     * ���Ԃ̕������Ԃ�
     * @param cal
     * @return �����FHH:MM
     */
    static public String getTimeForString(Date date)
    {
    	return kTimeFormat.format(date);
    }
    
    private static final SimpleDateFormat kTimeFormat = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat kDayFormat = new SimpleDateFormat("yyyy/MM/dd");
}
