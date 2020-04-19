package com.eis.dailycallregister.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class SpclDcrEditPhoneNo extends AppCompatActivity {

    ViewDialog progressDialoge;
    MaterialButton updateBtn;
    AppCompatEditText edtremk;
    EditText phonenoTxt;
    TextView drnameTxt;
    LinearLayout llt;
    public String serial;
    public String cntcd, custflg, phnno, drname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcldcr_editphnno);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Update Phn No.</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        progressDialoge = new ViewDialog(SpclDcrEditPhoneNo.this);
        edtremk = findViewById(R.id.remark);
        updateBtn = findViewById(R.id.updateBtn);
        llt = findViewById(R.id.editPhnLlt);
        phonenoTxt = findViewById(R.id.phoneno);
        drnameTxt = findViewById(R.id.drname);

        cntcd = getIntent().getStringExtra("cntcd");
        custflg = getIntent().getStringExtra("custflg");
        phnno = getIntent().getStringExtra("phnno");
        drname = getIntent().getStringExtra("drname");

        drnameTxt.setText(drname);
        phonenoTxt.setText(phnno);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SpclDcrEditPhoneNo.this);
                builder.setCancelable(true);
                builder.setTitle("Update ?");
                builder.setMessage("Are you sure to update Phn no.?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String phonenumber = phonenoTxt.getText().toString().trim();

                                if (phonenumber.isEmpty() || phonenumber.length() < 10
                                        || phonenumber.equalsIgnoreCase("0000000000")
                                        || phonenumber.indexOf("+")!=-1) {
                                    //Snackbar snackbar = Snackbar.make(llt, "Please Enter Valid Phone Number", Snackbar.LENGTH_LONG);
                                    //snackbar.show();
                                    Toast.makeText(SpclDcrEditPhoneNo.this, Html.fromHtml("<b>Please Enter Valid Phone Number<b>"), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    updatePhnno();
                                }
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
        });
    }

    private void updatePhnno() {
        if (phonenoTxt.getText().toString().trim().equalsIgnoreCase(phnno)) {
            Snackbar snackbar = Snackbar.make(llt, "No Change Found!", Snackbar.LENGTH_LONG);
            snackbar.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    SpclDcrEditPhoneNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            }, 1200);

        } else {
            progressDialoge.show();
            Call<DefaultResponse> call1 = RetrofitClient
                    .getInstance().getApi().updatePhnNoOfDr(Global.dcrno, Global.netid, cntcd , custflg,
                            phonenoTxt.getText().toString().trim(), Global.dbprefix);
            call1.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                    DefaultResponse res = response.body();
                    progressDialoge.dismiss();
                    if (!res.isError()) {
                        Snackbar snackbar = Snackbar.make(llt, res.getErrormsg(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent i = new Intent();
                                i.putExtra("updatedPhn",phonenoTxt.getText().toString().trim());
                                setResult(RESULT_OK,i);
                                finish();
                                SpclDcrEditPhoneNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                            }
                        }, 1200);
                    } else {
                        Snackbar snackbar = Snackbar.make(llt, res.getErrormsg(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                    progressDialoge.dismiss();
                    Snackbar snackbar = Snackbar.make(llt, "Some Problem Occured While Updating!", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updatePhnno();
                                }
                            });
                    snackbar.show();
                }
            });
        }
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
        SpclDcrEditPhoneNo.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
