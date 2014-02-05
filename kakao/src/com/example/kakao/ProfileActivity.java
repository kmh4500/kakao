package com.example.kakao;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	private String mPhone;
	private int mRoomId;
	private String mName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		mName = getIntent().getExtras().getString("name");
		mPhone = getIntent().getExtras().getString("phone");
		mRoomId = getIntent().getExtras().getInt("room_id");
		TextView view = (TextView) findViewById(R.id.profile_name);
		view.setText(mName);
		
		TextView call = (TextView) findViewById(R.id.call);
		call.setText(mPhone);
		
		ImageView close = (ImageView) findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.call).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + mPhone));
				startActivity(callIntent);
			}
		});
		
		findViewById(R.id.chat).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileActivity.this,
						RoomActivity.class);
				intent.putExtra("name", mName);
				intent.putExtra("room_id", mRoomId);
				startActivity(intent);
			}
		});
	}
}
