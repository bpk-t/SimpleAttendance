package com.gmail.kouh2501gis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AttendanceDbHelper extends SQLiteOpenHelper {

	public AttendanceDbHelper(Context context) {
		super(context, kDbName, null, kDdVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.beginTransaction();
		try {
			//勤怠時間テーブルを作成
			db.execSQL("create table attendance_table ("
					+ "date_id text primary key not null,"
					+ "begin_time text,"
					+ "end_time text);");
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * 日付で勤怠時間テーブルの該当レコードを探す
	 * 見つからない場合、nullを返す
	 * @param db
	 * @param day
	 * @return
	 */
	public AttendanceRecord serchByDayForAttendance(SQLiteDatabase db, String dataId)
	{
		AttendanceRecord result = null;
		Cursor cursor = null;
		
		try {
			cursor = db.query("attendance_table"
					, new String[]{"date_id", "begin_time", "end_time"}
					, "date_id=?"
					, new String[]{dataId}
					, null
					, null
					, null);
			cursor.moveToFirst();
			if (cursor.getCount() == 1) {
				//必ず一つになるはず
				int indexDateId = cursor.getColumnIndex("date_id");
				int indexBeginTime = cursor.getColumnIndex("begin_time");
				int indexEndTime = cursor.getColumnIndex("end_time");
				
				result = new AttendanceRecord();
				result.setKey(cursor.getString(indexDateId));
				result.setBeginTime(cursor.getString(indexBeginTime));
				result.setEndTime(cursor.getString(indexEndTime));
				return result;
			} else {
				return result;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	//既存データが存在する場合は上書きする
	public void addEntry(SQLiteDatabase db, String date_id, String beginTime, String endTime)
	{
		ContentValues contents = new ContentValues();
		contents.put("date_id", date_id);
		contents.put("begin_time", beginTime);
		contents.put("end_time", endTime);
		
		db.beginTransaction();
		try {
			if (serchByDayForAttendance(db, date_id) != null) {
				//既存レコードが存在する
				db.update("attendance_table", contents, "date_id=?", new String[]{date_id});
			} else {
				//既存レコードが存在しない
				db.insert("attendance_table", null, contents);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//DBのVerup時の処理
	}

	public static final String kDbName = "attend_db";
	public static final int kDdVersion = 1;
}
