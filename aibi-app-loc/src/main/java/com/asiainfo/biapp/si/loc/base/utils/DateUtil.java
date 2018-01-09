package com.asiainfo.biapp.si.loc.base.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;

public class DateUtil {

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "HH:mm yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM = "yyyy-MM";
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYYMM = "yyyyMM";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final int DATETYPE_MONTH = 0;   //日期类型：月
    public static final int DATETYPE_DAY = 1;     //日期类型：日
	private static String TABLE_LABEL_STAT = "CI_LABEL_STAT_";
	
	public static final int UPDATE_CYCLE_DAY = 3;//日周期
	public static final int UPDATE_CYCLE_MONTH = 2;//月周期
	

	public static String string3String(String date) {
		
		
		String s = "";
		if (date.indexOf(".") > 0) {
			int pos = date.indexOf(".");
			s = date.substring(0, pos);

		}
		return s;
	}
	public static String string2String2(String date) {
		
		/*
		String s = "";

		if (date.indexOf(".") > 0) {
			int pos = date.indexOf(".");
			s = date.substring(0, pos);

		}
		// 只显示年-月-日
		s = s.substring(0, 10);
		*/
		String s = date;

		if (s == null || s.equals("")) {
			return null;
		}
		String result1 = null;
//		String result2 = null;
		try {
			DateFormat format = null;
			if (s.length() > 15) {
				format = new SimpleDateFormat(DATETIME_FORMAT);
			} else if (s.length() > 8) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
			} else if (s.length() > 4) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM);
			} else {
				format = new SimpleDateFormat("yyyy");
			}
			result1 = date2String(format.parse(s), FORMAT_YYYY_MM_DD);
