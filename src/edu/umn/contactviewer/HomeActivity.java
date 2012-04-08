package edu.umn.contactviewer;

import edu.umn.contactviewer.storage.ContactRepository;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The home screen of the application.
 * 
 */
public class HomeActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        findViewById(R.id.contacts_button).setOnClickListener(this);
        findViewById(R.id.about_button).setOnClickListener(this);
        ContactRepository.initialize();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.contacts_button) {
            Intent contactListIntent = new Intent(this, ContactListActivity.class);
            startActivity(contactListIntent);
        }
        else if (v.getId() == R.id.about_button) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }
    }

}