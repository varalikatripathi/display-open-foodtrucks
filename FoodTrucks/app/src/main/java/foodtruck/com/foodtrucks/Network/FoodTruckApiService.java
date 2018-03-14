package foodtruck.com.foodtrucks.Network;

import foodtruck.com.foodtrucks.entities.Result;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodTruckApiService {
    @GET("/resource/bbb8-hzi6.json?$$app_token=CRPThMF4chd8DoD5aumohqDLj")
    Call<List<Result>> getResults();
}
