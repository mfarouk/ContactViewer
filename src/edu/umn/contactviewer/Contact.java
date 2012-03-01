package edu.umn.contactviewer;

import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Model class for storing a single contact.
 * 
 */
public class Contact implements Parcelable, Comparable<Contact> {

    private static final String TWITTER_ID_KEY = "twitterId";
    private static final String EMAIL_KEY = "email";
    private static final String TITLE_KEY = "title";
    private static final String PHONE_KEY = "phone";
    private static final String NAME_KEY = "name";
    private static final String UUID_KEY = "uuid";
    public static String EDIT_ID = "contactToEdit";
    public static String SELECTED_ID = "selectedContact";

    private String _uuid;
    private String _name;
    private String _phone;
    private String _title;
    private String _email;
    private String _twitterId;

    public static final int CONTACT_UPDATED = 0;
    public static final int CONTACT_DELETED = 1;

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    /**
     * Used by Parcelable interface. Contacts are passed between Activities
     * using Parcels.
     * 
     * @param in
     */
    public Contact(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Contacts are persisted and restored from the database using JSON.
     * 
     * @param json
     */
    public Contact(String json) {
        parseJSON(json);
    }

    public Contact() {
        _uuid = UUID.randomUUID().toString();
    }

    public String getUUID() {
        return _uuid;
    }

    /**
     * Set the contact's name.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Get the contact's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the contact's phone number
     */
    public String getPhone() {
        return _phone;
    }

    /**
     * Set's the contact's phone number.
     */
    public void setPhone(String phone) {
        _phone = phone;
    }

    /**
     * @return The contact's title
     */
    public String getTitle() {
        return _title;
    }

    /**
     * Sets the contact's title.
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * @return the contact's e-mail address
     */
    public String getEmail() {
        return _email;
    }

    /**
     * Sets the contact's e-mail address.
     */
    public void setEmail(String email) {
        _email = email;
    }

    /**
     * @return the contact's Twitter ID
     */
    public String getTwitterId() {
        return _twitterId;
    }

    /**
     * Sets the contact's Twitter ID.
     */
    public void setTwitterId(String twitterId) {
        _twitterId = twitterId;
    }

    public String toString() {
        return _name + " (" + _title + ")";
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_uuid);
        dest.writeString(_name);
        dest.writeString(_title);
        dest.writeString(_email);
        dest.writeString(_phone);
        dest.writeString(_twitterId);
    }

    private void readFromParcel(Parcel in) {
        _uuid = in.readString();
        _name = in.readString();
        _title = in.readString();
        _email = in.readString();
        _phone = in.readString();
        _twitterId = in.readString();
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(UUID_KEY, getUUID());
            jsonObject.put(NAME_KEY, getName());
            jsonObject.put(PHONE_KEY, getPhone());
            jsonObject.put(TITLE_KEY, getTitle());
            jsonObject.put(EMAIL_KEY, getEmail());
            jsonObject.put(TWITTER_ID_KEY, getTwitterId());

        } catch (JSONException e) {
            Log.e("Contact Retrieval", "Error building JSON: " + jsonObject, e);
        }
        return jsonObject.toString();
    }

    private void parseJSON(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            if(jsonObject.has(UUID_KEY))
            _uuid = jsonObject.getString(UUID_KEY);
            if(jsonObject.has(NAME_KEY)) 
            _name = jsonObject.getString(NAME_KEY);
            if(jsonObject.has(TITLE_KEY))
            _title = jsonObject.getString(TITLE_KEY);
            if(jsonObject.has(EMAIL_KEY))
            _email = jsonObject.getString(EMAIL_KEY);
            if(jsonObject.has(PHONE_KEY))
            _phone = jsonObject.getString(PHONE_KEY);
            if(jsonObject.has(TWITTER_ID_KEY))
            _twitterId = jsonObject.getString(TWITTER_ID_KEY);
        } catch (JSONException e) {
            Log.e("Contact Display", "Error restoring contact from JSON: " + jsonObject, e);
        }
    }

    public int describeContents() {
        return 0;
    }

    public int compareTo(Contact otherContact) {
        if (_name != null && otherContact._name != null) {
            return _name.compareToIgnoreCase(otherContact._name);
        }
        if (_email != null && otherContact._email != null) {
            return _email.compareToIgnoreCase(otherContact._email);
        }
        if (_twitterId != null && otherContact._twitterId != null) {
            return _twitterId.compareToIgnoreCase(otherContact._twitterId);
        }
        if (_phone != null && otherContact._phone != null) {
            return _phone.compareToIgnoreCase(otherContact._phone);
        }
        if (_title != null && otherContact._title != null) {
            return _title.compareToIgnoreCase(otherContact._title);
        }

        return 0;
    }

}
