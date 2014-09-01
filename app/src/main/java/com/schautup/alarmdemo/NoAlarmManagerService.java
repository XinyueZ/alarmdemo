package com.schautup.alarmdemo;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


/**
 * To demo the {@link android.content.Intent#ACTION_TIME_TICK}.
 * <p/>
 * Some logcat, {@link android.widget.Toast} and notification information will be updated when system per 1 min
 * broadcasts.
 *
 * @author Xinyue Zhao
 */
public final class NoAlarmManagerService extends Service {
	/**
	 * We wanna every-minute-event, this is the {@link android.content.IntentFilter} for every minute from system.
	 */
	private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
	/**
	 * We wanna event to handle for every minute, this is the {@link android.content.BroadcastReceiver} for every minute
	 * from system.
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		Calendar mCalendar;

		@Override
		public void onReceive(Context cxt, Intent intent) {
			Log.d("demo", "Every minute on NoAlarmManagerService: " + intent.toString());
			mCalendar = Calendar.getInstance();
			mCalendar.setTimeInMillis(System.currentTimeMillis());

			String msg = String.format(getString(R.string.lbl_change), mCalendar.getTime());
			Toast.makeText(cxt, msg, Toast.LENGTH_LONG).show();


			int notificationID = (int) System.currentTimeMillis();
			((NotificationManager) cxt.getSystemService(Context.NOTIFICATION_SERVICE)).notify(notificationID,
					buildInBox(cxt, buildNotificationCommon(cxt, notificationID)));
		}

		/**
		 * Get the {@link NotificationCompat.Builder} of {@link android.app.Notification}.
		 * @param cxt {@link android.content.Context}.
		 * @param id The id of this notification.
		 * @return A {@link NotificationCompat.Builder}.
		 */
		private NotificationCompat.Builder buildNotificationCommon(Context cxt, int id) {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(cxt).setWhen(
					System.currentTimeMillis()).setTicker("Ticker: NoAlarmManagerService").setAutoCancel(
					true).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher)).setContentIntent(createMainPendingIntent(cxt, id))
					.setContentTitle("Content title: " + mCalendar.getTime().toString()).setContentText("Content " +
							"text: " + mCalendar.getTime().toString());


			builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
			builder.setLights(Color.RED, 3000, 3000);
			//builder.setSound(Uri.parse(tone));

			return builder;
		}

		/**
		 * Make more new modern style on the {@link android.app.Notification}.
		 * @param cxt  {@link android.content.Context}.
		 * @param builder  A {@link NotificationCompat.Builder}.
		 * @return A built {@link android.app.Notification}.
		 */
		private Notification buildInBox(Context cxt, NotificationCompat.Builder builder) {
			builder.setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("Big Content Title").bigText(
					"Big text").setSummaryText("Summary Text"));
			builder.addAction(R.drawable.ic_launcher, "title add action 1", createActionPendingIntent(cxt, 0))
					.setAutoCancel(true);
			builder.addAction(R.drawable.ic_launcher, "title add action 2", createActionPendingIntent(cxt, 1))
					.setAutoCancel(true);
			return builder.build();
		}


		/**
		 * Dummy action pending for clicking the {@link android.app.Notification}.
		 * @param cxt {@link android.content.Context}.
		 * @param reqCode The request code after clicking  the {@link android.app.Notification}.
		 * @return A {@link android.app.PendingIntent}  clicking  the {@link android.app.Notification}.
		 */
		private PendingIntent createMainPendingIntent(Context cxt, int reqCode) {
			Intent intent = new Intent(cxt, NoAlarmManagerActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			return PendingIntent.getActivity(cxt, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		}

		/**
		 * Dummy action pending for action buttons on the {@link android.app.Notification}.
		 * @param cxt {@link android.content.Context}.
		 * @param reqCode The request code after firing the action on the {@link android.app.Notification}.
		 * @return A {@link android.app.PendingIntent} for action buttons on the {@link android.app.Notification}.
		 */
		private PendingIntent createActionPendingIntent(Context cxt, int reqCode) {
			Intent intent = new Intent(cxt, NoAlarmManagerActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			return PendingIntent.getActivity(cxt, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		registerReceiver(mReceiver, mIntentFilter);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	public NoAlarmManagerService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
