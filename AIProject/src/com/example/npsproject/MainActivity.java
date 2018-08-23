package com.example.npsproject;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends ListActivity {

	private static MyAdapter adapter;
	public static ArrayList<Item> items = new ArrayList<Item>();
	static ArrayList<String> listItems = new ArrayList<String>();
	public static Button btn;
	public static SQLiteDatabase db;

	private BroadcastReceiver the_receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context c, Intent i) {

		}
	};

	// Set When broadcast event will fire.
	private IntentFilter filter = new IntentFilter(
			Intent.ACTION_CONFIGURATION_CHANGED);

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		/*
		 * SQLiteDatabase db = openOrCreateDatabase("NPDB", MODE_PRIVATE, null);
		 * db.execSQL("insert into Spams values('" + currentDateTimeString +
		 * "', '0343', 'Test');"); db.close();
		 */

		db = openOrCreateDatabase("NPDB", MODE_PRIVATE, null);
		
		db.execSQL("Create table if not exists Spams (pkTime TIMESTAMP, number VARCHAR, msg VARCHAR);");

		Cursor c = db.rawQuery("select * from Spams order by pkTime", null);

		if (c.moveToFirst()) {
			while (c.isAfterLast() == false) {

				String number = c.getString(c.getColumnIndex("number"));
				String spam = c.getString(c.getColumnIndex("msg"));
				items.add(new Item(number, spam + '\n'));
				c.moveToNext();

			}
		}
		adapter = new MyAdapter(this, items);
		setListAdapter(adapter);
	}

	public static void updateAD() {
		adapter.notifyDataSetChanged();

	}

	@Override
	protected void onResume() {

		// Register reciever if activity is in front
		this.registerReceiver(the_receiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {

		// Unregister reciever if activity is not in front
		this.unregisterReceiver(the_receiver);
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
