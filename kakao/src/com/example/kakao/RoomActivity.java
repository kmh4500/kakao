package com.example.kakao;

import java.util.Date;

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

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        String name = getIntent().getExtras().getString("name");
        setTitle(name);
        initSendButton();
    }

	private void initSendButton() {
		View send = findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText text = (EditText) findViewById(R.id.edit);
				String message = text.getEditableText().toString();
				addMessageItem(message);
				text.setText("");
			}
		});
	}

	private void addMessageItem(String messageString) {
		View item = View.inflate(this, R.layout.message_item, null);
		TextView message = (TextView) item.findViewById(R.id.message);
		TextView time = (TextView) item.findViewById(R.id.time);
		time.setText(DateFormat.format("hh:mm", new Date()));
		
		message.setText(messageString);
		LinearLayout room = (LinearLayout) findViewById(R.id.room);
		room.addView(item, room.getChildCount() - 1);
	}
}
