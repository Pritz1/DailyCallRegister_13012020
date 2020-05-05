package com.eis.dailycallregister.Activity;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* poster 1 -> SaluteYourSensitivity -> img name in workspace : poster
* Poster 2 -> NEOLAYR-e (30/04/2020)-> img name in workspace : neolayr_e
* */

public class ShowImage extends AppCompatActivity {

    private ViewDialog progressDialoge;
    private MaterialButton closebtn;
    private LinearLayout outerLinearLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_alert);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'><i><b>MEGA BRAND ON ITS WAY...</b></i></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(ShowImage.this);

        closebtn = findViewById(R.id.closebtn);
        outerLinearLay = findViewById(R.id.outerLinearLay);

        closebtn.setVisibility(View.GONE);
            saveRecordInDB();
            //add data to DB Global.audioPopupShow;
    }

    private void saveRecordInDB() {
        progressDialoge.show();

        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().saveImgViewDetls(Global.ecode, Global.netid,
                        Global.audioPopupShow,"NEOLAYR-e", Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                if(res.isError()){
                    Snackbar snackbar = Snackbar.make(outerLinearLay, res.getErrormsg(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    saveRecordInDB();
                                }
                            });
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(outerLinearLay, "Failed to save poster view details !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveRecordInDB();
                            }
                        });
                snackbar.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        ShowImage.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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

}
