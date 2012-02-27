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

/** Displays a list of contacts.
 *
 */
public class ContactListActivity extends ListActivity {
	Contact contact;
	//final ArrayList<HashMap<String,String>> LIST = new ArrayList<HashMap<String,String>>();
	ContactRep crep;
	public void newContact(View view)
	{
		Intent i = new Intent(getApplicationContext(), ContactNewActivity.class);
		startActivity(i);
	
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        new ToolbarConfig(this, "Contacts");
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        SharedPreferences sp = getSharedPreferences(ContactEditActivity.APP_SHARED_PREFS,ContactEditActivity.MODE_PRIVATE);
    	Map<String,?> items=sp.getAll();
    	crep=new ContactRep();
    	contact=new Contact("RandonName");
    	for(String s : items.keySet()){ 
    	    contact=crep.parseJSON(items.get(s).toString());
    	    contacts.add(contact);
    	}
    	
        
    	// make some contacts - Hard Coded Contacts, could be used for first time fill up of db
        
        /*contacts.add(new Contact("Malcom Reynolds")
    		.setEmail("mal@serenity.com")
    		.setTitle("Captain")
    		.setPhone("612-555-1234")
    		.setTwitterId("malcomreynolds"));
        contacts.add(new Contact("Zoe Washburne")
			.setEmail("zoe@serenity.com")
			.setTitle("First Mate")
			.setPhone("612-555-5678")
			.setTwitterId("zoewashburne"));
        contacts.add(new Contact("Hoban Washburne")
			.setEmail("wash@serenity.com")
			.setTitle("Pilot")
			.setPhone("612-555-9012")
			.setTwitterId("wash"));
        contacts.add(new Contact("Jayne Cobb")
			.setEmail("jayne@serenity.com")
			.setTitle("Muscle")
			.setPhone("612-555-3456")
			.setTwitterId("heroofcanton"));
        contacts.add(new Contact("Kaylee Frye")
			.setEmail("kaylee@serenity.com")
			.setTitle("Engineer")
			.setPhone("612-555-7890")
			.setTwitterId("kaylee"));
        contacts.add(new Contact("Simon Tam")
			.setEmail("simon@serenity.com")
			.setTitle("Doctor")
			.setPhone("612-555-4321")
			.setTwitterId("simontam"));
        contacts.add(new Contact("River Tam")
			.setEmail("river@serenity.com")
			.setTitle("Doctor's Sister")
			.setPhone("612-555-8765")
			.setTwitterId("miranda"));
        contacts.add(new Contact("Shepherd Book")
			.setEmail("shepherd@serenity.com")
			.setTitle("Shepherd")
			.setPhone("612-555-2109")
			.setTwitterId("shepherdbook"));*/

        
        // initialize the list view
    	
        setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				//Toast.makeText(getApplicationContext(), 
					//"Clicked: " + ((ContactAdapter)getListAdapter()).getItem(position).getName(),
					//Toast.LENGTH_SHORT).show();     	
          	  //Serialize contact using JSON object
          	  contact = ((ContactAdapter)getListAdapter()).getItem(position);
          	  String jsonstring = ContactRep.toJSON(contact); 
          	  // Launching new Activity on selecting single List Item
          	  Intent i = new Intent(getApplicationContext(), ContactDetailActivity.class);  	  
          	  i.putExtra("contact", jsonstring);
          	  startActivity(i);		
				}
			}
        );
        
    }    


	/* We need to provide a custom adapter in order to use a custom list item view.
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
			((TextView)item.findViewById(R.id.item_name)).setText(contact.getName());
			((TextView)item.findViewById(R.id.item_title)).setText(contact.getTitle());
			((TextView)item.findViewById(R.id.item_phone)).setText(contact.getPhone());
			
			return item;
		}
	}
    
}


