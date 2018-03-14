package foodtruck.com.foodtrucks.repositories;

import android.arch.lifecycle.LiveData;
import foodtruck.com.foodtrucks.entities.ApiResponse;
/**  Repositories are used to abstract communication of rest of the code to the Data sources (such as Database or API calls)*/
public interface ResultRepository {
    LiveData<ApiResponse> getResults();
}
