package edu.umn.contactviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class ContactEditActivity extends Activity {
	
	EditText name_editText;
    EditText phone_editText;
    EditText title_editText;
    EditText email_editText;
    EditText twitter_editText;
    String jsonstr = null;
    Contact contact;
    ContactRep crep;
    SharedPreferences sp;
    SharedPreferences.Editor spedit;
    public static final String APP_SHARED_PREFS = "Shared_Prefs_file"; //  Name of the file -.xml
   
    
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
        
        contact=new Contact("RandomName");
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
public void commitContact (View view){
	sp = getSharedPreferences(APP_SHARED_PREFS,MODE_PRIVATE);
	spedit = sp.edit();
	contact.setPhone(String.valueOf(phone_editText.getText()));
	contact.setTitle(String.valueOf(title_editText.getText()));
	contact.setEmail(String.valueOf(email_editText.getText()));
	contact.setTwitterId(String.valueOf(twitter_editText.getText()));
	jsonstr = ContactRep.toJSON(contact);
	spedit.putString(contact.getEmail(),jsonstr);
	spedit.commit();
	Intent i = new Intent(getApplicationContext(), ContactListActivity.class);
	startActivity(i);
}	
public void deleteContact(View view){
	
	System.out.println("I got to deleteContact");
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Are you sure you want delete this contact?")
	       .setCancelable(false)
	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   sp=getSharedPreferences(APP_SHARED_PREFS,MODE_PRIVATE);
	        	   spedit = sp.edit();
	        	   spedit.remove(contact.getEmail()); 
	        	   spedit.commit();
	        	   Intent i = new Intent(getApplicationContext(), ContactListActivity.class);
	        	   startActivity(i);
	           }
	       })
	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });
	AlertDialog alert = builder.create();
	alert.show();
}


}