package com.brianleonard.cswipe;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */

@SuppressLint("JavascriptInterface")
public class LoginActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	public static Button signInButton;
	private CheckBox checkBox;
	public static TextView wrongText;
	private WebView webView;
	private ProgressDialog progressDialog;
	private SecurePreferences preferences;
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    

	    
	    signInButton = (Button) findViewById(R.id.sign_in_button);
	    signInButton.setEnabled(false);
	    signInButton.setBackgroundResource(R.drawable.loginbuttondisabled);
	    signInButton.setTextColor(Color.LTGRAY);
	    
	    wrongText = (TextView) findViewById(R.id.textView1);
	    wrongText.setVisibility(4);
	    
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:HtmlViewer.showJustHTML" +
                        "(document.getElementsByTagName('body')[0].innerHTML);");
                webView.loadUrl("javascript:HtmlViewer.showHTML" +
                        "(document.getElementsByTagName('body')[0].innerHTML" +
                ", document.getElementsByTagName('table')[7].getElementsByTagName('table')[3].outerHTML" +
                ", document.getElementsByTagName('table')[7].getElementsByTagName('table')[4].outerHTML" +
                ", document.getElementsByTagName('table')[7].getElementsByTagName('table')[5].outerHTML);");
            }
        });
        
        webView.loadUrl("https://cards.cuc.claremont.edu/login.php");


	    usernameEditText = (EditText) findViewById(R.id.email);
	    passwordEditText = (EditText) findViewById(R.id.password);
	    
		preferences = new SecurePreferences(getBaseContext(), "user-info", 
				"YourSecurityKey", true);
		//Get
		String username = preferences.getString("username");
		String password = preferences.getString("password");
		
		usernameEditText.setHint("Student ID");
		usernameEditText.setText(username);
		passwordEditText.setText(password);

	    signInButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	    		if(v.getId() == R.id.sign_in_button){
	    			progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...");
	    	        String givenUsername = usernameEditText.getEditableText().toString();
	    	        String givenPassword = passwordEditText.getEditableText().toString();
	    			
	    			// CHANNNGNGNGNENEGNGNENGNE
	    			//String givenUsername = "40155049";
	    			//String givenPassword = "Baloney1";
	    			
	    			CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
	    			if (checkBox.isChecked()) {
	    
	    				//Put (all puts are automatically committed)
	    				preferences.put("username", givenUsername);
	    				preferences.put("password", givenPassword);
	    			}
	    			else {
	    				preferences.put("username", "");
	    				preferences.put("password", "");
	    			}
	    			

	    	        //System.out.println("Given username :" + username + " Given password :" + password);
	    	        webView.loadUrl("javascript:document.getElementById('loginphrase').value='" + givenUsername +"';");
	    	        webView.loadUrl("javascript:document.getElementById('password').value='" + givenPassword +"';");
	    	        webView.loadUrl("javascript:document.getElementsByTagName('input')[0].click();");
	    	        webView.loadUrl("javascript:document.getElementsByTagName('input')[6].click();");
	        }
	    }});
       
	}
	
	 @Override
	    protected void onStart() {
	        super.onStart();
		    signInButton.setEnabled(false);
		    signInButton.setBackgroundResource(R.drawable.loginbuttondisabled);
		    signInButton.setTextColor(Color.LTGRAY);
			String username = preferences.getString("username");
			String password = preferences.getString("password");
			
			usernameEditText.setText(username);
			passwordEditText.setText(password);
	        webView.loadUrl("https://cards.cuc.claremont.edu/login.php");
	 }
	
	class MyJavaScriptInterface {

		@JavascriptInterface public void showJustHTML(String html) {
			if (html.indexOf("Please Login To Gain Access") > 0) {
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {

							LoginActivity.signInButton.setEnabled(true);
							LoginActivity.signInButton.setBackgroundResource(R.drawable.loginbutton);
							LoginActivity.signInButton.setTextColor(Color.WHITE);

				    }
				});
			}
			if (html.indexOf("Invalid") > 0) {
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
							LoginActivity.wrongText.setVisibility(0);
							progressDialog.dismiss();
				    }
				});
			}
			else {
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
							LoginActivity.wrongText.setVisibility(4);
				    }
				});
			}
		}
		@JavascriptInterface public void showHTML(String html, String flexTable, String cashTable, String mealsTable) {
	    	if (html.indexOf("Log Out") > 0) { 
	    		progressDialog.dismiss();
	    		// Find Flex Balance
	    		String flexRange = html.substring(html.indexOf("Board Plus</font>"),html.indexOf("Claremont Cash</font>"));
	    		int flexStartIndex = flexRange.indexOf("Current Balance: ");
	    		int flexEndIndex = flexRange.indexOf("</b>");
	    		String flex = flexRange.substring(flexStartIndex + 17, flexEndIndex);
	    		//System.out.println(flex);
	    		
	    		// Find Claremont Cash Balance
	    		String cashRangeStart = html.substring(html.indexOf("Claremont Cash</font>"));
	    		String cashRange = cashRangeStart.substring(0, cashRangeStart.indexOf("</b>") + 4);
	    		int cashStartIndex = cashRange.indexOf("Current Balance: ");
	    		int cashEndIndex = cashRange.indexOf("</b>");
	    		String cash = cashRange.substring(cashStartIndex + 17, cashEndIndex);
	    		//System.out.println(cash);
	    		
	    		// Find Meals Balance
	    		// CHANNGENNGENENGNEGNEGE
	    		//String newhtml = "<td class='tablecolnum'>5</td></tr><tr><td class='tablefirstcol' >5/10 11:12 AM</td><td class='tablecol'>PIT McConnell 2-Regular</td><td class='tablecolnum'>-1</td><td class='tablecolnum'>4</td></tr>";

	    		String mealsRangeStart = html.substring(html.lastIndexOf("tablecolnum"));
	    		String meals = mealsRangeStart.substring(13, mealsRangeStart.indexOf("<"));
	    		//System.out.println(meals);
	    		
	    	    Intent myIntent = new Intent(LoginActivity.this, BalanceActivity.class); 
	    	    myIntent.putExtra("flex", flex);
	    	    myIntent.putExtra("cash", cash);
	    	    myIntent.putExtra("meals", meals);
	    	    myIntent.putExtra("flexTable", flexTable);
	    	    myIntent.putExtra("cashTable", cashTable);
	    	    myIntent.putExtra("mealsTable", mealsTable);
	    	    startActivity(myIntent);
	    	}
	    }

	}
	
}

	