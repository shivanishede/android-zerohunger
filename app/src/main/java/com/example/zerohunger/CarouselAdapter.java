package com.example.zerohunger;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private Context context;
    private List<Integer> imageResources;
    private List<String> hindiCaptions;
    private List<String> englishCaptions;

    public CarouselAdapter(Context context, List<Integer> imageResources, List<String> hindiCaptions, List<String> englishCaptions) {
        this.context = context;
        this.imageResources = imageResources;
        this.hindiCaptions = hindiCaptions;
        this.englishCaptions = englishCaptions;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResources.get(position));
        holder.hindiCaption.setText(hindiCaptions.get(position));
        holder.englishCaption.setText(englishCaptions.get(position));
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView hindiCaption;
        TextView englishCaption;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image);
            hindiCaption = itemView.findViewById(R.id.carousel_caption_hindi);
            englishCaption = itemView.findViewById(R.id.carousel_caption_english);
        }
    }
}
