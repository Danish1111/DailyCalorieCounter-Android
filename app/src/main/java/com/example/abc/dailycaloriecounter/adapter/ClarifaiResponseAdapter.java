package com.example.abc.dailycaloriecounter.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.abc.dailycaloriecounter.R;
import com.example.abc.dailycaloriecounter.model.ClarifaiResponse;
import com.example.abc.dailycaloriecounter.views.TextViewPlus;

import java.util.List;

/**
 * Created by DANISH FAROOQ on 21/3/2018.
 */


public class ClarifaiResponseAdapter extends RecyclerView.Adapter<ClarifaiResponseAdapter.MyHolderClass> {
    List<ClarifaiResponse> list;

    public ClarifaiResponseAdapter(List<ClarifaiResponse> list) {
        this.list = list;
    }




    class MyHolderClass extends RecyclerView.ViewHolder {
        TextViewPlus tvFoodName, tvPercentage;

        MyHolderClass(View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
        }

    }

    @Override
    public MyHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calarifai_response_layout, parent, false);
        // Return a new holder instance
        return new MyHolderClass(contactView);
    }

    @Override
    public void onBindViewHolder(final MyHolderClass holder, int position) {
        ClarifaiResponse clarifaiResponse = list.get(position);
        holder.tvFoodName.setText(clarifaiResponse.getName());
        holder.tvPercentage.setText(clarifaiResponse.getValue()+"");

    }

    public void setNewList(List<ClarifaiResponse> clarifaiResponseList) {
        list.clear();
        list.addAll(clarifaiResponseList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }
}
