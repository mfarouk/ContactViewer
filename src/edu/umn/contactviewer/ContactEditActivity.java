package edu.umn.contactviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);

        contact = new Contact("RandomName");
        Intent intent = getIntent();

        contactJson = intent.getStringExtra("contact");
        contact = ContactRepository.parseJSON(contactJson);

        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        titleView.setText(contact.getTitle());
        emailView.setText(contact.getEmail());
        twitterView.setText(contact.getTwitterId());

    }

    public void selfDestruct(View view) {
        ContactEditActivity.this.finish();
        // Destroyed
    }

    public void commitContact(View view) {
        contact.setPhone(String.valueOf(phoneView.getText()));
        contact.setTitle(String.valueOf(titleView.getText()));
        contact.setEmail(String.valueOf(emailView.getText()));
        contact.setTwitterId(String.valueOf(twitterView.getText()));
        contactJson = ContactRepository.toJSON(contact);
        Editor sharedPreferenceEditor = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE).edit();
        sharedPreferenceEditor.putString(contact.getEmail(), contactJson);
        sharedPreferenceEditor.commit();
        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
        startActivity(intent);
    }

    public void deleteContact(View view) {

        System.out.println("I got to deleteContact");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this contact?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Editor sharedPreferenceEditor = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE).edit();
                        sharedPreferenceEditor.remove(contact.getEmail());
                        sharedPreferenceEditor.commit();
                        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}