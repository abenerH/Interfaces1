package primero.angie.andrew.blabla.interfaces.API;

import java.util.List;

import okhttp3.ResponseBody;
import primero.angie.andrew.blabla.interfaces.clases.AccessToken;
import primero.angie.andrew.blabla.interfaces.clases.Category;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import primero.angie.andrew.blabla.interfaces.clases.Picture;
import primero.angie.andrew.blabla.interfaces.clases.User;
import primero.angie.andrew.blabla.interfaces.clases.createComplaint;
import primero.angie.andrew.blabla.interfaces.clases.receiveComplaint;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    String ACCESS_TOKEN = "";
    @GET("Categories")
    Call<List<Category>> getCategories(@Header("Authorization") String authorization);


    /**
     * Get all the complaints
     *
     * @param authorization
     * @return
     */

    @GET("Complaints?filter={\"include\": [\"pictures\", \"user\"]}")
    Call<List<Complaint>> getComplaints(@Header("Authorization") String authorization);

    @POST("Complaints")
    Call<receiveComplaint> createComplaint(@Header("Authorization") String authorization, @Body createComplaint complaint);


    /**
     * Get complaints, user and pictures relations for complaint id
     *
     */
    @GET("Complaints/{id}?filter={\"include\": [\"pictures\", \"user\"]}")
    Call<receiveComplaint> getComplaint(@Header("Authorization") String authorization, @Path("id") int complaintId);

    /**
     * Get complaints from the user session
     *
     * @param authorization    --- {"include": ["pictures", "user"], "where":{"userId":1}}
     * @return
     */
    @GET("Complaints/me")
    Call<List<Complaint>> getMyComplaints(@Header("Authorization") String authorization);  

    /**
     * Create pictures in the API
     */
    @POST("Pictures")
    Call<Picture> createPictures(@Header("Authorization") String authorization, @Body Picture picture);

    /**The request is a user object with the user's login data
     * The response is an Access Token
     **/
    @POST("Users/login")
    Call<AccessToken> login(@Body User user);

    /**
     * To create a user
     *
     * @param
     * //object
     * @return
     */
    @POST("Users")
    Call<User> signUp(@Body User user);

    @GET("Users/{id}")
    Call<User> getProfile(@Header("Authorization") String authorization, @Path("id") int userId);


    @DELETE("Complaints/{id}")
    Call<ResponseBody> deleteComplaint(@Header("Authorization") String authorization, @Path("id") int id);


}
