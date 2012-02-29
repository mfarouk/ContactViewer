package edu.umn.contactviewer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactEditActivity extends Activity {

    private EditText nameView;
    private EditText phoneView;
    private EditText titleView;
    private EditText emailView;
    private EditText twitterView;
    
    private String contactJson = null;
    private Contact contact;

    public static final String APP_SHARED_PREFS = "Shared_Prefs_file";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_edit);

        // references to GUI elements
        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);

        
        // extract and display data
        Intent intent = getIntent();
        try {
            contactJson = intent.getStringExtra("contact");
            contact = new Contact(new JSONObject(contactJson));
            nameView.setText(contact.getName());
            phoneView.setText(contact.getPhone());
            titleView.setText(contact.getTitle());
            emailView.setText(contact.getEmail());
            twitterView.setText(contact.getTwitterId());
            
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    // Called when "Back" button is clicked
    public void selfDestruct(View view) {
    	//Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_SHORT).show();
    	setResult(RESULT_OK, null);
    	finish();
    }

    // Called when "Save" button is clicked
    public void commitContact(View view) {
    	//Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
    	
    	// Update contact object
    	contact.setName(nameView.getText().toString());
    	contact.setTitle(titleView.getText().toString());
    	contact.setPhone(phoneView.getText().toString());
    	contact.setEmail(emailView.getText().toString());
    	contact.setTwitterId(twitterView.getText().toString());

    	// build respond data
    	Intent data = new Intent();
    	try {
			data.putExtra("contact", contact.serialize().toString());
			setResult(Contact.CONTACT_UPDATED, data);
			finish();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Error saving edits.", Toast.LENGTH_SHORT).show();
			//setResult(RESULT_OK, null);
		}
    	//finish();
    	
    }

    // Called when "Delete" button is clicked
    public void deleteContact(View view) {
    	//Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this contact?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      	setResult(Contact.CONTACT_DELETED, null);
                    	finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //pass
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        
    }

}