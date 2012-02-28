package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactNewActivity extends Activity {

    EditText name_editText;
    EditText phone_editText;
    EditText title_editText;
    EditText email_editText;
    EditText twitter_editText;
    Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_new);

        name_editText = (EditText) findViewById(R.id.item_name);
        phone_editText = (EditText) findViewById(R.id.item_phone);
        title_editText = (EditText) findViewById(R.id.item_title);
        email_editText = (EditText) findViewById(R.id.item_email);
        twitter_editText = (EditText) findViewById(R.id.item_twitterId);
    }

    public void selfDestruct(View view) {
        ContactNewActivity.this.finish();
        // Destroyed
    }

    public void commitContact(View view) {
        SharedPreferences sp = getSharedPreferences(ContactEditActivity.APP_SHARED_PREFS,
                ContactEditActivity.MODE_PRIVATE);
        SharedPreferences.Editor spedit = sp.edit();
        contact = new Contact(String.valueOf(name_editText.getText()));
        contact.setPhone(String.valueOf(phone_editText.getText()));
        contact.setTitle(String.valueOf(title_editText.getText()));
        contact.setEmail(String.valueOf(email_editText.getText()));
        contact.setTwitterId(String.valueOf(twitter_editText.getText()));

        String jsonstr = ContactRepository.toJSON(contact);
        spedit.putString(contact.getEmail(), jsonstr);
        spedit.commit();
        Intent i = new Intent(getApplicationContext(), ContactListActivity.class);
        startActivity(i);
    }
}
