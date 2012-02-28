package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactNewActivity extends Activity {

    private EditText nameView;
    private EditText phoneView;
    private EditText titleView;
    private EditText emailView;
    private EditText twitterView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_new);

        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);
    }

    public void selfDestruct(View view) {
        ContactNewActivity.this.finish();
        // Destroyed
    }

    public void commitContact(View view) {
        Editor sharedPreferenceEditor = getSharedPreferences(ContactEditActivity.APP_SHARED_PREFS,
                ContactEditActivity.MODE_PRIVATE).edit();
        Contact contact = new Contact(String.valueOf(nameView.getText()));
        contact.setPhone(String.valueOf(phoneView.getText()));
        contact.setTitle(String.valueOf(titleView.getText()));
        contact.setEmail(String.valueOf(emailView.getText()));
        contact.setTwitterId(String.valueOf(twitterView.getText()));

        String contactJson = ContactRepository.toJSON(contact);
        sharedPreferenceEditor.putString(contact.getEmail(), contactJson);
        sharedPreferenceEditor.commit();
        Intent contactListIntent = new Intent(getApplicationContext(), ContactListActivity.class);
        startActivity(contactListIntent);
    }
}
