package edu.umn.contactviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import android.app.Activity;

public class ContactRepository {

    static {
        _instance = new ContactRepository();
    }

    private static ContactRepository _instance;
    private HashMap<String, Contact> contacts;
    private HashMap<String, String> contactIdsMap = new HashMap<String, String>();

    private ContactRepository() {
        loadContacts();
    }

    public static ContactRepository getInstance(Activity activity) {
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

            // Get contacts from remote persistent storage and load into memory
            Map<String, String> contactsJson = ContactRepositoryWebService.readContacts();
            for (Entry<String, String> contactJson : contactsJson.entrySet()) {
                contacts.put(contactJson.getKey(), new Contact((String) contactJson.getValue()));
                contactIdsMap.put(contactJson.getKey(), contactJson.getKey());
            }
        }
    }

    public Contact refreshContact(Contact contact) {
        return contacts.get(contact.getUUID());
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact.getUUID());

        // Remove contact from remote persistent storage
        ContactRepositoryWebService.deleteContact(contactIdsMap.get(contact.getUUID()));
    }

    public void putContact(Contact contact) {
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
    }
}
