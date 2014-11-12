package com.opaltech.nowledgenet;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.widget.LoginButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class LoginFragment extends Fragment {

	public LoginFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_fragment, container, false);
	}
	public void facebookAuth(View view){
		LoginButton authButton = (LoginButton) view.findViewById(R.id.login_button);
		authButton.setReadPermissions(Arrays.asList("basic_info"));
	}
}
