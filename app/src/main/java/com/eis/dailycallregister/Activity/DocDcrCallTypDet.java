package com.eis.dailycallregister.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DcrddrlstItem;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.GetPopupQuesRes;
import com.eis.dailycallregister.Pojo.QuestionslistItem;
import com.eis.dailycallregister.Pojo.SpcldcrdchlstItem;
import com.eis.dailycallregister.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocDcrCallTypDet extends AppCompatActivity {

    ViewDialog progressDialoge;
    MaterialButton submitbtn;
    RadioGroup rdgrp;
    TextView prCustnameTxt,quesTxt;
    LinearLayout outerll;
    private String cntcd,drname;
    private String custflg;
    private String oldCallTyp;
    RadioButton rad1,rad2;
    int position;

    public List<QuestionslistItem> questionslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcldcr_parctice_det);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Call Type Selection</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        progressDialoge = new ViewDialog(DocDcrCallTypDet.this);
        submitbtn = findViewById(R.id.submitBtn);
        rdgrp = findViewById(R.id.rdgrp);
        outerll = findViewById(R.id.outerll);
        prCustnameTxt = findViewById(R.id.prCustname);
        quesTxt = findViewById(R.id.quesTxt);
        rad1 = findViewById(R.id.yes);
        rad2 = findViewById(R.id.no);

        cntcd = getIntent().getStringExtra("cntcd");
        drname = getIntent().getStringExtra("drname");
        oldCallTyp = getIntent().getStringExtra("callType");

//reused YES/NO radio button xml for this.
        if(oldCallTyp!=null && oldCallTyp.equalsIgnoreCase("CLINIC")){
            rdgrp.check(R.id.yes);
        }else if(oldCallTyp!=null && oldCallTyp.equalsIgnoreCase("TELE")){
            rdgrp.check(R.id.no);
        }

        if(getIntent().getStringExtra("position")==null){
            Toast.makeText(this, "Some Problem encountered.Please Restart Reporting!", Toast.LENGTH_SHORT).show();
        }else {
            position = Integer.parseInt(getIntent().getStringExtra("position"));
        }
        prCustnameTxt.setText(drname);
        quesTxt.setText("Select Type of Call for this Dr");
        rad1.setText("In-Clinic-Call");
        rad2.setText("Tele-Call");

       // getRecord();

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
        DocDcrCallTypDet.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

   /* private void getRecord() {
        //rdgrp.check(gendd.equals("0") ? R.id.radio0 : R.id.radio1);
        progressDialoge.show();

        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().getPracticeDet(Global.dcrno, Global.netid, cntcd,
                        custflg, Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
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
    }*/

    private void saveRecord() {
        int selectedId = rdgrp.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(DocDcrCallTypDet.this, "No Input Received!", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButton = findViewById(selectedId);
            String sel = "";
            if (radioButton.getText().toString().equalsIgnoreCase("In-Clinic-Call")) {
                sel = "CLINIC";
            } else if (radioButton.getText().toString().equalsIgnoreCase("Tele-Call")) {
                sel = "TELE";
            } else {
                sel = "";
            }

            if (oldCallTyp != null && oldCallTyp.trim().equalsIgnoreCase(sel)) {
                Toast.makeText(DocDcrCallTypDet.this, "No Change Found!", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                save(sel);
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

private void save(final String sel){
    {
        progressDialoge.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().saveCallTypDet(Global.dcrno, Global.netid, cntcd,
                        sel, Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res = response.body();
                if (res != null) {

                    DcrddrlstItem modelx = DoctorsData.dcrdlst.get(position);
                    modelx.setCallType(sel);
                    DoctorsData.doctorslist.getAdapter().notifyDataSetChanged();


                    Toast.makeText(DocDcrCallTypDet.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(DocDcrCallTypDet.this, "Some Problem Occurred While Saving!", Toast.LENGTH_SHORT).show();
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
