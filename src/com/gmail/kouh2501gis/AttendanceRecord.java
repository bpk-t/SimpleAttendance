package com.gmail.kouh2501gis;

public class AttendanceRecord {

	private String beginTime_;
	private String endTime_;
	
	private String month_;
	private String year_;
	private String day_;
	
	/**
	 * DBのKeyとなる日付（YYYY/MM/DD）を返す
	 * @return
	 */
	public String getKey()
	{
		return year_ + "/" + month_ + "/" + day_; 
	}
	
	/**
	 * DBのKeyとなる日付（YYYY/MM/DD）を設定する
	 * @param key
	 */
	public void setKey(String key)
	{
		String [] splits = key.split("/");
		if (splits.length == 3) {
			year_ = splits[0];
			month_ = splits[1];
			day_ = splits[2];
		}
	}
	
	public String year()
	{
		return year_;
	}
	
	public String month()
	{
		return month_;
	}
	
	public String day()
	{
		return day_;
	}
	
	public String beginTime()
	{
		return beginTime_;
	}
	
	public String endTime()
	{
		return endTime_;
	}
	
	public void setBeginTime(String beginTime)
	{
		beginTime_ = beginTime;
	}
	
	public void setEndTime(String endTime)
	{
		endTime_ = endTime;
	}
}
