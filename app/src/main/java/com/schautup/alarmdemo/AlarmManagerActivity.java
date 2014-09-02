package com.schautup.alarmdemo;

import java.util.Calendar;
import java.util.Queue;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * To demo the {@link AlarmManagerActivity}.
 * <p/>
 * Some text will be updated when the {@link AlarmManagerActivity} per 1 min broadcasts.
 *
 * @author Xinyue Zhao
 */
public final class AlarmManagerActivity extends ActionBarActivity implements AddNewLineCallback {
	/**
	 * Daily alarming.
	 */
//	static final int PERIOD = 60 * 1000;
	static final long PERIOD = AlarmManager.INTERVAL_DAY;
	/**
	 * Main layout for this component.
	 */
	private static final int LAYOUT = R.layout.activity_alarm_manager;
	/**
	 * One minute in millis.
	 */
	private static final int ONE_MINUTE = 60 * 1000;
	/**
	 * Current time zone.
	 */
	private static final String TIMEZONE = "GMT+2";
	/**
	 * Parent view that contains all.
	 */
	private ViewGroup mContentVp;
	/**
	 * Output.
	 */
	private TextView mMsgTv;
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
		App app = (App) getApplication();
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);

		mContentVp = (ViewGroup) findViewById(R.id.content_ll);
		mMsgTv = (TextView) findViewById(R.id.msg_tv);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		mMsgTv.setText(String.format(getString(R.string.lbl_elapsed_per_min), c.getTime()));

		// Start tracking for every minute and call the AlarmReceiver as soon as possible.
		mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


		// Show start and stop buttons for single alarm demo.
		if (app.getPendIntentReceiver() != null) {
			// We've started once.
			findViewById(R.id.start_single_alarm_btn).setEnabled(false);
			findViewById(R.id.stop_single_alarm_btn).setEnabled(true);
		} else {
			// We've never started or once stopped.
			findViewById(R.id.start_single_alarm_btn).setEnabled(true);
			findViewById(R.id.stop_single_alarm_btn).setEnabled(false);
		}


		// Show setting groups if necessary.
		Queue<View> cachedViews = app.getAllShownSettingGroups();
		if (cachedViews.size() == 0) {
			// Add first setting group when we haven't setting any alarms.
			addNewLine();
		} else {
			// Resume the setting groups that we've added.
			ViewGroup parent;
			for (View v : cachedViews) {
				parent = (ViewGroup) v.getParent();
				parent.removeView(v);
				mContentVp.addView(v);
			}
		}
	}


	/**
	 * Start listening to the {@link android.app.AlarmManager}.
	 *
	 * @param view
	 * 		No be used.
	 */
	public void startAlarm(View view) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		mMsgTv.setText(String.format(getString(R.string.lbl_elapsed_per_min), c.getTime()));

		Intent intent = new Intent(AlarmManagerActivity.this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(AlarmManagerActivity.this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// Time since boot(as the time we see the view) + ONE_MINUTE(We should wait).
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ONE_MINUTE,
				ONE_MINUTE, pi);

		//We have started alarm for every minute, only to do is stop.
		findViewById(R.id.stop_single_alarm_btn).setEnabled(true);
		view.setEnabled(false);

		App app = (App) getApplication();
		app.setPendIntentReceiver(pi);
	}

	/**
	 * Stop listening to the {@link android.app.AlarmManager}.
	 *
	 * @param view
	 * 		No be used.
	 */
	public void stopAlarm(View view) {
		App app = (App) getApplication();

		// Stop all alarm tracks
		mAlarmManager.cancel(app.getPendIntentReceiver());
		Toast.makeText(this, R.string.lbl_canceled, Toast.LENGTH_SHORT).show();

		// We stop and can start again next time.
		findViewById(R.id.start_single_alarm_btn).setEnabled(true);
		view.setEnabled(false);

		app.setPendIntentReceiver(null);
	}

	@Override
	public void addNewLine() {
		// Add next setting group.
		View view = getLayoutInflater().inflate(R.layout.inc_hour_minute, null, false);
		mContentVp.addView(view);
		view.setTag(new ViewStub(this, mAlarmManager, view, this));

		// We cache the view and for next resume.
		App app = (App) getApplication();
		app.addSettingGroup(view);
	}

	/**
	 * A similar pattern to ViewHolder, the "start" and "stop" buttons communicate each other.
	 *
	 * @author Xinyue Zhao
	 */
	private static class ViewStub {
		/**
		 * {@link android.content.Context}.
		 */
		private Context mContext;
		/**
		 * {@link android.app.AlarmManager}. It should be initialized by the host {@link android.app .Activity}.
		 */
		private AlarmManager mAlarmManager;
		//----------------------------------------------------------
		// Description: All UI for each setting line(group).
		//----------------------------------------------------------
		private EditText mHourEt;
		private Button mStartBtn;
		private EditText mMinuteEt;
		private Button mStopBtn;
		/**
		 * The {@link android.app.PendingIntent} that be fired later when system alarms.
		 */
		private PendingIntent mPendingIntent;

		/**
		 * Constructor of {@link com.schautup.alarmdemo.AlarmManagerActivity.ViewStub}.
		 *
		 * @param cxt
		 * 		{@link android.content.Context}.
		 * @param alarmManager
		 * 		{@link android.app.AlarmManager}. It should be initialized by the host {@link android.app .Activity}.
		 * @param parentV
		 * 		A parent {@link android.view.View} for each setting line.
		 * @param callback
		 * 		A callback event when a new setting line(group) insert.
		 */
		public ViewStub(final Context cxt, AlarmManager alarmManager, View parentV, final AddNewLineCallback callback) {
			mContext = cxt;
			mAlarmManager = alarmManager;
			mStartBtn = (Button) parentV.findViewById(R.id.start_btn);
			mHourEt = (EditText) parentV.findViewById(R.id.hour_et);
			mStopBtn = (Button) parentV.findViewById(R.id.stop_btn);
			mMinuteEt = (EditText) parentV.findViewById(R.id.minute_et);

			mStartBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Time since boot(as the time we see the view).
					long firstTime = SystemClock.elapsedRealtime();
					// Current time point.
					long currentTime = System.currentTimeMillis();
					// The time we wanna get alarm.
					int hour = Integer.valueOf(mHourEt.getText().toString());
					int minute = Integer.valueOf(mMinuteEt.getText().toString());
					Calendar calendar = Calendar.getInstance();
					// Important! Otherwise there's deviation.
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
					calendar.set(Calendar.HOUR_OF_DAY, hour);
					calendar.set(Calendar.MINUTE, minute);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					long setTime = calendar.getTimeInMillis();
					// Reject when set time smaller.
					if (setTime < currentTime) {
						Toast.makeText(mContext, R.string.lbl_warning, Toast.LENGTH_LONG).show();
					} else {
						// Do alarm at the time point we wanna.
						// A difference between setTime and currentTime plus total time since boot equal to the time
						// point we wanna.
						long timeToAlarm = firstTime + (setTime - currentTime);
						Intent intent = new Intent(mContext, AlarmReceiver.class);
						mPendingIntent = PendingIntent.getBroadcast(mContext, 1, intent, PendingIntent.FLAG_ONE_SHOT);
						((App)cxt.getApplicationContext()).setPendIntentScheduleReceiver(mPendingIntent);
						if (android.os.Build.VERSION.SDK_INT >= 19) {
							mAlarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeToAlarm, mPendingIntent);
						} else {
							mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeToAlarm, mPendingIntent);

							// We schedule by ourselves instead of calling method below. See AlarmReceiver#scheduleAlarms(Context cxt);
							//
							// mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeToAlarm,
							//									PERIOD, mPendingIntent);
						}
						mStartBtn.setEnabled(false);
						mStopBtn.setEnabled(true);
						mHourEt.setEnabled(false);
						mMinuteEt.setEnabled(false);

						callback.addNewLine();
					}

				}
			});

			mStopBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mAlarmManager.cancel(((App)cxt.getApplicationContext()).getPendIntentScheduleReceiver());
					mStartBtn.setEnabled(true);
					mStopBtn.setEnabled(false);
					mHourEt.setEnabled(true);
					mMinuteEt.setEnabled(true);
				}
			});
		}
	}

}

/**
 * Callback when a new line(group) for setting hour-minute for demo will be added.
 *
 * @author Xinyue Zhao
 */
interface AddNewLineCallback {
	/**
	 * Event that inserts a new line(group).
	 */
	void addNewLine();
}
