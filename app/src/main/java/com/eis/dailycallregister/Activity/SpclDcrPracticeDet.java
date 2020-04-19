package com.eis.dailycallregister.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.ChemistListAWRes;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpclDcrPracticeDet extends AppCompatActivity {

    ViewDialog progressDialoge;
    MaterialButton submitbtn;
    RadioGroup rdgrp;
    TextView prCustnameTxt,quesTxt;
    LinearLayout outerll;
    private String cntcd,drname;
    private String custflg;
    private String oldPractFlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcldcr_parctice_det);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Please Ans The  Question</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        progressDialoge = new ViewDialog(SpclDcrPracticeDet.this);
        submitbtn = findViewById(R.id.submitBtn);
        rdgrp = findViewById(R.id.rdgrp);
        outerll = findViewById(R.id.outerll);
        prCustnameTxt = findViewById(R.id.prCustname);
        quesTxt = findViewById(R.id.quesTxt);

        cntcd = getIntent().getStringExtra("cntcd");
        custflg = getIntent().getStringExtra("custflg");
        drname = getIntent().getStringExtra("custName");

        prCustnameTxt.setText(drname);
        if(custflg!=null && custflg.equalsIgnoreCase("C"))
        quesTxt.setText("Is Chemist Open?");

        getRecord();
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showconfirmationdialog();
            }
        });
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
        SpclDcrPracticeDet.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
//TODO work on this
    private void getRecord() {
        //rdgrp.check(gendd.equals("0") ? R.id.radio0 : R.id.radio1);
        progressDialoge.show();

        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().getPracticeDet(Global.dcrno, Global.netid, cntcd,
                        custflg, Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res = response.body();
                if (!res.isError()) {
                    oldPractFlg = res.getErrormsg();
                        if(oldPractFlg!=null && oldPractFlg.equalsIgnoreCase("Y")){
                            rdgrp.check(R.id.yes);
                        }else if(oldPractFlg!=null && oldPractFlg.equalsIgnoreCase("N")){
                            rdgrp.check(R.id.no);
                        }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Failed to get data !", Snackbar.LENGTH_INDEFINITE)
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

    private void saveRecord() {
        int selectedId = rdgrp.getCheckedRadioButtonId();
        if(selectedId == -1){
            Toast.makeText(SpclDcrPracticeDet.this, "No Input Recieved!", Toast.LENGTH_SHORT).show();
        }else {
            RadioButton radioButton = findViewById(selectedId);
            String sel = "";
            if (radioButton.getText().toString().equalsIgnoreCase("YES")) {
                sel = "Y";
            } else if (radioButton.getText().toString().equalsIgnoreCase("NO")) {
                sel = "N";
            } else {
                sel = "";
            }

            if (oldPractFlg != null && oldPractFlg.trim().equalsIgnoreCase(sel)) {
                Toast.makeText(SpclDcrPracticeDet.this, "Saved Successfully!", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                progressDialoge.show();
                retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                        .getInstance().getApi().savePracticeDet(Global.dcrno, Global.netid, cntcd,
                                custflg, sel, Global.dbprefix);
                call1.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                        progressDialoge.dismiss();
                        DefaultResponse res = response.body();
                        if (res != null) {
                            Toast.makeText(SpclDcrPracticeDet.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(SpclDcrPracticeDet.this, "Some Problem Occurred While Saving!", Toast.LENGTH_SHORT).show();
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

}
