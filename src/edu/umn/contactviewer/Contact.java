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
public class Contact implements Parcelable {

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
     * Used by Parcelable interface
     * 
     * @param in
     */
    public Contact(Parcel in) {
        readFromParcel(in);
    }

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
	 * Creates a new Contact object, using the values that had previously been
	 * saved.
	 * 
	 * @param savedUser
	 * @throws JSONException
	 */
	public Contact(JSONObject savedUser) throws JSONException {
		_uuid = savedUser.getString("uuid");
		
		if(savedUser.has("name"))		_name = savedUser.getString("name");
		if(savedUser.has("title"))		_title = savedUser.getString("title");
		if(savedUser.has("phone"))		_phone = savedUser.getString("phone");
		if(savedUser.has("email"))		_email = savedUser.getString("email");
		if(savedUser.has("twitterId"))	_twitterId = savedUser.getString("twitterId");
	}

	/**
	 * Create a JSON representation of this Contact for persisting or passing 
	 * to other Activities.
	 * 
	 * @return
	 * @throws JSONException
	 */
	public JSONObject serialize() throws JSONException{
		JSONObject saveUser = new JSONObject();
		saveUser.put("uuid", _uuid);
		saveUser.put("name", _name);
		saveUser.put("title", _title);
		saveUser.put("phone", _phone);
		saveUser.put("email", _email);
		saveUser.put("twitterID", _twitterId);
		return saveUser;
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
            _uuid = jsonObject.getString(UUID_KEY);
            _name = jsonObject.getString(NAME_KEY);
            _title = jsonObject.getString(TITLE_KEY);
            _email = jsonObject.getString(EMAIL_KEY);
            _phone = jsonObject.getString(PHONE_KEY);
            _twitterId = jsonObject.getString(TWITTER_ID_KEY);
        } catch (JSONException e) {
            Log.e("Contact Display", "Error restoring contact from JSON: " + jsonObject, e);
        }
    }

    public int describeContents() {
        return 0;
    }

}
