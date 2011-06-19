package com.hartakarun;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HartaKarun extends Activity {
	/** Called when the activity is first created. */
	private Button daftar, login;
	private EditText username, password;
	private String status;
	private String url = "http://10.0.2.2/hartakarun/index.php/home/login";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		daftar = (Button) findViewById(R.id.daftar_awal);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getRequest(url, username, password);
				Toast.makeText(HartaKarun.this, status, Toast.LENGTH_LONG).show();
			}
		});
		daftar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendaftaran = new Intent(HartaKarun.this, SignUp.class);
				startActivityForResult(pendaftaran, 0);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void getRequest(String url, EditText username, EditText password) {
		Log.d("getRequest : ", url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost postdata = new HttpPost(url);
		try {
			@SuppressWarnings("rawtypes")
			List post = new ArrayList(1);
			post.add(new BasicNameValuePair("usernmae", username.getText().toString()));
			post.add(new BasicNameValuePair("password", password.getText().toString()));
			postdata.setEntity(new UrlEncodedFormEntity(post));
			HttpResponse response = httpclient.execute(postdata);
			status = request(response).toString();

			// hasil.setText(request(response));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(HartaKarun.this, "conection error",
					Toast.LENGTH_LONG).show();
		}
	}

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// this.closeContextMenu();
		super.onDestroy();
	};
}