package com.eis.dailycallregister.Activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CapNUpSelfie extends AppCompatActivity {


    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    public LinearLayout ll1, ll2;
    public RelativeLayout rl;
    public String cntcd="",doctorname="",keycontactper="", phonenumber="",chemistname="",doccntcd="",
            flag="",menu="",add1,add2,add3,city,state,sttype,pincode,camMode,noVst,cls,selTcpJson;
    public boolean isimgcropped = false,chmDetUpdtReq=false,isPatchUpdtd=false;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_ATTACH_IMAGE_REQUEST_CODE = 99;
    private Uri picUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    ViewDialog progressDialoge;
    private Uri fileUri; // file url to store image/video

    private ImageView btnCapturePicture; //cardpic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nup_selfie);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        cntcd = getIntent().getStringExtra("cntcd");
        chemistname = getIntent().getStringExtra("chemistname");
        doctorname = getIntent().getStringExtra("doctorname");
        keycontactper = getIntent().getStringExtra("keycontactper");
        phonenumber = getIntent().getStringExtra("phonenumber");
        doccntcd = getIntent().getStringExtra("doccntcd");
        flag = getIntent().getStringExtra("flag");
        menu = getIntent().getStringExtra("menu");

        add1 = getIntent().getStringExtra("add1");
        add2 = getIntent().getStringExtra("add2");
        add3 = getIntent().getStringExtra("add3");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        sttype = getIntent().getStringExtra("sttype");
        pincode = getIntent().getStringExtra("pincode");
        camMode = getIntent().getStringExtra("camMode");
        noVst = getIntent().getStringExtra("noVst");
        cls = getIntent().getStringExtra("cls");
        selTcpJson = getIntent().getStringExtra("selTcpJson");
        chmDetUpdtReq = getIntent().getBooleanExtra("chmDetUpdtReq",false);
        isPatchUpdtd = getIntent().getBooleanExtra("isPatchUpdtd",false);

      //status = getIntent().getStringExtra("status");
        // Changing action bar background color
        // These two lines are not needed
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
        if(menu != null && menu.equalsIgnoreCase("chemAddEdit"))
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Capture Visiting Card</font>"));
        else
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Take Selfie</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        btnCapturePicture = findViewById(R.id.btnCapturePicture);
        //cardpic = findViewById(R.id.cardpic);
        progressDialoge = new ViewDialog(CapNUpSelfie.this);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

           // capture picture

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }else if(camMode != null && camMode.equalsIgnoreCase("gallery")){
            getImageFromGallery();
        }else{
            captureImage();
        }
    }


    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                            //Log.d("fileUri", fileUri.toString());

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                            intent.putExtra("return-data", true);
                            // start the image capture Intent
                            //startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            //if (intent.resolveActivity(getPackageManager()) != null) { // by prithvi
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        //}else{}
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
       // Log.d("requestCode1",""+requestCode);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
               // Log.d("requestCode2", "" + requestCode);
                isimgcropped = false;
                picUri = fileUri;
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Image Captured !", Toast.LENGTH_SHORT)
                        .show();

            } else if (resultCode == RESULT_CANCELED) {
                    finish();
                    Toast.makeText(CapNUpSelfie.this,"User cancelled image capture operation" ,Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == GALLERY_ATTACH_IMAGE_REQUEST_CODE) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {

                fileUri = data.getData();
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                isimgcropped = false;
                picUri = fileUri;
                //Log.d("gallery pic path",fileUri.toString());
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Image Attached !", Toast.LENGTH_SHORT)
                        .show();
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(boolean isImage) {
       // Log.d("launchUploadActivity : ","launchUploadActivity");
        Intent i = new Intent(CapNUpSelfie.this, UploadSelFie.class);
        i.putExtra("filePath", picUri.getPath());
        i.putExtra("fileUri", fileUri.getPath());
        i.putExtra("isImage", isImage);
        i.putExtra("isimgcropped", isimgcropped);
        i.putExtra("cntcd", cntcd);
        i.putExtra("doccntcd", doccntcd);
        i.putExtra("chemistname", chemistname);
        i.putExtra("doctorname", doctorname);
        i.putExtra("keycontactper", keycontactper);
        i.putExtra("phonenumber", phonenumber);
        i.putExtra("flag", flag);
        i.putExtra("menu", menu);
        i.putExtra("sttype", sttype);
        if(camMode != null && camMode.equalsIgnoreCase("gallery"))
            i.putExtra("camMode", camMode);
        else
            i.putExtra("camMode", "");
        if(menu!=null && menu.equalsIgnoreCase("chemAddEdit")
        && chmDetUpdtReq==true){
            i.putExtra("add1", add1);
            i.putExtra("add2", add2);
            i.putExtra("add3", add3);
            i.putExtra("city", city);
            i.putExtra("state", state);
            i.putExtra("chmDetUpdtReq", "Y");
            i.putExtra("pincode", pincode);
            i.putExtra("cls", cls);
            i.putExtra("noVst", noVst);
        }else{
            i.putExtra("chmDetUpdtReq","N");
        }

        if(isPatchUpdtd){
            i.putExtra("selTcpJson", selTcpJson);
            i.putExtra("isPatchUpdtd","Y");
        }else{
            i.putExtra("isPatchUpdtd","N");
        }

        startActivity(i);
        finish();
    }


    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type, cntcd));
    }

    /**
     * returning image / video
     */


    private static File getOutputMediaFile(int type, String cntcd) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                RetrofitClient.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "+ RetrofitClient.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "ABC_" + Global.netid + "_" + cntcd + "_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
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
        CapNUpSelfie.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void getImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, GALLERY_ATTACH_IMAGE_REQUEST_CODE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
