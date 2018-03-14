package foodtruck.com.foodtrucks.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import foodtruck.com.foodtrucks.Network.FoodTruckApiService;
import foodtruck.com.foodtrucks.entities.ApiResponse;
import foodtruck.com.foodtrucks.entities.Result;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** This class returns Livedata*/
/**  Create a MutableLiveData from the data obtained from api.
  MutableLiveData is subclass of LiveData that has setValue() method that can be used to modify the value it holds.*/

public class ResultRepositoryImpl implements ResultRepository {

    public static final String BASE_URL = "https://data.sfgov.org/";
    private FoodTruckApiService mApiService;

    public ResultRepositoryImpl() {
        Retrofit retrofit =
            new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApiService = retrofit.create(FoodTruckApiService.class);
    }

 @Override public LiveData<ApiResponse> getResults() {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<List<Result>> call = mApiService.getResults();
        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override public void onFailure(Call<List<Result>> call, Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }

}

