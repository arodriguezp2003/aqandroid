package com.rsdev.aquacontrol.sql.models;

public class Auth {     
	    //private variables
		int _id;
	    Boolean _esta;
	
	     
	    // Empty constructor
	    public Auth(){
	         
	    }
	    // constructor
	    public Auth(int id, Boolean esta){
	        this._id = id;
	        this._esta = esta;
	        
	    }

	    // getting ID
	    public int getID(){
	        return this._id;
	    }
	     
	    // setting id
	    public void setID(int id){
	        this._id = id;
	    }
	     
	    // getting name
	    public Boolean getEsta(){
	        return this._esta;
	    }
	     
	    // setting name
	    public void setEsta(Boolean esta){
	        this._esta = esta;
	    }
	     
	    
	
}
