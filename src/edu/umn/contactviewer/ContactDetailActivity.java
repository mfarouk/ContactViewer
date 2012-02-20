package edu.umn.contactviewer;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class ContactDetailActivity extends Activity   {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_detail);
        
        TextView name_textView = (TextView)findViewById(R.id.item_name);
        TextView phone_textView = (TextView)findViewById(R.id.item_phone);
        TextView title_textView = (TextView)findViewById(R.id.item_title);
        TextView email_textView = (TextView)findViewById(R.id.item_email);
        
        Intent i = getIntent();
        // getting attached intent data
        String contact_name = i.getStringExtra("contact_name");
        String contact_phone = i.getStringExtra("contact_phone");
        String contact_title = i.getStringExtra("contact_title");
        String contact_email = i.getStringExtra("contact_email");
        
        // displaying selected contact
        name_textView.setText(contact_name);
        phone_textView.setText(contact_phone);
        title_textView.setText(contact_title);
        email_textView.setText(contact_email);
        
        
	}

}
