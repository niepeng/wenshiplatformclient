package liuyongxiang.robert.com.testtime.wheelview;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;

public class DateUtils {
	public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
	public static String currentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return sdf.format(calendar.getTime());
	}

	public static String formateStringH(String dateStr, String pattren) {
		Date date = parseDate(dateStr, pattren);
		try {
			String str = dateToString(date, DateUtils.yyyyMMddHHmm);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return dateStr;
		}
	}
	public static Date parseDate(String dateStr, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}
	public static String dateToString(Date date, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date stringToDate(String dateStr, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).parse(dateStr);
	}
	public static String currentTimeDeatil(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return sdf.format(calendar.getTime());

	}

	public static String currentMonth() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return sdf.format(calendar.getTime());

	}

	public static String lastMonth(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d;
		try {
			d = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.MONTH, -1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String nextMonth(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d;
		try {
			d = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.MONTH, +1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;

	}

	public static String lastDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return sdf.format(date);
	}

	// ��7澶╂�版��
	public static String lastSevenDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		date = calendar.getTime();
		return sdf.format(date);
	}

	// ��14澶╂�版��
	public static String lastFourteenDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -14);
		date = calendar.getTime();
		return sdf.format(date);
	}

	// ����绗�涓�澶╂�版��
	public static String currentFDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		sdf.format(calendar.getTime());
		return sdf.format(calendar.getTime());
	}

	// ��������澶╂�版��
	public static String currentLDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		sdf.format(calendar.getTime());
		return sdf.format(calendar.getTime());
	}
	public static String currentLDaySchedule() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		sdf.format(calendar.getTime());
		return sdf.format(calendar.getTime());
	}

	// 涓�涓���绗�涓�澶╂�版��
	public static String currentFFday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(gcLast.getTime());

	}
	// 涓�涓�������涓�澶╂�版��
	public static String currentLLday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();// 姝ゆ�舵���板���峰������绯荤�褰����堕��
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH - 1));
		Date theDate = calendar.getTime();
		return df.format(theDate);
	}
	// 涓�涓�������涓�澶╂�版��
		public static String currentLLdaySchedule(String date) {
			try {
				String aa =date.toString()+"-01";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
				Calendar calendar = Calendar.getInstance();// 姝ゆ�舵���板���峰������绯荤�褰����堕��
				calendar.setTime(df.parse(aa));
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH ));
				Date theDate = calendar.getTime();
				return df.format(theDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
	// 涓�涓�涓�������涓�澶╂�版��
	public static String lastLLdaySchedule(String date) {
		try {
			String aa =date.toString()+"-01";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
			Calendar calendar = Calendar.getInstance();// 姝ゆ�舵���板���峰������绯荤�褰����堕��
			calendar.setTime(df.parse(aa));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH ));
			Date theDate = calendar.getTime();
			return df.format(theDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	// �峰����杩�涓���澶╃���版��

	public static String lastThrDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();// 姝ゆ�舵���板���峰������绯荤�褰����堕��\

		// calendar.add(Calendar.MONTH, -1); //

		// if (calendar.get(calendar.DAY_OF_MONTH)==1) {
		//
		// }
		calendar.add(Calendar.DATE, -30);

		Date theDate = calendar.getTime();
		return df.format(theDate);
	}
	public static String[] weekName = { "������", "����涓�", "����浜�", "����涓�", "������", "����浜�","������" };  
	  
    public static int getMonthDays(int year, int month) {  
        if (month > 12) {  
            month = 1;  
            year += 1;  
        } else if (month < 1) {  
            month = 12;  
            year -= 1;  
        }  
        int[] arr = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  
        int days = 0;  
  
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {  
            arr[1] = 29; // 锟斤拷锟斤拷2锟斤拷29锟斤拷  
        }  
  
        try {  
            days = arr[month - 1];  
        } catch (Exception e) {  
            e.getStackTrace();  
        }  
  
        return days;  
    }  
      
    public static int getYear() {  
        return Calendar.getInstance().get(Calendar.YEAR);  
    }  
  
    public static int getMonth() {  
        return Calendar.getInstance().get(Calendar.MONTH) + 1;  
    }  
  
    public static int getCurrentMonthDay() {  
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);  
    }  
  
    public static int getWeekDay() {  
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);  
    }  
  
    public static int getHour() {  
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);  
    }  
    public static int getMinute() {  
        return Calendar.getInstance().get(Calendar.MINUTE);  
    }  
    public static CustomDate getNextSunday() {  
          
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, 7 - getWeekDay()+1);  
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),  
                c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));  
        return date;  
    }  
  
    public static int[] getWeekSunday(int year, int month, int day, int pervious) {  
        int[] time = new int[3];  
        Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, year);  
        c.set(Calendar.MONTH, month);  
        c.set(Calendar.DAY_OF_MONTH, day);  
        c.add(Calendar.DAY_OF_MONTH, pervious);  
        time[0] = c.get(Calendar.YEAR);  
        time[1] = c.get(Calendar.MONTH )+1;  
        time[2] = c.get(Calendar.DAY_OF_MONTH);  
        return time;  
  
    }  
  
    public static int getWeekDayFromDate(int year, int month) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getDateFromString(year, month));  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if (week_index < 0) {  
            week_index = 0;  
        }  
        return week_index;  
    }  
  
    @SuppressLint("SimpleDateFormat")  
    public static Date getDateFromString(int year, int month) {  
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))  
                + "-01";  
        Date date = null;  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            date = sdf.parse(dateString);  
        } catch (ParseException e) {  
            System.out.println(e.getMessage());  
        }  
        return date;  
    }  
    public static boolean isToday(CustomDate date){  
        return(date.year == DateUtils.getYear() &&  
                date.month == DateUtils.getMonth()   
                && date.day == DateUtils.getCurrentMonthDay());  
    }  
      
    public static boolean isCurrentMonth(CustomDate date){
        return(date.year == DateUtils.getYear() &&  
                date.month == DateUtils.getMonth());  
    }  
