package com.schautup.alarmdemo;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * To demo the {@link android.content.Intent#ACTION_TIME_TICK}.
 * <p/>
 * Some text will be updated when system per 1 min  broadcasts.
 *
 * @author Xinyue Zhao
 */
public final class NoAlarmManagerActivity extends ActionBarActivity {
	/**
	 * Main layout for this component.
	 */
	private static final int LAYOUT = R.layout.activity_no_alarm_manager;
	/**
	 * Output.
	 */
	private TextView mMsgTv;

	/**
	 * We wanna every-minute-event, this is the {@link android.content.IntentFilter} for every minute from system.
	 */
	private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
	/**
	 * We wanna event to handle for every minute, this is the {@link android.content.BroadcastReceiver} for every minute
	 * from system.
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("demo", "Every minute on NoAlarmManagerActivity: " + intent.toString());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			mMsgTv.setText(String.format(getString(R.string.lbl_change),  c.getTime()));
		}
	};

	/**
	 * Show an instance of {@link NoAlarmManagerActivity}
	 *
	 * @param cxt
	 * 		{@link android.content.Context}.
	 */
	public static void showInstance(Context cxt) {
		Intent intent = new Intent(cxt, NoAlarmManagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		cxt.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		mMsgTv = (TextView) findViewById(R.id.msg_tv);
		getApplication().registerReceiver(mReceiver, mIntentFilter);
		// A background service that listens to system tick changing.
		getApplication().startService(new Intent(getApplication(), NoAlarmManagerService.class));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getApplication().unregisterReceiver(mReceiver);
	}
}
