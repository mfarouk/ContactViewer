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

    private void loadContacts() {
        contacts = new HashMap<String, Contact>();
        Map<String, ?> contactsJson = getSharedPreferences(_baseContext).getAll();
        for (Entry<String, ?> contactJson : contactsJson.entrySet()) {
            contacts.put(contactJson.getKey(), new Contact((String) contactJson.getValue()));
        }
    }

    public List<Contact> getSortedContactList() {
        if (contacts == null) {
            loadContacts();
        }
        List<Contact> sortedContacts = new ArrayList<Contact>(contacts.values());
        Collections.sort(sortedContacts);
        return sortedContacts;
    }

    public HashMap<String, Contact> getContacts() {
        if (contacts == null) {
            loadContacts();
        }
        return contacts;
    }

    public void persistContacts() {
        Editor editor = getSharedPreferences(_baseContext).edit();
        for (Contact contact : contacts.values()) {
            editor.putString(contact.getUUID(), contact.toJSON());
        }
        editor.commit();
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact.getUUID());
        persistContacts();
    }

    public void putContact(Contact contact) {
        contacts.put(contact.getUUID(), contact);
        persistContacts();
    }

    private SharedPreferences getSharedPreferences(Context baseContext) {
        return PreferenceManager.getDefaultSharedPreferences(baseContext);
    }

    public Contact refreshContact(Contact contact) {
        return contacts.get(contact.getUUID());
    }

}
