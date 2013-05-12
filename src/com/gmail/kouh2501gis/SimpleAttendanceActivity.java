package com.gmail.kouh2501gis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import com.gmail.kouh2501gis.util.DataUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleAttendanceActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dbHelper_ = new AttendanceDbHelper(this);
        current_ = Calendar.getInstance();
        
        Button beginButton = (Button)findViewById(R.id.beginButton);
        Button endButton = (Button)findViewById(R.id.endButton);
        Button fileOutputButton = (Button)findViewById(R.id.fileOutputButton);
        
        beginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				SQLiteDatabase db = dbHelper_.getWritableDatabase();
				String date_id = DataUtil.getDayForString(current_);
				AttendanceRecord record = dbHelper_.serchByDayForAttendance(db, date_id);
				
				String date = DataUtil.getTimeForString(new Date(System.currentTimeMillis()));
				if (record == null) {
					dbHelper_.addEntry(db, date_id, date, "");
					Toast.makeText(SimpleAttendanceActivity.this, date_id + " " + date + "で打刻しました", Toast.LENGTH_LONG).show();
				} else {
					dbHelper_.addEntry(db, date_id, date, record.endTime());
					Toast.makeText(SimpleAttendanceActivity.this, date_id + " " + date + "で打刻しました", Toast.LENGTH_LONG).show();
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
					Toast.makeText(SimpleAttendanceActivity.this, date_id + " " + date + "で打刻しました", Toast.LENGTH_LONG).show();
				} else {
					dbHelper_.addEntry(db, date_id, record.endTime(), date);
					Toast.makeText(SimpleAttendanceActivity.this, date_id + " " + date + "で打刻しました", Toast.LENGTH_LONG).show();
				} 
				//TODO 既に登録されている場合、ダイアログを出して上書きしてもいいか確認する
				db.close();
			}
		});
        
        fileOutputButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int year = current_.get(Calendar.YEAR);
		        int monthOfYear = current_.get(Calendar.MONTH) + 1;
		        int dayOfMonth = 1;
				picker_ = new DatePickerDialog(SimpleAttendanceActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
								
								SQLiteDatabase db = dbHelper_.getReadableDatabase();
								
								File file = getOutputFilePath(year, monthOfYear);
								try {
							        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false),"UTF-8"));

							        for (int i = dayOfMonth; i <= 31; ++i) {
										String date_id = String.format("%1$04d/%2$02d/%3$02d", year, monthOfYear, i);
										AttendanceRecord record = dbHelper_.serchByDayForAttendance(db, date_id);
										
										if (record != null) {
											writer.append(date_id + "\t" + record.beginTime() + "\t" + record.endTime() + "\r\n");
										} else {
											writer.append(date_id + "\t" + "\r\n");
										}
									}
							        writer.flush();
							        writer.close();
							    } catch (IOException e) {
							        e.printStackTrace();
							    }
								Toast.makeText(SimpleAttendanceActivity.this, file.getAbsolutePath() + "に保存しました", Toast.LENGTH_LONG).show();
								db.close();
							}
						}, year, monthOfYear, dayOfMonth);
				picker_.show();
			}
		});
        
        
        TextView currentDate = (TextView)findViewById(R.id.currentDate);
        currentDate.setText(DataUtil.getDayForString(current_) + "(" + DataUtil.getDayOfWeekString(current_) + ")"
        		+ " " + DataUtil.getTimeForString(new Date(System.currentTimeMillis())));
    }
    
    /**
     * 勤怠ファイルのパスを返す
     * @param year
     * @param monthOfYear
     * @return
     */
    private File getOutputFilePath(int year, int monthOfYear)
    {
    	File outputFile = new File(getOutputFileDirPath() + "/" + year + "_" + monthOfYear + ".txt");
    	return outputFile;
    }
    
    /**
     * 勤怠ファイルを格納するディレクトリパスを返す
     * @return
     */
    private String getOutputFileDirPath()
    {
    	String status = Environment.getExternalStorageState();
    	if (status.equals(Environment.MEDIA_MOUNTED)) {
    		String extStoragePath = Environment.getExternalStorageDirectory().getPath();
    		extStoragePath += "/attendance";
    		File dir = new File(extStoragePath);
    		if (!dir.exists()) {
    			dir.mkdir();
    		}
    		return extStoragePath;
    	} else {
    		//TODO SDカードがマウントされていない場合の処理
    		return "";
    	}
    }
    
    private Calendar current_ = null;
    private AttendanceDbHelper dbHelper_ = null;
    private DatePickerDialog picker_ = null;
}