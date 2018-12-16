package com.example.myinterview.myfragment.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinterview.R;
import com.example.myinterview.common.model.Photo;
import com.example.myinterview.myfragment.activity.PhotoActivity;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private List<Photo> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            photoImage = itemView.findViewById(R.id.photo_image);
            photoName = itemView.findViewById(R.id.photo_name);
        }
    }

    public PhotoAdapter(List<Photo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Photo photo = list.get(position);
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra(PhotoActivity.PHOTO_NAME, photo.getName());
                intent.putExtra(PhotoActivity.PHOTO_IMAGE_ID, photo.getPhotoId());
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = list.get(position);
        holder.photoName.setText(photo.getName());
        Glide.with(context).load(photo.getPhotoId()).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
