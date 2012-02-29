package edu.umn.contactviewer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class ContactRepository {

    private static ContactRepository _instance;
    private static Context baseContext;
    private static boolean loadDefaultContacts = true;

    private HashMap<String, Contact> contacts;

    private ContactRepository() {
        loadContacts();
    }

    public static ContactRepository getInstance() {
        if (_instance == null && baseContext != null) {
            _instance = new ContactRepository();
        }
        return _instance;
    }

    private void loadContacts() {
        contacts = new HashMap<String, Contact>();
        Map<String, ?> contactsJson = getSharedPreferences().getAll();
        for (Entry<String, ?> contactJson : contactsJson.entrySet()) {
            contacts.put(contactJson.getKey(), new Contact((String) contactJson.getValue()));
        }

        if (loadDefaultContacts) {
            loadDefaultContacts();
        }
    }

    private void loadDefaultContacts() {
        Contact defaultContact = new Contact();
        defaultContact.setName("Malcom Reynolds");
        defaultContact.setEmail("mal@serenity.com");
        defaultContact.setTitle("Captain");
        defaultContact.setPhone("612-555-1234");
        defaultContact.setTwitterId("malcomreynolds");
        contacts.put(defaultContact.getUUID(), defaultContact);

        defaultContact = new Contact();
        defaultContact.setName("Zoe Washburne");
        defaultContact.setEmail("zoe@serenity.com");
        defaultContact.setTitle("First Mate");
        defaultContact.setPhone("612-555-5678");
        defaultContact.setTwitterId("zoewashburne");
        contacts.put(defaultContact.getUUID(), defaultContact);

        defaultContact = new Contact();
        defaultContact.setName("Hoban Washburne");
        defaultContact.setEmail("wash@serenity.com");
        defaultContact.setTitle("Pilot");
        defaultContact.setPhone("612-555-9012");
        defaultContact.setTwitterId("wash");
        contacts.put(defaultContact.getUUID(), defaultContact);

        defaultContact = new Contact();
        defaultContact.setName("Jayne Cobb");
        defaultContact.setEmail("jayne@serenity.com");
        defaultContact.setTitle("Muscle");
        defaultContact.setPhone("612-555-3456");
        defaultContact.setTwitterId("heroofcanton");
        contacts.put(defaultContact.getUUID(), defaultContact);
    }

    public HashMap<String, Contact> getContacts() {
        if (contacts == null) {
            loadContacts();
        }
        return contacts;
    }

    public void persistContacts() {
        Editor editor = getSharedPreferences().edit();
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
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(baseContext);
    }

    public static void setBaseContext(Context baseContext) {
        ContactRepository.baseContext = baseContext;
    }

}