//    �峰����缁�瀹�骞存��������涓�澶�
    
      
    
    public static String getMonthend(int year,int month){  
  
    	Calendar calendar = Calendar.getInstance();
    	// 璁剧疆�堕��,褰����堕�翠��ㄨ�剧疆
    	// calendar.setTime(new Date());
    	// 璁剧疆�ユ��涓烘������澶ф�ユ��
    	calendar.set(Calendar.MONTH, month-1 );  
    	calendar.set(Calendar.YEAR, year);  
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
    	
    	// ����
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println(format.format(calendar.getTime()));
    	return format.format(calendar.getTime());
    } 
    
    public static String getMonthStart(int year,int month){  
    	  
    	Calendar calendar = Calendar.getInstance();
    	// 璁剧疆�堕��,褰����堕�翠��ㄨ�剧疆
    	// calendar.setTime(new Date());
    	// 璁剧疆�ユ��涓烘������澶ф�ユ��
    	calendar.set(Calendar.MONTH, month-1 );  
    	calendar.set(Calendar.YEAR, year);  
    	calendar.set(Calendar.DATE, 1);
    	
    	// ����
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println(format.format(calendar.getTime()));
    	return format.format(calendar.getTime());
    }



	// �ㄥぉ
	public static String currentYesterday() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		calendar.setTime(date);
		return sdf.format(calendar.getTime());
	}
	// ���ㄧ��绗�涓�澶�
	public static String currentWeekone() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// �煎���瀵硅薄
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DAY_OF_WEEK)==0){
			calendar.add(Calendar.DATE,-6 );
		}else {

		calendar.add(Calendar.DATE,2-calendar.get(Calendar.DAY_OF_WEEK) );
		}

		return sdf.format(calendar.getTime());
	}


	// �峰��褰������跺��
	public static String currentHourMinute() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");// �煎���瀵硅薄

		return sdf.format(date);
	}


}
