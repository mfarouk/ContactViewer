package edu.umn.contactviewer.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.umn.contactviewer.Contact;
import android.app.Activity;
import android.os.AsyncTask;

public class ContactRepository {

    private static ContactRepository _instance;
    private HashMap<String, Contact> contacts;
    private HashMap<String, String> contactIdsMap = new HashMap<String, String>();

    private ContactRepository() {
        if (contacts == null) {
            contacts = new HashMap<String, Contact>();

            // Get contacts from remote persistent storage and load into memory
            Map<String, String> contactsJson = ContactRepositoryWebService.readContacts();
            for (Entry<String, String> contactJson : contactsJson.entrySet()) {
                contacts.put(contactJson.getKey(), new Contact((String) contactJson.getValue()));
                contactIdsMap.put(contactJson.getKey(), contactJson.getKey());
            }
        }
    }

    /**
     * Initializes the contact repository by querying the web service. This
     * method is blocking, and should only be called during app startup.
     */
    public static void initialize() {
        if (_instance == null) {
            _instance = new ContactRepository();
        }
    }

    public static ContactRepository getInstance(Activity activity) {
        return _instance;
    }

    public List<Contact> getSortedContactList() {
        List<Contact> sortedContacts = new ArrayList<Contact>(contacts.values());
        Collections.sort(sortedContacts);
        return sortedContacts;
    }

    public Contact refreshContact(Contact contact) {
        return contacts.get(contact.getUUID());
    }

    public void removeContact(final Contact contact, final Callback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Remove contact from local memory
                contacts.remove(contact.getUUID());

                // Remove contact from remote persistent storage
                ContactRepositoryWebService.deleteContact(contactIdsMap.get(contact.getUUID()));
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                callback.onSuccess(result);
            }
        }.execute();
    }

    public void putContact(final Contact contact, final Callback<Void> callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Put contact in local memory
                contacts.put(contact.getUUID(), contact);

                // Update contact in remote persistent storage
                if (contactIdsMap.containsKey(contact.getUUID())) {
                    ContactRepositoryWebService.updateContact(contactIdsMap.get(contact.getUUID()), contact.toJSON());
                }
                // Create contact in remote persistent storage
                else {
                    String remoteContactId = ContactRepositoryWebService.createContact(contact.toJSON());
                    contactIdsMap.put(contact.getUUID(), remoteContactId);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                callback.onSuccess(result);
            }
        }.execute();
    }

}
