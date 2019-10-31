package com.iknoortech.mitshubishidemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.iknoortech.mitshubishidemo.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class PdfViewActivity extends AppCompatActivity {

    private TextView header;
    private PDFView pdfView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        setBackButton(this);
        header = findViewById(R.id.tv_header);
        pdfView = findViewById(R.id.pdfView);
        pb = findViewById(R.id.pb);
        header.setText(getIntent().getStringExtra("pdfTitle"));
        new DownloadingTAsk().execute(getIntent().getStringExtra("pdfUrl"));

    }

    class DownloadingTAsk extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection mHttpURLConnection = (HttpURLConnection) url.openConnection();
                if (mHttpURLConnection.getResponseCode() == 200) {
                    try {
                        inputStream = new BufferedInputStream(mHttpURLConnection.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream == null) {
                pb.setVisibility(View.GONE);
                Toast.makeText(PdfViewActivity.this, "No Preview Available", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pb.setVisibility(View.GONE);
                    }
                }).load();
            }
        }
    }
}
