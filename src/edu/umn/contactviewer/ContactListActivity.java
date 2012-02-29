package edu.umn.contactviewer;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays a list of contacts.
 */
public class ContactListActivity extends ListActivity {

    public static final int CODE__NEW_CONTACT = 11;
    public static final int CODE__DETAILS = 12;

    // Called when "New" button is clicked
    public void newContact(View view) {
        Intent contactNewIntent = new Intent(getApplicationContext(), ContactNewActivity.class);
        startActivityForResult(contactNewIntent, CODE__NEW_CONTACT);
    }

    // Called when "Back" button is clicked
    public void backClicked(View view) {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        new ToolbarConfig(this, "Contacts");
        initListView(new ArrayList<Contact>(ContactRepository.getInstance(getBaseContext()).getContacts().values()));
    }

    private void initListView(ArrayList<Contact> contacts) {
        // TODO Sort contacts ArrayList so it always appears in the same order

        // create list
        setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = ((ContactAdapter) getListAdapter()).getItem(position);
                Intent data = new Intent(getApplicationContext(), ContactDetailActivity.class);
                data.putExtra(Contact.SELECTED_ID, contact);
                startActivityForResult(data, CODE__DETAILS);
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

            ContactRepository.getInstance(getBaseContext()).putContact(person);
            initListView(new ArrayList<Contact>(ContactRepository.getInstance(getBaseContext()).getContacts().values()));
        }

        // Details Activity Call-back handler
        else if (requestCode == CODE__DETAILS && resultCode == Contact.CONTACT_UPDATED) {
            Toast.makeText(getApplicationContext(), "Contact updated", Toast.LENGTH_SHORT);
        } else if (requestCode == CODE__DETAILS && resultCode == Contact.CONTACT_DELETED) {
            Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void finish() {
        ContactRepository.getInstance(getBaseContext()).persistContacts();
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
