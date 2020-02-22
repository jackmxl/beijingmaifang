package com.study.boot1.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


/**
 * <pre>
 *  Symbol   Meaning                 Presentation        Example
 *  ------   -------                 ------------        -------
 *  G        era designator          (Text)              AD
 *  y        year                    (Number)            1996
 *  M        month in year           (Text & Number)     July & 07
 *  d        day in month            (Number)            10
 *  h        hour in am/pm (1~12)    (Number)            12
 *  H        hour in day (0~23)      (Number)            0
 *  m        minute in hour          (Number)            30
 *  s        second in minute        (Number)            55
 *  S        millisecond             (Number)            978
 *  E        day in week             (Text)              Tuesday
 *  D        day in year             (Number)            189
 *  F        day of week in month    (Number)            2 (2nd Wed in July)
 *  w        week in year            (Number)            27
 *  W        week in month           (Number)            2
 *  a        am/pm marker            (Text)              PM
 *  k        hour in day (1~24)      (Number)            24
 *  K        hour in am/pm (0~11)    (Number)            0
 *  z        time zone               (Text)              Pacific Standard Time
 *  '        escape for text         (Delimiter)
 *  ''       single quote            (Literal)           '
 *
 *  The count of pattern letters determine the format.
 * (Text): 4 or more pattern letters--use full form, < 4--use short or abbreviated form if one exists.
 *
 * (Number): the minimum number of digits. Shorter numbers are zero-padded to this amount. Year is handled specially; that is, if the count of 'y' is 2, the Year will be truncated to 2 digits.
 *
 * (Text & Number): 3 or over, use text, otherwise use number.
 *
 * Any characters in the pattern that are not in the ranges of ['a'..'z'] and ['A'..'Z'] will be treated as quoted text. For instance, characters like ':', '.', ' ', '#' and '@' will appear in the resulting time text even they are not embraced within single quotes.
 *
 * A pattern containing any invalid pattern letter will result in a thrown exception during formatting or parsing.
 *
 * Examples Using the US Locale:
 *
 *  Format Pattern                         Result
 *  --------------                         -------
 *  "yyyy.MM.dd G 'at' hh:mm:ss z"    ->>  1996.07.10 AD at 15:08:56 PDT
 *  "EEE, MMM d, ''yy"                ->>  Wed, July 10, '96
 *  "h:mm a"                          ->>  12:08 PM
 *  "hh 'o''clock' a, zzzz"           ->>  12 o'clock PM, Pacific Daylight Time
 *  "K:mm a, z"                       ->>  0:00 PM, PST
 *  "yyyyy.MMMMM.dd GGG hh:mm aaa"    ->>  1996.July.10 AD 12:08 PM
 * </pre>
 * Title:
 * Description: date & string translate.
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Stephen Guo
 */
public class DateUtil
{
	public static String format = "yyyy-MM-dd HH:mm:ss";
	public static String format1 = "yyyy-MM-dd";
	public static String format2 = "yyyy-MM-dd HH24:mm:ss";

	public static String format3 = "yyyy-MM-dd HH24:mm:ss.S";
	public static String format4 = "MM-dd HH:mm:ss";
	public static String format5 = "dd日HH:mm";
	public static String format6 = "yyyy.MM.dd";
	/**
	 *  construct function
	 */

	/**
	 * parse a String to a java.util.Date object
	 */
	public static Date parseDate(String date, String format)
	{
		SimpleDateFormat formatter=new SimpleDateFormat(format,java.util.Locale.US);
		ParsePosition pos=new ParsePosition(0);
		Date ret=formatter.parse(date,pos);
		return ret;
	}



	/**
	 * format a java.util.Date object to a String
	 */
	public static String formatDate(Date date,String format)
	{
		if (date == null)
			return "" ;
		SimpleDateFormat formatter=new SimpleDateFormat(format,java.util.Locale.US);
		String ret=formatter.format(date);
		return ret;
	}

	@SuppressWarnings("static-access")
	public static String formatDate(java.util.Date date)
	{
		if (date == null)
			return "" ;
		java.util.Calendar ca = java.util .Calendar .getInstance ();
		ca.setTime (date);
		String datestr = ca.get (ca.YEAR )+"??"+(ca.get (ca.MONTH )+1)+"??" +ca.get (ca.DAY_OF_MONTH )+"??";
		return datestr ;
	}


