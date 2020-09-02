package com.eis.dailycallregister.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.P1P2P3DrProdRes;
import com.eis.dailycallregister.Pojo.ProductListRes;
import com.eis.dailycallregister.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Product Exposure Selection
public class P1P2P3 extends AppCompatActivity {

    TextView drnmTxt;
    Button submit;
    NestedScrollView scr;
    String cntcd="",drname="",netid="",old_phoneno;
    ViewDialog progressDialoge;
    TextView phCustnameTxt;
    List<String> dtypList = new ArrayList<String>();
    List<String> drProdidList = new ArrayList<String>();
    List<String> prodidList = new ArrayList<String>();
    List<String> pnameList = new ArrayList<String>();
    AppCompatSpinner spnP1,spnP2,spnP3,spnP4,spnP5;
    String newP1Sel,newP2Sel,newP3Sel,newP4Sel,newP5Sel;
    String newP1,newP2,newP3,newP4,newP5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1p2p3);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>P1-P5 Selection</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        cntcd=getIntent().getStringExtra("cntcd");
        drname=getIntent().getStringExtra("drname");
        if(Global.emplevel!=null && Global.emplevel.equalsIgnoreCase("1"))
            netid=Global.netid;
        else
            netid=getIntent().getStringExtra("wnetid");

        drnmTxt=findViewById(R.id.drnmTxt);
        submit=findViewById(R.id.submitBtn);
        phCustnameTxt=findViewById(R.id.phCustname);
        spnP1=findViewById(R.id.spn1);
        spnP2=findViewById(R.id.spn2);
        spnP3=findViewById(R.id.spn3);
        spnP4=findViewById(R.id.spn4);
        spnP5=findViewById(R.id.spn5);
        scr=findViewById(R.id.p_addedit_outerlay);

        progressDialoge=new ViewDialog(this);
        drnmTxt.setText("Dr. "+drname);

        getDrPrdFromDB();

        submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onFormSubmit();
           }
       });

    }

    private void getDrPrdFromDB() {
        progressDialoge.show();
        Call<P1P2P3DrProdRes> call1 = RetrofitClient
                .getInstance().getApi().getDrPrdFromDB(Global.ecode, netid, cntcd, "p1p2p3", Global.dbprefix);
        call1.enqueue(new Callback<P1P2P3DrProdRes>() {
            @Override
            public void onResponse(Call<P1P2P3DrProdRes> call1, Response<P1P2P3DrProdRes> response) {
                P1P2P3DrProdRes res = response.body();
                progressDialoge.dismiss();
                if (res!=null && !res.isError()) {
                    dtypList = res.getDtypList();
                    drProdidList = res.getProdidList();
                    if(dtypList==null){
                        Snackbar snackbar = Snackbar.make(scr, "Could not find existing tagged products!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else {
                        Log.d("dtypList ", dtypList.toString());
                        Log.d("prodidList ", drProdidList.toString());
                    }
                }else if(res!=null) {
                    Snackbar snackbar = Snackbar.make(scr, res.getErrormsg(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar.make(scr, "Some Problem Occured!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                getProducts();

            }

            @Override
            public void onFailure(Call<P1P2P3DrProdRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(scr, "Unable to fetch existing product exposure of Dr!", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getDrPrdFromDB();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void getProducts() {
        progressDialoge.show();
        Call<ProductListRes> call1 = RetrofitClient
                .getInstance().getApi().getProducts("", netid, cntcd, "p1p2p3", Global.dbprefix);
        call1.enqueue(new Callback<ProductListRes>() {
            @Override
            public void onResponse(Call<ProductListRes> call1, Response<ProductListRes> response) {
                progressDialoge.dismiss();
                try{
                    ProductListRes res = response.body();
                    //HashMap prodmap = new HashMap();
                    //jsonObject=new JSONObject(new Gson().toJson(response.body()));
                    if(!res.isError()){
                        prodidList = res.getProdidList();
                        pnameList = res.getPnameList();
                        //JSONArray prods=jsonObject.getJSONArray("prodMap");
                       /* for(int i=0;i<prods.length();i++)
                        {
                            JSONObject object=prods.getJSONObject(i);
                            prodmap.put(object.getString("prodid"),object.getString("pname"));
                        }*/

                        for(int i=1;i<=5;i++){
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(P1P2P3.this,
                                    android.R.layout.simple_spinner_item, res.getPnameList());
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            if(i==1)
                                spnP1.setAdapter(adapter);
                            else if(i==2)
                                spnP2.setAdapter(adapter);
                            else if(i==3)
                                spnP3.setAdapter(adapter);
                            else if(i==4)
                                spnP4.setAdapter(adapter);
                            else if(i==5)
                                spnP5.setAdapter(adapter);
                        }
                    if(drProdidList!=null && drProdidList.size()>0){
                        int size = drProdidList.size();
                        int ind = 0;
                        int dtyp = 0;
                        String prodid = "";
                        String temp ="";
                        for(int i=0;i<size;i++){
                            prodid = drProdidList.get(i);
                            temp = dtypList.get(i);
                            dtyp = (temp!= null && !temp.equalsIgnoreCase("") ? Integer.parseInt(temp) : 0);
                            //ind = res.getProdidList().indexOf(prodidList.get(i));
                           if( dtyp == 1){
                               spnP1.setSelection(prodidList.indexOf(prodid)!=-1? prodidList.indexOf(prodid) : 0);
                           }
                           else if(dtyp == 2)
                               spnP2.setSelection(prodidList.indexOf(prodid)!=-1? prodidList.indexOf(prodid) : 0);
                           else if(dtyp == 3)
                               spnP3.setSelection(prodidList.indexOf(prodid)!=-1? prodidList.indexOf(prodid) : 0);
                           else if(dtyp == 4)
                               spnP4.setSelection(prodidList.indexOf(prodid)!=-1? prodidList.indexOf(prodid) : 0);
                           else if(dtyp == 5)
                               spnP5.setSelection(prodidList.indexOf(prodid)!=-1? prodidList.indexOf(prodid) : 0);
                        }
                    }

                    }else{
                        Snackbar snackbar = Snackbar.make(scr, res.getErrormsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

                    //Log.d("","JsonReturned: " + jsonObject);
                    //Log.d("","prodmap: " + prodmap);
                }catch(Exception e){
                    Log.getStackTraceString(e);
                }

            }

            @Override
            public void onFailure(Call<ProductListRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(scr, "Unable to fetch product list!", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getProducts();
                            }
                        });
                snackbar.show();
            }
        });
    }

    public void onFormSubmit()
    {
        setValues();
            progressDialoge.show();
            Call<DefaultResponse> call1=RetrofitClient.getInstance().getApi().saveDrsP1toP5(netid,cntcd,
                    Global.ecode,Global.dbprefix, newP1, newP2, newP3, newP4, newP5);

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
                                P1P2P3.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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
                                    onFormSubmit    ();
                                }
                            });
                    snackbar.show();
                }
            });
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
        P1P2P3.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

private  void setValues(){
    newP1Sel = spnP1.getSelectedItem().toString().trim();
    newP1 = prodidList.get(pnameList.indexOf(newP1Sel)); //get the index of prodname and then set the prodid which will be at same index
    newP2Sel = spnP2.getSelectedItem().toString().trim();
    newP2 = prodidList.get(pnameList.indexOf(newP2Sel));
    newP3Sel = spnP3.getSelectedItem().toString().trim();
    newP3 = prodidList.get(pnameList.indexOf(newP3Sel));
    newP4Sel = spnP4.getSelectedItem().toString().trim();
    newP4 = prodidList.get(pnameList.indexOf(newP4Sel));
    newP5Sel = spnP5.getSelectedItem().toString().trim();
    newP5 = prodidList.get(pnameList.indexOf(newP5Sel));
}

}
