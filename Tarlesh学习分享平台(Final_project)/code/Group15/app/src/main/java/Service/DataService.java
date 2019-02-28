package Service;

import APIData.API_Response_user;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {

    @POST("/api/detail/getuser")
    Call<API_Response_user> getUserDetail(@Header("x-access-token") String token);

}