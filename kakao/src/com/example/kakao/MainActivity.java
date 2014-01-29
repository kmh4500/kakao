package com.example.kakao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFriend = (LinearLayout) findViewById(R.id.friend);
        mChat = findViewById(R.id.chat);
        mSearch = findViewById(R.id.search);
        mMore = findViewById(R.id.more);
        findViewById(R.id.friend_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFriend.setVisibility(View.VISIBLE);
				mChat.setVisibility(View.GONE);
				mSearch.setVisibility(View.GONE);
				mMore.setVisibility(View.GONE);
			}
		});

        findViewById(R.id.chat_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFriend.setVisibility(View.GONE);
				mChat.setVisibility(View.VISIBLE);
				mSearch.setVisibility(View.GONE);
				mMore.setVisibility(View.GONE);
			}
		});
        

        findViewById(R.id.more_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFriend.setVisibility(View.GONE);
				mChat.setVisibility(View.GONE);
				mSearch.setVisibility(View.GONE);
				mMore.setVisibility(View.VISIBLE);
			}
		});
        
        setPerson();
    }

	private String names[] = new String[] {
		"Seungwon", "Jongbae", "Wooseok", "Jinwoo"
	};
	
	private String texts[] = new String[] {
			"Hi", "Hello", "Nice to meet you", "Me too."
	};
	
	private Integer pics[] = new Integer[] {
			R.drawable.profile, R.drawable.profile2, 
			R.drawable.profile, R.drawable.profile2	
	};
	
	private TextView mName;
	private ImageView mPic;
	private TextView mText;
	
    private void setPerson() {
    	mFriend = (LinearLayout) findViewById(R.id.friend);
    	for (int i = 0; i < 4; ++i) {
	    	View friendItemView = View.inflate(this, R.layout.friend_item, null);
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
					Intent intent = new Intent(MainActivity.this, RoomActivity.class);
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
    
}
