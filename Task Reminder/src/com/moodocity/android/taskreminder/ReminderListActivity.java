package com.moodocity.android.taskreminder;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ReminderListActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_list);

		String[] items = new String[] { "Foo", "Bar", "Fizz", "Bin" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.reminder_row, R.id.text1, items);
		setListAdapter(adapter);
	}
}