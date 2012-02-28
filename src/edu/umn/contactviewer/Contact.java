package edu.umn.contactviewer;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class for storing a single contact.
 * 
 */
public class Contact {

    private String _uuid;
    private String _name;
    private String _phone;
    private String _title;
    private String _email;
    private String _twitterId;

	/**
	 * Creates a contact. Contacts should have a unique ID.
	 * 
	 * @param name
	 *            the contact's name
	 */
	public Contact(String name) {
		_uuid = UUID.randomUUID().toString();
		_name = name;
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
		_name = savedUser.getString("name");
		_title = savedUser.getString("title");
		_phone = savedUser.getString("phone");
		_email = savedUser.getString("email");
		_twitterId = savedUser.getString("twitterId");
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

	public String getUUID() {
		return _uuid;
	}


	/**
	 * Set the contact's name.
	 */
	public Contact setName(String name) {
		_name = name;
		return this;
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
	public Contact setPhone(String phone) {
		_phone = phone;
		return this;
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
	public Contact setTitle(String title) {
		_title = title;
		return this;
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
	public Contact setEmail(String email) {
		_email = email;
		return this;
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
	public Contact setTwitterId(String twitterId) {
		_twitterId = twitterId;
		return this;
	}

	public String toString() {
		return _name + " (" + _title + ")";
	}
}
