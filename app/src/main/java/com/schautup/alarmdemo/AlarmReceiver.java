package com.schautup.alarmdemo;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Xinyue Zhao
 */
public final class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("demo", "Every minute on AlarmManagerActivity: " + intent.toString());
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
//		mMsgTv.setText(String.format(getString(R.string.lbl_change), c.getTime()));
		Toast.makeText(context,String.format(context.getString(R.string.lbl_change), c.getTime()),
				Toast.LENGTH_LONG ).show();
	}
}
