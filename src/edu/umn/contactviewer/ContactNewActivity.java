package edu.umn.contactviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactNewActivity extends Activity {
	
	EditText name_editText;
    EditText phone_editText;
    EditText title_editText;
    EditText email_editText;
    EditText twitter_editText;
   
    
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_new);
        
        name_editText = (EditText)findViewById(R.id.item_name);
        phone_editText = (EditText)findViewById(R.id.item_phone);
        title_editText = (EditText)findViewById(R.id.item_title);
        email_editText = (EditText)findViewById(R.id.item_email);
        twitter_editText = (EditText)findViewById(R.id.item_twitterId);
          
        
	}
public void selfDestruct(View view){
		
		ContactNewActivity.this.finish();
		//Destroyed
		}
    

}

