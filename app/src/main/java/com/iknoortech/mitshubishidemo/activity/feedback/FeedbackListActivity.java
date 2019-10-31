package com.iknoortech.mitshubishidemo.activity.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.adapter.FeedbackListAdapter;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class FeedbackListActivity extends AppCompatActivity {

    private RecyclerView rv_feedback;
    private ProgressDialog pd;
    private TextView tv_noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        rv_feedback = findViewById(R.id.rv_feedback);
        tv_noData = findViewById(R.id.tv_feedbackList);

        setBackButton(this);

        rv_feedback.setLayoutManager(new LinearLayoutManager(this));

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        getData();
    }

    private void getData() {
        pd.show();
        ApiInterface apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);
        apiInterface.getFeedbackList("1").enqueue(new Callback<FeedbackListPojo>() {
            @Override
            public void onResponse(Call<FeedbackListPojo> call, Response<FeedbackListPojo> response) {
                pd.dismiss();
                try{
                    if(response.body().getStatus_code().equals("1")){
                        tv_noData.setVisibility(View.GONE);
                        rv_feedback.setVisibility(View.VISIBLE);
                        rv_feedback.setAdapter(new FeedbackListAdapter(FeedbackListActivity.this, response.body().getData()));
                    }else{
                        tv_noData.setVisibility(View.VISIBLE);
                        rv_feedback.setVisibility(View.GONE);
                        Toast.makeText(FeedbackListActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    tv_noData.setVisibility(View.VISIBLE);
                    rv_feedback.setVisibility(View.GONE);
                    pd.dismiss();
                    Toast.makeText(FeedbackListActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeedbackListPojo> call, Throwable t) {
                tv_noData.setVisibility(View.VISIBLE);
                rv_feedback.setVisibility(View.GONE);
                pd.dismiss();
                Toast.makeText(FeedbackListActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
