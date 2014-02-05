package com.example.kakao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String name = getIntent().getExtras().getString("name");
		TextView view = (TextView) findViewById(R.id.profile_name);
		view.setText(name);
	}
}
