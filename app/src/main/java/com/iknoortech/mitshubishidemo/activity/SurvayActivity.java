package com.iknoortech.mitshubishidemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.survey.SurveyData;
import com.iknoortech.mitshubishidemo.model.survey.SurveyPojo;
import com.iknoortech.mitshubishidemo.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurvayActivity extends AppCompatActivity {

    private TextView tv_noData, tv_surveyQues;
    private RadioGroup rg_main;
    private RadioButton rb_one, rb_two, rb_three, rb_four;
    private LinearLayout ll_survey;
    private String selectedAnswer = "", question_id = "";
    private ApiInterface apiInterface;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survay);
        Utils.setBackButton(this);

        tv_noData = findViewById(R.id.tv_noSurvey);
        ll_survey = findViewById(R.id.ll_survey);
        tv_surveyQues = findViewById(R.id.tv_surveyQues);
        rg_main = findViewById(R.id.rg_main);
        rb_one = findViewById(R.id.rb_one);
        rb_two = findViewById(R.id.rb_two);
        rb_three = findViewById(R.id.rb_three);
        rb_four = findViewById(R.id.rb_four);

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    selectedAnswer = rb.getText().toString();
                }
            }
        });

        apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCanceledOnTouchOutside(false);
        getSurvey();
    }

    private void getSurvey() {
        pd.show();
        apiInterface.getSurveryData("1")
                .enqueue(new Callback<SurveyPojo>() {
                    @Override
                    public void onResponse(Call<SurveyPojo> call, Response<SurveyPojo> response) {

                        pd.dismiss();
                        try{
                            tv_noData.setVisibility(View.GONE);
                            ll_survey.setVisibility(View.VISIBLE);
                            question_id = response.body().getSurvey().getQuestion_id();
                            tv_surveyQues.setText(response.body().getSurvey().getQuestion());
                            rb_one.setText(response.body().getSurvey().getOption1());
                            rb_two.setText(response.body().getSurvey().getOption2());
                            rb_three.setText(response.body().getSurvey().getOption3());
                            rb_four.setText(response.body().getSurvey().getOption4());

                        }catch (Exception e){
                            tv_noData.setVisibility(View.VISIBLE);
                            ll_survey.setVisibility(View.GONE);
                            Toast.makeText(SurvayActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SurveyPojo> call, Throwable t) {
                        tv_noData.setVisibility(View.VISIBLE);
                        ll_survey.setVisibility(View.GONE);
                        pd.dismiss();
                        Toast.makeText(SurvayActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void submitSurvey(View view) {
        if (selectedAnswer.equals("")) {
            Toast.makeText(this, "Please select your answer", Toast.LENGTH_SHORT).show();
        } else {
            if (Utils.isConnectionAvailable(this)) {
                submitSurveyAnswer();
            } else {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitSurveyAnswer() {
        pd.show();
        apiInterface.submitSurvey("1", question_id, selectedAnswer)
                .enqueue(new Callback<SurveyPojo>() {
                    @Override
                    public void onResponse(Call<SurveyPojo> call, Response<SurveyPojo> response) {
                        pd.dismiss();
                        try{

                            if(response.body().getStatus_code().equals("1")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SurvayActivity.this);
                                builder.setCancelable(false);
                                builder.setTitle("Survey Submit");
                                builder.setMessage("Thank you for submit survey.");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }).show();
                            }else{
                                Toast.makeText(SurvayActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(SurvayActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SurveyPojo> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(SurvayActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
