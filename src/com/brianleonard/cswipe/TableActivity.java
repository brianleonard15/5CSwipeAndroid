package com.brianleonard.cswipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;

public class TableActivity extends Activity {
	
	private WebView htmlTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);
		
		htmlTable = (WebView) findViewById(R.id.webView1);
		Intent intent = getIntent();
	    String table = intent.getStringExtra("table");
	    table = table.replace("390", "100%");
	    //System.out.println(table);
	    
	    //htmlTable.loadData(table, "text/html", null);
	    htmlTable.getSettings().setLoadWithOverviewMode(true);
	    htmlTable.getSettings().setUseWideViewPort(true);
	    //htmlTable.setInitialScale(1);
	    htmlTable.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    htmlTable.setScrollbarFadingEnabled(false);
	    htmlTable.getSettings().setBuiltInZoomControls(true);
	    StringBuilder sb = new StringBuilder();
	    sb.append("<HTML><HEAD><LINK href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
	    sb.append(table.toString());
	    sb.append("</body></HTML>");
	    htmlTable.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "utf-8", null);
	    htmlTable.setBackgroundColor(0x00000000);
	    //htmlTable.loadData("<html> <head> <link rel='stylesheet' type='text/css'  href='style.css'> </head> <body> " + table + " </body> </html>", "text/html", null);

	}
	
}

	
	
