/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱。

package com.schautup.alarmdemo;

import java.util.ArrayDeque;
import java.util.Queue;

import android.app.Application;
import android.app.PendingIntent;
import android.view.View;

/**
 * The application object.
 */
public final class App extends Application {
	/**
	 * A cache for all shown setting groups of demo in {@link com.schautup.alarmdemo.AlarmManagerActivity}.
	 * <p/>
	 * <b>This isn't a nice coding style, because here in demo we only wanna show the implementation of using {@link
	 * android.app.AlarmManager}, we ignore the harm side of this coding. Don't use it in production.</b>
	 */
	private Queue<View> mAllShownSettingGroups = new ArrayDeque<View>();
	/**
	 * The {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver} of demo associated
	 * with {@link com.schautup .alarmdemo.AlarmManagerActivity}.
	 * <p/>
	 * <b>This isn't a nice coding style, because here in demo we only wanna show the implementation of using {@link
	 * android.app.AlarmManager}, we ignore the harm side of this coding. Don't use it in production.</b>
	 */
	private PendingIntent mPendIntentReceiver;


	/**
	 * The {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver} of demo associated
	 * with {@link com.schautup .alarmdemo.AlarmManagerActivity}.
	 * <p/>
	 * <b>This isn't a nice coding style, because here in demo we only wanna show the implementation of using {@link
	 * android.app.AlarmManager}, we ignore the harm side of this coding. Don't use it in production.</b>
	 */
	private PendingIntent mPendIntentScheduleReceiver;

	/**
	 * Get cached setting groups for demo in {@link com.schautup.alarmdemo.AlarmManagerActivity}.
	 *
	 * @return A {@link java.util.Queue} of {@link android.view.View}s.
	 */
	public Queue<View> getAllShownSettingGroups() {
		return mAllShownSettingGroups;
	}

	/**
	 * Add a shown setting group.
	 *
	 * @param v
	 * 		{@link android.view.View} that has been shown.
	 */
	public void addSettingGroup(View v) {
		mAllShownSettingGroups.add(v);

	}

	/**
	 * Get the {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver}
	 *
	 * @return The {@link android.app.PendingIntent}.
	 */
	public PendingIntent getPendIntentReceiver() {
		return mPendIntentReceiver;
	}

	/**
	 * Set the {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver} of demo associated
	 * with {@link com.schautup .alarmdemo.AlarmManagerActivity}.
	 *
	 * @param pendIntentReceiver
	 * 		The {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver}.
	 */
	public void setPendIntentReceiver(PendingIntent pendIntentReceiver) {
		mPendIntentReceiver = pendIntentReceiver;
	}


	/**
	 * Get the {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver}
	 *
	 * @return The {@link android.app.PendingIntent}.
	 */
	public PendingIntent getPendIntentScheduleReceiver() {
		return mPendIntentScheduleReceiver;
	}

	/**
	 * Set the {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver} of demo associated
	 * with {@link com.schautup .alarmdemo.AlarmManagerActivity}.
	 *
	 * @param pendIntentScheduleReceiver
	 * 		The {@link android.app.PendingIntent} to call {@link com.schautup.alarmdemo.AlarmReceiver}.
	 */
	public void setPendIntentScheduleReceiver(PendingIntent pendIntentScheduleReceiver) {
		mPendIntentScheduleReceiver = pendIntentScheduleReceiver;
	}
}
