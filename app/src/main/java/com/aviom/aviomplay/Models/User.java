package com.aviom.aviomplay.Models;

/**
 * Created by Admin on 2017-02-18.
 */

public class User {
    int id;
    String username;
    String password;
    String created_on;
    int status;

    public User(){

    }

    public User(String _userrname, String _password, int _status){
        this.username = _userrname;
        this.password= _password;
        this.status=_status;
    }

    //Setters
    public void setId(int _id){
        this.id = _id;
    }

    public void setUsername(String _username){
        this.username= _username;
    }

    public void setPassword(String _password){
        this.password= _password;
    }

    public void setSatus(int _status){
        this.status= _status;
    }

    public void setCreatedOn(String _createdOn){
        this.created_on= _createdOn;
    }

    //Getters
    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public int getSatus(){
        return this.status;
    }

    public String getCreatedOn(){
        return this.created_on;
    }


}
