//package com.example.timemanagement.utils;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.timemanagement.dto.HiiTTimer;
//
//public class DatabaseHelper extends MSQLiteOpenHelper {
//	private static final int DB_VERSION = 1;
//	private static final String DB_NAME = "hiit.db";
//
//	public DatabaseHelper(Context context) {
//		super(context, DB_NAME, null, DB_VERSION);
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		createTable(db, HiiTTimer.class, true);
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		dropTable(db, HiiTTimer.class, true);
//
//		onCreate(db);
//	}
//}
