package com.eis.dailycallregister.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
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
import com.eis.dailycallregister.Pojo.ChemistListAWRes;
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

public class SpclDcrPracticeDet extends AppCompatActivity {

    ViewDialog progressDialoge;
    MaterialButton submitbtn;
    RadioGroup rdgrp;
    TextView prCustnameTxt,quesTxt;
    LinearLayout outerll;
    private String cntcd,drname;
    private String custflg;
    private String oldPractFlg;
    String showQPopup = "",yr="",mth="",d1d2 = "";
    int position;

    public List<QuestionslistItem> questionslist = new ArrayList<>();

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
        showQPopup = getIntent().getStringExtra("showQPopup");

        if(getIntent().getStringExtra("position")==null){
            Toast.makeText(this, "Some Problem encountered.Please Restart Reporting!", Toast.LENGTH_SHORT).show();
        }else {
            position = Integer.parseInt(getIntent().getStringExtra("position"));
        }
        prCustnameTxt.setText(drname);
        if(custflg!=null && custflg.equalsIgnoreCase("C"))
        quesTxt.setText("Is Chemist Open?");

        if (Global.hname!=null && Global.hname.indexOf("(")!=-1
                && Global.hname.indexOf(")")!=-1){
            d1d2 = (Global.hname.split("\\(")[1]).split("\\)")[0];
            //Log.d("d1d2 : ",d1d2);
        }

        yr = (Global.currDate).split("-")[0];
        mth = (Global.currDate).split("-")[1];

        if(showQPopup!=null && showQPopup.equalsIgnoreCase("Y")){
            getPopupQuestion();
        }else{
            getRecord();
        }

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

