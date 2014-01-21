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

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
	private Button bCourse1,bCourse2,bCourse3,bCourseNext,bCoursePrevious;
	private ArrayList<Button>listeButtons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(haveNetworkConnection())
		{
		bCourse1=(Button)findViewById(R.id.button_Track1);
		bCourse2=(Button)findViewById(R.id.button_Track2);
		bCourse3=(Button)findViewById(R.id.button_Track3);
		bCourseNext=(Button)findViewById(R.id.button_Next);
		bCoursePrevious=(Button)findViewById(R.id.button_Previous);
		listeButtons=new ArrayList<Button>();
		listeButtons.add(bCourse1);
		listeButtons.add(bCourse2);
		listeButtons.add(bCourse3);
		RecoverTrack recover=new RecoverTrack();
		recover.execute();
		}
		else
		{
			Intent intent= new Intent(this,No_Connection.class);
			startActivity(intent);
			finish();
		}

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private boolean haveNetworkConnection() 
	{
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

	public int recoverTrack(String name_track)
	{
		return listNametracks.indexOf(name_track);
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
		try{
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
			if(matches.get(i).equals("next"))
			{
				if(bCourseNext.isEnabled())
					bCourseNext.performClick();

			}
			if(matches.get(i).equals("previous"))
			{
				/*if(bCoursePrevious.isEnabled())
					bCoursePrevious.performClick();*/
				Toast toast = Toast.makeText(this, "previous", Toast.LENGTH_LONG);
				toast.show();

			}
			if(matches.get(i).equals("previews"))
			{
				/*if(bCoursePrevious.isEnabled())
					bCoursePrevious.performClick();*/
				Toast toast = Toast.makeText(this, "previews", Toast.LENGTH_LONG);
				toast.show();

			}
			if(matches.get(i).equals("stop application"))
			{
				System.exit(0);

			}
			if(matches.get(i).equals("i like this application"))
			{
				Toast toast = Toast.makeText(this, "Thank you, we are glad you like it", Toast.LENGTH_LONG);
				toast.show();

			}
			
			
			

		}
		}
		catch(Exception e)
		{
			Log.v("test", e.getMessage());
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
		

		//calcul de indice arraylist de la course en bouton 0 actuelement afficher
		int firstCurrentIndex= listeNameTracksShow.indexOf(listeButtons.get(0).getText());
		Log.v("Indice","firstcurrent debut deuxieme"+Integer.toString(firstCurrentIndex));

		Log.v("Previous","firstCurrent"+Integer.toString(firstCurrentIndex));
		
		

		int nbAfficher;
		if(firstCurrentIndex == 0)
		{
			nbAfficher = listeNameTracksShow.size()%3;
			Log.v("nb ","afficher"+Integer.toString(nbAfficher));
			
			if(nbAfficher == 0)
				nbAfficher=3;
			
		}
		else
			nbAfficher = 3;
		
		if(nbAfficher == 3)
		{
			if(firstCurrentIndex == 0)
			{
				for (int i=1;i<=3;i++)
				{
					listeButtons.get(3-i).setText(listeNameTracksShow.get(listeNameTracksShow.size()-i).toString());
					
					firstCurrentIndex = listeNameTracksShow.size()-3;
					
					listeButtons.get(i-1).setEnabled(true);
					listeButtons.get(i-1).setVisibility(View.VISIBLE);
				}
				
				
			}
			else
			{
				for (int i=1;i<=3;i++)
				{
					Log.v("i","i"+Integer.toString(i));
					Log.v("Indice","firstcurrent"+Integer.toString(firstCurrentIndex));
					listeButtons.get(3-i).setText(listeNameTracksShow.get(firstCurrentIndex-i).toString());
					listeButtons.get(i-1).setEnabled(true);
					listeButtons.get(i-1).setVisibility(View.VISIBLE);
				}
				firstCurrentIndex = firstCurrentIndex-3;
			}
		}
		else
		{
			for (int i=1;i<=nbAfficher;i++)
			{
				listeButtons.get(nbAfficher-i).setText(listeNameTracksShow.get(listeNameTracksShow.size()-i).toString());
			}
			listeButtons.get(2).setEnabled(false);
			listeButtons.get(2).setVisibility(View.GONE);
			firstCurrentIndex = listeNameTracksShow.size()- nbAfficher;
			Log.v("Indice","firstcurrent au premier tour"+Integer.toString(firstCurrentIndex));
			if (nbAfficher==1)
			{
				listeButtons.get(1).setEnabled(false);
				listeButtons.get(1).setVisibility(View.GONE);
			}
		}
	
	
	}

	private class RecoverTrack extends AsyncTask<Void, Void, String>
	{

		@Override
		protected void onPreExecute() {
			// TODO Stub de la méthode généré automatiquement
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
