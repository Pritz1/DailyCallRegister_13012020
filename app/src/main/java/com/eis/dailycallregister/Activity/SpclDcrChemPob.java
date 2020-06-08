package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DCRGiftListRes;
import com.eis.dailycallregister.Pojo.DcrdchlstItem;
import com.eis.dailycallregister.Pojo.DcrgiftslistItem;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.GetPopupQuesRes;
import com.eis.dailycallregister.Pojo.QuestionslistItem;
import com.eis.dailycallregister.Pojo.SpcldcrdchlstItem;
import com.eis.dailycallregister.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpclDcrChemPob extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 60000;
    public static final int READ_TIMEOUT = 90000;
    ViewDialog progressDialoge;
    MaterialButton submitbtn;
    ConstraintLayout nsv;
    TextView chname;
    public AppCompatEditText pob;
    //NestedScrollView nsv;
    int position;
    String oldPob = "";
    String cntcd = "";
    String custflg = "";
    String yr="",mth="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcldcr_chem_pob);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>POB Entry</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(SpclDcrChemPob.this);


        //Log.d("finyr ",finyr);
        submitbtn = findViewById(R.id.submit);
        nsv = findViewById(R.id.chmPobMain);
        chname = findViewById(R.id.chname);
        pob = findViewById(R.id.pob);

        cntcd = getIntent().getStringExtra("cntcd");
        custflg = getIntent().getStringExtra("custflg");
        oldPob = getIntent().getStringExtra("pob");
        position = Integer.parseInt(getIntent().getStringExtra("position"));
        chname.setText(getIntent().getStringExtra("chname"));

         yr = (Global.currDate).split("-")[0];
         mth = (Global.currDate).split("-")[1];

        if (oldPob!=null && !oldPob.equalsIgnoreCase("null") &&
                !oldPob.equalsIgnoreCase("")
                && Integer.parseInt(oldPob) >= 0) {

            pob.setText(getIntent().getStringExtra("pob"));

        }

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showconfirmationdialog();
                //Toast.makeText(DocDCRGift.this, myCustomArray.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void showconfirmationdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to submit ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveRecord();
                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveRecord() {
        String pobTxt = pob.getText().toString();
        if(pobTxt == null )
            pobTxt = "";
        
        if(pobTxt.equalsIgnoreCase("")){
            Toast.makeText(this, "No Input Recieved!", Toast.LENGTH_SHORT).show();
        }else {
            if (oldPob != null && oldPob.trim().equalsIgnoreCase(pobTxt.trim())) {
                Toast.makeText(SpclDcrChemPob.this, "Saved Succesfully!", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                progressDialoge.show();
                retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                        .getInstance().getApi().savePob(Global.dcrno, Global.netid, Global.ecode, cntcd,
                                custflg, pobTxt, Global.currDate, Global.dbprefix);
                call1.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                        progressDialoge.dismiss();
                        DefaultResponse res = response.body();
                        if (res != null) {

                            SpcldcrdchlstItem modelx = SpclDcrChemData.dcrdlst.get(position);
                            modelx.setPob(pob.getText().toString());
                            //onBackPressed();
                            SpclDcrChemData.chemListRv.getAdapter().notifyDataSetChanged();

                            Snackbar snackbar = Snackbar.make(nsv, res.getErrormsg(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    onBackPressed();
                                    SpclDcrChemPob.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                                }
                            }, 1200);
                            //Toast.makeText(SpclDcrChemPob.this, res.getErrormsg(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(SpclDcrChemPob.this, "Some Problem Occured While Saving!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                        progressDialoge.dismiss();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Failed to save data !", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        saveRecord();
                                    }
                                });
                        snackbar.show();
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
         if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        SpclDcrChemPob.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }



}
