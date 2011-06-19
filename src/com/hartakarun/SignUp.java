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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends Activity {
	private String url = "http://10.0.2.2/hartakarun/index.php/home/signup";
	private Button daftar;
	private EditText nama, username, password, alamat;
	private RadioButton laki, perempuan;
	private int jenis_kelamin;
	private String status;
	ProgressDialog success = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		daftar = (Button) findViewById(R.id.daftar);
		nama = (EditText) findViewById(R.id.nama_lengkap);
		username = (EditText) findViewById(R.id.username_daftar);
		password = (EditText) findViewById(R.id.password_daftar);
		alamat = (EditText) findViewById(R.id.alamat);
		laki = (RadioButton) findViewById(R.id.laki);
		perempuan = (RadioButton) findViewById(R.id.perempuan);

		
		daftar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(nama.getText().equals("")||username.getText().equals("")||password.getText().equals("")||alamat.getText().equals("")){
					Toast.makeText(SignUp.this,"tidak boleh kosong", Toast.LENGTH_LONG).show();
				}
				else {
					kelamin();
					getRequest(url, nama, username, password, jenis_kelamin, alamat);
					if(status.equals("1")){
						success = ProgressDialog.show(SignUp.this, "Sukses", "Pendaftaran Berhasil",true);
						Intent intent = new Intent();
		                setResult(RESULT_OK, intent);
		                finish();
		                success.dismiss();
					}
					else {
						Toast.makeText(SignUp.this, "Pendaftaran gagal !!! ",Toast.LENGTH_LONG).show();
					}
					
					//Toast.makeText(SignUp.this, status, Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void getRequest(String url, EditText nama, EditText username,
			EditText password, int jenis_kelamin, EditText alamat) {
		Log.d("getRequest : ", url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost postdata = new HttpPost(url);
		try {
			@SuppressWarnings("rawtypes")
			List post = new ArrayList(1);
			post.add(new BasicNameValuePair("nama", nama.getText().toString()));
			post.add(new BasicNameValuePair("username", username.getText()
					.toString()));
			post.add(new BasicNameValuePair("password", password.getText()
					.toString()));
			post.add(new BasicNameValuePair("jenis_kelamin", Integer
					.toString(jenis_kelamin)));
			post.add(new BasicNameValuePair("alamat", alamat.getText()
					.toString()));
			postdata.setEntity(new UrlEncodedFormEntity(post));
			HttpResponse response = httpclient.execute(postdata);
			status = request(response).toString();
		
			// hasil.setText(request(response));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(SignUp.this, "conection error", Toast.LENGTH_LONG)
					.show();
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

	public void kelamin() {

		if (perempuan.isChecked()==true) {
			jenis_kelamin = 0;
		}
		if (laki.isChecked()==true) {
			jenis_kelamin = 1;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
