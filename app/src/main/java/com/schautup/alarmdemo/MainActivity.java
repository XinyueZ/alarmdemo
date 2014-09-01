package com.schautup.alarmdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * Main screen shows buttons to start different test scenes.
 */
public final class MainActivity extends ActionBarActivity {
	/**
	 * Main layout for this component.
	 */
	public static final int LAYOUT = R.layout.activity_main;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Test without the {@link android.app.AlarmManager}.
	 *
	 * @param view
	 * 		Not used.
	 */
	public void testNoAlarmmgr(View view) {
		NoAlarmManagerActivity.showInstance(this);
	}

	/**
	 * Test with the {@link android.app.AlarmManager}.
	 *
	 * @param view
	 * 		Not used.
	 */
	public void testAlarmmgr(View view) {
		AlarmManagerActivity.showInstance(this);
	}
}
