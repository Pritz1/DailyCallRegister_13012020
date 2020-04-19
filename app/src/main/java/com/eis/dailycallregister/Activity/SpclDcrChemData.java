package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.eis.dailycallregister.Pojo.CheminawItem;
import com.eis.dailycallregister.Pojo.ChemistListAWRes;
import com.eis.dailycallregister.Pojo.DcrdchlstItem;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.DocinawItem;
import com.eis.dailycallregister.Pojo.DoctorListAWRes;
import com.eis.dailycallregister.Pojo.SpclDcrDcrdChLstRes;
import com.eis.dailycallregister.Pojo.SpclDcrdDrListRes;
import com.eis.dailycallregister.Pojo.SpcldcrdchlstItem;
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

public class SpclDcrChemData extends AppCompatActivity {

    public static final int EDITPHONE_REQUEST = 1;
    private ViewDialog progressDialoge;
    private List<ArealistItem> arealist = new ArrayList<>();
    private Spinner area;
    private List<String> arrayList = new ArrayList<>();
    private LinkedHashMap<String, String> tcpidWrkTypMap = new LinkedHashMap<String, String>();
    private NestedScrollView sv;
    private MaterialButton btnDocSrch;
    private List<CheminawItem> chlstawlist = new ArrayList<>();
    private ArrayList<String> selChList = new ArrayList();
    public static List<SpcldcrdchlstItem> dcrdlst = new ArrayList<>();
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final int READ_TIMEOUT = 90000;
    public static RecyclerView chemListRv;
    RadioGroup rdgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spcl_dcr_doctors_data);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Chemist Calls</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(SpclDcrChemData.this);

        area = findViewById(R.id.areaspinn);
        btnDocSrch = findViewById(R.id.btnDocSrch);
        sv = findViewById(R.id.sv);
        chemListRv = findViewById(R.id.doctorsListRv);
        rdgrp = findViewById(R.id.rdgrp);

        btnDocSrch.setText("Select Chemist");

        dcrdlst.clear();
        getAreaListApiCall();
        btnDocSrchOnClickListner();
        setDcrdChemAdapter();
    }

    private void getAreaListApiCall() {
        progressDialoge.show();

        Call<AreaJntWrkRes> call1 = RetrofitClient
                .getInstance().getApi().getSpclDcrAreaList(Global.ecode, Global.netid, Global.hname,
                        Global.currDate, Global.dbprefix);
        call1.enqueue(new Callback<AreaJntWrkRes>() {
            @Override
            public void onResponse(Call<AreaJntWrkRes> call1, Response<AreaJntWrkRes> response) {
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpclDcrChemData.this, android.R.layout.simple_spinner_item, arrayList);
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
                    getChemListApiCall();
            }
        });
    }

    private void getChemListApiCall() {
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

        Call<ChemistListAWRes> call1 = RetrofitClient
                .getInstance().getApi().getSpclDcrChemList(Global.ecode, Global.netid, Global.tcpid,
                        Global.currDate,sel,Global.dbprefix);
        call1.enqueue(new Callback<ChemistListAWRes>() {
            @Override
            public void onResponse(Call<ChemistListAWRes> call1, Response<ChemistListAWRes> response) {
                progressDialoge.dismiss();
                ChemistListAWRes res = response.body();
                if (!res.isError()) {
                    chlstawlist = res.getCheminaw(); //here, for AW and All both docinaw is used to get data.
                    showPopup();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SpclDcrChemData.this);
                    builder.setCancelable(true);
                    builder.setMessage(res.getErrormsg());
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
            public void onFailure(Call<ChemistListAWRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed To Get Chemist List !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getChemListApiCall();
                            }
                        });
                snackbar.show();
            }
        });

    }

    public void showPopup() {
        final Dialog dialog = new Dialog(SpclDcrChemData.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.jointwrklstpopup);
        TextView heading = dialog.findViewById(R.id.heading);
        heading.setText("Select Chemist");
        CardView cancelbtn = dialog.findViewById(R.id.no);
        CardView submitbtn = dialog.findViewById(R.id.yes);
        RecyclerView rv_list_popup = dialog.findViewById(R.id.jointwrkpopuplist);
        rv_list_popup.setNestedScrollingEnabled(false);
        rv_list_popup.setLayoutManager(new LinearLayoutManager(SpclDcrChemData.this));
        rv_list_popup.setAdapter(new RecyclerView.Adapter() {
                                     @NonNull
                                     @Override
                                     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                         View view = LayoutInflater.from(SpclDcrChemData.this).inflate(R.layout.jointwrkpopuplst_adapter, viewGroup, false);
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
                                         final CheminawItem model = chlstawlist.get(i);
                                         myHolder.drname.setText(model.getSTCD() + " - " + model.getSTNAME());
                                         if (selChList.size() > 0 && selChList.contains(model.getCNTCD())) {
                                             myHolder.ckb.setChecked(true);
                                         }
                                         myHolder.ckb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if (isChecked) {
                                                     selChList.add(model.getCNTCD());
                                                 } else {
                                                     selChList.remove(model.getCNTCD());
                                                 }
                                             }
                                         });

                                     }

                                     @Override
                                     public int getItemCount() {
                                         return chlstawlist.size();
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
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveSelChemInDB();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    public void setDcrdChemAdapter() {
        chemListRv.setNestedScrollingEnabled(false);
        chemListRv.setLayoutManager(new LinearLayoutManager(SpclDcrChemData.this));
        chemListRv.setAdapter(new RecyclerView.Adapter() {
                                   @NonNull
                                   @Override
                                   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                       View view = LayoutInflater.from(SpclDcrChemData.this).inflate(R.layout.spcldcr_chem_det_list_adapter, viewGroup, false);
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
                                       final SpcldcrdchlstItem model = dcrdlst.get(i);
                                       myHolder.stname.setText(model.getStname());

                                       myHolder.pob.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrChemData.this, SpclDcrChemPob.class);
                                               intent.putExtra("cntcd", model.getCntcd());
                                               intent.putExtra("pob", model.getPob());
                                               intent.putExtra("position", Integer.toString(i));
                                               intent.putExtra("chname", "Name - " + model.getStname());
                                               intent.putExtra("custflg", model.getCustflg());
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrChemData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivity(intent, bndlanimation);
                                           }
                                       });

                                       myHolder.isOpen.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrChemData.this, SpclDcrPracticeDet.class);
                                               intent.putExtra("cntcd",model.getCntcd());
                                               intent.putExtra("custflg","C");
                                               intent.putExtra("custName",model.getStname());
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrChemData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivity(intent, bndlanimation);
                                           }
                                       });

                                       myHolder.remarks.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(SpclDcrChemData.this, SpclDcrRemarks.class);
                                               intent.putExtra("cntcd",model.getCntcd());
                                               intent.putExtra("custflg","C");
                                               intent.putExtra("custName",model.getStname());
                                               Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SpclDcrChemData.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                               startActivity(intent, bndlanimation);
                                           }
                                       });

                                       myHolder.deletechem.setOnClickListener(new View.OnClickListener() {
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
                                       TextView stname;
                                       ImageButton isOpen, remarks, deletechem, pob;

                                       public Holder(@NonNull View itemView) {
                                           super(itemView);
                                           stname = itemView.findViewById(R.id.stname);
                                           isOpen = itemView.findViewById(R.id.isOpen);
                                           remarks = itemView.findViewById(R.id.remarks);
                                           deletechem = itemView.findViewById(R.id.deletechem);
                                           pob = itemView.findViewById(R.id.pob);
                                       }
                                   }
                               }
        );
    }

    private void saveSelChemInDB() {

        String tempdcrno = "";
        if (Global.dcrno == null)
            tempdcrno = "";
        else
            tempdcrno = Global.dcrno;

        new SpclDcrChemData.addSelChemInDB().execute(Global.ecode, Global.netid, Global.tcpid,
                Global.currDate, tempdcrno,
                Global.wrktype, selChList.toString(), Global.dbprefix);

    }


    public class addSelChemInDB extends AsyncTask<String, String, String> {
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
                        .appendQueryParameter("custFlg", "C")
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
                    Snackbar snackbar = Snackbar.make(sv, "Chemist(s) added successfully !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    apicall3();
                } else {
                    Snackbar snackbar = Snackbar.make(sv, "Failed to add Chemist(s) !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                        deleteChm(cntcd);
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

    private void deleteChm(String cntcd) {

        progressDialoge.show();
        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().deleteDrChfromSpclDcr(Global.ecode, Global.netid,
                        Global.dcrno, Global.currDate, cntcd,"C",Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                Toast.makeText(SpclDcrChemData.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                if (!res.isError() && res.getErrormsg().equalsIgnoreCase("deleted")) {
                    apicall3();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to delete chem !", Snackbar.LENGTH_LONG)
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
        SpclDcrChemData.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void apicall3() {
        progressDialoge.show();
//Global.dcrno
        retrofit2.Call<SpclDcrDcrdChLstRes> call1 = RetrofitClient
                .getInstance().getApi().spclDcrGetDcrdChem(Global.dcrno, Global.dbprefix,
                        Global.netid, Global.ecode,Global.currDate);
        call1.enqueue(new Callback<SpclDcrDcrdChLstRes>() {
            @Override
            public void onResponse(retrofit2.Call<SpclDcrDcrdChLstRes> call1,
                                   Response<SpclDcrDcrdChLstRes> response) {
                SpclDcrDcrdChLstRes res = response.body();
                progressDialoge.dismiss();
                dcrdlst.clear();
                if (!res.isError()) {
                    selChList.clear();

                    dcrdlst = res.getSpcldcrdchlst();
                    for (int z = 0; z < dcrdlst.size(); z++) {
                        selChList.add(dcrdlst.get(z).getCntcd());
                    }

                    chemListRv.setVisibility(View.VISIBLE);
                    chemListRv.getAdapter().notifyDataSetChanged();

                } else {
                    selChList.clear();
                    chemListRv.setVisibility(View.VISIBLE);
                    chemListRv.getAdapter().notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(sv, "Chemists not selected !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<SpclDcrDcrdChLstRes> call1, Throwable t) {
                progressDialoge.dismiss();
               // Log.d("call1 : ",call1.toString());
               // Log.d("T : ",t.toString());
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

}
