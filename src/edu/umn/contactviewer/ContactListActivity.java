package edu.umn.contactviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.*;

/**
 * Displays a list of contacts.
 */
public class ContactListActivity extends ListActivity {
    public static final int CODE__NEW_CONTACT = 11;
	// final ArrayList<HashMap<String,String>> LIST = new
    // ArrayList<HashMap<String,String>>();
    
    ArrayList<Contact> contacts;

    public void newContact(View view) {
        Intent contactNewIntent = new Intent(getApplicationContext(), ContactNewActivity.class);
        //Toast.makeText(getApplicationContext(), "Intent: contactNewIntent", Toast.LENGTH_SHORT).show();
        //startActivity(contactNewIntent);
        startActivityForResult(contactNewIntent, CODE__NEW_CONTACT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        new ToolbarConfig(this, "Contacts");
        contacts = new ArrayList<Contact>();
        SharedPreferences sp = getSharedPreferences(ContactEditActivity.APP_SHARED_PREFS,
                ContactEditActivity.MODE_PRIVATE);
        Map<String, ?> items = sp.getAll();
        for (String s : items.keySet()) {
            Contact contact = ContactRepository.parseJSON(items.get(s).toString());
            contacts.add(contact);
        }

        // initialize the list view
        initListView();
        
    }

    private void initListView()
    {
    	setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                // Toast.makeText(getApplicationContext(),
                // "Clicked: " +
                // ((ContactAdapter)getListAdapter()).getItem(position).getName(),
                // Toast.LENGTH_SHORT).show();
                // Serialize contact using JSON object
                Contact contact = ((ContactAdapter) getListAdapter()).getItem(position);
                String contactJson = ContactRepository.toJSON(contact);
                // Launching new Activity on selecting single List Item
                Intent contactDetailIntent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                contactDetailIntent.putExtra("contact", contactJson);
                Toast.makeText(getApplicationContext(), "Launch contactDetailIntent", Toast.LENGTH_SHORT).show();
                //startActivity(contactDetailIntent);
            }
        });
    }
    
    /**
     * Handles data returned from child Activities.
     */
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == CODE__NEW_CONTACT) {
			//Toast.makeText(getApplicationContext(), "new contact successful", Toast.LENGTH_SHORT).show();
			
			// make a new contact
			Contact person = new Contact();
			person.setName(data.getExtras().getString("name"));
			person.setTitle(data.getExtras().getString("title"));
			person.setPhone(data.getExtras().getString("phone"));
			person.setEmail(data.getExtras().getString("email"));
			person.setTwitterId(data.getExtras().getString("twitterId"));
			Toast.makeText(getApplicationContext(), "Contact: " + person.getName(), Toast.LENGTH_SHORT).show();
			
			// add contact to list...  How??
			contacts.add(person);
			initListView();
		}
		else if(requestCode == CODE__NEW_CONTACT) {
			Toast.makeText(getApplicationContext(), "new contact canceled", Toast.LENGTH_SHORT).show();
		}
	}
    
    
    /**
     * We need to provide a custom adapter in order to use a custom list item
     * view.
     */
    public class ContactAdapter extends ArrayAdapter<Contact> {

        public ContactAdapter(Context context, int textViewResourceId, List<Contact> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(R.layout.list_item, parent, false);

            Contact contact = getItem(position);
            ((TextView) item.findViewById(R.id.item_name)).setText(contact.getName());
            ((TextView) item.findViewById(R.id.item_title)).setText(contact.getTitle());
            ((TextView) item.findViewById(R.id.item_phone)).setText(contact.getPhone());

            return item;
        }
    }

}
