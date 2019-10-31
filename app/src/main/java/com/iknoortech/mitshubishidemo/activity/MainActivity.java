package com.iknoortech.mitshubishidemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.feedback.FeedbackActivity;
import com.iknoortech.mitshubishidemo.adapter.EndlessPagerAdapter;
import com.iknoortech.mitshubishidemo.adapter.HomePagerAdapter;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.privacy.PrivacyPojo;
import com.iknoortech.mitshubishidemo.utils.AppPrefference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iknoortech.mitshubishidemo.BuildConfig.VERSION_NAME;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    //    private TabLayout tabLayout;
    private HomePagerAdapter homePagerAdapter;
    private TextView morqueeText;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private Toolbar toolbar;

    private boolean doublePressedExit = false;

    private LinearLayout nav_feedback, nav_survey, nav_policy;

    private TextView tv_version;

    private static final String TAG = "MainActivity";

    private String token, android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        tv_version = findViewById(R.id.textView);
        nav_feedback = findViewById(R.id.nav_feedback);
        nav_survey = findViewById(R.id.nav_survey);
        nav_policy = findViewById(R.id.nav_policy);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_version.setText("App Version " + VERSION_NAME);

        viewPager = findViewById(R.id.vp_main);
        morqueeText = findViewById(R.id.MarqueeText);
        morqueeText.setSelected(true);

        final ArrayList<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.mit_one);
        bannerList.add(R.drawable.mit_two);
        bannerList.add(R.drawable.mit_three);
        bannerList.add(R.drawable.mit_four);
        bannerList.add(R.drawable.mit_five);

        morqueeText.setTextColor(Color.BLUE);
        morqueeText.setPaintFlags(morqueeText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        homePagerAdapter = new HomePagerAdapter(MainActivity.this, bannerList);
        EndlessPagerAdapter mAdapater = new EndlessPagerAdapter(homePagerAdapter);
        viewPager.setAdapter(mAdapater);

        morqueeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mitsubishielectric.in/"));
                startActivity(browserIntent);
            }
        });

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();

        nv = (NavigationView) findViewById(R.id.nv);

        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawer(GravityCompat.START);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                    }
                }, 50);

            }
        });

        nav_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawer(GravityCompat.START);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, SurvayActivity.class));
                    }
                }, 50);
            }
        });

        nav_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawer(GravityCompat.START);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, PrivacyActivity.class));
                    }
                }, 50);

            }
        });

        if (!AppPrefference.isUserRegister(this)) {
            getFirebaseToken();
        }

    }

    public void goToFeedback(View view) {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    public void goToSurvey(View view) {
        startActivity(new Intent(this, SurvayActivity.class));
    }

    public void goToPolicy(View view) {
        startActivity(new Intent(this, PrivacyActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            if (doublePressedExit) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                doublePressedExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doublePressedExit = false;
                    }
                }, 2000);
            }
        }
    }

    private void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @SuppressLint("HardwareIds")
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = Objects.requireNonNull(task.getResult()).getToken();
                            android_id = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            registerDevice();
                            Log.d(TAG, "onComplete: " + token);
                        } else {
                            getFirebaseToken();
                        }
                    }
                });
    }

    private void registerDevice() {
        ApiInterface apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);
        apiInterface.registerDevice(android_id, token)
                .enqueue(new Callback<PrivacyPojo>() {
                    @Override
                    public void onResponse(Call<PrivacyPojo> call, Response<PrivacyPojo> response) {
                        if (!response.isSuccessful()) {
                            registerDevice();
                        } else {
                            AppPrefference.setUserRegister(MainActivity.this, true);
                        }
                    }

                    @Override
                    public void onFailure(Call<PrivacyPojo> call, Throwable t) {
                        Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        if (getIntent().getBooleanExtra("isNotification", false)) {
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
