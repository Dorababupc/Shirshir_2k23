package com.nitmeghalaya.shishir_2k23.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nitmeghalaya.shishir_2k23.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    // creating a variable for
    // context and array list.
    private Context context;
    private List<SliderDataModel> mSliderItems = new ArrayList<>();

    //constructor for our database
    public SliderAdapter(Context context, List<SliderDataModel> mSliderItems){
        this.context = context;
        this.mSliderItems = mSliderItems;
    }
    @Override
    public SliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new SliderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapter.SliderAdapterVH viewHolder, int position) {
        final SliderDataModel sliderDataItem = mSliderItems.get(position);
        Picasso.get().load(sliderDataItem.getImgUrl()).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class SliderAdapterVH extends  SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageView;
        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            this.imageView = imageView;
        }
    }
}
