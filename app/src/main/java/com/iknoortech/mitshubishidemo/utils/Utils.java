package com.iknoortech.mitshubishidemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.iknoortech.mitshubishidemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {

    public static void setBackButton(final Activity activity){
        ImageView back = activity.findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String getFileToImageString(File path) {

        Bitmap bm = BitmapFactory.decodeFile(path.toString());
        int width = bm.getWidth();
        int hieght = bm.getHeight();
        bm = Bitmap.createScaledBitmap(bm, width / 6, hieght / 6, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArray = baos.toByteArray();

        if (byteArray != null) {
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }

    public static String getBitmapToImageString(Bitmap bm) {

//        Bitmap bm = BitmapFactory.decodeFile(path.toString());
        int width = bm.getWidth();
        int hieght = bm.getHeight();
        bm = Bitmap.createScaledBitmap(bm, width / 6, hieght / 6, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArray = baos.toByteArray();

        if (byteArray != null) {
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }

    public static String encodeFileToBase64Binary(File file) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public static String getUniqueNumber() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }

    public static String generateRandomNumber() {
        Random r = new Random();
        return String.valueOf(r.nextInt(80 - 65) + 65);
    }

    public static void closeKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
