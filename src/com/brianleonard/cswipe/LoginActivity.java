package com.brianleonard.cswipe;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */

@SuppressLint("JavascriptInterface")
public class LoginActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button signInButton;
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    
        final WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "(document.getElementsByTagName('body')[0].innerHTML);");
            }
        });
        
        webView.loadUrl("https://cards.cuc.claremont.edu/login.php");


	    usernameEditText = (EditText) findViewById(R.id.email);
	    passwordEditText = (EditText) findViewById(R.id.password);

	    signInButton = (Button) findViewById(R.id.sign_in_button);
	    signInButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	    		if(v.getId() == R.id.sign_in_button){
	    	        //String givenUsername = usernameEditText.getEditableText().toString();
	    	        //String givenPassword = passwordEditText.getEditableText().toString();
	    			
	    			// CHANNNGNGNGNENEGNGNENGNE
	    			String givenUsername = "40155049";
	    			String givenPassword = "Baloney1";
	    			

	    	        System.out.println("Given username :" + givenUsername + " Given password :" + givenPassword);
	    	        webView.loadUrl("javascript:document.getElementById('loginphrase').value='" + givenUsername +"';");
	    	        webView.loadUrl("javascript:document.getElementById('password').value='" + givenPassword +"';");
	    	        webView.loadUrl("javascript:document.getElementsByTagName('input')[0].click();");
	    	        webView.loadUrl("javascript:document.getElementsByTagName('input')[6].click();");
	        }
	    }});
       
	}
	
	class MyJavaScriptInterface {


	    public void showHTML(String html) {
	    	if (html.indexOf("Log Out") > 0) {  
	    		// Find Flex Balance
	    		String flexRange = html.substring(html.indexOf("Board Plus</font>"),html.indexOf("Claremont Cash</font>"));
	    		int flexStartIndex = flexRange.indexOf("Current Balance: ");
	    		int flexEndIndex = flexRange.indexOf("</b>");
	    		String flex = flexRange.substring(flexStartIndex + 17, flexEndIndex);
	    		System.out.println(flex);
	    		
	    		// Find Claremont Cash Balance
	    		String cashRangeStart = html.substring(html.indexOf("Claremont Cash</font>"));
	    		String cashRange = cashRangeStart.substring(0, cashRangeStart.indexOf("</b>") + 4);
	    		int cashStartIndex = cashRange.indexOf("Current Balance: ");
	    		int cashEndIndex = cashRange.indexOf("</b>");
	    		String cash = cashRange.substring(cashStartIndex + 17, cashEndIndex);
	    		System.out.println(cash);
	    	}
	    }

	}
	
}

	