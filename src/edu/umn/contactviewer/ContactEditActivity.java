package edu.umn.contactviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ContactEditActivity extends Activity {

    private static final String TAG = "ContactEditActivity";
    private EditText nameView;
    private EditText phoneView;
    private EditText titleView;
    private EditText emailView;
    private EditText twitterView;
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_edit);

        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);

        // extract contact passed from previous activity
        contact = getIntent().getExtras().getParcelable(Contact.EDIT_ID);

        if (contact != null) {
            nameView.setText(contact.getName());
            phoneView.setText(contact.getPhone());
            titleView.setText(contact.getTitle());
            emailView.setText(contact.getEmail());
            twitterView.setText(contact.getTwitterId());
        }
    }

    public void cancelClicked(View view) {
        finish();
    }

    public void commitContact(View view) {
        contact.setName(String.valueOf(nameView.getText()));
        contact.setPhone(String.valueOf(phoneView.getText()));
        contact.setTitle(String.valueOf(titleView.getText()));
        contact.setEmail(String.valueOf(emailView.getText()));
        contact.setTwitterId(String.valueOf(twitterView.getText()));

        ContactRepository.getInstance(this).putContact(contact);

        finish();
    }

    public void deleteContact(View view) {
        Log.i(TAG, "Deleting a contact!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this contact?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContactRepository.getInstance(ContactEditActivity.this).removeContact(contact);
                        finish();
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