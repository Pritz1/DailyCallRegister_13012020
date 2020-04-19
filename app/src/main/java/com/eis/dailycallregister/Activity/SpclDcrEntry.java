package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.GetDcrDateRes;
import com.eis.dailycallregister.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpclDcrEntry extends AppCompatActivity {

    TextView txtDate;
    MaterialButton btnDocData,btnChemData,btnConfirm;
    ViewDialog progressDialoge;
    LinearLayout llt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcl_dcr_entry);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Special Call Report</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(SpclDcrEntry.this);

        txtDate = findViewById(R.id.date);
        btnDocData = findViewById(R.id.docEntry);
        btnChemData = findViewById(R.id.chemEntry);
        btnConfirm = findViewById(R.id.btnConfirm);
        llt = findViewById(R.id.llt);

        final String cdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txtDate.setText("DATE : "+cdate);
        Global.currDate = cdate;


        btnDocData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpclDcrEntry.this, SpclDcrDoctorsData.class);
                intent.putExtra("cdate",cdate);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrEntry.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
            }
        });

        btnChemData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpclDcrEntry.this, SpclDcrChemData.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrEntry.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Global.dcrno == null || Global.dcrno.equalsIgnoreCase("")) {
                    alert("No Report Found To Submit. Please Fill in The Report and Then Submit."
                            , false);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SpclDcrEntry.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure you want to submit TODAY'S REPORT? \nOnce you submit you " +
                            "CANNOT EDIT it again");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitReport();
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        getDcrNoIfPresent();

    }

    private void getDcrNoIfPresent(){

            progressDialoge.show();

            retrofit2.Call<GetDcrDateRes> call1 = RetrofitClient
                    .getInstance().getApi().getSpclDcrno(Global.currDate, Global.ecode, Global.netid,
                            Global.dbprefix);
            call1.enqueue(new Callback<GetDcrDateRes>() {
                @Override
                public void onResponse(retrofit2.Call<GetDcrDateRes> call1, Response<GetDcrDateRes> response) {
                    progressDialoge.dismiss();
                    GetDcrDateRes res = response.body();
                    if (res != null && !res.isError()) {

                            Global.dcrno=res.getErrormsg();
                            if(res.getRemark()!=null && res.getRemark().indexOf(":")!=-1) {
                                Global.tcpid = res.getRemark().split(":")[0]; //used remark attr to save tcpid
                                Global.wrktype = res.getRemark().split(":")[0]; //used remark attr to save tcpid
                            }
                    }else if(res != null && res.isError()){
                        if(res.getRemark()!=null && res.getRemark().equalsIgnoreCase("confirmed")){
                            alert(res.getErrormsg(), true);
                        }
                    }else{
                        Toast.makeText(SpclDcrEntry.this, "Some Problem Occurred While Getting Spcl Report ID.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDcrDateRes> call1, Throwable t) {
                    progressDialoge.dismiss();
                    Snackbar snackbar = Snackbar.make(llt, "Failed to Get Spcl Report ID !", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Re-try", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getDcrNoIfPresent();
                                }
                            });
                    snackbar.show();
                }
            });
    }

    private void submitReport(){

            progressDialoge.show();

            retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                    .getInstance().getApi().submitSpclDcr(Global.currDate, Global.ecode, Global.netid,
                            Global.tcpid,Global.dcrno, Global.dbprefix);
            call1.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                    progressDialoge.dismiss();
                    DefaultResponse res = response.body();

                        if (res != null && !res.isError()) {
                            Global.dcrno = null;
                            Global.tcpid = null;
                            Global.dcrdate = null;
                            Global.wrktype = null;

                            alert(res.getErrormsg(), true);
                        } else {
                            alert(res.getErrormsg(),false);
                            /*Snackbar snackbar = Snackbar.make(llt, res.getErrormsg(), Snackbar.LENGTH_LONG);
                            snackbar.show();*/
                        }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                    progressDialoge.dismiss();
                    Snackbar snackbar = Snackbar.make(llt, "Failed to save data !", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Re-try", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submitReport();
                                }
                            });
                    snackbar.show();
                }
            });
    }

    private void alert(String msg, final boolean back){
        AlertDialog.Builder builder = new AlertDialog.Builder(SpclDcrEntry.this);
        builder.setCancelable(true);
        builder.setMessage(msg);
        builder.setPositiveButton(Html.fromHtml("<b>OK<b>"),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(back) {
                            finish();
                            SpclDcrEntry.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                        }else{
                            //do nothing
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        SpclDcrEntry.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
