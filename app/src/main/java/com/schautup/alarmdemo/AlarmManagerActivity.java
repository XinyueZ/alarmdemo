package com.schautup.alarmdemo;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * To demo the {@link AlarmManagerActivity}.
 * <p/>
 * Some text will be updated when the {@link AlarmManagerActivity} per 1 min broadcasts.
 *
 * @author Xinyue Zhao
 */
public final class AlarmManagerActivity extends ActionBarActivity {
	/**
	 * Main layout for this component.
	 */
	private static final int LAYOUT = R.layout.activity_alarm_manager;
	/**
	 * One minute in millis.
	 */
	private static final int ONE_MINUTE = 60 * 1000;
	/**
	 * Output.
	 */
	private TextView mMsgTv;
	/**
	 * Intent to call {@link com.schautup.alarmdemo.AlarmReceiver}.
	 */
	private PendingIntent mPendIntentReceiver;

	/**
	 * The {@link android.app.AlarmManager}.
	 */
	private AlarmManager mAlarmManager;

	/**
	 * /** Show an instance of {@link AlarmManagerActivity}
	 *
	 * @param cxt
	 * 		{@link android.content.Context}.
	 */
	public static void showInstance(Context cxt) {
		Intent intent = new Intent(cxt, AlarmManagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		cxt.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		mMsgTv = (TextView) findViewById(R.id.msg_tv);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		mMsgTv.setText(String.format(getString(R.string.lbl_rtc_per_min), c.getTime()));


		// Start tracking for every minute and call the AlarmReceiver as soon as possible.
		mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		mPendIntentReceiver = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Time since boot(as the time we see the view) + ONE_MINUTE(We should wait).
		mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+ ONE_MINUTE,
				ONE_MINUTE, mPendIntentReceiver);
	}


	/**
	 * Stop listening to the {@link android.app.AlarmManager}.
	 *
	 * @param view
	 * 		No be used.
	 */
	public void stopAlarm(View view) {
		//Stop all alarm tracks
		mAlarmManager.cancel(mPendIntentReceiver);
	}
}
