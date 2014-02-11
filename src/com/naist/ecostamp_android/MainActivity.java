package com.naist.ecostamp_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	TextView tvIsConnected,etReply;
	EditText etName,etCountry,etTwitter;
	Button btnPost;
	
	Person person;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
		etName = (EditText) findViewById(R.id.etName);
		etCountry = (EditText) findViewById(R.id.etCountry);
		etTwitter = (EditText) findViewById(R.id.etTwitter);
		btnPost = (Button) findViewById(R.id.btnPost);
		etReply = (TextView) findViewById(R.id.tvReply);
		
		if(isConnected()){
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You are connected");
		}
		else{
			tvIsConnected.setText("You are NOT connected");
		}
		
		btnPost.setOnClickListener(this);
	}
	
	public static String POST(String url, Person person){
		InputStream inputStream = null;
		String result = "";
		try{
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(url);
			
			String json = "";
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("name", person.getName());
			jsonObject.accumulate("country", person.getCountry());
			jsonObject.accumulate("twitter", person.getTwitter());
			
			json = jsonObject.toString();
			
			StringEntity se = new StringEntity(json);
			
			httpPost.setEntity(se);
			
			httpPost.setHeader("Accept","application/json");
			httpPost.setHeader("Content-type","application/json");
			
			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			inputStream = httpResponse.getEntity().getContent();
			
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
			
			
		} catch (Exception e){
			Log.d("InputStream",e.getLocalizedMessage());
		}
		
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}
	@Override
	public void onClick(View view){
		
		switch(view.getId()){
		case R.id.btnPost:
			if(!validate())
				Toast.makeText(getBaseContext(), "Enter some data", Toast.LENGTH_LONG).show();
			//http://hmkcode.appspot.com/jsonservlet
			new HttpAsyncTask().execute("http://ecostamp.aosekai.net/api/dummy/");
			//new HttpAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet");
		break;
		}
	}
	private class HttpAsyncTask extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String...urls){
			person = new Person();
			person.setName(etName.getText().toString());
			person.setCountry(etCountry.getText().toString());
			person.setTwitter(etTwitter.getText().toString());
			
			return POST(urls[0],person);
		}
		
		@Override
		protected void onPostExecute(String result){
			etReply.setText(result);
			Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
		}
	}
	
	private boolean validate(){
		if(etName.getText().toString().trim().equals(""))
			return false;
		else if(etCountry.getText().toString().trim().equals(""))
			return false;
		else if(etTwitter.getText().toString().trim().equals(""))
			return false;
		else
			return true;
	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}
}
