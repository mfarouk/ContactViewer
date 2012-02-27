package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class ContactEditActivity extends Activity {
	
	EditText name_editText;
    EditText phone_editText;
    EditText title_editText;
    EditText email_editText;
    EditText twitter_editText;
    String jsonstr;
    Contact contact;
   
    
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_edit);
        
        name_editText = (EditText)findViewById(R.id.item_name);
        phone_editText = (EditText)findViewById(R.id.item_phone);
        title_editText = (EditText)findViewById(R.id.item_title);
        email_editText = (EditText)findViewById(R.id.item_email);
        twitter_editText = (EditText)findViewById(R.id.item_twitterId);
        
        Intent i = getIntent();
        
        jsonstr = i.getStringExtra("contact");
        ContactRep crep=new ContactRep();
        contact=crep.parseJSON(jsonstr);    
        
        name_editText.setText(contact.getName());
		phone_editText.setText(contact.getPhone());
		title_editText.setText(contact.getTitle());
		email_editText.setText(contact.getEmail());
		twitter_editText.setText(contact.getTwitterId());  
        
	}
public void selfDestruct(View view){
		
		ContactEditActivity.this.finish();
		//Destroyed
		}
    

}
