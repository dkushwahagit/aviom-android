package com.aviom.aviomplay.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.aviom.aviomplay.Models.Interaction;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.Models.User;
import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Admin on 2017-02-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //DATABASE DETAILS
    private static final String DATABASE_NAME = "aviomplay.db" ;
    private static final int DATABASE_VERSION =1 ;

    //TABLES
    private static final String TBL_USER="user";
    private static final String TBL_LEAD="lead";
    private static final String TBL_LEAD_INTERACTION="lead_interaction";

    // Common column names
    private static final String COL_ID = "id";
    private static final String COL_CREATED_ON = "created_on";

    //Table column Names
    //User Table Starts Here
    private static final String COL_USER_USERNAME="username";
    private static final String COL_USER_PASSWORD="password";
    private static final String COL_USER_STATUS="status";
    //User Table Ends Here

    //Lead Table Starts Here
    private static final String COL_LEAD_CUSTOMERNAME="customername";
    private static final String COL_LEAD_PHONE="phone";
    private static final String COL_LEAD_EMAIL="email";
    private static final String COL_LEAD_BUDGET="budget";
    private static final String COL_LEAD_LOCATION="location";
    private static final String COL_LEAD_REMARKS="remarks";
    private static final String COL_LEAD_LATITUDE="latitude";
    private static final String COL_LEAD_LONGITUDE="longitude";
    private static final String COL_LEAD_STATUS="status";
    private static final String COL_LEAD_PUSHEDONDATE="pushedondate";
    private static final String COL_LEAD_PROPERTYIDENTIFIED="property_identified";
    private static final String COL_LEAD_PROPERTYADDRESS="property_Address";
    private static final String COL_LEAD_IMAGE="client_image";
    private static final String COL_LEAD_ID="lead_id";
    //Lead Table Ends Here

    //Lead Interaction Table Starts Here
    private static final String COL_STATUS="status";
    private static final String COL_PLANNED_BY="planned_by";
    private static final String COL_PLANNED_ON="planned_on";
    private static final String COL_REMARKS="remarks";
    //Lead Interaction Table Ends Here

    //Table Create Statements
    private static final String CREATE_TABLE_USER="CREATE TABLE "+ TBL_USER +
            "("+COL_ID +" INTEGER PRIMARY KEY, "+ COL_USER_USERNAME +" TEXT, "
            + COL_USER_PASSWORD +" TEXT, " +COL_USER_STATUS+ " INTEGER, " +COL_CREATED_ON + " DATETIME )";

    //Table Create Statements
    private static final String CREATE_TABLE_LEAD="CREATE TABLE "+ TBL_LEAD +
            "("+COL_ID+" INTEGER PRIMARY KEY, "+ COL_LEAD_CUSTOMERNAME +" TEXT, "
            + COL_LEAD_ID +" TEXT, "
            + COL_LEAD_PHONE +" TEXT, " + COL_LEAD_STATUS + " INTEGER, "
            + COL_LEAD_EMAIL +" TEXT, " + COL_LEAD_BUDGET + " TEXT, "
            + COL_LEAD_LOCATION +" TEXT, " + COL_LEAD_REMARKS + " TEXT, "
            + COL_LEAD_LATITUDE +" TEXT, " +COL_LEAD_LONGITUDE+ " TEXT, "
            + COL_LEAD_PROPERTYIDENTIFIED +" TEXT, "+ COL_LEAD_PROPERTYADDRESS + " TEXT, "
            + COL_CREATED_ON + " DATETIME, "+ COL_LEAD_IMAGE +" BLOB, "+ COL_LEAD_PUSHEDONDATE+ " DATETIME )";

    //Table Create Statements
    private static final String CREATE_TABLE_LEAD_INTERACTION="CREATE TABLE "+ TBL_LEAD_INTERACTION +
            "("+COL_ID+" INTEGER PRIMARY KEY, "+ COL_LEAD_ID +" TEXT, "
            + COL_STATUS +" INTEGER, "
            + COL_PLANNED_BY +" INTEGER, " +COL_PLANNED_ON+ " DATETIME, "
            + COL_REMARKS +" TEXT, "
            + COL_CREATED_ON + " DATETIME, "+ COL_LEAD_PUSHEDONDATE+ " DATETIME )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LEAD);
        db.execSQL(CREATE_TABLE_LEAD_INTERACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_LEAD);
        onCreate(db);
    }

    public long createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        //set values for table insert
        ContentValues values = new ContentValues();
        values.put(COL_USER_USERNAME,user.getUsername());
        values.put(COL_USER_PASSWORD,user.getPassword());
        values.put(COL_USER_STATUS,user.getSatus());
        values.put(COL_CREATED_ON,user.getCreatedOn());

        //insert into User table
        long user_id = db.insert(TBL_USER,null,values);

        return user_id;
    }

    public User getUser(long user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_USER + " WHERE " + COL_ID + " = " +user_id;
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null){
            c.moveToFirst();
        }
        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(COL_ID)));
        user.setUsername(c.getString(c.getColumnIndex(COL_USER_USERNAME)));
        user.setPassword(c.getString(c.getColumnIndex(COL_USER_PASSWORD)));
        user.setSatus(c.getInt(c.getColumnIndex(COL_USER_STATUS)));
        return user;
    }
    public Lead getLeadByLeadId(long Lead_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_LEAD_INTERACTION+" left join "+ TBL_LEAD +" on "+ TBL_LEAD+".id="+ TBL_LEAD_INTERACTION +".lead_id"+" WHERE " + TBL_LEAD+"."+COL_ID + " = " +Lead_id +  " GROUP BY "+TBL_LEAD+"."+COL_ID;

                //"SELECT * FROM " + TBL_LEAD + " WHERE " + COL_ID + " = " +Lead_id;
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null){
            c.moveToFirst();
        }
        Lead ld = new Lead();
        ld.setId(c.getInt(c.getColumnIndex(COL_ID)));
        ld.setCustomername(c.getString(c.getColumnIndex(COL_LEAD_CUSTOMERNAME)));
        ld.setEmail(c.getString(c.getColumnIndex(COL_LEAD_EMAIL)));
        ld.setPhone(c.getString(c.getColumnIndex(COL_LEAD_PHONE)));
        ld.setStatus(c.getInt(c.getColumnIndex(COL_LEAD_STATUS)));
        ld.setLocation(c.getString(c.getColumnIndex(COL_LEAD_LOCATION)));
        ld.setLatitude(c.getString(c.getColumnIndex(COL_LEAD_LATITUDE)));
        ld.setLongitude(c.getString(c.getColumnIndex(COL_LEAD_LONGITUDE)));
        ld.setProperty_identified(c.getString(c.getColumnIndex(COL_LEAD_PROPERTYIDENTIFIED)));
        ld.setProperty_address(c.getString(c.getColumnIndex(COL_LEAD_PROPERTYADDRESS)));
        ld.setBudget(c.getString(c.getColumnIndex(COL_LEAD_BUDGET)));
        ld.setImage(c.getBlob(c.getColumnIndex(COL_LEAD_IMAGE)));
        ld.setCreated_on(c.getString(c.getColumnIndex(COL_CREATED_ON)));
        ld.setAppoimentdate(c.getString(c.getColumnIndex(COL_PLANNED_ON)));

        return ld;
    }
    public List<Lead> getAllLeads(){
        List<Lead> lstLeads = new ArrayList<Lead>();
        String sqlQuery = "SELECT * FROM " + TBL_LEAD_INTERACTION+" left join "+ TBL_LEAD +" on "+ TBL_LEAD+".id="+ TBL_LEAD_INTERACTION +".lead_id"+" GROUP BY "+TBL_LEAD+"."+COL_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.moveToFirst()){
            do{
                Lead ld = new Lead();
                ld.setId(c.getInt(c.getColumnIndex(COL_ID)));
                ld.setCustomername(c.getString(c.getColumnIndex(COL_LEAD_CUSTOMERNAME)));
                ld.setEmail(c.getString(c.getColumnIndex(COL_LEAD_EMAIL)));
                ld.setPhone(c.getString(c.getColumnIndex(COL_LEAD_PHONE)));
                ld.setStatus(c.getInt(c.getColumnIndex(COL_LEAD_STATUS)));
                ld.setLocation(c.getString(c.getColumnIndex(COL_LEAD_LOCATION)));
                ld.setLatitude(c.getString(c.getColumnIndex(COL_LEAD_LATITUDE)));
                ld.setLongitude(c.getString(c.getColumnIndex(COL_LEAD_LONGITUDE)));
                ld.setBudget(c.getString(c.getColumnIndex(COL_LEAD_BUDGET)));
                ld.setImage(c.getBlob(c.getColumnIndex(COL_LEAD_IMAGE)));
                ld.setCreated_on(c.getString(c.getColumnIndex(COL_CREATED_ON)));
                ld.setAppoimentdate(c.getString(c.getColumnIndex(COL_PLANNED_ON)));
                lstLeads.add(ld);
            }while (c.moveToNext());
        }
        return lstLeads;
    }

    public long createLead(Lead lead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //set values for table insert
        ContentValues values = new ContentValues();
        values.put(COL_LEAD_CUSTOMERNAME,lead.getCustomername());
        values.put(COL_LEAD_PHONE,lead.getPhone());
        values.put(COL_LEAD_LOCATION,lead.getLocation());
        values.put(COL_LEAD_BUDGET,lead.getBudget());
        values.put(COL_LEAD_PROPERTYIDENTIFIED,lead.getProperty_identified());
        values.put(COL_LEAD_PROPERTYADDRESS,lead.getProperty_address());
        values.put(COL_LEAD_REMARKS,lead.getRemarks());
        values.put(COL_LEAD_STATUS,lead.getStatus());
        values.put(COL_LEAD_LATITUDE,lead.getLatitude());
        values.put(COL_LEAD_LONGITUDE,lead.getLongitude());
        values.put(COL_LEAD_ID,lead.getLeadid());
        values.put(COL_CREATED_ON,lead.getCreated_on());
        values.put(COL_LEAD_PUSHEDONDATE,"0");
        values.put(COL_LEAD_IMAGE,lead.getImage());
        //insert into User table
        long lead_id = db.insert(TBL_LEAD,null,values);

        return lead_id;
    }

    public long createInteraction(Interaction interaction)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //set values for table insert
        ContentValues values = new ContentValues();
        values.put(COL_PLANNED_ON,interaction.getPlannedon());
        values.put(COL_LEAD_ID,interaction.getLeadid());
        values.put(COL_STATUS,interaction.getStatus());
        values.put(COL_REMARKS,interaction.getRemarks());
        values.put(COL_PLANNED_BY,interaction.getPlanned_by());
        values.put(COL_CREATED_ON,interaction.getCreated_on());
        //insert into User table
        long interaction_id = db.insert(TBL_LEAD_INTERACTION,null,values);

        return interaction_id;
    }
    public int getLeadByCreateON(String date)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCount= db.rawQuery("select count(*) from "+TBL_LEAD+" where created_on like '" + date +"%"+ "'", null);
        String qry="select count(*) from "+TBL_LEAD+" where created_on like '" + date +"%"+ "'";
        Log.d("query",qry);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
    public boolean updateLeadAndCreateNewAppointment( int leadid, int leadstatus,String remark)
    {
        SQLiteDatabase db = this.getWritableDatabase();
     //   Cursor mCount= db.rawQuery("UPDATE "+TBL_LEAD+ " SET "+COL_LEAD_STATUS+" = "+leadstatus+", "+COL_LEAD_REMARKS+" = "+remark+" where "+COL_ID+" = "+leadid, null);
      //  String qry="select count(*) from "+TBL_LEAD+" where created_on like '" + date +"%"+ "'";
        ContentValues data=new ContentValues();
        data.put(COL_LEAD_STATUS, leadstatus);
        data.put(COL_LEAD_REMARKS,remark);
        db.update(TBL_LEAD, data, COL_ID+"=" + leadid, null);
        return true;
    }

    public List<Lead> getAllAppointment(){
        List<Lead> lstLeads = new ArrayList<Lead>();
        String sqlQuery = "SELECT * FROM " + TBL_LEAD_INTERACTION+" join "+ TBL_LEAD +" on "+ TBL_LEAD+".id="+ TBL_LEAD_INTERACTION +".lead_id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.moveToFirst()){
            do{
                Lead ld = new Lead();
                ld.setId(c.getInt(c.getColumnIndex(COL_ID)));
                ld.setCustomername(c.getString(c.getColumnIndex(COL_LEAD_CUSTOMERNAME)));
                ld.setEmail(c.getString(c.getColumnIndex(COL_LEAD_EMAIL)));
                ld.setPhone(c.getString(c.getColumnIndex(COL_LEAD_PHONE)));
                ld.setStatus(c.getInt(c.getColumnIndex(COL_LEAD_STATUS)));
                ld.setLocation(c.getString(c.getColumnIndex(COL_LEAD_LOCATION)));
                ld.setLatitude(c.getString(c.getColumnIndex(COL_LEAD_LATITUDE)));
                ld.setLongitude(c.getString(c.getColumnIndex(COL_LEAD_LONGITUDE)));
                ld.setBudget(c.getString(c.getColumnIndex(COL_LEAD_BUDGET)));
                ld.setImage(c.getBlob(c.getColumnIndex(COL_LEAD_IMAGE)));
                ld.setCreated_on(c.getString(c.getColumnIndex(COL_CREATED_ON)));
                ld.setAppoimentdate(c.getString(c.getColumnIndex(COL_PLANNED_ON)));
                lstLeads.add(ld);
            }while (c.moveToNext());
        }
        return lstLeads;
    }

    public List<Lead> getAllAppointmentByFilter(String date,String compare){
        List<Lead> lstLeads = new ArrayList<Lead>();
        String sqlQuery = "SELECT * FROM " + TBL_LEAD_INTERACTION+" join "+ TBL_LEAD +" on "+ TBL_LEAD+".id="+ TBL_LEAD_INTERACTION +".lead_id"+" Where "+ TBL_LEAD_INTERACTION+"."+COL_PLANNED_ON +compare+date;
       Log.d("Sql is =",sqlQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.moveToFirst()){
            do{
                Lead ld = new Lead();
                ld.setId(c.getInt(c.getColumnIndex(COL_ID)));
                ld.setCustomername(c.getString(c.getColumnIndex(COL_LEAD_CUSTOMERNAME)));
                ld.setEmail(c.getString(c.getColumnIndex(COL_LEAD_EMAIL)));
                ld.setPhone(c.getString(c.getColumnIndex(COL_LEAD_PHONE)));
                ld.setStatus(c.getInt(c.getColumnIndex(COL_LEAD_STATUS)));
                ld.setLocation(c.getString(c.getColumnIndex(COL_LEAD_LOCATION)));
                ld.setLatitude(c.getString(c.getColumnIndex(COL_LEAD_LATITUDE)));
                ld.setLongitude(c.getString(c.getColumnIndex(COL_LEAD_LONGITUDE)));
                ld.setBudget(c.getString(c.getColumnIndex(COL_LEAD_BUDGET)));
                ld.setImage(c.getBlob(c.getColumnIndex(COL_LEAD_IMAGE)));
                ld.setCreated_on(c.getString(c.getColumnIndex(COL_CREATED_ON)));
                ld.setAppoimentdate(c.getString(c.getColumnIndex(COL_PLANNED_ON)));
                lstLeads.add(ld);
            }while (c.moveToNext());
        }
        return lstLeads;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


}
