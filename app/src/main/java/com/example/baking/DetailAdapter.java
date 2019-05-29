package com.example.baking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baking.models.BakingModel;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private Context dContext;
    private ArrayList<BakingModel.Steps> dStepList;


    public DetailAdapter(Context context, ArrayList<BakingModel.Steps> stepList) {
        dContext = context;
        dStepList = stepList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(dContext).inflate(R.layout.step_item, parent, false);
        return new DetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder,  int position) {
        BakingModel.Steps currentItem = dStepList.get(position);

       String step = currentItem.getDescription();
        //List steps = currentItem.getStepsList();

        detailViewHolder.dTextViewDescrition.setText(step);
        //detailViewHolder.dTextStep.List(steps);
    }

    @Override
    public int getItemCount() {
        return dStepList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder{
        //public TextView dTextIngredients;
        public TextView dTextViewDescrition;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            //dTextIngredients = itemView.findViewById(R.id.txtIngredients);
            dTextViewDescrition = itemView.findViewById(R.id.txtStep);
        }
    }
}
