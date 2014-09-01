package com.schautup.alarmdemo;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * A {@link android.content.BroadcastReceiver} for the {@link android.app.AlarmManager}.
 * <p/>
 * <b>It will be called as soon as it was registered in to an {@link android.app.PendingIntent}.</b>
 * <p/>
 * For example:
 * <code>
 * <p/>
 * Intent intent = new Intent(this, AlarmReceiver.class);
 * <p/>
 * mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
 * <p/>
 * mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 60 * 1000, mPendingIntent);
 * </code>
 *
 * @author Xinyue Zhao
 */
public final class AlarmReceiver extends BroadcastReceiver {
	/**
	 * Time point when the {@link android.app.AlarmManager} called.
	 */
	private Calendar mCalendar;

	@Override
	public void onReceive(Context cxt, Intent intent) {
		mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(System.currentTimeMillis());

		Toast.makeText(cxt, String.format(cxt.getString(R.string.lbl_change_receiver), mCalendar.getTime()),
				Toast.LENGTH_LONG)
				.show();

		int notificationID = (int) System.currentTimeMillis();
		((NotificationManager) cxt.getSystemService(Context.NOTIFICATION_SERVICE)).notify(notificationID,
				buildInBox(cxt, buildNotificationCommon(cxt, notificationID)));
	}

	/**
	 * Get the {@link android.support.v4.app.NotificationCompat.Builder} of {@link android.app.Notification}.
	 * @param cxt {@link android.content.Context}.
	 * @param id The id of this notification.
	 * @return A {@link android.support.v4.app.NotificationCompat.Builder}.
	 */
	private NotificationCompat.Builder buildNotificationCommon(Context cxt, int id) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(cxt).setWhen(
				System.currentTimeMillis()).setTicker("Ticker: AlarmReceiver").setAutoCancel(
				true).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(cxt.getResources(),
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
		Intent intent = new Intent(cxt, AlarmManagerActivity.class);
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
		Intent intent = new Intent(cxt, AlarmManagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		return PendingIntent.getActivity(cxt, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	}
}