    private void getPopupQuestion() {
        progressDialoge.show();
        //Log.d("drclass//d1d2//cntcd",drclass+"//"+d1d2+"//"+cntcd);
        questionslist.clear();

        retrofit2.Call<GetPopupQuesRes> call1 = RetrofitClient
                .getInstance().getApi().getQuestnsForPopup(Global.ecode, Global.netid, custflg, d1d2,
                        mth, yr, cntcd, Global.dbprefix);
        call1.enqueue(new Callback<GetPopupQuesRes>() {
            @Override
            public void onResponse(retrofit2.Call<GetPopupQuesRes> call1, Response<GetPopupQuesRes> response) {
                progressDialoge.dismiss();
                GetPopupQuesRes res = response.body();
                if (!res.isError()) {
                    questionslist = res.getQuestionslist();
                    showQuesPopup();
                    //Toast.makeText(DocDCRProduct.this, "questions present", Toast.LENGTH_LONG).show();
                }/*else{
                    Toast.makeText(DocDCRProduct.this, "No question to ask", Toast.LENGTH_LONG).show();
                }*/


            }

            @Override
            public void onFailure(Call<GetPopupQuesRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(outerll, "Failed to get questionnaire !", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPopupQuestion();
                            }
                        });
                snackbar.show();
            }
        });
    }

    public void showQuesPopup() {
        final Dialog dialog = new Dialog(SpclDcrPracticeDet.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.jointwrklstpopup);
        TextView heading = dialog.findViewById(R.id.heading);
        heading.setText("Below questions are mandatory");
        CardView cancelbtn = dialog.findViewById(R.id.no);
        CardView submitbtn = dialog.findViewById(R.id.yes);
        RecyclerView rv_list_popup = dialog.findViewById(R.id.jointwrkpopuplist);
        rv_list_popup.setNestedScrollingEnabled(false);
        rv_list_popup.setLayoutManager(new LinearLayoutManager(SpclDcrPracticeDet.this));
        rv_list_popup.setAdapter(new RecyclerView.Adapter() {
                                     @NonNull
                                     @Override
                                     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                         View view = LayoutInflater.from(SpclDcrPracticeDet.this).inflate(R.layout.yes_no_questions_popup_adapter, viewGroup, false);
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
                                         final QuestionslistItem model = questionslist.get(i);
                                         myHolder.donewith.setVisibility(View.GONE);
                                         if(model.getAnsdesc()==null ||
                                                 model.getAnsdesc().equalsIgnoreCase("")) {
                                             myHolder.qty.setVisibility(View.VISIBLE);
                                             myHolder.ans.setVisibility(View.GONE);
                                         }
                                         myHolder.question.setText(model.getQdescrpn());

                                         /*myHolder.qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                             @Override
                                             public void onFocusChange(View view, boolean hasFocus) {
                                                 if (!hasFocus) {
                                                     if (myHolder.qty.getText().toString().equalsIgnoreCase("")) {
                                                         model.setAns("");
                                                     } else {
                                                         if (Integer.parseInt(myHolder.qty.getText().toString()) >= 0) {
                                                             model.setAns(myHolder.qty.getText().toString());
                                                         }
                                                     }
                                                     //Toast.makeText(DocDCRGift.this, "Focus Lose", Toast.LENGTH_SHORT).show();
                                                     InputMethodManager imm = (InputMethodManager) getSystemService(SpclDcrChemPob.this.INPUT_METHOD_SERVICE);
                                                     imm.hideSoftInputFromWindow(nsv.getWindowToken(), 0);
                                                 }

                                             }
                                         });*/

                                         myHolder.qty.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {

                                             }

                                             @Override
                                             public void afterTextChanged(Editable s) {
                                                 /*if (myHolder.qty.getText().toString().equalsIgnoreCase("")) {
                                                     model.setAns("");
                                                 } else {*/
                                                 if(myHolder.qty.getText()!=null)
                                                     model.setAns(myHolder.qty.getText().toString());
                                                 else
                                                     model.setAns("");
                                                 //}
                                                 //Toast.makeText(DocDCRGift.this, "Focus Lose", Toast.LENGTH_SHORT).show();
                                                 InputMethodManager imm = (InputMethodManager) getSystemService(SpclDcrPracticeDet.this.INPUT_METHOD_SERVICE);
                                                 imm.hideSoftInputFromWindow(outerll.getWindowToken(), 0);
                                             }
                                         });

                                     } //onBindViewHolder ends here

                                     @Override
                                     public int getItemCount() {
                                         return questionslist.size();
                                     }

                                     class Holder extends RecyclerView.ViewHolder {
                                         TextView question;
                                         Spinner ans, subans;
                                         LinearLayout donewith;
                                         EditText qty;
                                         public Holder(@NonNull View itemView) {
                                             super(itemView);
                                             question = itemView.findViewById(R.id.question);
                                             ans = itemView.findViewById(R.id.ans);
                                             subans = itemView.findViewById(R.id.subans);
                                             donewith = itemView.findViewById(R.id.donewith);
                                             qty = itemView.findViewById(R.id.qty1);
                                         }
                                     }
                                 }
        );

        rv_list_popup.getAdapter().notifyDataSetChanged();
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.dismiss();
                Toast.makeText(SpclDcrPracticeDet.this, "Answer all questions and submit !", Toast.LENGTH_SHORT).show();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isansgiven = true;
                //boolean issubansgiven = true;
                for (int m = 0; m < questionslist.size(); m++) {
                    if (questionslist.get(m).getAns() == null ||
                            questionslist.get(m).getAns().equalsIgnoreCase("")) {
                        isansgiven = false;
                    }
                }

                if (isansgiven) {
                    //if (issubansgiven) {

                    Gson gson = new GsonBuilder().create();
                    JsonArray myCustomArray = gson.toJsonTree(questionslist).getAsJsonArray();
                    // progressDialoge.dismiss();
                    //Toast.makeText(DocDCRProduct.this, myCustomArray.toString(), Toast.LENGTH_SHORT).show();
                    savePopupQAnsInDB(myCustomArray.toString());
                    dialog.dismiss();
                    /*} else {
                        Toast.makeText(SpclDcrChemPob.this, "First answer all sub question !", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Toast.makeText(SpclDcrPracticeDet.this, "Please answer all questions !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void savePopupQAnsInDB(String json) {
        progressDialoge.show();
        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().savePopupQASpclDcr(Global.ecode, Global.netid, Global.currDate,
                        json, mth, yr, cntcd,"S", Global.dbprefix);//json
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                progressDialoge.dismiss();
                DefaultResponse res = response.body();
                //Log.d("res : ",res.toString() + "--"+res.isError());
                if (!res.isError()) {
                    Toast.makeText(SpclDcrPracticeDet.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                    SpcldcrdchlstItem modelx = SpclDcrChemData.dcrdlst.get(position);
                    modelx.setShowQPopup("N");
                    SpclDcrChemData.chemListRv.getAdapter().notifyDataSetChanged();
                    getRecord();
                }else{
                    getRecord();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(outerll, "Failed to submit questionnaire !", Snackbar.LENGTH_LONG);
                snackbar.show();
                getRecord();
            }
        });
    }

}
