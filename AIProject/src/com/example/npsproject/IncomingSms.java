package com.example.npsproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public static String _message;
	public static String _senderNum;
	public static Context _context;

	public void onReceive(final Context context, Intent intent) {

		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();

					/*
					 * ContentValues my_values = new ContentValues();
					 * values.put("address", "+923001234567");//sender name
					 * values.put("body", "this is my text");
					 * getContentResolver(
					 * ).insert(Uri.parse("content://sms/inbox"), my_values);
					 */

					_senderNum = phoneNumber;
					_context = context;
					_message = currentMessage.getDisplayMessageBody();

					Log.d("textmsg", "here");
					CallServer callServer = new CallServer();

					callServer.execute(_message);

					Log.d("textmsg", callServer.getStatus().toString());
					abortBroadcast();
					Log.d("textmsg", callServer.getStatus().toString());

					// Show Alert

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}
	}

	private class CallServer extends AsyncTask<String, String, String> {
		@SuppressWarnings("deprecation")
		@SuppressLint("NewApi")
		@Override
		protected String doInBackground(String... arg0) {
			Socket socket = null;
			DataOutputStream dataOutputStream = null;
			DataInputStream dataInputStream = null;

			try {
				socket = new Socket("54.200.102.36", 8081);// Your server ip
				Log.d("textmsg", "connected");
				dataOutputStream = new DataOutputStream(
						socket.getOutputStream());
				dataInputStream = new DataInputStream(socket.getInputStream());

				if (!_message.isEmpty()) {
					dataOutputStream.writeUTF("From: " + _senderNum
							+ "Message: " + _message);
					return dataInputStream.readLine();
				} else {
					Log.d("textmsg", "else");
					return "false";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("SmsReceiver", "Exception smsReceiver" + e);
			}

			return "false";
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			Log.d("textmsg", result + " +++ here");

			if (result.equals("false")) {
				NotificationManager nm = (NotificationManager) _context
						.getSystemService(Context.NOTIFICATION_SERVICE);

				Notification notification = new Notification(
						android.R.drawable.ic_dialog_email, _message,
						System.currentTimeMillis());

				Intent notificationIntent = new Intent(_context,
						IncomingSms.class);
				PendingIntent contentIntent = PendingIntent.getActivity(
						_context, 0, notificationIntent, 0);

				notification.setLatestEventInfo(_context, _senderNum, _message,
						contentIntent);
				notification.defaults = Notification.DEFAULT_ALL;
				nm.notify(0, notification);

				ContentValues values = new ContentValues();
				values.put("address", _senderNum);
				values.put("body", _message);
				_context.getContentResolver().insert(
						Uri.parse("content://sms/inbox"), values);

			}

			else {
				DBHelper helper = new DBHelper(_context);
				helper.insertSpam(_senderNum, _message);
			}

			Toast toast = Toast.makeText(_context, "senderNum: " + _senderNum
					+ ", message: " + _message, Toast.LENGTH_SHORT);
			toast.show();
		}

	}
}
