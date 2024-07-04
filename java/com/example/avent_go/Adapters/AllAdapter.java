package com.example.avent_go.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.avent_go.Activities.DetailActivity;
import com.example.avent_go.Domains.PopularDomain;
import com.example.avent_go.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder>{
    ArrayList<PopularDomain> eventsList;

    public AllAdapter(ArrayList<PopularDomain> items) {
        this.eventsList = items;
    }

    public  void setFilteredList(ArrayList<PopularDomain> filteredList) {
        this.eventsList = filteredList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_all, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull AllAdapter.ViewHolder holder, int position) {
        holder.titleTxt.setText(eventsList.get(position).getTitle());
        holder.locationTxt.setText(eventsList.get(position).getLocation());
        holder.scoreTxt.setText("" +eventsList.get(position).getScore());

        int drawableResId = holder.itemView.getResources().getIdentifier(eventsList.get(position).getPic()
                , "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResId)
                .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                .into(holder.pic);
        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object",eventsList.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt,locationTxt,scoreTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleT);
            locationTxt = itemView.findViewById(R.id.locationT);
            scoreTxt = itemView.findViewById(R.id.scoreT);
            pic = itemView.findViewById(R.id.picI);

        }
    }
}
