package edu.umn.contactviewer;

import edu.umn.contactviewer.storage.ContactRepository;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ContactDetailActivity extends Activity {

    private static final String TAG = "ContactDetailActivity";
    private TextView nameView;
    private TextView phoneView;
    private TextView titleView;
    private TextView emailView;
    private TextView twitterView;
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contact = getIntent().getExtras().getParcelable(Contact.SELECTED_ID);

        this.setContentView(R.layout.detail_screen);

        nameView = (TextView) findViewById(R.id.item_name);
        phoneView = (TextView) findViewById(R.id.item_phone);
        titleView = (TextView) findViewById(R.id.item_title);
        emailView = (TextView) findViewById(R.id.item_email);
        twitterView = (TextView) findViewById(R.id.item_twitterId);

        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        contact = ContactRepository.getInstance(this).refreshContact(contact);
        if (contact == null) {
            Log.i(TAG, "Contact does not exist after onResume(). Finishing instead");
            finish();
        } else {
            updateView();
        }
    }

    private void updateView() {
        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        titleView.setText(contact.getTitle());
        emailView.setText(contact.getEmail());
        twitterView.setText(contact.getTwitterId());
    }

    public void editContact(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
        intent.putExtra(Contact.EDIT_ID, contact);
        startActivity(intent);
    }

    public void contactsClicked(View view) {
        finish();
    }

}
