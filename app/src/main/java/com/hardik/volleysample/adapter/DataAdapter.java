package com.hardik.volleysample.adapter;

import static com.hardik.volleysample.utils.Constants.RANDOM_IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hardik.volleysample.R;
import com.hardik.volleysample.models.DataResponseItem;

import java.util.List;
import java.util.Random;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<DataResponseItem> photos;
    public DataAdapter(List<DataResponseItem> photos) {
        this.photos = photos;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<DataResponseItem> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataResponseItem data = photos.get(position);

        holder.textView.setText(data.getTitle());
        holder.textView2.setText(data.getBody());

        // Generate number of url parameters
        Random random = new Random();
        int param = random.nextInt(1000000000)+1;

        Glide.with(context)
                .load(Uri.parse(RANDOM_IMAGE_URL+param))
//                .load(Uri.parse("https://picsum.photos/200/300?random="+param))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
