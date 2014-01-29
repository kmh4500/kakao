package com.example.kakao;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    private View mFriend;
	private View mChat;
	private View mSearch;
	private View mMore;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFriend = findViewById(R.id.friend);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
