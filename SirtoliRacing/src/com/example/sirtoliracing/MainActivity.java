package com.example.sirtoliracing;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String url_track="",name_Track="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		switch (view.getId()) {
		
		case R.id.button_RaphParadise:
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/Raph_paradise";
			intent.putExtra("url_track", url_track);
			name_Track="Raph paradise";
			intent.putExtra("name_track", name_Track);
			
			break;
		case R.id.button_SirtoliRace:
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/Drift_Town";
			
			intent.putExtra("url_track", url_track);
			name_Track="Sirtoli Race";
			intent.putExtra("name_track", name_Track);
			break;
		case R.id.button_Zig_Zag:
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/Zig_&_Zag";
			intent.putExtra("url_track", url_track);
			name_Track="Zig Zag";
			intent.putExtra("name_track", name_Track);
			break;
		case R.id.button_Oli_track:
			url_track="http://193.190.66.14:6080/SirtoliRacing/track/Oli_Track";
			intent.putExtra("url_track", url_track);
			name_Track="Oli Track";
			intent.putExtra("name_track", name_Track);
			break;
		default:
			break;
		}
    	/*fonctionne passage d une variable a une second activité
    	 * */
    	
    	startActivity(intent);
	}

}
