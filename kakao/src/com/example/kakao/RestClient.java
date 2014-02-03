package com.example.kakao;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class RestClient {

	public static String getMessages(int roomId) {
		HttpContext localContext = new BasicHttpContext();
		HttpClient client = new DefaultHttpClient(); 
		HttpGet get = new HttpGet("http://kakao-kmh.appspot.com/messages?room_id=" + roomId);
		try {
			HttpResponse response = client.execute(get, localContext);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
