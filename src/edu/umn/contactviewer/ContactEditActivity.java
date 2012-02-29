package edu.umn.contactviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        this.setContentView(R.layout.contact_edit);

        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);

        // extract contact passed from previous activity
        Intent intent = getIntent();
        contact = intent.getExtras().getParcelable(Contact.EDIT_ID);

        if (contact != null) {
            nameView.setText(contact.getName());
            phoneView.setText(contact.getPhone());
            titleView.setText(contact.getTitle());
            emailView.setText(contact.getTwitterId());
            twitterView.setText(contact.getEmail());
        }
    }

    public void selfDestruct(View view) {
        ContactEditActivity.this.finish();
        // Destroyed
    }

    public void commitContact(View view) {
        contact.setName(String.valueOf(nameView.getText()));
        contact.setPhone(String.valueOf(phoneView.getText()));
        contact.setTitle(String.valueOf(titleView.getText()));
        contact.setEmail(String.valueOf(emailView.getText()));
        contact.setTwitterId(String.valueOf(twitterView.getText()));

        ContactRepository.getInstance().putContact(contact);

        // return back to details
        Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
        intent.putExtra(Contact.SELECTED_ID, contact);
        startActivity(intent);
    }

    public void deleteContact(View view) {
        Log.i(TAG, "Deleting a contact!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this contact?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContactRepository.getInstance().removeContact(contact);
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