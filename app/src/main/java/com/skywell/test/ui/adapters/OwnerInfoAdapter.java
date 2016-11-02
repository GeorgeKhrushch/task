package com.skywell.test.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skywell.test.R;
import com.skywell.test.data.OwnerDataItem;
import com.skywell.test.ui.images.LoadListViewImage;

import java.util.ArrayList;

public class OwnerInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private ArrayList<OwnerDataItem> mOwnerDataItems;

    public OwnerInfoAdapter(ArrayList<OwnerDataItem> ownerDataItems) {
        mOwnerDataItems = ownerDataItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            ImageView imageView = new ImageView(parent.getContext());
            return new HeaderViewHolder(imageView);

        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_owner_data, parent, false);
            return new UserViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OwnerDataItem dataItem = mOwnerDataItems.get(position);
        if(position != 0) {
            StringBuilder builder = new StringBuilder();
            builder
                    .append(dataItem.getName())
                    .append(" : ")
                    .append(dataItem.getValue());

            ((UserViewHolder)holder).textView.setText(builder);
        }else{
            String url = dataItem.getValue();
            LoadListViewImage.validLoad(((HeaderViewHolder)holder).imageView, url);
        }
    }

    @Override
    public int getItemCount() {
        return mOwnerDataItems.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        UserViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewOwnerItem);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            imageView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setMinimumHeight(400);
        }
    }
}
