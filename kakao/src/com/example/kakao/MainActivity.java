package com.example.kakao;

import java.io.File;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private LinearLayout mFriend;
	private View mChat;
	private View mSearch;
	private View mMore;
	private ImageView mProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFriend = (LinearLayout) findViewById(R.id.friend);
		mChat = findViewById(R.id.chat);
		mSearch = findViewById(R.id.search);
		mMore = findViewById(R.id.more);
		findViewById(R.id.friend_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mFriend.setVisibility(View.VISIBLE);
						mChat.setVisibility(View.GONE);
						mSearch.setVisibility(View.GONE);
						mMore.setVisibility(View.GONE);
					}
				});

		findViewById(R.id.chat_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mFriend.setVisibility(View.GONE);
						mChat.setVisibility(View.VISIBLE);
						mSearch.setVisibility(View.GONE);
						mMore.setVisibility(View.GONE);
					}
				});

		findViewById(R.id.more_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mFriend.setVisibility(View.GONE);
						mChat.setVisibility(View.GONE);
						mSearch.setVisibility(View.GONE);
						mMore.setVisibility(View.VISIBLE);
					}
				});

		setPerson();

		mProfile = (ImageView) findViewById(R.id.profile);
		mProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent();
			}
		});
	}

	private String names[] = new String[] { "Seungwon", "Jongbae", "Wooseok",
			"Jinwoo" };

	private String texts[] = new String[] { "Hi", "Hello", "Nice to meet you",
			"Me too." };

	private Integer pics[] = new Integer[] { R.drawable.profile,
			R.drawable.profile2, R.drawable.profile, R.drawable.profile2 };

	private TextView mName;
	private ImageView mPic;
	private TextView mText;

	private void setPerson() {
		mFriend = (LinearLayout) findViewById(R.id.friend);
		for (int i = 0; i < 4; ++i) {
			View friendItemView = View
					.inflate(this, R.layout.friend_item, null);
			mFriend.addView(friendItemView);
			mName = (TextView) friendItemView.findViewById(R.id.name);
			mName.setText(names[i]);
			mPic = (ImageView) friendItemView.findViewById(R.id.pic);
			mPic.setImageResource(pics[i]);
			mText = (TextView) friendItemView.findViewById(R.id.text);
			final String roomName = names[i];
			friendItemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,
							RoomActivity.class);
					intent.putExtra("name", roomName);
					startActivity(intent);
				}
			});
			mText.setText(texts[i]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			/*
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			mProfile.setImageBitmap(imageBitmap);*/
			setPic();
		}
	}
	
	private void setPic() {    
		// Get the dimensions of the View    
		int targetW = mProfile.getWidth();    
		int targetH = mProfile.getHeight();    
		
		// Get the dimensions of the bitmap   
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();    
		bmOptions.inJustDecodeBounds = true;    
		
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);    
		int photoW = bmOptions.outWidth;    
		int photoH = bmOptions.outHeight;   
		// Determine how much to scale down the image    
		int scaleFactor = Math.min(photoW/targetW, photoH/targetH);    
		
		// Decode the image file into a Bitmap sized to fill the View    
		bmOptions.inJustDecodeBounds = false;    
		bmOptions.inSampleSize = scaleFactor;    
		// bmOptions.inPurgeable = true;    
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions); 
		mProfile.setImageBitmap(bitmap);
	}

	String mCurrentPhotoPath;

	private File createImageFile() throws IOException {
		// Create an image file name
		String imageFileName = "profile";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = new File(storageDir.toString(), imageFileName + ".jpg");
		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}
}
