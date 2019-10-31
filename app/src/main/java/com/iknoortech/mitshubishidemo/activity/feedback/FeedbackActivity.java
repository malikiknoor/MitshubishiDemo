package com.iknoortech.mitshubishidemo.activity.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.feedback.CreateFeedbackActivity;

import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        setBackButton(this);
    }

    public void goToCreateFeedback(View view) {
        startActivity(new Intent(this, CreateFeedbackActivity.class));
    }

    public void goToFeedbackList(View view) {
        startActivity(new Intent(this, FeedbackListActivity.class));
    }
}
