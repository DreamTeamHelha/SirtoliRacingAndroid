package com.example.sirtoliracing;

import java.io.IOException;






import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import android.util.Log;


public class RecoverInformation extends AsyncTask<String, Void,String> {

	
	private List<Joueur>listeJoueurs= new ArrayList<Joueur>(); 
	
	@Override
	protected void onPreExecute() {
	// TODO Stub de la méthode généré automatiquement
	super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(String result) {
	// TODO Stub de la méthode généré automatiquement
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
			Log.v("test",Integer.toString(getListeJoueurs().size()));
			
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

	public List<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}
	
	
	
	
	
}
