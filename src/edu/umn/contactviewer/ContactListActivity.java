package edu.umn.contactviewer;

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

/**
 * Displays a list of contacts.
 */
public class ContactListActivity extends ListActivity {

    private ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        initListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        contactAdapter.setList(ContactRepository.getInstance(this).getSortedContactList());
        contactAdapter.notifyDataSetChanged();
    }

    // Called when "New" button is clicked
    public void newContact(View view) {
        Intent contactNewIntent = new Intent(getApplicationContext(), ContactNewActivity.class);
        startActivity(contactNewIntent);
    }

    // Called when "Back" button is clicked
    public void backClicked(View view) {
        finish();
    }

    private void initListView() {
        // create list
        if (contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, R.layout.list_item, ContactRepository.getInstance(this)
                    .getSortedContactList());
            setListAdapter(contactAdapter);
        }
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        // handle the item click events
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = ((ContactAdapter) getListAdapter()).getItem(position);
                Intent contactDetailsIntent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                contactDetailsIntent.putExtra(Contact.SELECTED_ID, contact);
                startActivity(contactDetailsIntent);
            }
        });
    }

    /**
     * We need to provide a custom adapter in order to use a custom list item
     * view.
     */
    public class ContactAdapter extends ArrayAdapter<Contact> {

        public ContactAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
            super(context, textViewResourceId, contacts);
        }

        public void setList(List<Contact> contactList) {
            clear();
            addAll(contactList);
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
