package com.iknoortech.mitshubishidemo.activity.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.MainActivity;
import com.iknoortech.mitshubishidemo.connection.ApiInterface;
import com.iknoortech.mitshubishidemo.connection.BaseUrl;
import com.iknoortech.mitshubishidemo.model.createFeedback.CreateFeedbackPojo;
import com.iknoortech.mitshubishidemo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.iknoortech.mitshubishidemo.utils.RealPathUtil.getPath;
import static com.iknoortech.mitshubishidemo.utils.Utils.generateRandomNumber;
import static com.iknoortech.mitshubishidemo.utils.Utils.getUniqueNumber;
import static com.iknoortech.mitshubishidemo.utils.Utils.isConnectionAvailable;
import static com.iknoortech.mitshubishidemo.utils.Utils.setBackButton;

public class CreateFeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp_createFb;
    private EditText ed_description;
    private CheckBox cb_anonymous;
    private LinearLayout layoutImage1, layoutImage2, layoutImage3, layoutImage4;
    private ImageView ivLocation1, ivLocation2, ivLocation3, ivLocation4;
    private int clickedImage;
    private String photofile, picString1 = "", picString2 = "", picString3 = "", picString4 = "";
    private File picfile;
    private String selectedFileExtensionFirst, selectedFileExtensionSecond, selectedFileExtensionThird, selectedFileExtensionForth;
    private boolean isFirstUpload = false, isSecondUpload = false, isThirdUpload = false, isForthUpload = false;
    private String selectedCategory, userId, employeeId, uniqueNumber;
    private ProgressDialog pd;
    private static final String TAG = "CreateFeedbackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feedback);

        layoutImage1 = findViewById(R.id.layout_image_1);
        layoutImage2 = findViewById(R.id.layout_image_2);
        layoutImage3 = findViewById(R.id.layout_image_3);
        layoutImage4 = findViewById(R.id.layout_image_4);

        ivLocation1 = findViewById(R.id.iv_location_image_1);
        ivLocation2 = findViewById(R.id.iv_location_image_2);
        ivLocation3 = findViewById(R.id.iv_location_image_3);
        ivLocation4 = findViewById(R.id.iv_location_image_4);

        ed_description = findViewById(R.id.ed_description);
        cb_anonymous = findViewById(R.id.cb_anonymous);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        setBackButton(this);

        setSpinner();

        ivLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission() && checkWriteStoragePermission() && checkReadStoragePermission()) {
                    selectImage();
                    clickedImage = 1;
                } else {
                    ActivityCompat.requestPermissions(CreateFeedbackActivity.this, new String[]
                            {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        ivLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission() && checkWriteStoragePermission() && checkReadStoragePermission()) {
                    selectImage();
                    clickedImage = 2;
                } else {
                    ActivityCompat.requestPermissions(CreateFeedbackActivity.this, new String[]
                            {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        ivLocation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission() && checkWriteStoragePermission() && checkReadStoragePermission()) {
                    selectImage();
                    clickedImage = 3;
                } else {
                    ActivityCompat.requestPermissions(CreateFeedbackActivity.this, new String[]
                            {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        ivLocation4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission() && checkWriteStoragePermission() && checkReadStoragePermission()) {
                    selectImage();
                    clickedImage = 4;
                } else {
                    ActivityCompat.requestPermissions(CreateFeedbackActivity.this, new String[]
                            {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });
    }

    private void setSpinner() {

        sp_createFb = findViewById(R.id.sp_createFb);
        sp_createFb.setOnItemSelectedListener(CreateFeedbackActivity.this);

        List<String> reason = new ArrayList<>();
        reason.add("Select Category");
        reason.add("Canteen Related");
        reason.add("Corruption (चोरी)");
        reason.add("Plant Related");
        reason.add("Quality");
        reason.add("Safety");
        reason.add("Sexual Harassment");
        reason.add("Others");

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, reason);
        aa.setDropDownViewResource(R.layout.spinner_item);
        sp_createFb.setAdapter(aa);
    }

    public void submitCreateFeedback(View view) {

        if (selectedCategory.equals("")) {
            Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ed_description.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isConnectionAvailable(getApplicationContext())) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Category", selectedCategory);
                jsonObject.put("Description", ed_description.getText().toString());

                if (cb_anonymous.isChecked())
                    jsonObject.put("PostAsAnonymous", "yes");
                else
                    jsonObject.put("PostAsAnonymous", "no");

                employeeId = generateRandomNumber();
                uniqueNumber = employeeId + getUniqueNumber();

                jsonObject.put("ImageOne", picString1);
                jsonObject.put("ImageTwo", picString2);
                jsonObject.put("ImageThree", picString3);
                jsonObject.put("ImageFour", picString4);
                jsonObject.put("Id", "1");
                jsonObject.put("EmployeeId", employeeId);
                jsonObject.put("InsertSessionID", uniqueNumber);

                if (isFirstUpload) {
                    jsonObject.put("ImageOneType", selectedFileExtensionFirst);
                } else {
                    jsonObject.put("ImageOneType", "");
                }

                if (isSecondUpload) {
                    jsonObject.put("ImageTwoType", selectedFileExtensionSecond);
                } else {
                    jsonObject.put("ImageTwoType", "");
                }

                if (isThirdUpload) {
                    jsonObject.put("ImageThreeType", selectedFileExtensionThird);
                } else {
                    jsonObject.put("ImageThreeType", "");
                }

                if (isForthUpload) {
                    jsonObject.put("ImageFourType", selectedFileExtensionForth);
                } else {
                    jsonObject.put("ImageFourType", "");
                }

                String json = jsonObject.toString();
                callService(json);
                Log.d(TAG, "createFeedbackJSON: " + json);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void callService(String json) {

        pd.show();
        ApiInterface apiInterface = BaseUrl.getRetrofitInstance().create(ApiInterface.class);
        apiInterface.createFeedback(json).enqueue(new Callback<CreateFeedbackPojo>() {
            @Override
            public void onResponse(Call<CreateFeedbackPojo> call, Response<CreateFeedbackPojo> response) {
                pd.dismiss();
                try {

                    if (response.body().getStatus_code().equals("1")) {
                        String reqNumber = response.body().getGeneratedIdString();
                        String days = response.body().getTimeDuration();

                        final Dialog alert = new Dialog(CreateFeedbackActivity.this);
                        alert.setContentView(R.layout.dialog_feedback_thankyou);
                        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        alert.setCancelable(false);

                        TextView btSelectFromGallery = alert.findViewById(R.id.tv_msg);
                        Button btClose = alert.findViewById(R.id.bt_close);

                        btSelectFromGallery.setText("Thanks you for sharing your feedback. Your Feedback ID is : " + reqNumber +
                                ". We will look into the same and shall get back within next " + days + " days.");

                        btClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                alert.cancel();
                            }
                        });

                        alert.show();
                    } else {
                        Toast.makeText(CreateFeedbackActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    pd.dismiss();
                    Toast.makeText(CreateFeedbackActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateFeedbackPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(CreateFeedbackActivity.this, "Ooops! something went wrong, Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = sp_createFb.getSelectedItem().toString();

        if (selectedCategory.equals("Select Category")) {
            selectedCategory = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private boolean checkCameraPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkReadStoragePermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkWriteStoragePermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void selectImage() {

        final Dialog alert = new Dialog(CreateFeedbackActivity.this);
        alert.setContentView(R.layout.dialog_add_image);
        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_gallery = alert.findViewById(R.id.btn_gallery);
        Button btn_camera = alert.findViewById(R.id.btn_camera);
        Button btn_cancel = alert.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    File file_image = getDirc();
                    if (!file_image.exists() && !file_image.mkdirs()) {
                        Toast.makeText(getApplicationContext(), "Can't create directory to save image", Toast.LENGTH_LONG).show();
                        return;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String date = simpleDateFormat.format(new Date());
                    photofile = "Mitshubishidemo" + date + ".jpg";

                    File f = new File(getDirc(), photofile);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext().getApplicationContext(),
                            "com.iknoortech.mitshubishidemo.fileprovider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        intent.setClipData(ClipData.newRawUri("", photoURI));
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                    alert.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
                alert.cancel();
            }
        });

        alert.show();
    }

    private File getDirc() {
        File dics = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(dics, "LUMAX");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {

                    FileOutputStream outStream = null;
                    String file_name = getDirc().getAbsolutePath() + "/" + photofile;
                    picfile = new File(file_name);

                    Uri uri = Uri.fromFile(new File(String.valueOf(picfile)));

                    switch (clickedImage) {
                        case 1:
                            isFirstUpload = true;
                            selectedFileExtensionFirst = "jpg";
                            picString1 = Utils.getFileToImageString(picfile);
                            Glide.with(getApplicationContext()).load(uri).into(ivLocation1);
                            break;
                        case 2:
                            isSecondUpload = true;
                            selectedFileExtensionSecond = "jpg";
                            picString2 = Utils.getFileToImageString(picfile);
                            Glide.with(getApplicationContext()).load(uri).into(ivLocation2);
                            break;
                        case 3:
                            isThirdUpload = true;
                            selectedFileExtensionThird = "jpg";
                            picString3 = Utils.getFileToImageString(picfile);
                            Glide.with(getApplicationContext()).load(uri).into(ivLocation3);
                            break;
                        case 4:
                            isForthUpload = true;
                            selectedFileExtensionForth = "jpg";
                            picString4 = Utils.getFileToImageString(picfile);
                            Glide.with(getApplicationContext()).load(uri).into(ivLocation4);
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                try {

                    Uri uri = data.getData();
                    String filePath = getPath(this, uri);
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                    Bitmap bm1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    int width = bm1.getWidth();
                    int hieght = bm1.getHeight();
                    bm1 = Bitmap.createScaledBitmap(bm1, width / 6, hieght / 6, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm1.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                    switch (clickedImage) {
                        case 1:
                            isFirstUpload = true;
                            selectedFileExtensionFirst = filePath.substring(filePath.lastIndexOf("."));
                            picString1 = Utils.getBitmapToImageString(bm);
                            Glide.with(this).load(baos.toByteArray()).asBitmap().into(ivLocation1);
                            break;
                        case 2:
                            isSecondUpload = true;
                            selectedFileExtensionSecond = filePath.substring(filePath.lastIndexOf("."));
                            picString2 = Utils.getBitmapToImageString(bm);
                            Glide.with(this).load(baos.toByteArray()).asBitmap().into(ivLocation2);
                            break;
                        case 3:
                            isThirdUpload = true;
                            selectedFileExtensionThird = filePath.substring(filePath.lastIndexOf("."));
                            picString3 = Utils.getBitmapToImageString(bm);
                            Glide.with(this).load(baos.toByteArray()).asBitmap().into(ivLocation3);
                            break;
                        case 4:
                            isForthUpload = true;
                            selectedFileExtensionForth = filePath.substring(filePath.lastIndexOf("."));
                            picString4 = Utils.getBitmapToImageString(bm);
                            Glide.with(this).load(baos.toByteArray()).asBitmap().into(ivLocation4);
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "File not selected properly, Please select file again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
