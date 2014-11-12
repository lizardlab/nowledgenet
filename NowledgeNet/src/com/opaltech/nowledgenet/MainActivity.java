package com.opaltech.nowledgenet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;

public class MainActivity extends FragmentActivity {
	private static final int LOGIN = 0;
	private static final int AUTHED = 1;
	private static final int LOGOUT = 2;
	private static final int FRAGMENT_COUNT = LOGOUT +1;
	private boolean isResumed = false;
	public static String difficulty;
	public static String category;
	private WebDialog dialog = null;
	public int techLvl = 0;
	public int score = 0;

	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    //restores any saved score or if no saved score sets to 0
	    SharedPreferences sharedPref = this.getSharedPreferences(
		        "savedScore", Context.MODE_PRIVATE);
	    score = sharedPref.getInt("saved_score", 0);
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);

	    FragmentManager fm = getSupportFragmentManager();
	    fragments[LOGIN] = fm.findFragmentById(R.id.loginFragment);
	    fragments[AUTHED] = fm.findFragmentById(R.id.authedFragment);
	    fragments[LOGOUT] = fm.findFragmentById(R.id.userSettingsFragment);

	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
            aboutApp();
            return true;
	    case R.id.action_achieve:
            goAchieve();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void aboutApp(){
		// when the user goes to the about in the Action bar send them to the about page
		Intent intent = new Intent(this, AboutApp.class);
		startActivity(intent);
	}
	
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (int i = 0; i < fragments.length; i++) {
	        if (i == fragmentIndex) {
	            transaction.show(fragments[i]);
	        } else {
	            transaction.hide(fragments[i]);
	        }
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    transaction.commit();
	}


	@Override
	public void onResume() {
		//restores any saved score or if no saved score sets to 0
		SharedPreferences sharedPref = this.getSharedPreferences(
		        "savedScore", Context.MODE_PRIVATE);
	    score = sharedPref.getInt("saved_score", 0);
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		SharedPreferences sharedPref = this.getSharedPreferences(
		        "savedScore", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("saved_score", score);
		editor.commit();
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	            // If the session state is open:
	            // Show the authenticated fragment
	            showFragment(AUTHED, false);
	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(LOGIN, false);
	        }
	    }
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	        showFragment(AUTHED, false);
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
	        showFragment(LOGIN, false);
	    }
	}
	public void confidenceSelected(View view) {
		//checks if something was actually checked
		boolean checked = ((RadioButton) view).isChecked();
		//finds out which one was checked and assigns appropriate level
		switch(view.getId()){
			case R.id.radio_expertTech:
				if (checked)
					techLvl = 4;
				break;
			case R.id.radio_okayTech:
				if (checked)
					techLvl = 3;
				break;
			case R.id.radio_helpTech:
				if (checked)
					techLvl = 2;
				break;
			case R.id.radio_tutorTech:
				if (checked)
					techLvl = 1;
				break;
		}
	}
	public void difficultySelected(View view) {
		//checks if something was actually checked
		boolean checked = ((RadioButton) view).isChecked();
		//finds out which one was checked and assigns appropriate level
		switch(view.getId()){
			case R.id.radio_easy:
				if (checked)
					difficulty = getString(R.string.lvlDiff1);
				break;
			case R.id.radio_med:
				if (checked)
					difficulty = getString(R.string.lvlDiff2);
				break;
			case R.id.radio_hard:
				if (checked)
					difficulty = getString(R.string.lvlDiff3);
				break;
		}
	}
	@SuppressLint("DefaultLocale")
	public void categorySelected(View view) {
		//checks if something was actually checked
		boolean checked = ((RadioButton) view).isChecked();
		//finds out which one was checked and assigns appropriate level
		switch(view.getId()){
			case R.id.catSecurity:
				if (checked)
					category = getString(R.string.security).toLowerCase();
				break;
			case R.id.catSoftware:
				if (checked)
					category = getString(R.string.software).toLowerCase();
				break;
			case R.id.catHardware:
				if (checked)
					category = getString(R.string.hardware).toLowerCase();
				break;
			case R.id.catNetwork:
				if (checked)
					category = getString(R.string.network).toLowerCase();
				break;
			case R.id.catServer:
				if (checked)
					category = getString(R.string.server).toLowerCase();
				break;
			case R.id.catSite:
				if (checked)
					category = getString(R.string.sites).toLowerCase();
				break;
			case R.id.catAV:
				if (checked)
					category = getString(R.string.a_v).toLowerCase();
				break;
		}
	}
	public void postHelp(View view){
		// make sure to check that the (l)user filled out everything
		if(techLvl == 0){
			Context context = getApplicationContext();
			CharSequence text = getString(R.string.confidence_error);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		else if(category == null){
			Context context = getApplicationContext();
			CharSequence text = getString(R.string.category_error);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		else if(difficulty == null){
			Context context = getApplicationContext();
			CharSequence text = getString(R.string.difficulty_error);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		else{
			score += 1;
			SharedPreferences sharedPref = this.getSharedPreferences(
			        "savedScore", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("saved_score", score);
			editor.commit();
			Bundle params = new Bundle();
			params.putString("message", getString(R.string.requestHelp) + category +
			    getString(R.string.problemDiff) + difficulty + ".");
			showDialogWithoutNotificationBar("apprequests", params);
		}
	}
	private void showDialogWithoutNotificationBar(String action, Bundle params){
		dialog = new WebDialog.Builder(this, Session.getActiveSession(), action, params).
			    setOnCompleteListener(new WebDialog.OnCompleteListener() {
			    @Override
			    public void onComplete(Bundle values, FacebookException error) {
			        if (error != null && !(error instanceof FacebookOperationCanceledException)) {
			                // nothing to do here, except ignore non existent errors in my perfect code
			        }
			    }
			}).build();

			Window dialog_window = dialog.getWindow();
			dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
			dialog.show();
	}
	public void goAchieve(){
		// when the user goes to the about in the Action bar send them to their achievements
		Intent intent = new Intent(this, AchieveActivity.class);
		startActivity(intent);
	}
	public void logOut(View view){
		showFragment(LOGOUT, true);
	}
}
