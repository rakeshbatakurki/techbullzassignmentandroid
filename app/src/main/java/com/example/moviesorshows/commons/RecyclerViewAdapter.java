package com.example.moviesorshows.commons;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesorshows.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Activity context;

    private List<MovieOrShow> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvYear;
        public ImageView imgView;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = (TextView) v.findViewById(R.id.tv_show_title);
            tvYear = (TextView) v.findViewById(R.id.tv_show_year);
            imgView = (ImageView) v.findViewById(R.id.iv_movie_image);
        }
    }

    public void add(int position, MovieOrShow item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public RecyclerViewAdapter(List<MovieOrShow> myDataset, Activity context) {
        values = myDataset;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.movie_or_show_holder, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String title = values.get(position).getTitle();
        final String year = values.get(position).getYear();
        final String imgUrl = values.get(position).getPoster();

        holder.tvTitle.setText(title);
        holder.tvYear.setText(year);

        Picasso.get().load(imgUrl).into(holder.imgView);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void addAll(List<MovieOrShow> newDataSet) {
        if(newDataSet != null)
        {
            values.clear();
            values = new ArrayList<>();
            values.addAll(newDataSet);
        }


        notifyDataSetChanged();
    }
}