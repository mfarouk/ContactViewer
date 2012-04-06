package edu.umn.contactviewer;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ResponseHandler;
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

public class ContactRepositoryWebService {

    static final String URL_BASE = "http://contacts.tinyapollo.com/contacts/";
    static final String API_KEY = "pinkpanthers";

    public static Map<String, String> readContacts() {
        String resultString = null;
        Map<String, String> contactMap = new HashMap<String, String>();
        AsyncTask<Void, Void, String> result = new ReadContactsTask().execute();
        try {
            resultString = result.get();
            JSONObject resultJson = (JSONObject) new JSONTokener(resultString).nextValue();
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
        String resultString = null;
        String contactId = null;
        AsyncTask<String, Void, String> result = new CreateContactTask().execute(contactJson);
        try {
            resultString = result.get();
            JSONObject resultJson = (JSONObject) new JSONTokener(resultString).nextValue();
            JSONObject contact = resultJson.getJSONObject("contact");
            contactId = (String) contact.get(Contact.UUID_KEY);
        } catch (Exception e) {
            Log.e(ContactRepositoryWebService.class.getName(), e.getMessage());
        }
        return contactId;
    }

    public static void updateContact(String contactId, String contactJson) {
        new UpdateContactTask().execute(contactId, contactJson);
    }

    public static void deleteContact(String contactId) {
        new DeleteContactTask().execute(contactId);
    }

}

class ReadContactsTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... v) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = null;

        HttpGet get = new HttpGet(ContactRepositoryWebService.URL_BASE + "?key=" + ContactRepositoryWebService.API_KEY);

        try {
            response = client.execute(get, responseHandler);
        } catch (Exception e) {
            Log.e(ReadContactsTask.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }
}

class CreateContactTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... contactJson) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = null;

        HttpPost post = new HttpPost(ContactRepositoryWebService.URL_BASE + "?key="
                + ContactRepositoryWebService.API_KEY);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try {
            StringEntity entity = new StringEntity(contactJson[0]);
            post.setEntity(entity);

            response = client.execute(post, responseHandler);
        } catch (Exception e) {
            Log.e(CreateContactTask.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }
}

class UpdateContactTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... contactInfo) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = null;

        HttpPut put = new HttpPut(ContactRepositoryWebService.URL_BASE + contactInfo[0] + "?key="
                + ContactRepositoryWebService.API_KEY);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");

        try {
            StringEntity entity = new StringEntity(contactInfo[1]);
            put.setEntity(entity);

            response = client.execute(put, responseHandler);
        } catch (Exception e) {
            Log.e(UpdateContactTask.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }
}

class DeleteContactTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... contactId) {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = null;

        HttpDelete delete = new HttpDelete(ContactRepositoryWebService.URL_BASE + contactId[0] + "?key="
                + ContactRepositoryWebService.API_KEY);

        try {
            response = client.execute(delete, responseHandler);
        } catch (Exception e) {
            Log.e(UpdateContactTask.class.getName(), e.getMessage());
        } finally {
            client.close();
        }

        return response;
    }
}
