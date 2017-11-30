package primero.angie.andrew.blabla.interfaces.API;

import java.util.List;

import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by isi3 on 17/4/2017.
 */

public interface ApiInterface {

    @GET("Complaints")
    Call<List<Complaint>> getComplaints();

    @POST("Complaints")
    Call<Complaint> createComplaint(@Body Complaint complaint);

}