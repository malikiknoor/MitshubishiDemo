package com.iknoortech.mitshubishidemo.activity.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.adapter.MessageAdapter;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListData;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListPojo;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListReply;
import com.iknoortech.mitshubishidemo.utils.AppPrefference;
import com.iknoortech.mitshubishidemo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class FeedbackDetailActivity extends AppCompatActivity {

    private ArrayList<FeedbackListReply> feedbackReply;
    private Button btn_sendMessage;
    private String feedbackId, userId, defeedbackId;
    private TextView tvReqId, tvSubmitDate, tvCategory, tvDescription, tvNoReply, tv_fbClose;
    private LinearLayout layoutImage1, layoutImage2, layoutImage3, layoutImage4, ll_fb_send;
    private ImageView ivLocation1, ivLocation2, ivLocation3, ivLocation4;
    private EditText edMessage;
    private ProgressDialog pd;
    private static final String TAG = "FeedbackDetailActivity";

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);

        feedbackReply = new ArrayList<>();

        tvReqId = findViewById(R.id.tvFeedReq);
        tvSubmitDate = findViewById(R.id.tv_submit_date);
        tvCategory = findViewById(R.id.tv_reason);
        tvDescription = findViewById(R.id.tv_description);
        tvNoReply = findViewById(R.id.tv_no_reply);
        tv_fbClose = findViewById(R.id.tv_fbClose);

        layoutImage1 = findViewById(R.id.layout_image_1);
        layoutImage2 = findViewById(R.id.layout_image_2);
        layoutImage3 = findViewById(R.id.layout_image_3);
        layoutImage4 = findViewById(R.id.layout_image_4);

        ivLocation1 = findViewById(R.id.iv_location_image_1);
        ivLocation2 = findViewById(R.id.iv_location_image_2);
        ivLocation3 = findViewById(R.id.iv_location_image_3);
        ivLocation4 = findViewById(R.id.iv_location_image_4);
        ll_fb_send = findViewById(R.id.ll_fb_send);

        defeedbackId = getIntent().getStringExtra("FeedbackId");

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                defeedbackId = getIntent().getExtras().getString("body");
                Log.d(TAG, "Key: " + key + " Value: " + defeedbackId);
            }
        }

        edMessage = findViewById(R.id.ed_message);
        btn_sendMessage = findViewById(R.id.btn_sendMessage);

        setBackButton(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);

        userId = "1";

        getFeedbackData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void getFeedbackData() {
        pd.show();
        Log.d(TAG, "getFeedbackData: "+defeedbackId);
        ApiInterface apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);
        apiInterface.getFeedbackDetail(defeedbackId).enqueue(new Callback<FeedbackListPojo>() {
            @Override
            public void onResponse(Call<FeedbackListPojo> call, Response<FeedbackListPojo> response) {
                pd.dismiss();
                try {
                    if (response.body().getStatus_code().equals("1")) {
                        if (response.body().getData().size() == 0) {
                            Toast.makeText(FeedbackDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            setFeedbackData(response.body().getData().get(0), response.body().getUrl());
                        }
                    } else {
                        Toast.makeText(FeedbackDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(FeedbackDetailActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<FeedbackListPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(FeedbackDetailActivity.this, "Something went wrong from server", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setFeedbackData(FeedbackListData feedbackData, String imageBaseURL) {
        feedbackId = feedbackData.getId();
        feedbackReply.addAll(feedbackData.getReply());

        tvReqId.setText("ID: " + feedbackData.getCategoryGeneratedID());
        tvSubmitDate.setText(feedbackData.getAddeddate());
        tvCategory.setText("Reason: " + feedbackData.getCategory());
        tvDescription.setText("Description: " + feedbackData.getDescription());

        Glide.with(getApplicationContext())
                .load(imageBaseURL + feedbackData.getImageOne()).into(ivLocation1);
        Glide.with(getApplicationContext())
                .load(imageBaseURL + feedbackData.getImageTwo()).into(ivLocation2);
        Glide.with(getApplicationContext())
                .load(imageBaseURL + feedbackData.getImageThree()).into(ivLocation3);
        Glide.with(getApplicationContext())
                .load(imageBaseURL + feedbackData.getImageFour()).into(ivLocation4);

        if (feedbackReply.size() != 0) {
//            setAdapter(feedbackReply);
            setAdapterMessage(feedbackReply, userId);
            tvNoReply.setVisibility(View.GONE);
        } else {
            tvNoReply.setVisibility(View.VISIBLE);
        }

        if (feedbackData.getStatus().equals("1")) {
            ll_fb_send.setVisibility(View.GONE);
            tv_fbClose.setVisibility(View.VISIBLE);
        } else {
            ll_fb_send.setVisibility(View.VISIBLE);
            tv_fbClose.setVisibility(View.GONE);
        }

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edMessage.getText().toString().trim();
                if (!messageText.equals("")) {
                    Utils.closeKeyboard(FeedbackDetailActivity.this);
                    edMessage.setText("");

//                    FeedbackListReply message = new FeedbackListReply();
//                    message.setReplyText(messageText);
//                    message.setAddeddate("2018-10-29 14:20");
//                    message.setReplyFrom(userId);
//                    feedbackReply.add(message);
//
//                    setAdapterMessage(feedbackReply, userId);
//                    tvNoReply.setVisibility(View.GONE);
//                    viewDataAdapter.notifyDataSetChanged();

                    if (Utils.isConnectionAvailable(getApplicationContext())) {
                        pd.show();
                        serviceSendMessage(feedbackId, messageText, userId);
                    } else {
                        Toast.makeText(FeedbackDetailActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FeedbackDetailActivity.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void serviceSendMessage(String feedbackId, final String messageText, String addedBy) {
        pd.show();
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("FeedbackID", feedbackId);
            jsonObject1.put("ReplyText", messageText);
            jsonObject1.put("ReplyFrom", addedBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject1.toString();

        apiInterface.sendFeedbackMessage(json).enqueue(new Callback<FeedbackListPojo>() {
            @Override
            public void onResponse(Call<FeedbackListPojo> call, Response<FeedbackListPojo> response) {
                pd.dismiss();
                try {
                    if (response.body().getStatus_code().equals("1")) {
                        Toast.makeText(FeedbackDetailActivity.this, "Message send successfully", Toast.LENGTH_SHORT).show();
                        FeedbackListReply message = new FeedbackListReply();
                        message.setReplyText(messageText);
                        message.setAddeddate("2018-10-29 14:20");
                        message.setReplyFrom(userId);
                        feedbackReply.add(message);

                        setAdapterMessage(feedbackReply, userId);
                        tvNoReply.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(FeedbackDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    pd.dismiss();
                    Toast.makeText(FeedbackDetailActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeedbackListPojo> call, Throwable t) {
                pd.cancel();
                Toast.makeText(FeedbackDetailActivity.this, "Something wrong from server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapterMessage(final List<FeedbackListReply> feedbackReplyList, String userId) {

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MessageAdapter viewDataAdapter = new MessageAdapter(feedbackReplyList, userId);
        recyclerView.setAdapter(viewDataAdapter);
    }
}
