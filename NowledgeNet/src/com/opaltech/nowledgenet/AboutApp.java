package com.opaltech.nowledgenet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AboutApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_app, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_achieve:
            goAchieve();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void visitInnovation(View view){
		// When button is pressed bring up website for iN using browser
		Uri webpage = Uri.parse("http://innovationcorporation.net/");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(webIntent);
	}
	public void visitDynPro(View view){
		// Same thing here except with DynPro site
		Uri webpage = Uri.parse("http://burnedtoast.cu.cc/?pk_campaign=NowledgeNet");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(webIntent);
	}
	public void visitOPAL(View view){
		// I still think we should've went with my idea of a domain
		Uri webpage = Uri.parse("http://opaltech.us");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(webIntent);
	}
	public void bringBaan(View view){
		// huehuehuehue
		Context context = getApplicationContext();
		CharSequence text = "Bring Baan";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	public void goAchieve(){
		// when the user goes to the about in the Action bar send them to their achievements
		Intent intent = new Intent(this, AchieveActivity.class);
		startActivity(intent);
	}
}
