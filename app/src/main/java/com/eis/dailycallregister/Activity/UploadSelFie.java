package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class UploadSelFie extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ViewDialog progressDialoge;
    private ProgressBar progressBar;
    private String filePath = null, fileUri = null;
    private TextView txtPercentage;
    private AppCompatImageView imgPreview;
    LinearLayout lin1;
    //private VideoView vidPreview;
    private Button btnUpload;
    long totalSize = 0;
    public String cntcd="",chemistname="",doctorname="",keycontactper="", phonenumber="",doccntcd="",
            flag="",menu="",sttype,add1,add2,add3,city,state,pincode,chmDetUpdtReq,camMode,noVst,cls,
            selTcpJson,isPatchUpdtd;
    public boolean isimgcropped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploade_selfie);

        menu = getIntent().getStringExtra("menu");
        if(menu !=null && menu.equalsIgnoreCase("chemAddEdit"))
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Capture Image</font>"));
        else
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Upload Selfie</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(UploadSelFie.this);
        cntcd = getIntent().getStringExtra("cntcd");
        chemistname = getIntent().getStringExtra("chemistname");
        doctorname = getIntent().getStringExtra("doctorname");
        keycontactper = getIntent().getStringExtra("keycontactper");
        phonenumber = getIntent().getStringExtra("phonenumber");
        doccntcd = getIntent().getStringExtra("doccntcd");
        filePath = getIntent().getStringExtra("filePath");
        fileUri = getIntent().getStringExtra("fileUri");
        isimgcropped = getIntent().getExtras().getBoolean("isimgcropped");
        flag = getIntent().getStringExtra("flag");
        menu = getIntent().getStringExtra("menu");
        sttype = getIntent().getStringExtra("sttype");
        chmDetUpdtReq = getIntent().getStringExtra("chmDetUpdtReq");
        camMode = getIntent().getStringExtra("camMode");
        selTcpJson = getIntent().getStringExtra("selTcpJson");
        isPatchUpdtd = getIntent().getStringExtra("isPatchUpdtd");

        if(menu!=null && menu.equalsIgnoreCase("chemAddEdit")
                && chmDetUpdtReq!=null && chmDetUpdtReq.equalsIgnoreCase("Y")){
            add1 = getIntent().getStringExtra("add1");
            add2 = getIntent().getStringExtra("add2");
            add3 = getIntent().getStringExtra("add3");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            pincode = getIntent().getStringExtra("pincode");
            noVst = getIntent().getStringExtra("noVst");
            cls = getIntent().getStringExtra("cls");
        }

        txtPercentage = findViewById(R.id.txtPercentage);
        btnUpload = findViewById(R.id.btnUploadselfie);
        progressBar = findViewById(R.id.progressBar);
        imgPreview = findViewById(R.id.imgPreview);
        lin1 = findViewById(R.id.lin1);
        //vidPreview = findViewById(R.id.videoPreview);

        /*// Changing action bar background color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor(getResources().getString(
                        R.color.action_bar))));*/

        // Receiving the data from previous activity
        Intent i = getIntent();

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");
        fileUri = i.getStringExtra("fileUri");
        isimgcropped = i.getExtras().getBoolean("isimgcropped");
        //Log.d("isimgcropped : ",isimgcropped+"");
        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnUpload.setEnabled(false);
                // uploading the file to server

                new UploadFileToServer().execute();
            }
        });

    }

    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        //if (isImage) {

        //vidPreview.setVisibility(View.GONE);
        // bimatp factory


        // down sizing image as it throws OutOfMemory Exception for larger
        // images
        //options.inSampleSize = 8;
        /*imgPreview.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);*/
        /*} else {
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }*/
        imgPreview.setVisibility(View.VISIBLE);
        File file = new File(filePath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        if (file.exists() && fileSizeInKB > 5) {
            Glide.with(this).load(filePath).into(imgPreview);
        } else {
            filePath = fileUri;
            isimgcropped = false;
            Glide.with(this).load(fileUri).into(imgPreview);
            Toast.makeText(UploadSelFie.this, "Image not saved !", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressDialoge.show();
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = null;
            if(menu!=null && menu.equalsIgnoreCase("chemAddEdit")) //prithvi 08/05/2020
                httppost = new HttpPost(RetrofitClient.BASE_URL + "uploadChemVstCard.php");
            else
                httppost = new HttpPost(RetrofitClient.BASE_URL + "uploadSelfie.php");

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date()); //prithvi 14092019

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                //Log.d("file path", filePath);
                File sourceFile = new File(filePath);
                if (!isimgcropped) {
                    File compressedImageFile = new Compressor(UploadSelFie.this).compressToFile(sourceFile);
                    // Adding file data to http body
                    entity.addPart("image", new FileBody(compressedImageFile));
                } else {
                    entity.addPart("image", new FileBody(sourceFile));
                }
                // Extra parameters if you want to pass to server
                entity.addPart("netid", new StringBody(Global.netid));
                entity.addPart("cntcd", new StringBody(cntcd!=null ? cntcd : ""));
                entity.addPart("DBPrefix", new StringBody(Global.dbprefix));
                entity.addPart("timeStamp", new StringBody(timeStamp)); //prithvi
                entity.addPart("chemistname", new StringBody(chemistname)); //aniket
                entity.addPart("keycontactper", new StringBody(keycontactper)); //aniket
                entity.addPart("phonenumber", new StringBody(phonenumber)); //aniket
                entity.addPart("doccntcd", new StringBody(doccntcd!=null ? doccntcd : "")); //aniket
                entity.addPart("ecode", new StringBody(Global.ecode)); //aniket
                entity.addPart("flag", new StringBody(flag)); //aniket
                //prithvi08/05/2020
                entity.addPart("menu", new StringBody(menu!=null ? menu : ""));
                entity.addPart("sttype", new StringBody(sttype !=null ? sttype : ""));
                if(menu!=null && menu.equalsIgnoreCase("chemAddEdit")){
                    entity.addPart("add1", new StringBody(add1!=null ? add1 : ""));
                    entity.addPart("add2", new StringBody(add2!=null ? add2 : ""));
                    entity.addPart("add3", new StringBody(add3!=null ? add3 : ""));
                    entity.addPart("city", new StringBody(city!=null ? city : ""));
                    entity.addPart("state",new StringBody(state!=null ? state : ""));
                    entity.addPart("pincode",new StringBody(pincode!=null ? pincode : ""));
                    entity.addPart("chmDetUpdtReq",new StringBody(chmDetUpdtReq!=null ? chmDetUpdtReq : ""));
                    entity.addPart("camMode",new StringBody(camMode!=null ? camMode : ""));
                    entity.addPart("noVst",new StringBody(noVst!=null ? noVst : ""));
                    entity.addPart("cls",new StringBody(cls!=null ? cls : ""));
                    entity.addPart("isPatchUpdtd",new StringBody(isPatchUpdtd!=null ? isPatchUpdtd : ""));
                    if(isPatchUpdtd!=null && isPatchUpdtd.equalsIgnoreCase("Y"))
                        entity.addPart("selTcpJson",new StringBody(selTcpJson!=null ? selTcpJson : ""));
                    else
                        entity.addPart("selTcpJson",new StringBody(""));
                }

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
          //  Log.d("result ", "Response from server: " + result);
            progressDialoge.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (!jsonObject.getBoolean("error")) {
                    // showing the server response in an alert dialog
                    showAlert(jsonObject.getString("errormsg"));

                } else {
                    Snackbar snackbar = Snackbar.make(lin1, jsonObject.getString("errormsg"), Snackbar.LENGTH_LONG);
                    snackbar.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            finish();
                            UploadSelFie.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                        }
                    }, 1200);



                }

            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar snackbar = Snackbar.make(lin1, e.getMessage(), Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
            //super.onPostExecute(result);
        }
    }
    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        final Dialog dialog = new Dialog(UploadSelFie.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dilogue);
        MaterialButton button = dialog.findViewById(R.id.btnsucces);
        AppCompatTextView textView = dialog.findViewById(R.id.successtext);
        //textView.setText("Visiting card added successfully in system. \nClick on OK to get back.");
        textView.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                    Intent intent = new Intent(UploadSelFie.this, HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //to finish chemAddEdit activity too, as after logout on backpress it opens up that activity again
                if(menu!=null && menu.equalsIgnoreCase("chemAddEdit"))
                    intent.putExtra("openfrag", "chemAddEdit");
                else
                    intent.putExtra("openfrag", "chemistpr");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(UploadSelFie.this, R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
                    startActivity(intent, bndlanimation);

                finish();

            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        UploadSelFie.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

}
