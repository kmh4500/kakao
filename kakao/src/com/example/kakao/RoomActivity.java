package com.example.kakao;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.util.TimeUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RoomActivity extends Activity {

	private static final String MY_NAME = "kmh4500";
	private static final String AUTO_NAME = "auto";
	private HashMap<String, String> mKeywordMap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        String name = getIntent().getExtras().getString("name");
        setTitle(name);
        initSendButton();
        
        mKeywordMap = new HashMap<String, String>();
        mKeywordMap.put("hi", "Hello");
        mKeywordMap.put("hello", "Hello.");
        mKeywordMap.put("nice", "Nice to meet you, too.");
    }

	private void initSendButton() {
		View send = findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText text = (EditText) findViewById(R.id.edit);
				String message = text.getEditableText().toString();
				addMessageItem(MY_NAME, message);
				analyzeMessage(message);
				
				text.setText("");
			}

		});
	}

	private void analyzeMessage(String message) {
		Set<String> keySet = mKeywordMap.keySet();
		for (String key : keySet) {
			if (message.contains(key)) {
				addMessageItem(AUTO_NAME, mKeywordMap.get(key));
			}
		}
	}
	
	private void addMessageItem(String nameString, String messageString) {
		View item = View.inflate(this, R.layout.message_item, null);
		TextView message = (TextView) item.findViewById(R.id.message);
		TextView time = (TextView) item.findViewById(R.id.time);
		time.setText(DateFormat.format("hh:mm", new Date()));
		
		message.setText(messageString);
		LinearLayout messages = (LinearLayout) findViewById(R.id.messages);
		
		TextView name = (TextView) item.findViewById(R.id.name);
		name.setText(nameString);
		messages.addView(item);
	}
}
