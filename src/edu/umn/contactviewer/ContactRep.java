package edu.umn.contactviewer;


import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class ContactRep extends Activity {
	private static String jsonstr;
	public String name,phone,title,email,twitterId;
	public static final String Current_Contact = "ContactFile";
	SharedPreferences sp;
	SharedPreferences.Editor spedit;
	static Contact contact;
	public static String toJSON(Contact contact){
		JSONObject jsonobj = new JSONObject();
		try{
			jsonobj.put("name", contact.getName());
			jsonobj.put("phone", contact.getPhone());
	  		jsonobj.put("title", contact.getTitle());
	  		jsonobj.put("email", contact.getEmail());
	  		jsonobj.put("twitterId", contact.getTwitterId());
			
		} catch (JSONException e) {
	  		Log.e("Contact Retrieval","Error building JSON: " + jsonobj, e);
	  	}   	
	     jsonstr= jsonobj.toString();
	     return jsonstr;
		}
	public Contact parseJSON(String jsonstr){
		JSONObject jsonret = null;
		try {
			jsonret = new JSONObject(jsonstr);
			contact = new Contact(jsonret.getString("name"));
			contact.setName(jsonret.getString("name"));
        	contact.setPhone(jsonret.getString("phone"));
        	contact.setTitle(jsonret.getString("title"));
        	contact.setEmail(jsonret.getString("email"));
        	contact.setTwitterId(jsonret.getString("twitterId"));
        	
		} catch (JSONException e) {
			Log.e("Contact Display","Error restoring contact from JSON: " + jsonret, e);
		}
		return contact;
	}
	
	public void saveContact(Contact contact){
		sp = getSharedPreferences(Current_Contact, MODE_PRIVATE);
		spedit = sp.edit();
		spedit.putString(contact.getEmail(),jsonstr);
		spedit.commit();	
		
	}
	public void deleteContact(){
		
	}
	
	//public String getContact(){
	//	jsonget=sp.getAll();
	//	String s;
	//	return s;
		
	//}
	
	

}
