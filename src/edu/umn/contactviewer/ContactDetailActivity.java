package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactDetailActivity extends Activity {

    private TextView nameView;
    private TextView phoneView;
    private TextView titleView;
    private TextView emailView;
    private TextView twitterView;
    private String contactJson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_detail);

        nameView = (TextView) findViewById(R.id.item_name);
        phoneView = (TextView) findViewById(R.id.item_phone);
        titleView = (TextView) findViewById(R.id.item_title);
        emailView = (TextView) findViewById(R.id.item_email);
        twitterView = (TextView) findViewById(R.id.item_twitterId);

        Intent intent = getIntent();

        // getting attached intent data
        try {
            contactJson = intent.getStringExtra("contact");
            Contact contact = ContactRepository.parseJSON(contactJson);
            nameView.setText(contact.getName());
            phoneView.setText(contact.getPhone());
            titleView.setText(contact.getTitle());
            emailView.setText(contact.getEmail());
            twitterView.setText(contact.getTwitterId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selfDestruct(View view) {
        ContactDetailActivity.this.finish();
        // Destroyed
    }

    public void editContact(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
        intent.putExtra("contact", contactJson);
        startActivity(intent);
    }

}
