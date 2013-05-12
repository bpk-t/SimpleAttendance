package com.gmail.kouh2501gis.util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
    /**
     * —j“ú‚ğ•¶š—ñ‚Å•Ô‚·
     * @param cal
     * @return
     */
    static public String getDayOfWeekString(Calendar cal)
    {
    	String result = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
        	result = "“ú";
        	break;
        case Calendar.MONDAY:
        	result = "Œ";
        	break;
        case Calendar.TUESDAY:
        	result = "‰Î";
        	break;
        case Calendar.WEDNESDAY:
        	result = "…";
        	break;
        case Calendar.THURSDAY:
        	result = "–Ø";
        	break;
        case Calendar.FRIDAY:
        	result = "‹à";
        	break;
        case Calendar.SATURDAY:
        	result = "“y";
        	break;
        }
        return result;
    }
    
    /**
     * “ú•t‚Ì•¶š—ñ‚ğ•Ô‚·
     * @param cal
     * @return ‘®FYYYY/MM/DD
     */
    static public String getDayForString(Calendar cal)
    {
    	MessageFormat mf = new MessageFormat("{0,date,yyyy/MM/dd}");
    	return mf.format(new Object[]{cal.getTime()});
    }
    
    /**
     * ŠÔ‚Ì•¶š—ñ‚ğ•Ô‚·
     * @param cal
     * @return ‘®FHH:MM
     */
    static public String getTimeForString(Date date)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(date);
    }
}
