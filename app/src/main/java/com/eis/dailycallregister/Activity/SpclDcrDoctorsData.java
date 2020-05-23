package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.AreaJntWrkRes;
import com.eis.dailycallregister.Pojo.ArealistItem;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.DocinawItem;
import com.eis.dailycallregister.Pojo.DoctorListAWRes;
import com.eis.dailycallregister.Pojo.SpclDcrdDrListRes;
import com.eis.dailycallregister.Pojo.SpcldcrddrlstItem;
import com.eis.dailycallregister.R;

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
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpclDcrDoctorsData extends AppCompatActivity {

    public static final int EDITPHONE_REQUEST = 1;
    private ViewDialog progressDialoge;
    private List<ArealistItem> arealist = new ArrayList<>();
    private Spinner area;
    private List<String> arrayList = new ArrayList<>();
    private LinkedHashMap<String, String> tcpidWrkTypMap = new LinkedHashMap<String, String>();
    private NestedScrollView sv;
    private MaterialButton btnDocSrch;
    private List<DocinawItem> dclstawlist = new ArrayList<>();
    private ArrayList<String> selDrList = new ArrayList();
    public static List<SpcldcrddrlstItem> dcrdlst = new ArrayList<>();
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final int READ_TIMEOUT = 90000;
    RecyclerView doctorsListRv;
    RadioGroup rdgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcl_dcr_doctors_data);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Doctor Calls</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(SpclDcrDoctorsData.this);

        area = findViewById(R.id.areaspinn);
        btnDocSrch = findViewById(R.id.btnDocSrch);
        sv = findViewById(R.id.sv);
        doctorsListRv = findViewById(R.id.doctorsListRv);
        rdgrp = findViewById(R.id.rdgrp);


        dcrdlst.clear();
        getAreaListApiCall();
        btnDocSrchOnClickListner();
        setDcrddocAdapter();
    }

    private void getAreaListApiCall() {
        progressDialoge.show();

        retrofit2.Call<AreaJntWrkRes> call1 = RetrofitClient
                .getInstance().getApi().getSpclDcrAreaList(Global.ecode, Global.netid, Global.hname,
                        Global.currDate, Global.dbprefix);
        call1.enqueue(new Callback<AreaJntWrkRes>() {
            @Override
            public void onResponse(retrofit2.Call<AreaJntWrkRes> call1, Response<AreaJntWrkRes> response) {
                AreaJntWrkRes res = response.body();
                progressDialoge.dismiss();
                arealist = res.getArealist();
                String areaname = "";
                String sel = "";
                if(Global.tcpid!=null && !Global.tcpid.equalsIgnoreCase("")
                    && Global.wrktype!=null && !Global.wrktype.equalsIgnoreCase("") ){
                 sel = Global.tcpid + ":" + Global.wrktype;
                }

                for (int i = 0; i < arealist.size(); i++) {

                    String key = arealist.get(i).getTOWN() + " / " + arealist.get(i).getTOWNID();
                    arrayList.add(key);
                    String value = arealist.get(i).getTCPID() + ":" + arealist.get(i).getWTYPE();
                    if(value.equalsIgnoreCase(sel)){
                        areaname = key;
                    }
                    tcpidWrkTypMap.put(key, value);
                }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpclDcrDoctorsData.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                   area.setAdapter(adapter);
                   area.setSelection(adapter.getPosition(areaname));

                if (Global.dcrno != null && !Global.dcrno.equalsIgnoreCase("")) {
                    apicall3();
                }
            }

            @Override
            public void onFailure(Call<AreaJntWrkRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to get Area List !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getAreaListApiCall();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void btnDocSrchOnClickListner() {
        btnDocSrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getDrsListApiCall();
            }
        });
    }

    private void getDrsListApiCall() {
        progressDialoge.show();
        String selitem = area.getSelectedItem().toString().trim();
        String valuefrmhm = tcpidWrkTypMap.get(selitem);
        String[] valspt = valuefrmhm.split(":");
        Global.tcpid = valspt[0];
        Global.wrktype = valspt[1];

        int selectedId = rdgrp.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        String sel = "";
        if (radioButton.getText().toString().equalsIgnoreCase("Area Wise")) {
            sel = "aw";
        } else {
            sel = "all";
        }

        retrofit2.Call<DoctorListAWRes> call1 = RetrofitClient
                .getInstance().getApi().getSpclDcrDrList(Global.ecode, Global.netid, Global.tcpid,
                        Global.currDate,sel,Global.dbprefix);
        call1.enqueue(new Callback<DoctorListAWRes>() {
            @Override
            public void onResponse(retrofit2.Call<DoctorListAWRes> call1, Response<DoctorListAWRes> response) {
                progressDialoge.dismiss();
                DoctorListAWRes res = response.body();
                if (!res.isError()) {
                    dclstawlist = res.getDocinaw(); //here, for AW and All both docinaw is used to get data.
                    showPopup();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SpclDcrDoctorsData.this);
                    builder.setCancelable(true);
                    //builder.setTitle("LOGOUT ?");
                    builder.setMessage("No doctors available to show for selected criteria !");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<DoctorListAWRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed To Get Doctor List !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getDrsListApiCall();
                            }
                        });
                snackbar.show();
            }
        });

    }

    public void showPopup() {
        final Dialog dialog = new Dialog(SpclDcrDoctorsData.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.jointwrklstpopup);
        TextView heading = dialog.findViewById(R.id.heading);
        heading.setText("Select Doctor");
        CardView cancelbtn = dialog.findViewById(R.id.no);
        CardView submitbtn = dialog.findViewById(R.id.yes);
        RecyclerView rv_list_popup = dialog.findViewById(R.id.jointwrkpopuplist);
        rv_list_popup.setNestedScrollingEnabled(false);
        rv_list_popup.setLayoutManager(new LinearLayoutManager(SpclDcrDoctorsData.this));
        rv_list_popup.setAdapter(new RecyclerView.Adapter() {
                                     @NonNull
                                     @Override
                                     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                         View view = LayoutInflater.from(SpclDcrDoctorsData.this).inflate(R.layout.jointwrkpopuplst_adapter, viewGroup, false);
                                         Holder holder = new Holder(view);
                                         return holder;
                                     }

                                     @Override
                                     public long getItemId(int position) {
                                         return position;
                                     }

                                     @Override
                                     public int getItemViewType(int position) {
                                         return position;
                                     }

                                     @Override
                                     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                                         final Holder myHolder = (Holder) viewHolder;
                                         final DocinawItem model = dclstawlist.get(i);
                                         myHolder.drname.setText(model.getDRCD() + " - " + model.getDRNAME());
                                         Log.d("selDrList :",selDrList.toString());
                                         if (selDrList.size() > 0 && selDrList.contains(model.getCNTCD())) {
                                             myHolder.ckb.setChecked(true);
                                         }
                                         if(model.getVstCrdPrsnt() == null || model.getVstCrdPrsnt().equalsIgnoreCase("N")){
                                             myHolder.ckb.setEnabled(false);
                                         }
                                         myHolder.ckb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if (isChecked) {
                                                     selDrList.add(model.getCNTCD());
                                                 } else {
                                                     selDrList.remove(model.getCNTCD());
                                                 }
                                             }
                                         });
                                     }

                                     @Override
                                     public int getItemCount() {
                                         return dclstawlist.size();
                                     }

                                     class Holder extends RecyclerView.ViewHolder {
                                         TextView drname;
                                         AppCompatCheckBox ckb;

                                         public Holder(@NonNull View itemView) {
                                             super(itemView);
                                             drname = itemView.findViewById(R.id.jnempname);
                                             ckb = itemView.findViewById(R.id.jnwrkchkbx);
                                         }
                                     }
                                 }
        );

        rv_list_popup.getAdapter().notifyDataSetChanged();
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                progressDialoge.dismiss();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveSelDrsInDB();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private void saveSelDrsInDB() {
        //progressDialoge.show();

        String tempdcrno = "";
        if (Global.dcrno == null)
            tempdcrno = "";
        else
            tempdcrno = Global.dcrno;

        new SpclDcrDoctorsData.addSelDrsInDB().execute(Global.ecode, Global.netid, Global.tcpid,
                Global.currDate,tempdcrno, Global.wrktype, selDrList.toString(), Global.dbprefix);
    }

    public class addSelDrsInDB extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialoge.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(RetrofitClient.BASE_URL + "spclDcrSelDrChDataAdd.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("emp", params[0])
                        .appendQueryParameter("netid", params[1])
                        .appendQueryParameter("tcpid", params[2])
                        .appendQueryParameter("cdate", params[3])
                        .appendQueryParameter("dcrno", params[4])
                        .appendQueryParameter("wrkflg", params[5])
                        .appendQueryParameter("selcntcd", params[6])
                        .appendQueryParameter("custFlg", "D")
                        .appendQueryParameter("DBPrefix", params[7]);

                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            progressDialoge.dismiss();
            try {
                JSONObject jobj = new JSONObject(result);

                if (!jobj.getBoolean("error")) {
                    // Log.d("error msg",jobj.getString("errormsg"));
                    Global.dcrno = jobj.getString("errormsg");
                    Snackbar snackbar = Snackbar.make(sv, "Doctor(s) added successfully !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    apicall3();
                } else {
                    Snackbar snackbar = Snackbar.make(sv, "Failed to add doctor(s) !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void apicall3() {
        progressDialoge.show();
//Global.dcrno
        retrofit2.Call<SpclDcrdDrListRes> call1 = RetrofitClient
                .getInstance().getApi().spclDcrGetDcrdDoc(Global.dcrno, Global.dbprefix,
                        Global.netid, Global.ecode,Global.currDate);
        call1.enqueue(new Callback<SpclDcrdDrListRes>() {
            @Override
            public void onResponse(retrofit2.Call<SpclDcrdDrListRes> call1,
                                   Response<SpclDcrdDrListRes> response) {
                SpclDcrdDrListRes res = response.body();
                progressDialoge.dismiss();
                dcrdlst.clear();
                if (!res.isError()) {
                    selDrList.clear();

                    dcrdlst = res.getSpcldcrddrlst();
                    for (int z = 0; z < dcrdlst.size(); z++) {
                        selDrList.add(dcrdlst.get(z).getCntcd());
                    }

                    doctorsListRv.setVisibility(View.VISIBLE);
                    doctorsListRv.getAdapter().notifyDataSetChanged();

                } else {
                    selDrList.clear();
                    doctorsListRv.setVisibility(View.VISIBLE);
                    doctorsListRv.getAdapter().notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(sv, "Doctors not selected !", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<SpclDcrdDrListRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Log.d("call1 : ",call1.toString());
                Log.d("T : ",t.toString());
                Snackbar snackbar = Snackbar.make(sv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recreate();
                                //apicall3();
                            }
                        });
                snackbar.show();
            }
        });

    }

    public void setDcrddocAdapter() {
        doctorsListRv.setNestedScrollingEnabled(false);
        doctorsListRv.setLayoutManager(new LinearLayoutManager(SpclDcrDoctorsData.this));
        doctorsListRv.setAdapter(new RecyclerView.Adapter() {
                                   @NonNull
                                   @Override
                                   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                       View view = LayoutInflater.from(SpclDcrDoctorsData.this).inflate(R.layout.spcldcr_doc_det_adapter, viewGroup, false);
                                       Holder holder = new Holder(view);
                                       return holder;
                                   }

                                   @Override
                                   public long getItemId(int position) {
                                       return position;
                                   }

                                   @Override
                                   public int getItemViewType(int position) {
                                       return position;
                                   }

                                   @Override
                                   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                                       final Holder myHolder = (Holder) viewHolder;
                                       //final SpcldcrddrlstItem model = dcrdlst.get(i);
                                       final SpcldcrddrlstItem model = dcrdlst.get(i);
                                       myHolder.drname.setText("Dr. "+model.getDrname());
                                       myHolder.phno.setText("("+model.getMobileno()+")");

                                       myHolder.isPracticing.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrDoctorsData.this, SpclDcrPracticeDet.class);
                                               intent.putExtra("cntcd",model.getCntcd());
                                               intent.putExtra("drname",model.getDrname());
                                               intent.putExtra("custflg",model.getCustflg());
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrDoctorsData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivity(intent, bndlanimation);
                                           }
                                       });

                                       myHolder.editPhNo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrDoctorsData.this, SpclDcrEditPhoneNo.class);
                                               intent.putExtra("cntcd",model.getCntcd());
                                               intent.putExtra("custName",model.getDrname());
                                               intent.putExtra("custflg",model.getCustflg());
                                               intent.putExtra("phnno",model.getMobileno());
                                               //intent.putExtra("position",i);
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrDoctorsData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivityForResult(intent,EDITPHONE_REQUEST, bndlanimation);
                                           }
                                       });

                                       myHolder.remarks.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrDoctorsData.this, SpclDcrRemarks.class);
                                               intent.putExtra("cntcd",model.getCntcd());
                                               intent.putExtra("custName",model.getDrname());
                                               intent.putExtra("custflg",model.getCustflg());
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrDoctorsData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivity(intent, bndlanimation);
                                           }
                                       });

                                       myHolder.deletedoc.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                              showconfirmationdialog(model.getCntcd());
                                           }
                                       });


                                   }

                                   @Override
                                   public int getItemCount() {
                                       return dcrdlst.size();
                                   }

                                   class Holder extends RecyclerView.ViewHolder {
                                       TextView drname,phno;
                                       ImageButton isPracticing, editPhNo, remarks, deletedoc;

                                       public Holder(@NonNull View itemView) {
                                           super(itemView);
                                           drname = itemView.findViewById(R.id.drname);
                                           phno = itemView.findViewById(R.id.phno);
                                           remarks = itemView.findViewById(R.id.remarks);
                                           deletedoc = itemView.findViewById(R.id.deletedoc);
                                           isPracticing = itemView.findViewById(R.id.isPracticing);
                                           editPhNo = itemView.findViewById(R.id.editPhNo);

                                       }
                                   }
                               }
        );
    }

    private void showconfirmationdialog(final String cntcd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete ?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDr(cntcd);
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

    private void deleteDr(String cntcd) {

        progressDialoge.show();
        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().deleteDrChfromSpclDcr(Global.ecode, Global.netid,
                        Global.dcrno, Global.currDate, cntcd,"D",Global.dbprefix,"");
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                Toast.makeText(SpclDcrDoctorsData.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                if (!res.isError() && !res.getErrormsg().equalsIgnoreCase("")) {
                    apicall3();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to delete dr !", Snackbar.LENGTH_LONG)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //apicall4(serial);
                                recreate();
                            }
                        });
                snackbar.show();
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
        SpclDcrDoctorsData.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDITPHONE_REQUEST  && resultCode == RESULT_OK){
            Log.d("requestCode","requestCode");
            if(data.hasExtra("updatedPhn")) {
                apicall3(); //If phn no is changed then update in 1st screen too.
            }

        }
    }

}
