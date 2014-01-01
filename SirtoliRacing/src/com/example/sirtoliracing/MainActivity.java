package com.example.sirtoliracing;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int depart;
	private String url_AllTracks="http://193.190.66.14:6080/SirtoliRacing/tracks";
	private String url_track="",name_Track="";
	private ArrayList<String>listNametracks;
	private ArrayList<String>listeNameTracksShow;
	private Button bCourse1,bCourse2,bCourse3;
	private ArrayList<Button>listeButtons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bCourse1=(Button)findViewById(R.id.button_Track1);
		bCourse2=(Button)findViewById(R.id.button_Track2);
		bCourse3=(Button)findViewById(R.id.button_Track3);
		listeButtons=new ArrayList<Button>();
		listeButtons.add(bCourse1);
		listeButtons.add(bCourse2);
		listeButtons.add(bCourse3);
		RecoverTrack recover=new RecoverTrack();
		recover.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	public void afficherScore(View view)
	{
		
		Intent intent= new Intent(this,Score.class);
		int indice;
		switch (view.getId()) {
		
		case R.id.button_Track1:
			name_Track=(String) listeButtons.get(0).getText();
			intent.putExtra("name_track", name_Track);
			name_Track= name_Track.replaceAll(" ", "_");
			indice=recoverTrack(name_Track);
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/"+name_Track;
			intent.putExtra("url_track", url_track);			
			intent.putExtra("Track_Array", listNametracks);
			intent.putExtra("Indice_Current", indice);			
			break;
		case R.id.button_Track2:
			name_Track=(String) listeButtons.get(1).getText();
			intent.putExtra("name_track", name_Track);
			name_Track= name_Track.replaceAll(" ", "_");
			indice=recoverTrack(name_Track);
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/"+name_Track;
			intent.putExtra("url_track", url_track);
			intent.putExtra("Track_Array", listNametracks);
			intent.putExtra("Indice_Current", indice);
			break;
		case R.id.button_Track3:
			name_Track=(String) listeButtons.get(2).getText();
			intent.putExtra("name_track", name_Track);
			name_Track= name_Track.replaceAll(" ", "_");
			indice=recoverTrack(name_Track);
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/"+name_Track;
			intent.putExtra("url_track", url_track);
			intent.putExtra("Track_Array", listNametracks);
			intent.putExtra("Indice_Current", indice);
			break;
		default:
			break;
		}
    	
    	startActivity(intent);
	
	}
	
	public void rechercherRaceVocal(View view)
	{
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text");
		startActivityForResult(intent, 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);
			Log.v("Indice", matches.toString());
			afficherScoreByVocal(matches);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void afficherScoreByVocal(ArrayList<String>matches)
	{
		for(int i=0;i<matches.size();i++)
		{
			if(matches.get(i).equals("race 1"))
			{
				if(bCourse1.isEnabled())
				bCourse1.performClick();
				
			}
			if(matches.get(i).equals("race 2"))
			{
				if(bCourse2.isEnabled())
				bCourse2.performClick();
				
			}
			if(matches.get(i).equals("race 3"))
			{
				if(bCourse3.isEnabled())
				bCourse3.performClick();
				
			}
		}
	}
	
	
	public void afficherSuivant(View view)
	{
		
		int size=listNametracks.size()-(depart+1);
		Log.v("NomButton",Integer.toString(size));
		if(size <3)
		{
			if(size==0)
			{
				
				for(int i=0;i<3;i++)
				{
					listeButtons.get(i).setText(listeNameTracksShow.get(i).toString());	
					
					listeButtons.get(i).setVisibility(View.VISIBLE);
					listeButtons.get(i).setEnabled(true);
					depart=i;
				}
			}
			else
			{
				for(int i=0;i<size;i++)
				{
					listeButtons.get(i).setText(listeNameTracksShow.get(depart+1).toString());
					listeButtons.get(i).setEnabled(true);
					depart++;
				}
				for(int i=size;i<3;i++)
				{
					listeButtons.get(i).setVisibility(View.GONE);
					listeButtons.get(i).setEnabled(false);
				}
			}
			
			
		}
		else
		{
			for(int i=0;i<3;i++)
			{
				listeButtons.get(i).setText(listeNameTracksShow.get(depart).toString());
				
				depart++;
			}
		}
	}
	public void afficherPrecedent(View view)
	{
		
		//recherche de la course prec�dente 
		
		int firstCurrentIndex= listeNameTracksShow.indexOf(listeButtons.get(0).getText());
		
		
		Log.v("Previous","firstCurrent"+Integer.toString(firstCurrentIndex));
		
		int reste= (listNametracks.size()-1)-firstCurrentIndex;
		
		Log.v("Previous","reste  "+Integer.toString(reste));
		int indice= firstCurrentIndex-1;
		Log.v("Indice","indice a afficher" +Integer.toString(indice));
		//partie ok ne pas toucher
		if (firstCurrentIndex==0)
		{
			indice=listeNameTracksShow.size()-1;
		}
		Log.v("Indice","indice a afficher"+Integer.toString(indice));
		if(reste<3)
		{
			for(int i=0;i<reste;i++)
			{
				listeButtons.get(i).setText(listeNameTracksShow.get(indice).toString());	
				indice++;
			}
			for(int i=reste;i<3;i++)
			{
				listeButtons.get(i).setVisibility(View.GONE);
			}
		}
		else
		{
			
				
				for(int i=0;i<3;i--)
				{
					listeButtons.get(i).setText(listeNameTracksShow.get(indice).toString());	
					listeButtons.get(i).setVisibility(View.VISIBLE);
					indice++;
					
				}
		}
		
	}
	
	public int recoverTrack(String name_track)
	{
		return listNametracks.indexOf(name_track);
	}
	
	private class RecoverTrack extends AsyncTask<Void, Void, String>
	{
		
		@Override
		protected void onPreExecute() {
		// TODO Stub de la m�thode g�n�r� automatiquement
		super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(String result) {
		try {
				listeNameTracksShow=new ArrayList<String>();
					listNametracks=new ArrayList<String>();
				JSONArray array = new JSONArray(result);
				
				for(int i = 0 ; i < array.length() ; i++){
					
					String name=array.getString(i); 
					listNametracks.add(name);
					name= name.replaceAll("_", " ");
					listeNameTracksShow.add(name);
				
			}
				for(int i=0;i<3;i++)
				{
					listeButtons.get(i).setText(listeNameTracksShow.get(i).toString());
					
					depart=i;
				}
				
			}
			catch(Exception e)
			{
				Log.v("NomButton",e.getMessage());
			}
			
		super.onPostExecute(result);
		}


		String response=null;
		@Override
		protected String doInBackground(Void... params) {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url_AllTracks);
			
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
		
	}

}
