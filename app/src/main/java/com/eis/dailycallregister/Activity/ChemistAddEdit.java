package com.eis.dailycallregister.Activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.ChemistDoctorNameRes;
import com.eis.dailycallregister.Pojo.ChemistdataItem;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.DoctornamelistItem;
import com.eis.dailycallregister.Pojo.ChemistDetailRes;
import com.eis.dailycallregister.Pojo.StateListResponse;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistAddEdit extends AppCompatActivity {


    EditText chmkeypername,chmphoneno,add1,add2,add3,city,chmName,pincode;
    String oldKeyPerNm,oldPhnno,oldAdd1,oldAdd2,oldAdd3,oldCity,oldState,oldChmnm,oldPincode,oldImgUrl;
    String newKeyPerNm,newPhnno,newAdd1,newAdd2,newAdd3,newCity,newState,newChmnm,newPincode;
    Button imgBtn,updtBtn;
    AppCompatSpinner chemistdoctor,state;
    LinearLayout ll1,ll2;
    NestedScrollView chm_addedit_outerlay;
    String cntcd="",chemistname="",doccntcd="",sttype="",menu,addEdit="",drcd,drname,drcntcd;
    ViewDialog progressDialoge;
    ImageView imgcard;
    //TextView chemname;
    boolean validateFlag=false;

    List<DoctornamelistItem> doctornamelistItemList = new ArrayList();
    public List<String> arrayList = new ArrayList<>();
    public List<String> stateList = new ArrayList<>();
    List<ChemistdataItem> chemistdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_add_edit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Chemist Profile</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        cntcd = getIntent().getStringExtra("cntcd");
        chemistname=getIntent().getStringExtra("chemistname");
        sttype=getIntent().getStringExtra("sttype");
        addEdit=getIntent().getStringExtra("addEdit");
        menu=getIntent().getStringExtra("menu");

        drcd=getIntent().getStringExtra("drcd");
        drname=getIntent().getStringExtra("drname");
        drcntcd=getIntent().getStringExtra("doccntcd");

        chmkeypername=findViewById(R.id.chmkeypername);
        chmName=findViewById(R.id.chmName);
        chmphoneno=findViewById(R.id.chmphoneno);
        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);
        add3=findViewById(R.id.add3);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        imgBtn=findViewById(R.id.imgBtn);
        pincode=findViewById(R.id.pincode);

        chm_addedit_outerlay=findViewById(R.id.chm_addedit_outerlay);
        chemistdoctor=findViewById(R.id.chemistdoctor);
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        imgcard=findViewById(R.id.imagecard);
        updtBtn=findViewById(R.id.updtBtn);

        progressDialoge = new ViewDialog(this);

        /*if(chemistname != null)
        chemname.setText(chemistname);*/

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkDate(docname);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChemistAddEdit.this);
                builder.setCancelable(true);
                builder.setTitle("CONFIRM");
                builder.setMessage("Are you sure you want to capture image & submit? ");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendData();
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
        if(addEdit !=null && addEdit.equalsIgnoreCase("ADD")){
            updtBtn.setBackgroundColor(getResources().getColor(R.color.textcolorgray));
            updtBtn.setEnabled(false);
        }else {
            updtBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ChemistAddEdit.this);
                    builder.setCancelable(true);
                    builder.setTitle("CONFIRM");
                    builder.setMessage("Are you sure you want to update? ");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        updateRecord();
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

        if(addEdit!=null && addEdit.equalsIgnoreCase("EDIT")) {
            getDoctorName();

        }else if(addEdit!=null && addEdit.equalsIgnoreCase("ADD")){

            arrayList.add(drname);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistAddEdit.this,
                    android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chemistdoctor.setAdapter(adapter);
            //chemistdoctor.setPrompt("Tagged Doctor(s)");
            ll1.setVisibility(View.VISIBLE);

            sttype="O";

            getStates();

        }
    }
