package com.aviom.aviomplay.Models;

/**
 * Created by Admin on 2017-02-21.
 */

public class Lead {
    int Id;
    String customername;
    String phone;
    String email;
    int status;
    String created_on;
    String location;
    String remarks;
    String latitude;
    String longitude;
    String budget;
    String property_identified;
    String property_address;
    String leadid;
    public String getLeadid() {
        return leadid;
    }

    public void setLeadid(String leadid) {
        this.leadid = leadid;
    }



    public String getProperty_address() {
        return property_address;
    }

    public void setProperty_address(String property_address) {
        this.property_address = property_address;
    }


    public String getProperty_identified() {
        return property_identified;
    }

    public void setProperty_identified(String property_identified) {
        this.property_identified = property_identified;
    }



    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }


}
