package edu.umn.contactviewer;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceActivity;
import android.util.Log;

public class ContactRepository extends PreferenceActivity {
/*
    public static final String Current_Contact = "ContactFile";
    public String name, phone, title, email, twitterId;

    public static String toJSON(Contact contact) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", contact.getName());
            jsonObject.put("phone", contact.getPhone());
            jsonObject.put("title", contact.getTitle());
            jsonObject.put("email", contact.getEmail());
            jsonObject.put("twitterId", contact.getTwitterId());

        } catch (JSONException e) {
            Log.e("Contact Retrieval", "Error building JSON: " + jsonObject, e);
        }
        return jsonObject.toString();
    }

    public static Contact parseJSON(String json) {
        JSONObject jsonObject = null;
        Contact contact = null;
        try {
            jsonObject = new JSONObject(json);
            contact = new Contact(jsonObject.getString("name"));
            // contact.setName(jsonret.getString("name"));
            contact.setPhone(jsonObject.getString("phone"));
            contact.setTitle(jsonObject.getString("title"));
            contact.setEmail(jsonObject.getString("email"));
            contact.setTwitterId(jsonObject.getString("twitterId"));
        } catch (JSONException e) {
            Log.e("Contact Display", "Error restoring contact from JSON: " + jsonObject, e);
        }
        return contact;
    }

    public void saveContact(Contact contact) {
        System.out.println("Hello");
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        Editor spedit = sp.edit();
        // jsonstr = toJSON(contact);
        // spedit.putString(contact.getEmail(),jsonstr);
        // spedit.commit();
    }

    public void deleteContact() {

    }
*/
}
