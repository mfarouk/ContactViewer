package edu.umn.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/** The home screen of the application.
 *
 */
public class HomeActivity extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.contacts_button).setOnClickListener(this);
    }

	public void onClick(View v) {
		if (v.getId() == R.id.contacts_button) {
			Intent intent = new Intent(this, ContactListActivity.class);
			startActivity(intent);
		}
	}
}