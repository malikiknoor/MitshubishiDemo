package com.iknoortech.mitshubishidemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iknoortech.mitshubishidemo.activity.PdfViewActivity;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.model.privacy.PrivacyData;

import java.util.ArrayList;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PrivacyData> arrayList;
    private String url;

    public PolicyAdapter(Context context, ArrayList<PrivacyData> arrayList, String url) {
        this.context = context;
        this.arrayList = arrayList;
        this.url = url;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_privacy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(arrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfViewActivity.class);
                intent.putExtra("pdfTitle", arrayList.get(position).getTitle());
                intent.putExtra("pdfUrl", url+arrayList.get(position).getPdf());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView128);

        }
    }
}
