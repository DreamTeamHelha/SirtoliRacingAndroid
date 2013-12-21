package com.example.sirtoliracing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.model.sirtoliracing.Joueur;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Score extends Activity {

	private List<Joueur>listeJoueurs= new ArrayList<Joueur>(); 
	private TextView raceName;
	private TextView nameTab[]=new TextView[10],timeTab[]=new TextView[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
	
		Bundle extra = getIntent().getExtras();
        String variable = extra.getString("url_track");
        String nameTrack= extra.getString("name_track");
    	RecoverInformation recover= new RecoverInformation();
		recover.execute(variable);
		raceName=(TextView)findViewById(R.id.textView_Titre);
		raceName.setText(nameTrack);
	
		
		nameTab[0]=(TextView)findViewById(R.id.textView_name1);
		nameTab[1]=(TextView)findViewById(R.id.textView_name2);
		nameTab[2]=(TextView)findViewById(R.id.textView_name3);
		nameTab[3]=(TextView)findViewById(R.id.textView_name4);
		nameTab[4]=(TextView)findViewById(R.id.textView_name5);
		nameTab[5]=(TextView)findViewById(R.id.textView_name6);
		nameTab[6]=(TextView)findViewById(R.id.textView_name7);
		nameTab[7]=(TextView)findViewById(R.id.textView_name8);
		nameTab[8]=(TextView)findViewById(R.id.textView_name9);
		nameTab[9]=(TextView)findViewById(R.id.textView_name10);
		
		timeTab[0]=(TextView)findViewById(R.id.textView_time1);
		timeTab[1]=(TextView)findViewById(R.id.textView_time2);		
		timeTab[2]=(TextView)findViewById(R.id.textView_time3);	
		timeTab[3]=(TextView)findViewById(R.id.textView_time4);	
		timeTab[4]=(TextView)findViewById(R.id.textView_time5);	
		timeTab[5]=(TextView)findViewById(R.id.textView_time6);	
		timeTab[6]=(TextView)findViewById(R.id.textView_time7);	
		timeTab[7]=(TextView)findViewById(R.id.textView_time8);	
		timeTab[8]=(TextView)findViewById(R.id.textView_time9);	
		timeTab[9]=(TextView)findViewById(R.id.textView_time10);	
        
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}
	
	private class RecoverInformation extends AsyncTask<String, Void,String> {

	
	
	@Override
	protected void onPreExecute() {
	// TODO Stub de la m�thode g�n�r� automatiquement
	super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(String result) {
	// TODO Stub de la m�thode g�n�r� automatiquement
		//Log.v("test",result);//verification check param ok
		try {
			
			JSONArray array = new JSONArray(result);
			
			for(int i = 0 ; i < array.length() ; i++){
				JSONObject jsonobj =  array.getJSONObject(i);
				String name= (String)jsonobj.get("Name");
				int time=jsonobj.getInt("Time");				
				Joueur j= new Joueur(name, time);
				listeJoueurs.add(j);
				
			}
			Collections.sort(listeJoueurs);
			String timing;
			for(int i=0;i<10;i++)
			{
				nameTab[i].setText(listeJoueurs.get(i).getName());
				timing=calculTime(listeJoueurs.get(i).getScore());
				timeTab[i].setText(timing);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	super.onPostExecute(result);
	}

	
	
	String response=null;
	
	@Override
	protected String doInBackground(String... params) {
		
		//Log.v("test",params[0]);//verification check param ok
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
		
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.v("test",response);//verification check param ok

		return response;
	}
	private String calculTime(int time)
	{
		
		int min=time/60000;
		int sec=(time-(min*60000))/1000;
		int mili=(time-(sec*1000)-(min*60000))/10;
		//int milisec=
		
		
		return min+"min"+sec+"sec"+mili;
	}

	
}	
	
	
	
	
}
	
	
	
	