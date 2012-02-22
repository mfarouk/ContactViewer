package edu.umn.contactviewer;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class ContactDetailActivity extends Activity   {
	 
	 TextView name_textView;
     TextView phone_textView;
     TextView title_textView;
     TextView email_textView;
     TextView twitter_textView;
     
	public void parseJSON(String jsonstr){
		JSONObject jsonret = null;
		try {
			jsonret = new JSONObject(jsonstr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        // displaying selected contact
        try {
			name_textView.setText(jsonret.getString("name"));
			phone_textView.setText(jsonret.getString("phone"));
			title_textView.setText(jsonret.getString("title"));
			email_textView.setText(jsonret.getString("email"));
			twitter_textView.setText(jsonret.getString("twitterId"));
		} catch (JSONException e) {
			Log.e("Contact Display","Error restoring contact from JSON: " + jsonret, e);
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_detail);
        
        name_textView = (TextView)findViewById(R.id.item_name);
        phone_textView = (TextView)findViewById(R.id.item_phone);
        title_textView = (TextView)findViewById(R.id.item_title);
        email_textView = (TextView)findViewById(R.id.item_email);
        twitter_textView = (TextView)findViewById(R.id.item_twitterId);
        
        Intent i = getIntent();
        
        // getting attached intent data
        String jsonstr = i.getStringExtra("contact");
        parseJSON(jsonstr);     
        
	}
	public void selfDestruct(View view){
		
		ContactDetailActivity.this.finish();
		//Destroyed
		}
	public void editContact(View view)
	{
		Intent i = new Intent(getApplicationContext(), ContactEditActivity.class);
		startActivity(i);
	
	}
}
