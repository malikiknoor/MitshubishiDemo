package com.iknoortech.mitshubishidemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.adapter.PolicyAdapter;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.privacy.PrivacyPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class PrivacyActivity extends AppCompatActivity {

    private RecyclerView rv_policy;
    private ProgressDialog pd;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        setBackButton(this);

        rv_policy = findViewById(R.id.rv_policy);
        noData = findViewById(R.id.tv_policyNoData);

        rv_policy.setLayoutManager(new LinearLayoutManager(this));

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        ApiInterface apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);
        apiInterface.getPolicy("1")
                .enqueue(new Callback<PrivacyPojo>() {
                    @Override
                    public void onResponse(Call<PrivacyPojo> call, Response<PrivacyPojo> response) {
                        pd.dismiss();
                        try {
                            if (response.body().getStatus_code().equals("1")) {
                                if (response.body().getData().size() != 0) {
                                    rv_policy.setAdapter(new PolicyAdapter(PrivacyActivity.this, response.body().getData(), response.body().getUrl()));
                                } else {
                                    rv_policy.setVisibility(View.GONE);
                                    noData.setVisibility(View.VISIBLE);
                                }
                            } else {
                                rv_policy.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            rv_policy.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                            Toast.makeText(PrivacyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PrivacyPojo> call, Throwable t) {
                        rv_policy.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                        pd.dismiss();
                        Toast.makeText(PrivacyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
