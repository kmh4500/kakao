package com.example.kakao;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.TimeUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RoomActivity extends Activity {

	private static final String MY_NAME = "kmh4500";
	private static final String AUTO_NAME = "auto";
	private HashMap<String, String> mKeywordMap;
	private ScrollView mScrollView;
	private View mCamera;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		String name = getIntent().getExtras().getString("name");
		setTitle(name);
		initSendButton();

		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		mKeywordMap = new HashMap<String, String>();
		mKeywordMap.put("hi", "Hello");
		mKeywordMap.put("hello", "Hello.");
		mKeywordMap.put("nice", "Nice to meet you, too.");
		
		mCamera = findViewById(R.id.camera);
		mCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent();
			}
		});
		
		mImageView = (ImageView) findViewById(R.id.image);
	}

	private void initSendButton() {
		View send = findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText text = (EditText) findViewById(R.id.edit);
				String message = text.getEditableText().toString();
				addMessageItem(MY_NAME, message, null);
				analyzeMessage(message);

				text.setText("");
				mScrollView.post(new Runnable() {

					@Override
					public void run() {
						mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
			}

		});
	}

	private void analyzeMessage(String message) {
		Set<String> keySet = mKeywordMap.keySet();
		for (String key : keySet) {
			if (message.contains(key)) {
				addMessageItem(AUTO_NAME, mKeywordMap.get(key), null);
			}
		}
	}

	private void addMessageItem(String nameString, String messageString, Bitmap imageBitmap) {
		View item = View.inflate(this, R.layout.message_item, null);
		TextView time = (TextView) item.findViewById(R.id.time);
		time.setText(DateFormat.format("hh:mm", new Date()));
		
		TextView name = (TextView) item.findViewById(R.id.name);
		name.setText(nameString);
		
		TextView message = (TextView) item.findViewById(R.id.message);
		ImageView image = (ImageView) item.findViewById(R.id.image);
		
		if (imageBitmap == null) {
			message.setText(messageString);
			message.setVisibility(View.VISIBLE);
			image.setVisibility(View.GONE);
		} else {
			image.setImageBitmap(imageBitmap);
			message.setVisibility(View.GONE);
			image.setVisibility(View.VISIBLE);
		}
		LinearLayout messages = (LinearLayout) findViewById(R.id.messages);
		messages.addView(item);
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			addMessageItem(MY_NAME, null, imageBitmap);
		}
	}
}
