package com.iknoortech.mitshubishidemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.feedback.FeedbackDetailActivity;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListData;

import java.util.ArrayList;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FeedbackListData> listData;

    public FeedbackListAdapter(Context context, ArrayList<FeedbackListData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final FeedbackListData data = listData.get(position);

        holder.tv_id.setText("ID: "+data.getCategoryGeneratedID());
        holder.tv_reason.setText("Reason: "+data.getCategory());
        holder.tv_desc.setText("Description: "+data.getDescription());
        holder.tv_time.setText(data.getAddeddate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FeedbackDetailActivity.class);
                intent.putExtra("FeedbackId", data.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_id, tv_reason, tv_desc, tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_item_feed_id);
            tv_reason = itemView.findViewById(R.id.tv_item_feed_reason);
            tv_desc = itemView.findViewById(R.id.tv_item_feed_description);
            tv_time = itemView.findViewById(R.id.tv_item_feed_time);

        }
    }
}
