package com.brianleonard.cswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent intent = new Intent(this, LoginActivity.class);
	    startActivity(intent);
	    finish();
	}
}

	