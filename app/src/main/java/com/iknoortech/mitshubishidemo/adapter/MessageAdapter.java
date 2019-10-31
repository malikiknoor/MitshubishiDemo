package com.iknoortech.mitshubishidemo.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListReply;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<FeedbackListReply> feedbackReplyList;
    String userId;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvReply,tvReplyBy, tvReplyDate;
        LinearLayout messageLayout;

        MyViewHolder(View view) {
            super(view);
            messageLayout = view.findViewById(R.id.notification_layout);
            tvReply = view.findViewById(R.id.tv_message);
            tvReplyBy = view.findViewById(R.id.tv_message_by);
            tvReplyDate = view.findViewById(R.id.tv_date_time);
        }
    }

    public MessageAdapter(List<FeedbackListReply> userDataList, String userId) {
        this.feedbackReplyList = userDataList;
        this.userId = userId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_message_box, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FeedbackListReply detailReports = feedbackReplyList.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if (!detailReports.getReplyFrom().equals(userId)) {
            holder.messageLayout.setBackgroundResource(R.drawable.message_sent_background);
            params.gravity = Gravity.START;
            holder.tvReply.setText(detailReports.getReplyText());
            holder.tvReplyBy.setText("ADMIN:");
        }else {
            holder.messageLayout.setBackgroundResource(R.drawable.message_received_background);
            holder.tvReply.setText(detailReports.getReplyText());
            holder.tvReplyBy.setText("YOU:");
            params.gravity = Gravity.END;
        }
        holder.messageLayout.setLayoutParams(params);

        holder.tvReplyDate.setText(detailReports.getAddeddate());
    }

    @Override
    public int getItemCount() {
        return feedbackReplyList.size();
    }
}
