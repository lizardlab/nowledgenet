package com.opaltech.nowledgenet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AchieveActivity extends Activity {
	public int score;
	public String strScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achieve);
		// Show the Up button in the action bar.
		setupActionBar();
		// get the score from the preferences file
		SharedPreferences sharedPref = this.getSharedPreferences(
		        "savedScore", Context.MODE_PRIVATE);
	    score = sharedPref.getInt("saved_score", 0);
	    //score = 51;
	    strScore = Integer.toString(score);
	    // them display the score to the user
	    TextView txtPoints = (TextView) findViewById(R.id.txtPoints);
		txtPoints.setText(strScore);
		enableAchievements();
	}
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.achieve, menu);
		return true;
	}
	public void enableAchievements(){
		// this will change the text back to black in accordance with user score
		// also the textviews are set here to be manipulated for this reason
		TextView linusTitle = (TextView) findViewById(R.id.TextView01);
		TextView linusDescription = (TextView) findViewById(R.id.textView1);
		TextView richardTitle = (TextView) findViewById(R.id.TextView02);
		TextView richardDescription = (TextView) findViewById(R.id.textView2);
		TextView graceTitle = (TextView) findViewById(R.id.TextView03);
		TextView graceDescription = (TextView) findViewById(R.id.textView4);
		// can't forget the buttons
		Button btnLinus = (Button) findViewById(R.id.btnLinus);
		Button btnRichard = (Button) findViewById(R.id.btnRichard);
		Button btnGrace = (Button) findViewById(R.id.btnGrace);
		if(score >= 50){
			linusTitle.setTextColor(Color.parseColor("#000000"));
			linusDescription.setTextColor(Color.parseColor("#000000"));
			richardTitle.setTextColor(Color.parseColor("#000000"));
			richardDescription.setTextColor(Color.parseColor("#000000"));
			graceTitle.setTextColor(Color.parseColor("#000000"));
			graceDescription.setTextColor(Color.parseColor("#000000"));
			btnLinus.setEnabled(true);
			btnRichard.setEnabled(true);
			btnGrace.setEnabled(true);
		}
		else if(score >= 20){
			linusTitle.setTextColor(Color.parseColor("#000000"));
			linusDescription.setTextColor(Color.parseColor("#000000"));
			richardTitle.setTextColor(Color.parseColor("#000000"));
			richardDescription.setTextColor(Color.parseColor("#000000"));
			btnLinus.setEnabled(true);
			btnRichard.setEnabled(true);
		}
		if(score >= 10){
			linusTitle.setTextColor(Color.parseColor("#000000"));
			linusDescription.setTextColor(Color.parseColor("#000000"));
			btnLinus.setEnabled(true);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_about:
			aboutApp();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void aboutApp(){
		// when the user goes to the about in the Action bar send them to the about page
		Intent intent = new Intent(this, AboutApp.class);
		startActivity(intent);
	}
	public void playLinus(View view){
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.strings);
		mediaPlayer.start();
	}
	public void playRichard(View view){
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.slim_scape);
		mediaPlayer.start();
	}
	public void playGrace(View view){
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.christmas_tree);
		mediaPlayer.start();
	}

}
