package edu.umn.contactviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class ContactRepository {

    private static ContactRepository _instance;
    private static Context _baseContext;

    private HashMap<String, Contact> contacts;

    private ContactRepository() {
        loadContacts();
    }

    public static ContactRepository getInstance(Activity activity) {
        if (_baseContext == null) {
            _baseContext = activity.getBaseContext();
        }
        if (_instance == null) {
            _instance = new ContactRepository();
        }
        return _instance;
    }

    public List<Contact> getSortedContactList() {
        loadContacts();
        List<Contact> sortedContacts = new ArrayList<Contact>(contacts.values());
        Collections.sort(sortedContacts);
        return sortedContacts;
    }

    public HashMap<String, Contact> getContacts() {
        loadContacts();
        return contacts;
    }
    
    private void loadContacts() {
        if (contacts == null) {
            contacts = new HashMap<String, Contact>();
            Map<String, ?> contactsJson = getSharedPreferences(_baseContext).getAll();
            for (Entry<String, ?> contactJson : contactsJson.entrySet()) {
                contacts.put(contactJson.getKey(), new Contact((String) contactJson.getValue()));
            }
        }
    }
    
    public Contact refreshContact(Contact contact) {
        return contacts.get(contact.getUUID());
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact.getUUID());
        Editor database = open();
        database.remove(contact.getUUID());
        commit(database);
    }

    public void putContact(Contact contact) {
        contacts.put(contact.getUUID(), contact);
        Editor database = open();
        database.putString(contact.getUUID(), contact.toJSON());
        commit(database);
    }
    
    private Editor open() {
        return getSharedPreferences(_baseContext).edit();
    }
    
    private void commit(Editor editor) {
        editor.commit();
    }

    private SharedPreferences getSharedPreferences(Context baseContext) {
        return PreferenceManager.getDefaultSharedPreferences(baseContext);
    }

}
