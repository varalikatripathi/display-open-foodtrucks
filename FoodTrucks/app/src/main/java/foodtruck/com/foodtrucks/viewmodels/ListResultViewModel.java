package foodtruck.com.foodtrucks.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import foodtruck.com.foodtrucks.entities.ApiResponse;
import foodtruck.com.foodtrucks.repositories.ResultRepository;
import foodtruck.com.foodtrucks.repositories.ResultRepositoryImpl;

/***It has MediatorLiveData mApiResponse which is observed by the UI*/
public class ListResultViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> mApiResponse;
    private ResultRepository mResultRepository;

    public ListResultViewModel() {
        mApiResponse = new MediatorLiveData<>();
        mResultRepository = new ResultRepositoryImpl();
    }

    @NonNull public LiveData<ApiResponse> getApiResponse() {
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadIssues() {
        mApiResponse.addSource(mResultRepository.getResults(),
            apiResponse -> mApiResponse.setValue(apiResponse));
        return mApiResponse;
    }
}