//			result2 = date2String(format.parse(s), DATE_FORMAT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result1;
	}
	
	/**
	 * 格式化当前日期为需要的格式
	 * @param dateStr
	 * @param dateStrFormat
	 * @param afterFormat
	 * @return
	 * @version ZJ
	 */
	public static String string2StringFormat(String dateStr, String dateStrFormat, String afterFormat) {
		if(dateStr == null || "".equals(dateStr)) {
			return null;
		}
		String result = null;
		try {
			DateFormat format = new SimpleDateFormat(dateStrFormat);
			
			result = date2String(format.parse(dateStr), afterFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String string2String(String date) {
		String s = "";
		
		if (date == null || date.equals("")) {
			return null;
		}
		
		if (date.indexOf(".") > 0) {
			int pos = date.indexOf(".");
			s = date.substring(0, pos);

		}

		if (s == null || s.equals("")) {
			return null;
		}
		String result = null;
		try {
			DateFormat format = null;
			if (s.length() > 15) {
				format = new SimpleDateFormat(DATETIME_FORMAT);
			} else if (s.length() > 8) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
			} else if (s.length() > 4) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM);
			} else {
				format = new SimpleDateFormat("yyyy");
			}
			result = date2String(format.parse(s), DATE_FORMAT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String date2String(Date date) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		result = dateFormat.format(date);
		return result;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String formatPra) {
		String format = formatPra;
		if (date == null) {
			return "";
		}
		if (format == null || format.equals("")) {
			format = DATETIME_FORMAT;
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		result = dateFormat.format(date);
		return result;
	}

	public static String timeStamp2String(java.sql.Timestamp date) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		result = dateFormat.format(date);
		return result;
	}
	public static String timeStamp2String(java.sql.Timestamp date,String format) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		result = dateFormat.format(date);
		return result;
	}
	
	public static Date string2Date(String dateStr, String formatStr) {
		if (dateStr == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Timestamp  string2Timestamp(String sdate) {
		if (sdate == null) {
			return null;
		}
		Timestamp ts=null;
		try {
			ts = Timestamp.valueOf(sdate);
			
		}catch (Exception e){
			
		}
		return ts;
	}
	
	/**
	 * 获取当前月份往前推一定周期的日期字符串
	 * @param frontNum 往前推frontNum个周期（日或月）
	 * @param dataDate 当前日期  月yyyymm  日yyyymmdd
	 * @param updateCycle 1日周期，2月周期
	 * @return 日或月字符串的List 
	 */
	public static List<String> getAssignFrontDate(int frontNum, String dataDate, int updateCycle) {
		List<String> list = new LinkedList<String>();
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if(LabelInfoContants.LABEL_CYCLE_TYPE_M == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if(LabelInfoContants.LABEL_CYCLE_TYPE_D == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				for (int i = 0; i < frontNum; i++) {
					dateStr = format.format(calendar.getTime());
					list.add(dateStr);
					calendar.add(dateType,-1);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取指定时间段内有哪些日期
	 * @param frontNum 
	 * @param dataDate 当前日期  月yyyymm  日yyyymmdd
	 * @param updateCycle 1日周期，2月周期
	 * @return 日或月字符串的List 
	 */
	public static List<String> getAssignFrontDate2(String beginDate, String endDate, int updateCycle) {
		List<String> list = new LinkedList<String>();
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if(LabelInfoContants.LABEL_CYCLE_TYPE_M == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if(LabelInfoContants.LABEL_CYCLE_TYPE_D == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(endDate)) {
				while (true) {
					dateStr = format.format(calendar.getTime());
					list.add(dateStr);
					calendar.add(dateType,-1);
					if(dateStr.equals(beginDate)) {
						break;
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取指定时间段内有哪些日期
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 * @return 指定日期段内连续日期列表
	 */
	public static List<String> dateList(String beginDate, String endDate){
		String formatStr = FORMAT_YYYYMMDD;
    	SimpleDateFormat format=new SimpleDateFormat(formatStr);
    	ArrayList<String> dateList=new ArrayList<String>();
    	try {
			Date begin=format.parse(beginDate);
			Date end=format.parse(endDate);
			double   between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒      
			double  day=between/(24*3600);
			for(int i = 0;i<=day;i++){
				
				Calendar cd = Calendar.getInstance();   
				cd.setTime(format.parse(beginDate));   
				cd.add(Calendar.DATE, i);//增加一天   
				//cd.add(Calendar.MONTH, n);//增加一个月
				dateList.add(format.format(cd.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
    }
	/**
	 * 获取当前月份往前推一定月份的日期字符串
	 * @param monthNum 往前推monthNum个月（包括当前月份）
	 * @param dataDate 当前日期  格式为yyyymm
	 * @return yyyymm格式的日期字符串
	 * dataDate是给查询条件用的，格式为yyyymm
	 * 
	 */
	public static String getFrontMonth(int monthNum, String dataDate) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMM);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.MONTH,-monthNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 获取往前推N周期(日或者月)
	 * @param frontNum 往前推N个周期
	 * @param dataDate 日yyyymmdd，月yyyymm
	 * @param updateCycle 1日周期，2月周期
	 * @return
	 */
	public static String getFrontDate(int frontNum, String dataDate, int updateCycle) {
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if(LabelInfoContants.LABEL_CYCLE_TYPE_M == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if(LabelInfoContants.LABEL_CYCLE_TYPE_D == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				calendar.add(dateType,-frontNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 获取往前推N周期(日或者月)
	 * @param frontNum 往前推N个周期
	 * @param dataDate 日yyyymmdd，月yyyymm
	 * @param updateCycle 1日周期，2月周期
	 * @return
	 */
	public static String getCustomFrontDate(int frontNum, String dataDate, int updateCycle) {
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if(LabelInfoContants.CUSTOM_CYCLE_TYPE_M == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if(LabelInfoContants.CUSTOM_CYCLE_TYPE_D == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		} else if(LabelInfoContants.CUSTOM_CYCLE_TYPE_ONE == updateCycle){
			return dataDate;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				calendar.add(dateType,-frontNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	
	
	/**
	 * 获取上月的月份字符串
	 * @return 格式为yyyymm
	 */
//	public static String getLastMonthStr() {
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.MONTH, -1);//前一个月
//		Date dataDate = c.getTime();
//		DateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_YYYYMM);
//		String dataTime = dateFormat.format(dataDate);
//		return dataTime;
//	}
	
	/**
	 * 获取当前日期往前推一定日期的日期字符串
	 * @author YZ
	 * TODO
	 * @param monthNum
	 * @param dataDate 当前日期  格式为yyyymmdd
	 * @return
	 *
	 */
	public static String getFrontDay(int dayNum, String dataDate) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.DATE,-dayNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 
	 * @author YZ
	 * TODO 获取当前日期往前推一定日期的日期字符串
	 * @param dayNum
	 * @param dataDate
	 * @param dateFormat
	 * @return
	 *
	 */
	public static String getFrontDay(int dayNum, String dataDate,String dateFormat) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.DATE,-dayNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 
	 * @author YZ
	 * TODO 获取当月最后一天
	 * @param date
	 * @return
	 *
	 */
	public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }
	
	/**
	 * 获取指定月份的最后一天的字符串
	 * @param dataDate yyyymm
	 * @return yyyymmdd
	 */
	public static String lastDayOfMonth(String dataDate) {
		String dayStr = "";
		SimpleDateFormat month_format = new SimpleDateFormat(FORMAT_YYYYMM);
		SimpleDateFormat day_format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		try {
			Date monthDate = month_format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(monthDate);
	        int value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, value);
			dayStr = day_format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayStr;
	}
	
	/**
	 * 
	 * @author DG
	 * TODO 获取当月第一天
	 * @param date
	 * @return
	 *
	 */
	public static Date firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }
	
	/**
	 * 获得指定日期的前几天的日期字符串
	 * @param currentDay 当前日期
	 * @param num 当前日期前几天
	 * @return yyyyMMdd型的日期
	 * @throws Exception
	 */
	public static String getLastDate(String currentDay, int num) {
		DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date date = null;
		String dateStr = null;
		try {
			date = df.parse(currentDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DATE);
			calendar.set(Calendar.DATE, day - num); // 得到前几天
			Date lastDate = calendar.getTime();
			dateStr = df.format(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 获得当前日期的前一天的日期字符串
	 * @return yyyyMMdd型的日期
	 * @throws Exception
	 */
	public static String getLastDate() {
		DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1); // 得到前一天
		Date lastDate = calendar.getTime();
		String dateStr = df.format(lastDate);
		return dateStr;
	}

    /**
     * 获取偏移日期
     * @param offset 偏移量
     * @param dateType  日期类型
     * @return
     */
//    public static String getOffsetDate(int offset,int dateType){
//        String offsetDate = "";
//        Calendar c = Calendar.getInstance();
//        switch (dateType){
//            case DATETYPE_MONTH:
//                c.add(Calendar.MONTH, offset);//前一个月
//                DateFormat yyyyMMDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_YYYYMM);
//                offsetDate = yyyyMMDateFormat.format(c.getTime());
//                break;
//            case DATETYPE_DAY:
//                c.add(Calendar.DATE, offset);//前一天，建议从配置文件中取
//                DateFormat yyyyMMdateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_YYYYMMDD);
//                offsetDate = yyyyMMdateFormat.format(c.getTime());
//                break;
//            default:
//        }
//        return offsetDate;
//    }
	
    /**
     * 获取指定日期偏移日期
     * @param date yyyyMM或者yyyyMMdd形式字符串，根据dateType决定
     * @param offset 偏移量
     * @param dateType  日期类型
     * @return
     */
    public static String getOffsetDateByDate(String date, int offset, int dateType) {
    	if(date == null || "".equals(date)) {
    		return null;
    	}
        String offsetDate = "";
        Calendar c = Calendar.getInstance();
        try {
			switch (dateType) {
			case DATETYPE_MONTH:
				DateFormat yyyyMMDateFormat = new SimpleDateFormat(FORMAT_YYYYMM);
				Date m_date = yyyyMMDateFormat.parse(date);
				c.setTime(m_date);
				c.add(Calendar.MONTH, offset);
				offsetDate = yyyyMMDateFormat.format(c.getTime());
				break;
			case DATETYPE_DAY:
				DateFormat yyyyMMddDateFormat = new SimpleDateFormat(FORMAT_YYYYMMDD);
				Date d_date = yyyyMMddDateFormat.parse(date);
				c.setTime(d_date);
				c.add(Calendar.DATE, offset);
				offsetDate = yyyyMMddDateFormat.format(c.getTime());
				break;
			default:
			}
        } catch (ParseException e) {
			e.printStackTrace();
		}
        return offsetDate;
    }
    /**
     * 获取指定日期偏移日期
     * @param date yyyyMM或者yyyyMMdd形式字符串，根据dateType决定
     * @param offset 偏移量
     * @param dateType  日期类型
     * @return
     */
    public static String getOffsetDateByDate(String date, int offset, int dateType, String fmt) {
    	if(date == null || "".equals(date)) {
    		return null;
    	}
        String offsetDate = "";
        Calendar c = Calendar.getInstance();
        try {
			switch (dateType) {
			case UPDATE_CYCLE_MONTH:
				DateFormat yyyyMMDateFormat = new SimpleDateFormat(fmt);
				Date m_date = yyyyMMDateFormat.parse(date);
				c.setTime(m_date);
				c.add(Calendar.MONTH, offset);
				offsetDate = yyyyMMDateFormat.format(c.getTime());
				break;
			case UPDATE_CYCLE_DAY:
				DateFormat yyyyMMddDateFormat = new SimpleDateFormat(fmt);
				Date d_date = yyyyMMddDateFormat.parse(date);
				c.setTime(d_date);
				c.add(Calendar.DATE, offset);
				offsetDate = yyyyMMddDateFormat.format(c.getTime());
				break;
			default:
			}
        } catch (ParseException e) {
			e.printStackTrace();
		}
        return offsetDate;
    }
	public static String getCurrentDay(){
		String date = date2String(new Date(), FORMAT_YYYY_MM_DD)+" 00:00:00";
		return date;
	}
	
	/**
	 * 获取当前时间
	 * 日期管理中添加，用于作为日期选择组件的截止日期
	 * @return
	 * @author caihq 68434
	 */
	public static String getCurrentDayYYYYMMDD(){
        String date = date2String(new Date(), FORMAT_YYYYMMDD);
        return date;
    }
	
	/**
	 * 通过数据日期获取当前标签的更新周期
	 * @param dataDate
	 * @return
	 */
	public static int getUpdateCycleByDate(String dataDate) {
		int updateCycle = LabelInfoContants.LABEL_CYCLE_TYPE_M;
		if(!StringUtil.isEmpty(dataDate)) {
			if(dataDate.length() == 6) {
				updateCycle = LabelInfoContants.LABEL_CYCLE_TYPE_M;
			} else if(dataDate.length() > 6) {
				updateCycle = LabelInfoContants.LABEL_CYCLE_TYPE_D;
			}
		}
		
		return updateCycle;
	}
	
	/**
	 * 根据数据日期获取标签统计信息表的表名
	 * @param dataDate
	 * @return
	 */
//	public static String getLabelStatTableName(String dataDate) {
//		String tableName = "";
//		int updateCycle = getUpdateCycleByDate(dataDate);
//		String tempDataDate = "";
//		if(updateCycle == LabelInfoContants.LABEL_CYCLE_TYPE_M) {
//			tempDataDate = CacheBase.getInstance().getNewLabelMonth();
//			tableName = TABLE_LABEL_STAT + LabelInfoContants.LABEL_STAT_CYCLE_MM + "_" + tempDataDate;
//		} else if(updateCycle == LabelInfoContants.LABEL_CYCLE_TYPE_D) {
//			tableName = TABLE_LABEL_STAT + LabelInfoContants.LABEL_STAT_CYCLE_DM + "_" + dataDate;
//		}
//		
//		return tableName;
//	}
	
	/**
	 * 根据标签更新周期获取标签统计信息表的表名
	 * @param dataDate
	 * @return
	 */
//	public static String getLabelTrendTableName(int updateCycle) {
//		String tableName = "";
//		String dataDate = "";
//		if(updateCycle == LabelInfoContants.LABEL_CYCLE_TYPE_M) {
//			dataDate = CacheBase.getInstance().getNewLabelMonth();
//			tableName = TABLE_LABEL_STAT + LabelInfoContants.LABEL_STAT_CYCLE_MM + "_" + dataDate;
//		} else if(updateCycle == LabelInfoContants.LABEL_CYCLE_TYPE_D) {
//			dataDate = CacheBase.getInstance().getNewLabelDay();
//			tableName = TABLE_LABEL_STAT + LabelInfoContants.LABEL_STAT_CYCLE_DM + "_" + dataDate;
//		}
//		
//		return tableName;
//	}
	
	/**
	 * 将yyyymm和yyyymmdd格式的日期转换成yyyy-mm和yyyy-mm-dd
	 * @param dataDate
	 * @return
	 */
	public static String dateFormat(String dataDate) {
		String formatStr = "";
		String resultFormatStr = "";
		String dateStr = "";
		if(null != dataDate && !"".equals(dataDate)) {
			if(dataDate.length() == 6) {
				formatStr = FORMAT_YYYYMM;
				resultFormatStr = FORMAT_YYYY_MM;
			}
			if(dataDate.length() == 8) {
				formatStr = FORMAT_YYYYMMDD;
				resultFormatStr = FORMAT_YYYY_MM_DD;
			}
		} else {
			return null;
		}
		
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		SimpleDateFormat resultFormat = new SimpleDateFormat(resultFormatStr);
		try {
			Date theDate = format.parse(dataDate);
			dateStr = resultFormat.format(theDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * 获取date往前推latestDays天的日期
	 * @version ZJ
	 * @param date
	 * @param latestDays
	 * @return
	 */
	public static Date  getStartDate(Date date,int latestDays){
		Calendar cd = Calendar.getInstance();   
		cd.setTime(date);
		cd.add(Calendar.DATE, -latestDays);
		return cd.getTime();
	}
	
	/**
	 * 计算结束日期减去开始日期的时间差,日周期取间隔天数，月周期取间隔月数
	 * @version ZJ
	 * @param beginDate
	 * @param endDate
	 * @param formatStr
	 * @return
	 */
	public static int dateInterval(String beginDate, String endDate, String formatStr, int updateCycle){
    	SimpleDateFormat format=new SimpleDateFormat(formatStr);
    	int interval = 0;
		if (StringUtil.isNotEmpty(beginDate) && StringUtil.isNotEmpty(endDate)) {
    		try {
    			Date begin=format.parse(beginDate);
    			Date end=format.parse(endDate);
    			
    			//如果是日周期则计算间隔天数，如果是月则计算间隔月数
    			if (updateCycle == LabelInfoContants.CUSTOM_CYCLE_TYPE_M) {
    				Calendar calendarBeigin = Calendar.getInstance();
    				calendarBeigin.setTime(begin);
    				int year1 = calendarBeigin.get(Calendar.YEAR);
    				int month1 = calendarBeigin.get(Calendar.MONTH);
    				
    				Calendar calendarEnd = Calendar.getInstance();
    				calendarEnd.setTime(end);
    				int year2 = calendarEnd.get(Calendar.YEAR);
    				int month2 = calendarEnd.get(Calendar.MONTH);
    				
    				if (year1 == year2) {
    					interval = month2 - month1;
    				} else {
    					interval = 12 * (year2 - year1) + month2 - month1;
    				}
    				
    			} else if (updateCycle == LabelInfoContants.CUSTOM_CYCLE_TYPE_D) {
    				long beginTime = begin.getTime();
    				long endTime = end.getTime();
    				long between = (endTime - beginTime) / 1000;// 除以1000是为了转换成秒
    				interval = (int) between / (24 * 3600);
    			}
    			
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
		return interval;
    }
	
	/**
	 * 计算偏移后的日期
	 * @version ZJ
	 * @param dateStr 需要偏移的初始日期
	 * @param interval 偏移量，日周期偏移一天，月周期偏移一月
	 * @param formatStr 日期格式
	 * @param updateCycle 周期性
	 * @return
	 */
	public static String calculateOffsetDate(String dateStr, int interval, String formatStr, int updateCycle) {
		String resultDate = dateStr;
		if (StringUtil.isNotEmpty(dateStr) && interval != 0) {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			try {
				Date date = format.parse(dateStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				if (updateCycle == LabelInfoContants.CUSTOM_CYCLE_TYPE_M) {
					calendar.add(Calendar.MONTH, interval);
					resultDate = format.format(calendar.getTime());
				} else if (updateCycle == LabelInfoContants.CUSTOM_CYCLE_TYPE_D) {
					calendar.add(Calendar.DATE, interval);
					resultDate = format.format(calendar.getTime());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultDate;
	}
	 //-----------------------------------------------------------------------
    /**
     * Adds a number of years to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of months to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of weeks to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of days to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of hours to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of minutes to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of seconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of milliseconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param calendarField  the calendar field to add to
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     * @deprecated Will become privately scoped in 3.0
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    
    public static String getEarlierDate(String dateAStr,String dateBStr,String format){
    	Date dateA = DateUtil.string2Date(dateAStr, format);
    	Date dateB = DateUtil.string2Date(dateBStr, format);
    	if(dateA.getTime()>dateB.getTime()){
    		return dateBStr;
    	}else{
    		return dateAStr;
    	}
    }
	
	public static void main(String[] args) {
//		String beginDate1 = "20140728";
//		String endDate1 = "20140729";
//		String formatStr1 = "yyyyMMdd";
//		
//		String beginDate = "201407";
//		String endDate = "201408";
//		String formatStr = "yyyyMM";
//		int days = dateInterval(beginDate1, endDate1, formatStr1, 1);
//		int months = dateInterval(beginDate, endDate, formatStr, 2);
//		System.out.println(days);
//		System.out.println(months);
//		String startDateStr = "20140625";
//		String resultDatestr = calculateOffsetDate(startDateStr, 1, formatStr1, 3);
//		System.out.println(resultDatestr);
		System.out.println(getEarlierDate("20150201","20150202","yyyyMMdd"));
	}
}