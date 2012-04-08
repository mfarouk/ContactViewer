package edu.umn.contactviewer.storage;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import edu.umn.contactviewer.Contact;

public class ContactRepositoryWebService {

    static final String URL_BASE = "http://contacts.tinyapollo.com/contacts/";
    static final String API_KEY = "pinkpanthers";

    public static Map<String, String> readContacts() {
        Map<String, String> contactMap = new HashMap<String, String>();
        
        AsyncTask<Void, Void, String> result = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... v) {
                AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
                String response = null;

                HttpGet get = new HttpGet(ContactRepositoryWebService.URL_BASE + "?key="
                        + ContactRepositoryWebService.API_KEY);

                try {
                    response = client.execute(get, new BasicResponseHandler());
                } catch (Exception e) {
                    Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
                } finally {
                    client.close();
                }

                return response;
            }
        }.execute();

        try {
            JSONObject resultJson = (JSONObject) new JSONTokener(result.get()).nextValue();
            JSONArray contactsArray = resultJson.getJSONArray("contacts");
            for (int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                contactMap.put(contact.getString(Contact.UUID_KEY), contact.toString());
            }
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        }

        return contactMap;
    }

    public static String createContact(String contactJson) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);

        HttpPost post = new HttpPost(ContactRepositoryWebService.URL_BASE + "?key="
                + ContactRepositoryWebService.API_KEY);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        
        String response = null;
        try {
            StringEntity entity = new StringEntity(contactJson);
            post.setEntity(entity);

            response = client.execute(post, new BasicResponseHandler());
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        String contactId = null;
        try {
            JSONObject resultJson = (JSONObject) new JSONTokener(response).nextValue();
            JSONObject contact = resultJson.getJSONObject("contact");
            contactId = (String) contact.get(Contact.UUID_KEY);
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        }
        
        return contactId;
    }

    public static String updateContact(String contactId, String contactJson) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        String response = null;

        HttpPut put = new HttpPut(ContactRepositoryWebService.URL_BASE + contactId + "?key="
                + ContactRepositoryWebService.API_KEY);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");

        try {
            StringEntity entity = new StringEntity(contactJson);
            put.setEntity(entity);

            response = client.execute(put, new BasicResponseHandler());
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }

    public static String deleteContact(String contactId) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        String response = null;

        HttpDelete delete = new HttpDelete(ContactRepositoryWebService.URL_BASE + contactId + "?key="
                + ContactRepositoryWebService.API_KEY);

        try {
            response = client.execute(delete, new BasicResponseHandler());
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }

}

