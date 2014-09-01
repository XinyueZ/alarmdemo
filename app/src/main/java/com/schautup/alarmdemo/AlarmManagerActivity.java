package com.schautup.alarmdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
	 * Output.
	 */
	private TextView mMsgTv;

	/**
	 * Show an instance of {@link AlarmManagerActivity}
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
	}
}
