package foodtruck.com.foodtrucks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import foodtruck.com.foodtrucks.R;
import foodtruck.com.foodtrucks.entities.Result;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;

public class FoodTruckAdapter extends RecyclerView.Adapter<FoodTruckAdapter.AdapterViewHolder> {

    private final LayoutInflater inflator;
    private List<Result> resultList;

    public FoodTruckAdapter(LayoutInflater inflator) {
        this.inflator = inflator;
        resultList = new ArrayList<>();
    }

    @Override
    public FoodTruckAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterViewHolder(inflator.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(FoodTruckAdapter.AdapterViewHolder holder, int position) {

        holder.mFoodTruckName.setText(resultList.get(position).getApplicant());
        holder.mAddress.setText(resultList.get(position).getLocation());
    }

    @Override public int getItemCount() {
        if (resultList == null) return 0;
        return resultList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mFoodTruckName;
        TextView mAddress;

        public AdapterViewHolder(View view) {
            super(view);
            mFoodTruckName = view.findViewById(R.id.truck_name);
            mAddress = view.findViewById(R.id.address);
        }
    }

    public void addResults(List<Result> results) throws ParseException {
        resultList.addAll(results);
        notifyDataSetChanged();
    }
}

