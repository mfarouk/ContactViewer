package edu.umn.contactviewer.storage;

public interface Callback <X> {
    
    public void onSuccess(X result);
    
    public void onFailure(X result);

}