	public static String formatOracleDate ( String dateStr ) {
		return "to_date('yyyy-mm-dd','dateStr')";
	}

	public static String dateForPages(Date date)
	{
		return DateUtil.formatDate(date,"yyyy-MM-dd,HH:mm:ss");
	}

	@SuppressWarnings("static-access")
	public static java.util.Date addMonth(java.util.Date date,int n)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		for(int i=0;i<n;i++)
		{
			ca.roll(Calendar.MONTH,true);
			int month = ca.get (ca.MONTH );
			if (month == 0)
			{
				ca.roll (ca.YEAR ,true);
			}
		}
		return ca.getTime();
	}

	@SuppressWarnings("static-access")
	public static java.util.Date delMonth(java.util.Date date,int n)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		for(int i=0;i<n;i++)
		{
			ca.roll(Calendar.MONTH,false);
			int month = ca.get (ca.MONTH );
			if (month == 11)
			{
				ca.roll (ca.YEAR ,false);
			}

		}
		return ca.getTime();
	}

	@SuppressWarnings("static-access")
	public static java.util.Date addDay(java.util.Date date,int n)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		for(int i=0;i<n;i++)
		{
			ca.roll(Calendar.DAY_OF_YEAR,true);
			int day = ca.get (ca.DAY_OF_YEAR );
			if (day == 1)
			{
				ca.roll(Calendar.YEAR,true);
			}
		}
		return ca.getTime();
	}

	@SuppressWarnings("static-access")
	public static java.util.Date delDay(java.util.Date date,int n)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		for(int i=0;i<n;i++)
		{
			int day = ca.get (ca.DAY_OF_YEAR );
			ca.roll(Calendar.DAY_OF_YEAR,false);
			if (day == 1)
			{
				ca.roll(Calendar.YEAR,false);
			}
		}
		return ca.getTime();
	}

	public static java.util.Calendar addMonth(int month)
	{
		Calendar ca=Calendar.getInstance();
		/*
		for (int i =0;i < month;i++)
		{
			ca.roll (ca.MONTH ,true);
		}
		return ca ;
		*/
		java.util .Date d =  addMonth(ca.getTime (),month);
		ca.setTime (d);
		return ca ;

	}

	public static java.util.Calendar rollTime(int field,int time)
	{
		Calendar ca=Calendar.getInstance();
		return rollTime(ca,field,time);
	}
	public static java.util.Calendar rollTime(Calendar ca ,int field,int time)
	{
		//System.out.println("  0------- rollTime(Calendar "+ca+" ,int "+field+",int "+time+")");
		//int md = ca.DAY_OF_YEAR;
		//int mm = ca.MONTH ;
		switch(field)
		{
		case 6://ca.DAY_OF_YEAR
			ca.setTime (addDay(ca.getTime (),time));
			return ca ;
		case 2 ://ca.MONTH
			ca.setTime (addMonth(ca.getTime (),time));
			return ca ;
		}
		for (int i =0;i < time;i++)
		{
		//	System.out.println(" 1 ------- rollTime(Calendar "+ca+" ,int "+field+",int "+time+")");
			ca.roll (field ,true);


		}
	//	System.out.println(" 2 ------- rollTime(Calendar "+ca+" ,int "+field+",int "+time+")");
		return ca ;
	}

	public static java.util.Calendar addTime(int field,int time)
	{
		Calendar ca=Calendar.getInstance();
		return addTime(ca,field,time);
	}
	public static java.util.Calendar addTime(Calendar ca ,int field,int time)
	{
		 ca.add (field,time);
		 return ca ;
	}

	public static Date getWeekBegin(Date date)
	{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date mm = nDaysAgo(cal.get(Calendar.DAY_OF_WEEK)-2,date);
        return getDayBegin(mm);
    }

	 public static Date nDaysAgo(Integer n, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
		return cal.getTime();
	}

	public static Date getDayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

	public static Date getWeekEnd(Date date)
	{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date mm = nDaysAfter(8-cal.get(Calendar.DAY_OF_WEEK),date);
        return getDayEnd(mm);
    }

	public static Date nDaysAfter(int n,Date date)
	{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+n);
        return cal.getTime();
    }

	protected static Date getDayEnd(Date date)
	{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)+1, 0, 0, 0);
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - 1);
        return cal.getTime();
    }

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	int m_nYear;
	int m_nMonth;
	int m_nDate;

	//2001.07.03
	int m_nHour=0;
	int m_nMinute=0;
	int m_nSecond=0;

	// Static --------------------------------------------------------


	// Constructors --------------------------------------------------

	public DateUtil()
	{

		Calendar ca=Calendar.getInstance();

		m_nYear=ca.get(Calendar.YEAR);
		m_nMonth=ca.get(Calendar.MONTH)+1;
		m_nDate=ca.get(Calendar.DATE);

		m_nHour=ca.get(Calendar.HOUR);
		if(ca.get(Calendar.AM_PM)==Calendar.PM)m_nHour=m_nHour+12;

		m_nMinute=ca.get(Calendar.MINUTE);
		m_nSecond=ca.get(Calendar.SECOND);


	}

	public DateUtil(int y,int m,int d)
	{
		Calendar ca=Calendar.getInstance();
		ca.set(y,m-1,d);
		m_nYear=ca.get(Calendar.YEAR);
		m_nMonth=ca.get(Calendar.MONTH)+1;
		m_nDate=ca.get(Calendar.DATE);

	}

	public DateUtil(int y,int m,int d, int ho,int mi,int se)
	{
		this.m_nYear=y;
		this.m_nMonth=m;
		this.m_nDate=d;
		this.m_nHour=ho;
		this.m_nMinute=mi;
		this.m_nSecond=se;

	}

	public DateUtil(java.util.Date date)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		m_nYear=ca.get(Calendar.YEAR);
		m_nMonth=ca.get(Calendar.MONTH) +1;
		m_nDate=ca.get(Calendar.DATE);
		//
		m_nHour=ca.get(Calendar.HOUR);
		if(ca.get(Calendar.AM_PM)==Calendar.PM)m_nHour=m_nHour+12;

		m_nMinute=ca.get(Calendar.MINUTE);
		m_nSecond=ca.get(Calendar.SECOND);


	}

	public void setDate(java.util.Date date)
	{
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		m_nYear=ca.get(Calendar.YEAR);
		m_nMonth=ca.get(Calendar.MONTH) +1;
		m_nDate=ca.get(Calendar.DATE);
		//
		m_nHour=ca.get(Calendar.HOUR);
		if(ca.get(Calendar.AM_PM)==Calendar.PM)m_nHour=m_nHour+12;

		m_nMinute=ca.get(Calendar.MINUTE);
		m_nSecond=ca.get(Calendar.SECOND);
	}
	// Public --------------------------------------------------------

	public static Date getFirstDayOfMonth()
	{
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.DAY_OF_MONTH, begin.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);

        return begin.getTime();
	}

	public static Date getLastDayOfMonth()
	{
        Calendar ed = Calendar.getInstance();
        ed.set(Calendar.DAY_OF_MONTH, ed.getActualMaximum(Calendar.DAY_OF_MONTH));
        ed.set(Calendar.SECOND, 59);
        ed.set(Calendar.HOUR_OF_DAY, 23);
        ed.set(Calendar.MINUTE, 59);

        return ed.getTime();
	}

	public int getLastDateOfMonth()
	{

		return getLastDateOfMonth(m_nYear,m_nMonth,m_nDate);


	}


	public static int getLastDateOfMonth(int y, int m, int d)
	{
		int[] date={0,31,-1,31,30,31,30,31,31,30,31,30,31};
		if(date[m]>0)return date[m];
		if(y%400==0)return 29;
		else if(y%100!=0&&y%4==0)return 29;
		else return 28;

	}

	public DateUtil getNextDate()
	{
		Calendar ca=Calendar.getInstance();
		ca.set(m_nYear,m_nMonth,m_nDate);

		int nYear=0;
		int nMonth=0;
		int nDate=0;

		if(m_nDate + 1 > getLastDateOfMonth(m_nYear,m_nMonth,m_nDate))
		{
			int year=m_nYear;
			int month=m_nMonth;
			if(m_nMonth + 1> 12){year=m_nYear+1;month=1;}
			else month++;
			ca.set(year,month,1);

			nYear=year;
			nMonth=month;
			nDate=1;

		}
		else
		{

			nYear=m_nYear;
			nMonth=m_nMonth;
			nDate=m_nDate+1;
		}


		return new DateUtil(nYear,nMonth,nDate);
	}

	public DateUtil getNextMonth()
	{
		Calendar ca=Calendar.getInstance();
		ca.set(m_nYear,m_nMonth,m_nDate);
		ca.roll(Calendar.MONTH,true);

		int nYear=ca.get(Calendar.YEAR);
		int nMonth=ca.get(Calendar.MONTH);
		int nDate=ca.get(Calendar.DATE);

		return new DateUtil(nYear,nMonth,nDate);
	}

	public Date getFirstDateOfWeek()
	{
		Calendar ca=Calendar.getInstance();
		ca.set(m_nYear,m_nMonth-1,m_nDate);

		int nowDay = ca.get(Calendar.DAY_OF_WEEK);
		int firstDay = ca.getFirstDayOfWeek();

		Calendar ca2=Calendar.getInstance();
		ca2.set(m_nYear,m_nMonth,m_nDate-(nowDay-firstDay));
		return ca.getTime ();
		//return new Date(m_nYear,m_nMonth,m_nDate-(nowDay-firstDay));

	}

	public long getTimeInMillis()
	{
		Calendar ca=Calendar.getInstance();
		ca.set(m_nYear,m_nMonth-1,m_nDate,m_nHour,m_nMinute,m_nSecond);
		return ca.getTime().getTime();
	}

	@SuppressWarnings("static-access")
	public boolean hasInDate(int y, int m, int d,int y1, int m1,int d1 , int y2, int m2,int d2)
	{

		int longtime1, longtime2,longtime ;
		Calendar ca=Calendar.getInstance();
		ca.set(y,m,d);
		longtime = ca.get(ca.DAY_OF_YEAR);
		ca.set(y1,m1,d1);
		longtime1 = ca.get(ca.DAY_OF_YEAR);
		ca.set(y2,m2,d2);
		longtime2 = ca.get(ca.DAY_OF_YEAR);
		if (( y > y1 ) &&(y< y2))
			return true ;
		else
		if (( y == y2 )&&(longtime <= longtime2))
			return true ;
		else
		if ((y == y1 )&&(longtime >= longtime1))
			return true;
		else
			return false ;
	}
	@SuppressWarnings("static-access")
	public int CompareTime(int y1,int m1,int d1,int y2,int m2,int d2)
	{
		int longtime1, longtime2 ;
		Calendar ca=Calendar.getInstance();
		ca.set(y1,m1,d1);
		longtime1 = ca.get(ca.DAY_OF_YEAR);
		ca.set(y2,m2,d2);
		longtime2 = ca.get(ca.DAY_OF_YEAR);
		if ( y1 > y2 )
			return 1 ;
		if ( y1 < y2)
			return -1 ;
		else
		{
			if ( longtime1 > longtime2 )
				return 1 ;
			else
			if ( longtime1 < longtime2)
				return -1 ;
			else
				return 0 ;
		}
	}
	public int CompareTime(int y2,int m2,int d2)
	{
		return CompareTime(m_nYear,m_nMonth,m_nDate, y2,m2,d2) ;
	}
	public int CompareTime(DateUtil date)
	{
		return CompareTime(m_nYear,m_nMonth,m_nDate, date.getYear(),date.getMonth(),date.getDate()) ;
	}



	public String toString(int y  , int m ,int d)
	{
		String result = String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d);
		return result ;
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public Vector GetDateVector(int y ,int m , int d,int count)
	{
		Vector vector = new Vector();
		Calendar ca=Calendar.getInstance();
		for ( int i = 0 ; i < count ; i++)
		{
			ca.roll(ca.MONTH,true);
			int year = ca.get(ca.YEAR) ;
			int month = ca.get(ca.MONTH)+1;
			if (month < m)
				year ++ ;
			vector.addElement("??"+String.valueOf(year)+"??"+String.valueOf(month)+"??????");
		}

		return vector ;
	}
	@SuppressWarnings("unchecked")
	public Vector GetDateVector(int count )
	{
		return GetDateVector (m_nYear,m_nMonth,m_nDate,count);
	}

	public int Get(String DateTime ,int type)
	{
		String dati = new String (DateTime);
		switch(type)
		{
		case 0:{
				return Integer.parseInt(dati.substring(0,dati.indexOf("-")));
			}
		case 1:{
				return Integer.parseInt(dati.substring(dati.indexOf("-"),dati.lastIndexOf("-")));

			}
		case 2:{
				return Integer.parseInt(dati.substring(dati.lastIndexOf("-")));
			}
		default: return 0 ;
		}

	}
	@SuppressWarnings("static-access")
	public static boolean isSameDay(long cur, long last)
	{
		/*
		cur = (cur / 1000) / 86400;
		last = (last / 60) / 86400;
		long Margin = cur - last;
		return Margin == 0 ? true : false;
		*/
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTimeInMillis(cur);
		cal2.setTimeInMillis(last);

		int cur_day = cal1.get(cal1.DAY_OF_YEAR);

		int last_day = cal2.get(cal2.DAY_OF_YEAR);

		int tmp = cur_day - last_day;

		return tmp == 0 ? true : false;

	}

	/**
	 * 0:今天，1，昨天，-1：至少是前天
	 * @param time
	 * @return
	 * @throws Throwable
	 */
	public static int isYeaterday(Date time) throws Throwable
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = format.format(new Date());
		Date today = format.parse(todayStr);
		//昨天 86400000=24*60*60*1000 一天
		if((today.getTime() - time.getTime())>0 && (today.getTime() - time.getTime()) <= 86400000)
		{
			return 1;
		}
		else if((today.getTime() - time.getTime())<=0)
		{
			//至少是今天
			return 0;
		}
		else
		{
			//至少是前天
			return -1;
		}
	}


	/**
	 * 两个时间相差多少天
	 * @param cur
	 * @param last
	 * @return
	 */
	public static int aMongDays(long cur, long last)
	{
		/*
		cur = (cur / 1000) / 86400;
		last = (last / 60) / 86400;
		long Margin = cur - last;
		return Margin == 0 ? true : false;
		*/
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTimeInMillis(cur);
		cal2.setTimeInMillis(last);

		int cur_day = cal1.get(cal1.DAY_OF_YEAR);

		int last_day = cal2.get(cal2.DAY_OF_YEAR);

		int tmp = cur_day - last_day;

		return Math.abs(tmp);

	}
	/**
	 *
	 * @Title: jackisSameHour
	 * @Description: TODO(比较日期时间yyyy-mm-dd hh:mm:ss 和 hh:mm:ss )
	 * @param cur
	 * @param last
	 * @return
	 * @author jack
	 * @date 2015年11月6日 下午4:43:23
	 * @throws
	 */
	@SuppressWarnings("static-access")
	public static boolean jackisSameHour(long cur, long last)
	{
		boolean is = false;


			Calendar CurrCal = Calendar.getInstance();
			CurrCal.setTimeInMillis(cur);
			int CurrHour = CurrCal.get(CurrCal.HOUR_OF_DAY);

			Calendar LastCal = Calendar.getInstance();
			LastCal.setTimeInMillis(last);
			int lastHour = LastCal.get(LastCal.HOUR_OF_DAY);

			if(lastHour == CurrHour){
				is = true;
			}

		return is;
	}

	@SuppressWarnings("static-access")
	public static boolean isSameHour(long cur, long last)
	{
		boolean is = false;
		boolean isSameday = DateUtil.isSameDay(cur, last);
		if(isSameday){

			Calendar CurrCal = Calendar.getInstance();
			CurrCal.setTimeInMillis(cur);
			int CurrHour = CurrCal.get(CurrCal.HOUR_OF_DAY);

			Calendar LastCal = Calendar.getInstance();
			LastCal.setTimeInMillis(last);
			int lastHour = LastCal.get(LastCal.HOUR_OF_DAY);

			if(lastHour == CurrHour){
				is = true;
			}
		}else{
			is =  false;
		}
		return is;
	}

	public static boolean isSameMinute(long cur, long last)
	{
		boolean is = false;


			Calendar CurrCal = Calendar.getInstance();
			CurrCal.setTimeInMillis(cur);
			int CurrHour = CurrCal.get(CurrCal.MINUTE);
			Calendar LastCal = Calendar.getInstance();
			LastCal.setTimeInMillis(last);
			int lastHour = LastCal.get(CurrCal.MINUTE);
			if(lastHour == CurrHour){
				is = true;
			}

		return is;
	}
	/**
	 * 一周为周一到周日
	 * @param cur
	 * @param last
	 * @return
	 */
	public static boolean isSameWeek(long cur,long last)
	{

		Calendar curcalendar = Calendar.getInstance();
		curcalendar.setTimeInMillis(cur);
		Calendar lastcalendar = Calendar.getInstance();
		lastcalendar.setTimeInMillis(last);
        int curweek = getGameWeekOfYearString(curcalendar);
        int lastweek =  getGameWeekOfYearString(lastcalendar);
        if(curweek == lastweek){
        	return true;
        }
        return false;
//
//        // 获取firstDate在当前周的第几天. （星期一~星期日：1~7）
//        int monday = calendarMonday.get(Calendar.DAY_OF_WEEK);
//        System.out.println(monday);
//        if (monday == 1){
//        	monday = 7;
//        }
//
//        // 星期一开始时间
//        calendarMonday.add(Calendar.DAY_OF_YEAR, monday-2);
//        calendarMonday.set(Calendar.HOUR, 0);
//        calendarMonday.set(Calendar.MINUTE, 0);
//        calendarMonday.set(Calendar.SECOND, 0);
//
//        // 星期日结束时间
//        Calendar calendarSunday = Calendar.getInstance();
//        calendarSunday.setTime(calendarMonday.getTime());
//        calendarSunday.add(Calendar.DAY_OF_YEAR, 5);
//        calendarSunday.set(Calendar.HOUR, 23);
//        calendarSunday.set(Calendar.MINUTE, 59);
//        calendarSunday.set(Calendar.SECOND, 59);
//        SimpleDateFormat datetimeDf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("星期一开始时间：" + datetimeDf.format(calendarMonday.getTime()));
//        System.out.println("星期日结束时间：" + datetimeDf.format(calendarSunday.getTime()));
//        // 比较第二个时间是否与第一个时间在同一周
//        if (last >= calendarMonday.getTimeInMillis() &&
//        		last <= calendarSunday.getTimeInMillis()) {
//            return true;
//        }
//        return false;
	}
	public static int getGameWeekOfYearString(Calendar cal) {
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if(weekDay == Calendar.SUNDAY) {
			week--;
			if(week == 0) {
				cal.add(Calendar.DAY_OF_YEAR, -1);
				week = cal.get(Calendar.WEEK_OF_YEAR);
			}
		}
		String s = DateUtil.formatDate(cal.getTime(), "yyyy");
		if(week < 10) {
			s = s + "0" + week;
		} else {
			s = s + week;
		}
		return Integer.valueOf(s);
	}
	public static void main(String[] args)
	{
		try{
		SimpleDateFormat datetimeDf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date date1=datetimeDf.parse("2014-04-27 00:00:00");
			Date date2=datetimeDf.parse("2014-05-05 23:59:59");
			long time1=date1.getTime();

			long time2=date2.getTime();
			boolean flag=DateUtil.isSameWeek(time1, time2);
			System.out.println(time1+"  "+flag);
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("======" + DateUtil.getWeekBegin(new Date()));

		System.out.println(parseDate("2014-12-22", format1));

		System.out.println(getDayBegin(new Date()));
	}
	@SuppressWarnings("static-access")
	public static int preHourDistance()
	{
		Calendar CurrCal = Calendar.getInstance();
		CurrCal.setTimeInMillis(System.currentTimeMillis());
		int CurrMinute = CurrCal.get(CurrCal.MINUTE);

		return 60 - CurrMinute;
    }

    public static long compareDaysNum(long greattime, long lesstime)
    {

//    	Calendar great = Calendar.getInstance();
//    	great.setTimeInMillis(greattime);
//
//		Calendar less = Calendar.getInstance();
//		less.setTimeInMillis(lesstime);
//
//		return great.get(great.DAY_OF_YEAR)-less.get(less.DAY_OF_YEAR) ;
    	return (greattime  - lesstime) / (86400000);
    }

    /**
     * 当前时间是否在制定的开始时间与结束时间之内(有效期内)
     * @param starttime
     * @param endtime
     * @return
     */
    public static boolean nowTimeIsBetweenStartAndEnd(long starttime, long endtime)
    {
    	boolean isBetween = false;
    	long curr = System.currentTimeMillis();
    	if(curr >= starttime && curr < endtime)
    	{
    		isBetween = true;
    	}
    	return isBetween;
    }

	@SuppressWarnings("static-access")
	public static int getDay(Date time) throws Exception
	{
		int day = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		day = cal.get(cal.DAY_OF_YEAR);
		return day;
	}

	public static int getYear(Date time) throws Exception
	{
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		year = cal.get(cal.YEAR);
		return year;
	}

	public static int getMonth(Date time) throws Exception
	{
		int month = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		month = cal.get(cal.MONTH) + 1;
		return month;
	}

	public static int getWeekOfYear(Date time)
	{
		int month = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		month = cal.get(cal.WEEK_OF_YEAR);
		return month;
	}

	public static int getDayOfMonth(Date time) throws Exception
	{
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		year = cal.get(cal.DAY_OF_MONTH);
		return year;
	}

	public static int getDayOfWeek(Date time) throws Exception
	{
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		year = cal.get(cal.DAY_OF_WEEK);
		return year;
	}

	public static int getHourOfDay(Date time) throws Exception
	{
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		year = cal.get(cal.HOUR_OF_DAY);
		return year;
	}
	/**
	 * 每小时计划任务中，当前时间到下一次任务的分钟数
	 * @param lasttime(上一次执行时的时间)
	 * @return
	 */
	public static int getOneHourScheduleTime(long lasttime)
	{
		long diff = System.currentTimeMillis() - lasttime;
		int result = (int)((3600000 - diff) / 60000);
		return result;
	}

	public int getYear()
	{
		return m_nYear;
	}
	public void setYear(int year)
	{
		m_nYear=year;
	}
	//
	public int getMonth(){
		return m_nMonth;
	}
	public void setMonth(int month)
	{
		m_nMonth=month;
	}
	//
	public int getDate()
	{
		return m_nDate;
	}
	public void setDate(int date)
	{
		m_nDate=date;
	}


	public int getHour()
	{
		return m_nHour;
	}
	public void setHour(int hour)
	{
		m_nHour=hour;
	}
	//
	public int getMinute()
	{
		return m_nMinute;
	}
	public void setMinute(int minute)
	{
		m_nMinute=minute;
	}
	//
	public int getSecond()
	{
		return m_nSecond;
	}
	public void setSecond(int second)
	{
		m_nSecond=second;
	}

	@Override
	public String toString()
	{
		return m_nYear+"-"+m_nMonth+"-"+m_nDate;
	}

	public String toOraString()
	{
		return m_nYear+"-"+m_nMonth+"-"+m_nDate+","+m_nHour+":"+m_nMinute+":"+m_nSecond;
	}
	// interface implementation ----------------------------------------------

	// parent class overrides ---------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
	public static boolean betweenHour(long time1,long time2,int hour){
		double between = Math.abs(time1-time2);
		if(between > hour*60*60*1000){
			return true;
		}
		return false;
	}
	public static boolean betweenDay(long time1,long time2,int day){
		double between = Math.abs(time1-time2);
		if(between > day*24*60*60*1000){
			return true;
		}
		return false;
	}
	public static int getBetweenDays(long time1,long time2){
		try{
			int day1=getDay(new Date(time1));
			int day2=getDay(new Date(time2));
			return Math.abs(day1-day2);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
