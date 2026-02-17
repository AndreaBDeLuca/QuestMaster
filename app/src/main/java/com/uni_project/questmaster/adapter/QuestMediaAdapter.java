package com.uni_project.questmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uni_project.questmaster.R;
import com.uni_project.questmaster.model.QuestLocation;

import java.util.List;
import java.util.Locale;

public class QuestMediaAdapter extends RecyclerView.Adapter<QuestMediaAdapter.ImageViewHolder> {

    private final Context context;
    private final List<Uri> mediaUris;
    private final boolean isEditable;
    private final OnImageDeleteListener onImageDeleteListener;
    private QuestLocation mapLocation;
    private final String apiKey;


    public interface OnImageDeleteListener {
        void onImageDelete(int position);
    }

    public QuestMediaAdapter(Context context, List<Uri> mediaUris, OnImageDeleteListener onImageDeleteListener, boolean isEditable, QuestLocation location, String apiKey) {
        this.context = context;
        this.mediaUris = mediaUris;
        this.onImageDeleteListener = onImageDeleteListener;
        this.isEditable = isEditable;
        this.mapLocation = location;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (mapLocation != null && position == 0) {
            String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" +
                    mapLocation.getLatitude() + "," + mapLocation.getLongitude() +
                    "&zoom=15&size=600x300&maptype=roadmap&markers=color:red%7C" +
                    mapLocation.getLatitude() + "," + mapLocation.getLongitude() +
                    "&key=" + apiKey;
            Glide.with(context).load(mapUrl).into(holder.imageView);

            holder.itemView.setOnClickListener(v -> {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mapLocation.getLatitude(), mapLocation.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            });
        } else {
            int imagePosition = (mapLocation != null) ? position - 1 : position;
            Uri mediaUri = mediaUris.get(imagePosition);
            Glide.with(context).load(mediaUri).into(holder.imageView);
        }



        if (isEditable) {
            holder.buttonDeleteImage.setVisibility(View.VISIBLE);
            holder.buttonDeleteImage.setOnClickListener(v -> {
                if (onImageDeleteListener != null) {
                    onImageDeleteListener.onImageDelete(position);
                }
            });
        } else {
            holder.buttonDeleteImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        int count = mediaUris.size();
        if (mapLocation != null) {
            count++;
        }
        return count;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton buttonDeleteImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            buttonDeleteImage = itemView.findViewById(R.id.button_delete_image);
        }
    }
}
