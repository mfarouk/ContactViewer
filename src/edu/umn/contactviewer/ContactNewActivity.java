package edu.umn.contactviewer;

import edu.umn.contactviewer.storage.Callback;
import edu.umn.contactviewer.storage.ContactRepository;
import android.app.Activity;
import android.app.ProgressDialog;
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
        this.setContentView(R.layout.new_screen);

        nameView = (EditText) findViewById(R.id.item_name);
        phoneView = (EditText) findViewById(R.id.item_phone);
        titleView = (EditText) findViewById(R.id.item_title);
        emailView = (EditText) findViewById(R.id.item_email);
        twitterView = (EditText) findViewById(R.id.item_twitterId);
    }

    public void commitContact(View view) {
        Contact contact = new Contact();
        contact.setName(nameView.getText().toString());
        contact.setPhone(phoneView.getText().toString());
        contact.setTitle(titleView.getText().toString());
        contact.setEmail(emailView.getText().toString());
        contact.setTwitterId(twitterView.getText().toString());

        final ProgressDialog spinner = ProgressDialog.show(this, "Working..", "Committing contact...", true, false);
        ContactRepository.getInstance(this).putContact(contact, new Callback<Void>() {
            public void onSuccess(Void result) {
                spinner.dismiss();
                finish();
            }

            public void onFailure(Void result) {
                spinner.dismiss();
                finish();
            }
        });
    }

    public void cancelClicked(View view) {
        finish();
    }
}
