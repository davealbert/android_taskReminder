package com.moodocity.android.taskreminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class ReminderEditActivity extends Activity {
	private Button mTimeButton;
	private EditText mTitleText;
	private Button mConfirmButton;
	private EditText mBodyText;
	private RemindersDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new RemindersDbAdapter(this);

		mConfirmButton = (Button) findViewById(R.id.confirm);
		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);

		setContentView(R.layout.reminder_edit);

		mDateButton = (Button) findViewById(R.id.reminder_date);
		mTimeButton = (Button) findViewById(R.id.reminder_time);

		if (getIntent() != null) {
			Bundle extras = getIntent().getExtras();
			int rowId = extras != null ? extras.getInt("RowId") : -1;
			// Do stuff with the row id here
		}

		registerButtonListenersAndSetDefaultText();
	}

	private Button mDateButton;
	private static final int DATE_PICKER_DIALOG = 0;
	private static final int TIME_PICKER_DIALOG = 1;

	private void registerButtonListenersAndSetDefaultText() {
		mDateButton.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_DIALOG);
			}
		});

		mTimeButton.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View v) {
				showDialog(TIME_PICKER_DIALOG);
			}
		});

		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				saveState();
				setResult(RESULT_OK);
				Toast.makeText(ReminderEditActivity.this,
						getString(R.string.task_saved_message),
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		updateDateButtonText();
		updateTimeButtonText();
	}

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

	private void saveState() {
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());
		long id = mDbHelper.createReminder(title, body, reminderDateTime);
	}

	private Calendar mCalendar = Calendar.getInstance();
	private static final String TIME_FORMAT = "kk:mm";

	private void updateTimeButtonText() {
		SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
		String timeForButton = timeFormat.format(mCalendar.getTime());
		mTimeButton.setText(timeForButton);
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private void updateDateButtonText() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String dateForButton = dateFormat.format(mCalendar.getTime());
		mDateButton.setText(dateForButton);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG:
			return showDatePicker();

		case TIME_PICKER_DIALOG:
			return showTimePicker();
		}

		return super.onCreateDialog(id);
	}

	private TimePickerDialog showTimePicker() {
		TimePickerDialog timePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					// @Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						mCalendar.set(Calendar.MINUTE, minute);
						updateTimeButtonText();
					}
				}, mCalendar.get(Calendar.HOUR_OF_DAY),
				mCalendar.get(Calendar.MINUTE), true);
		return timePicker;
	}

	private DatePickerDialog showDatePicker() {
		// TODO Auto-generated method stub
		DatePickerDialog datePicker = new DatePickerDialog(
				ReminderEditActivity.this,
				new DatePickerDialog.OnDateSetListener() {
					// @Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						mCalendar.set(Calendar.YEAR, year);
						mCalendar.set(Calendar.MONTH, monthOfYear);
						mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						updateDateButtonText();
					}
				}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH));
		return datePicker;

	}

}
