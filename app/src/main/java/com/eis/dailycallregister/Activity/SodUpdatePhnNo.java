package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SodUpdatePhnNo extends AppCompatActivity {

    EditText phoneno;
    Button submit;
    LinearLayout scr;
    String cntcd="",drname="",netid="",old_phoneno;
    ViewDialog progressDialoge;
    TextView phCustnameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sod_phnno);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Dr's Phn No For SamPal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        cntcd=getIntent().getStringExtra("cntcd");
        drname=getIntent().getStringExtra("drname");
        if(Global.emplevel!=null && Global.emplevel.equalsIgnoreCase("1"))
            netid=Global.netid;
        else
            netid=getIntent().getStringExtra("wnetid");

        phoneno=findViewById(R.id.phnNo);
        submit=findViewById(R.id.submitBtn);
        phCustnameTxt=findViewById(R.id.phCustname);
        scr=findViewById(R.id.outerll);

        progressDialoge=new ViewDialog(this);
        phCustnameTxt.setText(drname);

        getPhnoFromDB();

        submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               checkForm();
           }
       });

    }

    private void getPhnoFromDB() {
        progressDialoge.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().getSodPhnnoFromDb(netid, cntcd, Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                if (res!=null && !res.isError()) {
                    old_phoneno = res.getErrormsg();
                    phoneno.setText(old_phoneno);
                } else if(res!=null) {
                    Snackbar snackbar = Snackbar.make(scr, res.getErrormsg(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar.make(scr, "Some Problem Occured!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(scr, "Unable to fetch phone no. !", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPhnoFromDB();
                            }
                        });
                snackbar.show();
            }
        });
    }


    public void  checkForm() {
        String phonenumber = phoneno.getText().toString().trim();

        if (phonenumber.isEmpty() || phonenumber.length() < 10 || phonenumber.equalsIgnoreCase("0000000000")) {
            Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show();
        }else {
            submitForm(phonenumber);
        }
        }


    public void submitForm(final String phonenumber)
    {
        if (phoneno.getText().toString().trim().equalsIgnoreCase(old_phoneno)) {
            Snackbar snackbar = Snackbar.make(scr, "No Change Found!", Snackbar.LENGTH_LONG);
            snackbar.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    SodUpdatePhnNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            }, 1200);

        } else {
            progressDialoge.show();
            Call<DefaultResponse> call1=RetrofitClient.getInstance().getApi().saveSodPhnNo(netid,cntcd,
                    phonenumber,Global.ecode,Global.dbprefix);

            call1.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                    DefaultResponse res = response.body();
                    progressDialoge.dismiss();
                    if (!res.isError()) {
                        Snackbar snackbar = Snackbar.make(scr, res.getErrormsg(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                                SodUpdatePhnNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                            }
                        }, 1200);
                    } else {
                        Snackbar snackbar = Snackbar.make(scr, res.getErrormsg(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                    progressDialoge.dismiss();
                    Snackbar snackbar = Snackbar.make(scr, "Failed to save !", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submitForm(phonenumber);
                                }
                            });
                    snackbar.show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
        SodUpdatePhnNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
