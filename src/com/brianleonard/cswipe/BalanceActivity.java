package com.brianleonard.cswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class BalanceActivity extends Activity implements View.OnClickListener {
	private Button flexButton;
	private Button cashButton;
	private Button mealsButton;
	private String flexTable;
	private String cashTable;
	private String mealsTable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		
		Intent intent = getIntent();

	    String flex = intent.getStringExtra("flex");
	    String cash = intent.getStringExtra("cash");
	    String meals = intent.getStringExtra("meals");
	    flexTable = intent.getStringExtra("flexTable");
	    cashTable = intent.getStringExtra("cashTable");
	    mealsTable = intent.getStringExtra("mealsTable");
	    
	    flexButton = (Button) findViewById(R.id.buttonFlex);
	    cashButton = (Button) findViewById(R.id.buttonCash);
	    mealsButton = (Button) findViewById(R.id.buttonMeals);
	    
	    flexButton.setText("Flex\n$" + flex);
	    cashButton.setText("Claremont Cash\n$" + cash);
	    mealsButton.setText("Meals\n" + meals);
	    flexButton.setOnClickListener(this);
	    cashButton.setOnClickListener(this);
	    mealsButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonFlex){
			Intent myIntent = new Intent(BalanceActivity.this, TableActivity.class); 
			myIntent.putExtra("table", flexTable);
			startActivity(myIntent);
		}
		if(v.getId() == R.id.buttonCash){
			Intent myIntent = new Intent(BalanceActivity.this, TableActivity.class); 
			myIntent.putExtra("table", cashTable);
			startActivity(myIntent);
		}
		if(v.getId() == R.id.buttonMeals){
			Intent myIntent = new Intent(BalanceActivity.this, TableActivity.class); 
			myIntent.putExtra("table", mealsTable);
			startActivity(myIntent);
		}
	}
};

