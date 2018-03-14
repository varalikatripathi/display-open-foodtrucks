package foodtruck.com.foodtrucks.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import foodtruck.com.foodtrucks.R;
import foodtruck.com.foodtrucks.adapter.FoodTruckAdapter;
import foodtruck.com.foodtrucks.entities.Result;
import foodtruck.com.foodtrucks.viewmodels.ListResultViewModel;
import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListResultViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FoodTruckAdapter mFoodTruckAdapter;
    private ProgressBar mProgressBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(ListResultViewModel.class);
        setUpView();
        // Handle changes emitted by LiveData
        mViewModel.loadIssues();
        setProgress(true);
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                handleResponse(apiResponse.getResponseList());
            } else {
                handleError(apiResponse.getError());
            }
        });
    }

    private void setUpView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mProgressBar = findViewById(R.id.progress);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration =
            new DividerItemDecoration(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mFoodTruckAdapter = new FoodTruckAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mFoodTruckAdapter);
    }

    private void handleResponse(List<Result> results) {
        setProgress(false);
        if (results != null && results.size() > 0) {
            try {
                mFoodTruckAdapter.addResults(results);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No result found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable error) {
        setProgress(false);
        Toast.makeText(this, "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

    public void setProgress(boolean flag) {
        if (flag) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
