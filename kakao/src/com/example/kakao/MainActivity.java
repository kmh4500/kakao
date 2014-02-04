package com.example.kakao;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private static final String TAG = "MainActivity";
	
	private LinearLayout mFriend;
	private View mChat;
	private View mSearch;
	private View mMore;
	private ImageView mProfile;

	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "1086162951234";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;
    
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
		context = getApplicationContext();
		// Check device for Play Services APK.
	    if (checkPlayServices()) {
	    	gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            //if (TextUtils.isEmpty(regid)) {
                registerInBackground();
            //}
	    } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (TextUtils.isEmpty(registrationId)) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	public static String names[] = new String[] { "Seungwon", "Jongbae", "Wooseok",
			"Jinwoo" };

	private static String texts[] = new String[] { "Hi", "Hello", "Nice to meet you",
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
			final int roomId = i + 1;
			friendItemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,
							RoomActivity.class);
					intent.putExtra("name", roomName);
					intent.putExtra("room_id", roomId);
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
	
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	        	System.out.println("reg id : " + msg);
	        	Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	        }
	    }.execute(null, null, null);
	}
	
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
	    // Your implementation here.
	}
	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
}
