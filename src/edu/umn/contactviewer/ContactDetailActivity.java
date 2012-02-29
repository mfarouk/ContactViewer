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
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contact = getIntent().getExtras().getParcelable("selectedContact");

        this.setContentView(R.layout.contact_detail);

        nameView = (TextView) findViewById(R.id.item_name);
        phoneView = (TextView) findViewById(R.id.item_phone);
        titleView = (TextView) findViewById(R.id.item_title);
        emailView = (TextView) findViewById(R.id.item_email);
        twitterView = (TextView) findViewById(R.id.item_twitterId);

        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        titleView.setText(contact.getTitle());
        emailView.setText(contact.getEmail());
        twitterView.setText(contact.getTwitterId());
    }

    public void selfDestruct(View view) {
        ContactDetailActivity.this.finish();
        // Destroyed
    }

    public void editContact(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
        intent.putExtra(Contact.EDIT_ID, this.contact);
        startActivity(intent);
    }

    public void returnToContacts(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }

}