//todo doubt -> is it required to check for duplicate phn no & name on update?
    public void sendData()
    {
        progressDialoge.show();
        setNewValues(); //set all values from fields in variables
        String errormsg[] = (validate()).split("~"); //checks all mandatory fields
        if(errormsg[0].equalsIgnoreCase("Y")){
            progressDialoge.dismiss();
            Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, errormsg[1], Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            progressDialoge.dismiss();
            validateChemistAndSendData();

        }
    }

    public void getDoctorName()
    {
        /*Log.d("Global.netid",Global.netid);
        Log.d("cntcd",cntcd);*/

        progressDialoge.show();
        Call<ChemistDoctorNameRes> call = RetrofitClient.getInstance().getApi().
                getChemistDrName(Global.netid,cntcd,sttype ,Global.dbprefix);
       // Log.d("call",call.toString());
        call.enqueue(new Callback<ChemistDoctorNameRes>() {
            @Override
            public void onResponse(Call<ChemistDoctorNameRes> call, Response<ChemistDoctorNameRes> response) {
                progressDialoge.dismiss();

                ChemistDoctorNameRes res=response.body();
             //   Log.d("res",res.toString());
                if(!res.isError())
                {
                    doctornamelistItemList = res.getDoctornamelist();
                    if(doctornamelistItemList.size()>0) {

                        for (int i = 0; i < doctornamelistItemList.size(); i++) {
                            arrayList.add(doctornamelistItemList.get(i).getDrname());
                            doccntcd=doctornamelistItemList.get(i).getCntcd();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistAddEdit.this, android.R.layout.simple_spinner_item, arrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        chemistdoctor.setAdapter(adapter);
                        //chemistdoctor.setPrompt("Tagged Doctor(s)");
                        ll1.setVisibility(View.VISIBLE);

                        getChemistDetails();
                    }

                    else {
                            arrayList.add(" ");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistAddEdit.this, android.R.layout.simple_spinner_item, arrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            chemistdoctor.setAdapter(adapter);
                            //chemistdoctor.setPrompt("Tagged Doctor(s)");
                            ll1.setVisibility(View.VISIBLE);
                        Toast.makeText(ChemistAddEdit.this, "No Doctor Found With This Chemist !", Toast.LENGTH_LONG).show();
                        disableButtons();
                        }
                }else {
                    Toast.makeText(ChemistAddEdit.this, "No Doctor Attached to This Chemist !", Toast.LENGTH_LONG).show();
                    disableButtons();
                }
            }
            @Override
            public void onFailure(Call<ChemistDoctorNameRes> call, Throwable t) {
               // Log.d("onFailure",t.toString());
                progressDialoge.dismiss();
                Toast.makeText(ChemistAddEdit.this, "Failed to get Attached Doctor(s). Please Try Again Later !", Toast.LENGTH_LONG).show();
                disableButtons();
            }
        });
    }

   public void getChemistDetails()
   {
       /*Log.d("getExistingSelfie","getExistingSelfie");
       Log.d("Global.netid",Global.netid);
       Log.d("cntcd",cntcd);*/
       progressDialoge.show();
       Call<ChemistDetailRes> call = RetrofitClient.getInstance().getApi().getChemistData(cntcd,
               Global.netid ,Global.dbprefix,menu);
       call.enqueue(new Callback<ChemistDetailRes>() {
           @SuppressLint("CheckResult")
           @Override
           public void onResponse(Call<ChemistDetailRes> call, Response<ChemistDetailRes> response) {
               ChemistDetailRes res=response.body();
               progressDialoge.dismiss();
               //Log.d("res",res.toString());
               if(!res.isError())
               {
                   progressDialoge.dismiss();
                    chemistdata=res.getChemistdata();
                    stateList = res.getStatelist();
                    int size = stateList.size();
                    ChemistdataItem cd=chemistdata.get(0);
                    if(chemistdata.size()>0)
                    {
                        chmName.setText(cd.getStname());
                        oldChmnm = cd.getStname();
                        chmkeypername.setText(cd.getKeyp());
                        oldKeyPerNm = cd.getKeyp();
                        chmphoneno.setText(cd.getMobileno());
                        oldPhnno = cd.getMobileno();
                        add1.setText(cd.getAdd1());
                        oldAdd1 = cd.getAdd1();
                        add2.setText(cd.getAdd2());
                        oldAdd2 = cd.getAdd2();
                        add3.setText(cd.getAdd3());
                        oldAdd3 = cd.getAdd3();
                        city.setText(cd.getCity());
                        oldCity = cd.getCity();
                        pincode.setText(cd.getPincode());
                        oldPincode = cd.getPincode();

                        //state.setText(cd.getState());
                        if(cd.getImg_url()==null)
                            cd.setImg_url("");
                            // && !cd.getImg_url().equalsIgnoreCase("")) {
                            oldImgUrl = cd.getImg_url();
                            /*Glide.with(ChemistAddEdit.this).load(cd.getImg_url()).
                                    error(Glide.with(ChemistAddEdit.this)
                                            .load(R.drawable.ic_photo_not_found_24dp)).into(imgcard);*/
                        Glide.with(ChemistAddEdit.this).load(cd.getImg_url())
                            .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_photo_not_found_24dp)
                            .error(R.drawable.ic_photo_not_found_24dp)
                            ).into(imgcard);
                      //  }
                    }else{
                        chmName.setText(chemistname);
                        chmkeypername.setText(null);
                        chmphoneno.setText(null);
                        add1.setText(null);
                        add2.setText(null);
                        add3.setText(null);
                        city.setText(null);
                        //state.setText(null);

                    }

                   if(size > 0) {
/*
                   for (int i = 0; i < size; i++) {
                       arrayList.add(doctornamelistItemList.get(i).getDrname());
                       doccntcd=doctornamelistItemList.get(i).getCntcd();
                   }*/
                   ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistAddEdit.this,
                           android.R.layout.simple_spinner_item, stateList);
                   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       state.setAdapter(adapter);
                       //state.setPrompt("Select State");
                       oldState = cd.getState();
                       if(oldState != null)
                            state.setSelection(adapter.getPosition(oldState.toUpperCase()));
               }

               }
           }

           @Override
           public void onFailure(Call<ChemistDetailRes> call, Throwable t) {
               progressDialoge.dismiss();
               Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                       .setAction("Retry", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               getChemistDetails();
                           }
                       });
               snackbar.show();

           }
       });
   }

   public void getStates()
   {
       progressDialoge.show();
       Call<StateListResponse> call = RetrofitClient.getInstance().getApi().getStateList(Global.dbprefix);
       call.enqueue(new Callback<StateListResponse>() {
           @Override
           public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
               StateListResponse res=response.body();
               //Log.d("res",res.toString());
               if(!res.isError())
               {
                   progressDialoge.dismiss();
                   stateList = res.getStatelist();
                   int size = stateList.size();

                   if(size > 0) {
                       ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistAddEdit.this,
                               android.R.layout.simple_spinner_item, stateList);
                       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       state.setAdapter(adapter);
                       //state.setPrompt("Select State");
                   }

               }else{
                   Toast.makeText(ChemistAddEdit.this, "Could Not Get State List", Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<StateListResponse> call, Throwable t) {
               progressDialoge.dismiss();
               Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                       .setAction("Retry", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               getChemistDetails();
                           }
                       });
               snackbar.show();

           }
       });
   }

   public void updateRecord()
   {

       setNewValues(); //set all values from fields in variables
       String errormsg[] = (validate()).split("~"); //checks all mandatory fields
       if(errormsg[0].equalsIgnoreCase("Y")){

           Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, errormsg[1], Snackbar.LENGTH_LONG);
           snackbar.show();
       }else {
           if(oldImgUrl == null || oldImgUrl.equalsIgnoreCase("")){

               Global.alert(ChemistAddEdit.this,"You cannot submit without uploading " +
                       "Visiting Card. Please Upload and click on Attach and Submit. " +
                       "If visiting card is already uploaded then only you can submit with this button","Alert");

           }else {
               progressDialoge.show();
               boolean flag = isUpdateRequired(); //if all old and new data are same for all fields then save only img
               if(flag) {
                   //uploadChemVstCard.php

                   Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().
                           updateChemDetails(Global.ecode,Global.netid,cntcd,newKeyPerNm,
                                   newChmnm,newPhnno,sttype,newAdd1,newAdd2,newAdd3,
                                   newCity,newState,newPincode,Global.dbprefix);
                   call.enqueue(new Callback<DefaultResponse>() {
                       @Override
                       public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                           DefaultResponse res = response.body();
                           progressDialoge.dismiss();
                           //Log.d("res",res.toString());
                           if (!res.isError()) {
                               //Toast.makeText(ChemistAddEdit.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                               Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, res.getErrormsg(),
                                       Snackbar.LENGTH_LONG);
                               snackbar.show();
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   public void run() {
                                       finish();
                                       ChemistAddEdit.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                                   }
                               }, 1200);

                           } else {
                               Toast.makeText(ChemistAddEdit.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                           }

                       }

                       @Override
                       public void onFailure(Call<DefaultResponse> call, Throwable t) {
                           progressDialoge.dismiss();
                           Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "Failed to save data !", Snackbar.LENGTH_LONG)
                                   .setAction("Retry", new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           getChemistDetails();
                                       }
                                   });
                           snackbar.show();

                       }
                   });
               }else{

                   Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "No Change Found !", Snackbar.LENGTH_LONG);
                   snackbar.show();
                   Handler handler = new Handler();
                   handler.postDelayed(new Runnable() {
                       public void run() {
                           finish();
                           ChemistAddEdit.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                       }
                   }, 1200);

               }
           }
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
        ChemistAddEdit.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void setNewValues(){
        newKeyPerNm = chmkeypername.getText().toString().trim();
        newChmnm = chmName.getText().toString().trim();
        newPhnno = chmphoneno.getText().toString().trim();
        newAdd1 = add1.getText().toString().trim();
        newAdd2 = add2.getText().toString().trim();
        newAdd3 = add3.getText().toString().trim();
        newCity = city.getText().toString().trim();
        newState = state.getSelectedItem().toString().trim();
        newPincode = pincode.getText().toString().trim();
    }

    private boolean isUpdateRequired(){
        boolean flag=false;
        //below fields are mandatory. only address is not.
        if(oldKeyPerNm == null || oldKeyPerNm.equalsIgnoreCase("") ||
                (oldKeyPerNm!=null && !oldKeyPerNm.equals(newKeyPerNm))){
            flag = true;
        }else if(oldChmnm == null || oldChmnm.equalsIgnoreCase("") ||
                (oldChmnm!=null && !oldChmnm.equals(newChmnm))){
            flag = true;
        }else if(oldPhnno == null || oldPhnno.equalsIgnoreCase("") ||
                (oldPhnno!=null && !oldPhnno.equals(newPhnno))){
            flag = true;
        }else if(oldCity == null || oldCity.equalsIgnoreCase("") ||
                (oldCity!=null && !oldCity.equals(newCity))){
            flag = true;
        }else if(oldState == null || oldState.equalsIgnoreCase("") ||
                (oldState!=null && !oldState.equals(newState))){
            flag = true;
        }else if(oldPincode == null || oldPincode.equalsIgnoreCase("") ||
                (oldPincode!=null && !oldPincode.equals(newPincode))){
            flag = true;
        }else{
            if(oldAdd1==null) oldAdd1="";
            if(oldAdd2==null) oldAdd2="";
            if(oldAdd3==null) oldAdd3="";

            if(newAdd1==null) newAdd1="";
            if(newAdd2==null) newAdd2="";
            if(newAdd3==null) newAdd3="";

            if(!oldAdd1.equalsIgnoreCase(newAdd1))
                flag = true;
            if(!oldAdd2.equalsIgnoreCase(newAdd2))
                flag = true;
            if(!oldAdd3.equalsIgnoreCase(newAdd3))
                flag = true;
        }
        return flag;
    }

    private String validate(){
        boolean error = false;
        String errormsg="";
        if(newKeyPerNm == null || newKeyPerNm.equalsIgnoreCase("")){
            error = true;
            errormsg = "Please Enter Key Person !";
        }else if(newChmnm == null || newChmnm.equalsIgnoreCase("")){
            error = true;
            errormsg = "Please Enter Chemist Name !";
        }else if(newPhnno.isEmpty() || newPhnno.length() < 10){
            error = true;
            errormsg = "Please Enter Valid Phone No. !";
        }else if(newCity == null || newCity.equalsIgnoreCase("")){
            error = true;
            errormsg = "Please Enter City !";
        }else if(newState == null || newState.equalsIgnoreCase("")
                 || newState.equalsIgnoreCase("--Select State--")){
            error = true;
            errormsg = "Please Select State !";
        }else if(newPincode == null || newPincode.equalsIgnoreCase("")){
            error = true;
            errormsg = "Please Enter Pincode !";
        }
        String doctorname="";
        try {
            doctorname = chemistdoctor.getSelectedItem().toString().trim();
        }catch(NullPointerException e){
            Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "Chemist is Not Attached to Any Doctor", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        if(error)
            return "Y~"+errormsg;
        else
            return "N~"+"";
    }

    private void validateChemistAndSendData(){ //validates if phnno entered and chem name entered already exists or not.

        validateFlag = false;
        progressDialoge.show();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().chemDetlsValidate(Global.ecode,
                Global.netid,( cntcd!=null ? cntcd : ""),newChmnm,newPhnno,addEdit,Global.dbprefix);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res=response.body();
                //Log.d("res",res.toString());
                if(res.isError())
                {
                    Global.alert(ChemistAddEdit.this,res.getErrormsg(),"ERROR");
                }else{
                    validateFlag=true;
                    if(validateFlag){

                        Intent intent = new Intent(ChemistAddEdit.this, CapNUpSelfie.class);

                        if (addEdit != null && addEdit.equalsIgnoreCase("EDIT")) {

                            boolean flag = isUpdateRequired(); //if all old and new data are same for all fields then save only img
                            intent.putExtra("flag", "UPDATE");
                            intent.putExtra("chmDetUpdtReq", flag);

                        } else {
                            intent.putExtra("flag", "ADD");
                            intent.putExtra("chmDetUpdtReq", true);
                        }
                        //if (flag) {
                        intent.putExtra("chemistname", newChmnm);
                        intent.putExtra("keycontactper", newKeyPerNm);
                        intent.putExtra("phonenumber", newPhnno);
                        intent.putExtra("add1", newAdd1);
                        intent.putExtra("add2", newAdd2);
                        intent.putExtra("add3", newAdd3);
                        intent.putExtra("city", newCity);
                        intent.putExtra("state", newState);
                        intent.putExtra("pincode", newPincode);
                        intent.putExtra("doccntcd", drcntcd);
                        // }
                        intent.putExtra("cntcd", cntcd);
                        intent.putExtra("sttype", sttype);
                        intent.putExtra("menu", menu);

                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ChemistAddEdit.this,
                                R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(chm_addedit_outerlay, "Failed to validate chemist !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                validateChemistAndSendData();
                            }
                        });
                snackbar.show();

            }
        });
    }

    private void disableButtons(){
        updtBtn.setBackgroundColor(getResources().getColor(R.color.textcolorgray));
        updtBtn.setEnabled(false);
        imgBtn.setBackgroundColor(getResources().getColor(R.color.textcolorgray));
        imgBtn.setEnabled(false);
    }

}
