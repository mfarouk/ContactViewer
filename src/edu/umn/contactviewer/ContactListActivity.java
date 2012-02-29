package edu.umn.contactviewer;

// For Activity communication help:
//     http://www.vogella.de/articles/AndroidIntent/article.html#explicitintents
//


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
    public static final int CODE__DETAILS = 12;
    
    ArrayList<Contact> contacts;
    int userSelectedContactPosition;

    // Called when "New" button is clicked
    public void newContact(View view) {
        Intent contactNewIntent = new Intent(getApplicationContext(), ContactNewActivity.class);
        startActivityForResult(contactNewIntent, CODE__NEW_CONTACT);
    }
    
    // Called when "Back" button is clicked
    public void backClicked(View view){
    	finish();
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
            //Contact contact = ContactRepository.parseJSON(items.get(s).toString());
            Contact contact;
			try {
				contact = new Contact(new JSONObject(items.get(s).toString()));
				contacts.add(contact);
			} catch (JSONException e) {
				Log.e("ContactListActivity","Error restoring contact");
			}
            
        }

        // initialize the list view
        initListView();
        
    }

    //@SuppressWarnings("unchecked")
	private void initListView()
    {
    	// Sort contacts ArrayList so it always appears in the same order
    	Collections.sort(contacts);
    	
    	// create list
    	setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	try{
            		userSelectedContactPosition = position;
            		
            		// Serialize contact that was clicked on
            		Contact contact = ((ContactAdapter) getListAdapter()).getItem(position);
            		String contactJson = contact.serialize().toString();
            		
            		// Launch Details view Activity -> provice "call-back"
            		Intent data = new Intent(getApplicationContext(), ContactDetailActivity.class);
            		data.putExtra("contact", contactJson);
            		startActivityForResult(data, CODE__DETAILS);
            	}
            	catch(JSONException e){
            		Log.e("ContactListActivity","Unable to build json object for details view");
            	}
            }
        });
    }
    
    /**
     * Handles data returned from child Activities.
     */
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// New Contact Activity Call-back handler
		if (resultCode == RESULT_OK && requestCode == CODE__NEW_CONTACT) {
			// make a new contact
			Contact person = new Contact();
			person.setName(data.getExtras().getString("name"));
			person.setTitle(data.getExtras().getString("title"));
			person.setPhone(data.getExtras().getString("phone"));
			person.setEmail(data.getExtras().getString("email"));
			person.setTwitterId(data.getExtras().getString("twitterId"));
			Toast.makeText(getApplicationContext(), "Contact: " + person.getName(), Toast.LENGTH_SHORT).show();
			
			contacts.add(person);
			initListView();
		}
		
		// Details Activity Call-back handler
		else if(requestCode == CODE__DETAILS && resultCode == Contact.CONTACT_UPDATED){
			//Toast.makeText(getApplicationContext(), "Contact updated", Toast.LENGTH_SHORT);
			
			// extract contact from array list -> using uuid
			contacts.get(userSelectedContactPosition).update(data.getExtras().getString("contact"));
			
			// Update view
			initListView();
		}
		else if(requestCode == CODE__DETAILS && resultCode == Contact.CONTACT_DELETED){
			//Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_SHORT);
			// remove contact from ArrayList
			contacts.remove(userSelectedContactPosition);
			
			// Update view
			initListView();
		}
	}
    
    @Override
	public void finish() {
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);
        Editor spedit = getSharedPreferences(ContactEditActivity.APP_SHARED_PREFS,
                ContactEditActivity.MODE_PRIVATE).edit();
        spedit.clear();		// erase all entries
        
        for(Contact person : contacts){
        	try{
        		spedit.putString(person.getUUID(), person.serialize().toString());
        		//Toast.makeText(getApplicationContext(), 
        		//		"Saved: {" + person.getUUID() + ", " + person.getName() + "}",
        		//		Toast.LENGTH_SHORT).show();
        	}
        	catch(JSONException e){
        		Toast.makeText(getApplicationContext(), "Error: failed to save " + person.getName(), Toast.LENGTH_SHORT).show();
        	}
        }
        
        spedit.apply();
        spedit.commit();
        super.finish();
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
