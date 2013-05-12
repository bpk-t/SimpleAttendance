package com.gmail.kouh2501gis;

import java.util.Calendar;
import java.util.Date;

import com.gmail.kouh2501gis.util.DataUtil;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleAttendanceActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dbHelper_ = new AttendanceDbHelper(this);
        
        Button beginButton = (Button)findViewById(R.id.beginButton);
        Button endButton = (Button)findViewById(R.id.endButton);
        
        beginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				SQLiteDatabase db = dbHelper_.getWritableDatabase();
				String date_id = DataUtil.getDayForString(current_);
				AttendanceRecord record = dbHelper_.serchByDayForAttendance(db, date_id);
				
				String date = DataUtil.getTimeForString(new Date(System.currentTimeMillis()));
				if (record == null) {
					dbHelper_.addEntry(db, date_id, date, "");
					Toast.makeText(SimpleAttendanceActivity.this, date + "で打刻しました", Toast.LENGTH_LONG).show();
				} else {
					dbHelper_.addEntry(db, date_id, date, record.endTime());
					Toast.makeText(SimpleAttendanceActivity.this, date + "で打刻しました", Toast.LENGTH_LONG).show();
				} 
				//TODO 既に登録されている場合、ダイアログを出して上書きしてもいいか確認する
				db.close();
			}
		});
        
        endButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper_.getWritableDatabase();
				String date_id = DataUtil.getDayForString(current_);
				AttendanceRecord record = dbHelper_.serchByDayForAttendance(db, date_id);
				
				String date = DataUtil.getTimeForString(new Date(System.currentTimeMillis()));
				if (record == null) {
					dbHelper_.addEntry(db, date_id, "", date);
					Toast.makeText(SimpleAttendanceActivity.this, date + "で打刻しました", Toast.LENGTH_LONG).show();
				} else {
					dbHelper_.addEntry(db, date_id, record.endTime(), date);
					Toast.makeText(SimpleAttendanceActivity.this, date + "で打刻しました", Toast.LENGTH_LONG).show();
				} 
				//TODO 既に登録されている場合、ダイアログを出して上書きしてもいいか確認する
				db.close();
			}
		});
        
        current_ = Calendar.getInstance();
        TextView currentDate = (TextView)findViewById(R.id.currentDate);
        currentDate.setText(DataUtil.getDayForString(current_) + "(" + DataUtil.getDayOfWeekString(current_) + ")"
        		+ " " + DataUtil.getTimeForString(new Date(System.currentTimeMillis())));
    }
    
    private Calendar current_ = null;
    private AttendanceDbHelper dbHelper_ = null;
}