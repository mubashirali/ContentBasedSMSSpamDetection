package com.example.npsproject;

import java.util.Calendar;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "NPDB";

	// Login table name
	private static final String TABLE = "Spams";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void insertSpam(String number, String msg) {

		String currentDateTime = java.text.DateFormat.getDateTimeInstance()
				.format(Calendar.getInstance().getTime());
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("pkTime", currentDateTime);
		values.put("number", number);
		values.put("msg", msg);
		// Inserting Row
		db.insert(TABLE, null, values);
		db.close(); // Closing database connection
		MainActivity.items.add(new Item(number, msg + '\n'));
		MainActivity.updateAD();

	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE + " order by pkTime";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("pkTime", cursor.getString(1));
			user.put("number", cursor.getString(2));
			user.put("msg", cursor.getString(3));
		}
		cursor.close();
		db.close();
		// return user
		return user;
	}
}
