package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactDetailActivity extends Activity {

    TextView name_textView;
    TextView phone_textView;
    TextView title_textView;
    TextView email_textView;
    TextView twitter_textView;
    String jsonstr;
    Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_detail);

        name_textView = (TextView) findViewById(R.id.item_name);
        phone_textView = (TextView) findViewById(R.id.item_phone);
        title_textView = (TextView) findViewById(R.id.item_title);
        email_textView = (TextView) findViewById(R.id.item_email);
        twitter_textView = (TextView) findViewById(R.id.item_twitterId);

        Intent i = getIntent();

        // getting attached intent data
        try {
            jsonstr = i.getStringExtra("contact");
            ContactRep crep = new ContactRep();
            contact = crep.parseJSON(jsonstr);
            name_textView.setText(contact.getName());
            phone_textView.setText(contact.getPhone());
            title_textView.setText(contact.getTitle());
            email_textView.setText(contact.getEmail());
            twitter_textView.setText(contact.getTwitterId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selfDestruct(View view) {
        ContactDetailActivity.this.finish();
        // Destroyed
    }

    public void editContact(View view) {
        Intent i = new Intent(getApplicationContext(), ContactEditActivity.class);
        i.putExtra("contact", jsonstr);
        startActivity(i);
    }
    
}
