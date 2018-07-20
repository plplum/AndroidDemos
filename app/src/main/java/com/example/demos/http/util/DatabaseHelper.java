package com.example.demos.http.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// private SQLiteDatabase database;
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, "downloadFile.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type = ? AND name = ?", new String[] { "table", "info" });
			if (cursor.moveToNext()) {
				Log.d("DatabaseHelper", "该表已经存在");
			} else {
				Log.d("DatabaseHelper", "该表不存在 ，马上建立");
				db.execSQL("CREATE TABLE info (path VARCHAR(1024), threadid INTEGER , downloadlength INTEGER , PRIMARY KEY(path,threadid))");
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * db.execSQL("CREATE TABLE info (path VARCHAR(1024), threadid INTEGER , "
		 * + "downloadlength INTEGER , PRIMARY KEY(path,threadid))");
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
