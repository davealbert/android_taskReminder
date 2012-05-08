package com.moodocity.android.taskreminder;

import android.app.Activity;
import android.os.Bundle;

public class ReminderEditActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_edit);

		if (getIntent() != null) {
			Bundle extras = getIntent().getExtras();
			int rowId = extras != null ? extras.getInt("RowId") : -1;
			// Do stuff with the row id here
		}
	}
}