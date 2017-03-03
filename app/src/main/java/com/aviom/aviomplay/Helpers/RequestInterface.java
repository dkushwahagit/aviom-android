package com.aviom.aviomplay.Helpers;

/**
 * Created by SOS on 1/24/2017.
 */

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {

   // @POST("andy/")
 //   Call<ServerResponse> operation(@Body ServerRequest request);


  //  @GET("andy/")
   // Call<List<Leads>> getLeadsByUserName(@Query("method") String method, @Query("id") String id);
    @GET("Api/v1/getTodaysAppointment/{id}")
    Call<JsonObject> leadById(@Path("id") int id);

  @GET("Api/v1/verify-password")
    Call<JsonObject> loginCheck(@Query("deviceid") String deviceid, @Query("Employee_Code") String username, @Query("password") String password);

  @GET("Api/v1/getLeadByUserId/{id}")
  Call<JsonObject> getLeadByUserId(@Path("id") String id);


  @GET("Api/v1/getLeadsWithLastInteraction/{id}")
  Call<JsonObject> getLeadsWithLastInteraction(@Path("id") int id);

  @GET("Api/v1/saveLeadInteraction/{id}/{userid}")
  Call<JsonObject> updateinteraction(@Path("id") int id, @Path("userid") String userid, @Query("Planned_On") String plannedon, @Query("Updated_On") String updatedon, @Query("Planned_Status") String plannedstatus, @Query("Planned_Remarks") String plannedremarks, @Query("Updated_By") String updatedby);


  @GET("Api/v1/getTodaysAppointment/{id}")
  Call<JsonObject> getTodaysAppointment(@Path("id") String id);

  //  @GET("andy/")

  //  Call<List<Leads>> getFilteredBy(@Query("method") String method, @Query("column") String column, @Query("filterBy") String filterBy);

   // Call<List<Leads>>getUserById();

  @Multipart
  @POST("Api/v1/addNewLead/{id}")
  Call<JsonObject> uploadImage(@Path("id") String id, @Part MultipartBody.Part file, @Part("Client_Name") RequestBody Client_Name, @Part("Client_Contact_Number") RequestBody Client_Contact_Number, @Part("Address") RequestBody Address, @Part("Lead_Bucket") RequestBody Lead_Bucket, @Part("Latitude") RequestBody Latitude, @Part("Longitude") RequestBody Longitude);
}


