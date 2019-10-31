package com.iknoortech.mitshubishidemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.iknoortech.mitshubishidemo.R;

import java.util.ArrayList;

public class HomePagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Integer> bannerList;


    public HomePagerAdapter(Context context, ArrayList<Integer> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View mImageLayout = inflater.inflate(R.layout.item_home_slider, container, false);
        ImageView imageView = mImageLayout.findViewById(R.id.img_banner);
        imageView.setImageResource(bannerList.get(position));
        container.addView(mImageLayout, 0);
        return mImageLayout;
    }
}
