package edu.umn.contactviewer;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailActivity extends Activity {
	public static final int CODE__DETAILS = 89;
	
    private TextView nameView;
    private TextView phoneView;
    private TextView titleView;
    private TextView emailView;
    private TextView twitterView;
    private String contactJson;
    
    private Contact person;
    private boolean deleted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_detail);

        // References to display fields
        nameView = (TextView) findViewById(R.id.item_name);
        phoneView = (TextView) findViewById(R.id.item_phone);
        titleView = (TextView) findViewById(R.id.item_title);
        emailView = (TextView) findViewById(R.id.item_email);
        twitterView = (TextView) findViewById(R.id.item_twitterId);

        // extract and display data
        Intent intent = getIntent();
        try {
            contactJson = intent.getStringExtra("contact");
            person = new Contact(new JSONObject(contactJson));
            nameView.setText(person.getName());
            phoneView.setText(person.getPhone());
            titleView.setText(person.getTitle());
            emailView.setText(person.getEmail());
            twitterView.setText(person.getTwitterId());
            
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    // Called when "Back" button is clicked
    public void selfDestruct(View view) {
        finish();
    }

    // Called when "Edit" button is clicked
    public void editContact(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
        intent.putExtra("contact", contactJson);
        startActivityForResult(intent, CODE__DETAILS);	// TODO
    }
    
    
    @Override
	public void finish() {
        Intent data = new Intent();		

        // Deleted
        if(deleted){
        	setResult(Contact.CONTACT_DELETED, null);
        }
        
        // If text fields as displayed match the object they started from... no changes
        else if(
        		nameView.getText().equals(person.getName()) &&
        		titleView.getText().equals(person.getTitle()) &&
        		phoneView.getText().equals(person.getPhone()) &&
        		emailView.getText().equals(person.getEmail()) &&
        		twitterView.getText().equals(person.getTwitterId())
        		)
        {
        	setResult(RESULT_OK, null);
        }
        
        // Else, changes to report
        else{
        	// update all the fields in the Contacts object
        	// then serialize object
        	person.setName(nameView.getText().toString());
        	person.setTitle(titleView.getText().toString());
        	person.setPhone(phoneView.getText().toString());
        	person.setEmail(emailView.getText().toString());
        	person.setTwitterId(twitterView.getText().toString());
        	try{
        		data.putExtra("contact", person.serialize().toString());
        	} catch(Exception e) {
        		data.putExtra("contact", "");
        	}
        	setResult(Contact.CONTACT_UPDATED, data);
        }

		super.finish();
	}

}
