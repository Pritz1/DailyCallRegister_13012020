package com.eis.dailycallregister.Fragment;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.cleveroad.adaptivetablelayout.OnItemLongClickListener;
import com.eis.dailycallregister.Activity.HomeActivity;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.SampleLinkedTableAdapter;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.GetRetailerAlertCnt;
import com.eis.dailycallregister.Pojo.MenuaccessItem;
import com.eis.dailycallregister.Pojo.MissCallDocsRes;
import com.eis.dailycallregister.Pojo.MisscalldrsItem;
import com.eis.dailycallregister.Pojo.PropsItem;
import com.eis.dailycallregister.Pojo.RetailerAndOptions;
import com.eis.dailycallregister.Pojo.RetailerscntItem;
import com.eis.dailycallregister.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eis.dailycallregister.Others.Global.dbprefix;
import static com.eis.dailycallregister.Others.Global.menuaccessItemsGlobal;


public class Options extends Fragment {

    MaterialButton dcr, mtp, uploadcard, vps, elearn,
            report, mgrrcpa, patientpr, chemistpr, hodcr, homtp, spclDcr,ho_ojt,chemAddEdit;//report --> added by aniket 21/09/19 --> dcrrcpa
    ViewDialog progressDialoge;
    List<MisscalldrsItem> misscall = new ArrayList<>();
    LinearLayout menuoptions;
    AdaptiveTableLayout mTableLayout;
    String[][] misseddr;
    View view;
    String checkmtp = "";
    public static List<MenuaccessItem> menuaccessItems = new ArrayList<>();
    public List<RetailerscntItem> retailerscnt = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_options, container, false);

        dcr = view.findViewById(R.id.dcr);
        mtp = view.findViewById(R.id.mtp);
        vps = view.findViewById(R.id.vps);
        menuoptions = view.findViewById(R.id.menuoptions);
        progressDialoge = new ViewDialog(getActivity());
        mTableLayout = view.findViewById(R.id.tableLayout);
        uploadcard = view.findViewById(R.id.uploadcard);
        elearn = view.findViewById(R.id.elearn);
        report = view.findViewById(R.id.report); //added by aniket
        mgrrcpa = view.findViewById(R.id.mgrrcpa); //added by aniket
        patientpr = view.findViewById(R.id.patientpr); //added by aniket
        chemistpr = view.findViewById(R.id.chemistpr); //added by aniket
        hodcr = view.findViewById(R.id.hodcr); //patanjali
        homtp = view.findViewById(R.id.homtp);
		ho_ojt = view.findViewById(R.id.ho_ojt); // added by patanjali
        spclDcr = view.findViewById(R.id.spclRep);
        chemAddEdit = view.findViewById(R.id.chemAddEdit);

        /*empacc.clear();
        //CD
        empacc.add("02680");
        empacc.add("02684");
        empacc.add("02957");
        empacc.add("03069");
        empacc.add("01804");
        empacc.add("02274");
        empacc.add("02681");
        empacc.add("02956");
        empacc.add("01652");
        empacc.add("02706");
        empacc.add("02944");
        empacc.add("03196");
        empacc.add("03340");
        empacc.add("03358");
        empacc.add("01973");
        empacc.add("02901");
        empacc.add("03239");
        empacc.add("03260");
        empacc.add("03339");
        empacc.add("03402");

        //AB
        empacc.add("02654");
        empacc.add("02663");
        empacc.add("02672");
        empacc.add("02782");
        empacc.add("03286");
        empacc.add("01637");
        empacc.add("00475");
        empacc.add("01941");
        empacc.add("02364");
        empacc.add("02366");
        empacc.add("03293");
        empacc.add("00431");
        empacc.add("01575");
        empacc.add("02985");
        empacc.add("03118");
        empacc.add("03151");
        empacc.add("03202");
        empacc.add("03303");

        //kol team
        empacc.add("01349");
        empacc.add("01511");
        empacc.add("01723");
        empacc.add("01809");
        empacc.add("02042");
        empacc.add("02712");*/

        //getProps();


        menuaccessItems = menuaccessItemsGlobal;

        if (menuaccessItems != null && menuaccessItems.size() > 0) {

            if (menuaccessItems.get(0).getDcr().equalsIgnoreCase("Y")) {
                dcr.setVisibility(View.VISIBLE);
            } else {
                dcr.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getHodcr().equalsIgnoreCase("Y")) {
                hodcr.setVisibility(View.VISIBLE);
            } else {
                hodcr.setVisibility(View.GONE);
            }

            if(menuaccessItems.get(0).getHoojt().equalsIgnoreCase("Y")){
                ho_ojt.setVisibility(View.VISIBLE);
            }else{
                ho_ojt.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getMtp().equalsIgnoreCase("Y")) {
                mtp.setVisibility(View.VISIBLE);
            } else {
                mtp.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getUploadVisitingCard().equalsIgnoreCase("Y")) {
                uploadcard.setVisibility(View.VISIBLE);
            } else {
                uploadcard.setVisibility(View.GONE);
            }


            if (menuaccessItems.get(0).getVps().equalsIgnoreCase("Y")) {
                vps.setVisibility(View.VISIBLE);
            } else {
                vps.setVisibility(View.GONE);
            }


            if (menuaccessItems.get(0).getElearning().equalsIgnoreCase("Y")) {
                elearn.setVisibility(View.VISIBLE);
            } else {
                elearn.setVisibility(View.GONE);
            }


            if (menuaccessItems.get(0).getReport().equalsIgnoreCase("Y")) {
                report.setVisibility(View.VISIBLE);
            } else {
                report.setVisibility(View.GONE);
            }


            if (menuaccessItems.get(0).getPatientProfile().equalsIgnoreCase("Y")) {
                patientpr.setVisibility(View.VISIBLE);
            } else {
                patientpr.setVisibility(View.GONE);
            }


            if (menuaccessItems.get(0).getRetailReachOut().equalsIgnoreCase("Y")) {
                chemistpr.setVisibility(View.VISIBLE);
            } else {
                chemistpr.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getMgrRcpa().equalsIgnoreCase("Y")) {
                mgrrcpa.setVisibility(View.VISIBLE);
            } else {
                mgrrcpa.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getHoMtp().equalsIgnoreCase("Y")) {
                homtp.setVisibility(View.VISIBLE);
            } else {
                homtp.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getSpclRep().equalsIgnoreCase("Y")) {
                spclDcr.setVisibility(View.VISIBLE);
            } else {
                spclDcr.setVisibility(View.GONE);
            }

            if (menuaccessItems.get(0).getChemAddEdit() != null &&
                    menuaccessItems.get(0).getChemAddEdit().equalsIgnoreCase("Y")) {
                chemAddEdit.setVisibility(View.VISIBLE);
            } else {
                chemAddEdit.setVisibility(View.GONE);
            }


        }

        Global.whichmth = null;
        dcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Global().notAllowed(getActivity());
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    //if(empacc.contains(Global.ecode)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "dcr");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                    /*}else{
                        new Global().notAllowed(getActivity());
                    }*/
                } else {
                    new Global().afmNotAllowed(getActivity());
                }
            }
        });
        hodcr.setOnClickListener(new View.OnClickListener() {//patanjali
            @Override
            public void onClick(View v) {
                //new Global().notAllowed(getActivity());
                if (Integer.parseInt(Global.emplevel) >= 7) {
                    //if(empacc.contains(Global.ecode)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "hodcr");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                    /*}else{
                        new Global().notAllowed(getActivity());
                    }*/
                } else {
                    new Global().afmNotAllowed(getActivity());
                }
            }
        });
        ho_ojt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Global().notAllowed(getActivity());
                if (Integer.parseInt(Global.emplevel) >= 7 ) {
                    //if(empacc.contains(Global.ecode)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "hoojt");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                    /*}else{
                        new Global().notAllowed(getActivity());
                    }*/
                } else {
                    new Global().hoAllowed(getActivity());
                }
            }
        });
        uploadcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "visitingcard");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                } else {
                    new Global().afmNotAllowed(getActivity());
                }

            }
        });

        vps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "visitplansum");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                } else {
                    new Global().afmNotAllowed(getActivity());
                }

            }
        });

        elearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "elearn");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();
            }
        });

        report.setOnClickListener(new View.OnClickListener() { //added by aniket 21/09/19
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "report");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();
            }
        });

        mgrrcpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                //added by Aniket 26/09/2019
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    new Global().psrNotAllowed(getActivity());
                } else {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "mgrrcpa");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();

                }
            }
        });
        //added by Aniket 13/11/2019
        patientpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "patientpr");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();
            }
        });
        //added by Aniket 14/11/2019
        chemistpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "chemistpr");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();

            }
        });

        homtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "homtp");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();

            }
        });

        spclDcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "spclDcr");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();

            }
        });

//added by prithvi - 08/05/2020
        chemAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("ecode", Global.ecode);
                intent.putExtra("date", Global.date);
                intent.putExtra("dbprefix", Global.dbprefix);
                intent.putExtra("openfrag", "chemAddEdit");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                getActivity().finish();

            }
        });


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Alert ?");
                builder.setMessage("Which month of MTP you wants to view ?");
                builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Global.whichmth = "NEXT";
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        intent.putExtra("ecode", Global.ecode);
                        intent.putExtra("date",Global.date);
                        intent.putExtra("dbprefix",Global.dbprefix);
                        intent.putExtra("openfrag","mtp");
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                        getActivity().finish();
                    }
                });
                builder.setNeutralButton("CURRENT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new Global().notAllowed(getActivity());
                        Global.whichmth = "CURRENT";
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        intent.putExtra("ecode", Global.ecode);
                        intent.putExtra("date",Global.date);
                        intent.putExtra("dbprefix",Global.dbprefix);
                        intent.putExtra("openfrag","mtp");
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                        getActivity().finish();
                    }
                });
                AlertDialog dialog2 = builder.create();
                dialog2.show();*/
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    // if (empacc.contains(Global.ecode)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("ecode", Global.ecode);
                    intent.putExtra("date", Global.date);
                    intent.putExtra("dbprefix", Global.dbprefix);
                    intent.putExtra("openfrag", "mtp");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                    getActivity().finish();
                    /*} else {
                        new Global().notAllowed(getActivity());
                    }*/
                } else {
                    new Global().afmNotAllowed(getActivity());
                }
            }
        });

        if(!dbprefix.equalsIgnoreCase("Aqua-Basale")
        && !dbprefix.equalsIgnoreCase("Chroma-Dayon"))
        {
            Global.misscallpopup=1;
        }

        if (menuaccessItems.get(0).getRetailerAlert().equalsIgnoreCase("Y") && Global.isfirst) {
            getRetailerAlert();
        }

        //call image activity here, must appear after retaileralert;
        else if (menuaccessItems.get(0).getImgMsg().equalsIgnoreCase("Y")
                && Global.imgPopupShow == 0) {

            showImgMsgDialog();
        }

//call audio activity here, must appear after image alert;
        else if (menuaccessItems.get(0).getAudioMsg()!=null &&
                menuaccessItems.get(0).getAudioMsg().equalsIgnoreCase("Y")
                && Global.audioPopupShow == 0) {

            showAudioMsgDialog();
        }

        
        else if (Global.misscallpopup == 0 ) {

            callForMissCalls();

        }

        /*String valid_until = "19";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (new Date().after(strDate)) {
            Toast.makeText(getActivity(), "Show mtp", Toast.LENGTH_LONG).show();
        }*/


        return view;

    }

    private void getMissCalls() {

        String[] newdate = Global.date.split("-");
        progressDialoge.show();
        Call<MissCallDocsRes> call = RetrofitClient.getInstance()
                .getApi().DrMissCallAlert(Global.ecode, Global.netid, newdate[0], newdate[1], checkmtp, Global.dbprefix);
        call.enqueue(new Callback<MissCallDocsRes>() {
            @Override
            public void onResponse(Call<MissCallDocsRes> call, Response<MissCallDocsRes> response) {
                MissCallDocsRes res = response.body();
                Global.misscallpopup = 1;
                // && (Global.ecode.equalsIgnoreCase("01349") || Global.ecode.equalsIgnoreCase("01511") || Global.ecode.equalsIgnoreCase("01723") || Global.ecode.equalsIgnoreCase("01809") || Global.ecode.equalsIgnoreCase("02042") || Global.ecode.equalsIgnoreCase("02712"))
                if (res.isMtpflg()) { //&& (empacc.contains(Global.ecode))
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(true);
                    builder.setMessage("Next month MTP is ready to view.");
                    builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Global.whichmth = "NEXT";
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra("ecode", Global.ecode);
                            intent.putExtra("date", Global.date);
                            intent.putExtra("dbprefix", Global.dbprefix);
                            intent.putExtra("openfrag", "mtp");
                            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                            startActivity(intent, bndlanimation);
                            getActivity().finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //do nothing
                        }
                    });
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                }

                if (!res.isError()) {
                    misscall = res.getMisscalldrs();
                    misseddr = new String[misscall.size()][3];
                    for (int i = 0; i < misscall.size(); i++) {
                        MisscalldrsItem temp = misscall.get(i);
                        misseddr[i][0] = temp.getTOWN();
                        misseddr[i][1] = temp.getDRNAMES();
                        misseddr[i][2] = temp.getTOTAL();
                    }

                    if (misscall.size() > 1)
                        detailedTablePopup(getActivity(), "MISSED CALL DOCTORS", misseddr);
                    /*else{
                        Snackbar.make(menuoptions, "Doctors not missed yet.", Snackbar.LENGTH_LONG).show();
                    }*/

                    progressDialoge.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MissCallDocsRes> call, Throwable t) {
                progressDialoge.dismiss();
                Snackbar.make(menuoptions, "Failed to get miss calls !", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void detailedTablePopup(final Context context, String stringmsg, String[][] aary) {
        final String[][] dataarray;
        dataarray = new String[aary.length][];
        for (int i = 0; i < dataarray.length; ++i) {
            dataarray[i] = new String[aary[i].length];
            for (int j = 0; j < dataarray[i].length; ++j) {
                dataarray[i][j] = aary[i][j];
            }
        }
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.detailed_table_popup);
        TextView textView = dialog.findViewById(R.id.title);
        textView.setText(stringmsg);
        ImageButton goback = dialog.findViewById(R.id.goback);
        AdaptiveTableLayout mTableLayout3 = dialog.findViewById(R.id.dettablelayout);
        LinkedAdaptiveTableAdapter mTableAdapter3 = new SampleLinkedTableAdapter(context, dataarray, "2");
        mTableAdapter3.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {
                dialogCloseType(context, dataarray[row][column]);
            }

            @Override
            public void onRowHeaderClick(int row) {
                dialogCloseType(context, dataarray[row][0]);
            }

            @Override
            public void onColumnHeaderClick(int column) {
                dialogCloseType(context, dataarray[0][column]);
            }

            @Override
            public void onLeftTopHeaderClick() {
                dialogCloseType(context, dataarray[0][0]);
            }
        });
        mTableAdapter3.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int row, int column) {

            }

            @Override
            public void onLeftTopHeaderLongClick() {

            }
        });
        mTableLayout3.setAdapter(mTableAdapter3);
        mTableAdapter3.notifyDataSetChanged();

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void dialogCloseType(final Context context, String stringmsg) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_item);
        TextView textView = dialog.findViewById(R.id.tvTitle);
        AppCompatButton bPositive = dialog.findViewById(R.id.bPositive);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(stringmsg);
        bPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void getRetailerAlert() {
        //Log.d("getRetailerAlert","getRetailerAlert");
        progressDialoge.show();
        Call<GetRetailerAlertCnt> call = RetrofitClient.getInstance().getApi().getRetailerCntAlert(Global.netid, Global.dbprefix);
        call.enqueue(new Callback<GetRetailerAlertCnt>() {
            @Override
            public void onResponse(Call<GetRetailerAlertCnt> call, Response<GetRetailerAlertCnt> response) {
                GetRetailerAlertCnt res = response.body();
                // Log.d("res", res.toString());
                if (!res.isError()) {
                    // Log.d("retailerscnt", retailerscnt.toString());
                    progressDialoge.dismiss();
                    retailerscnt = res.getRetailerscnt();
                    RetailerscntItem rct = retailerscnt.get(0);

                    int totaldrs = Integer.parseInt((rct.getTot() != null) ? rct.getTot() : 0 + "");
                    int totalupl = Integer.parseInt((rct.getTotUpld() != null) ? rct.getTotUpld() : 0 + "");
                    int totalnotupl = totaldrs - totalupl;

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.retail_reach_out_alert);
                    ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                    TextView textView1 = dialog.findViewById(R.id.txt1);
                    TextView textView2 = dialog.findViewById(R.id.txt2);
                    TextView textView3 = dialog.findViewById(R.id.txt3);
                    TextView textView4 = dialog.findViewById(R.id.txt4);


                    MaterialButton bPositive = dialog.findViewById(R.id.ok);
                    textView1.setText("Retail Reachout Alert");
                    if (totalnotupl == 0) {
                        textView2.setText("You have done a great job !");
                    } else if (totalnotupl > 0) {
                        textView2.setText(totalnotupl + " chemists more to go !");
                    }
                    textView3.setText("Done : " + totalupl);
                    textView4.setText("Total : " + totaldrs);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.junglegreen)));
                    } else {
                        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.junglegreen), PorterDuff.Mode.SRC_IN);
                    }
                    progressBar.setMax(totaldrs);
                    progressBar.setProgress(totalupl);
                    bPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //showAudioMsgDialog();
                            showImgMsgDialog();
                        }
                    });


                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);

                    Global.isfirst = false;
                }
                else{
                    progressDialoge.dismiss();
                    Toast.makeText(getActivity(), "Problem Occurred While Getting Retailer Reachout Status!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRetailerAlertCnt> call, Throwable t) {
                //Log.d("onFailure", "onFailure");
                progressDialoge.dismiss();
                Toast.makeText(getActivity(), "Failed to get Retailer Reachout Status!", Toast.LENGTH_SHORT).show();
                //showAudioMsgDialog();
                    showImgMsgDialog();
            }
        });

    }
    private void showAudioMsgDialog(){

        if(menuaccessItems.get(0).getAudioMsg()!=null &&
                menuaccessItems.get(0).getAudioMsg().equalsIgnoreCase("Y")) {
            progressDialoge.show();
            //Log.d("Global.ecode",Global.ecode);
            //Log.d("Global.dbprefix",Global.dbprefix);

            Call<DefaultResponse> call = RetrofitClient.getInstance()
                    .getApi().getAudioMsgViewDet(Global.ecode, Global.dbprefix,
                            "msg_to_palsons_derma_family_5thmay2020");
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call,
                                       Response<DefaultResponse> response) {
                    DefaultResponse res = response.body();

                    if (res.getErrormsg() != null && (res.getErrormsg().equalsIgnoreCase("0")
                            || res.getErrormsg().equalsIgnoreCase("1"))) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setCancelable(true);
                        LayoutInflater factory = LayoutInflater.from(getActivity());
                        final View view = factory.inflate(R.layout.audio_img_for_alert, null);
                        builder.setView(view);
                        // builder.setMessage("Listen to what our MD Mr. Partha Paul has to say in challenging times as such");
                        builder.setPositiveButton(Html.fromHtml("<b>Listen<b>"), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.putExtra("ecode", Global.ecode);
                                intent.putExtra("date", Global.date);
                                intent.putExtra("dbprefix", Global.dbprefix);
                                intent.putExtra("openfrag", "audioMsg");
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                startActivity(intent, bndlanimation);
                                getActivity().finish();
                            }
                        });
                        builder.setNegativeButton(Html.fromHtml("<b>Skip<b>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callForMissCalls();
                                //do nothing
                            }
                        });
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
                        Global.audioPopupShow = 1;
                        progressDialoge.dismiss();
                    } else if (res.getErrormsg() != null && res.getErrormsg().equalsIgnoreCase("2")) {
                        progressDialoge.dismiss();
                        Global.audioPopupShow = 2;
                        callForMissCalls();
                    } else {
                        progressDialoge.dismiss();
                        Snackbar.make(menuoptions, "Some Problem Occurred While Getting Audio Msg Details !", Snackbar.LENGTH_LONG).show();
                        callForMissCalls();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    progressDialoge.dismiss();
                    Snackbar.make(menuoptions, "Failed to Get Audio Msg Details !", Snackbar.LENGTH_LONG).show();
                    callForMissCalls();
                }
            });
        }else if(Global.misscallpopup == 0){
            callForMissCalls();
        }
    }

    private void callForMissCalls(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //String datex = new SimpleDateFormat("yyyy-MM-15", Locale.getDefault()).format(new Date());
        String datex = new SimpleDateFormat("yyyy-MM-15", Locale.getDefault()).format(new Date());
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(datex);
            date2 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        calendar1.setTime(date1);
        calendar2.setTime(date2);
        //Toast.makeText(getActivity(),datex +"///"+ date , Toast.LENGTH_LONG).show();
        if (calendar2.compareTo(calendar1) < 0) {
            checkmtp = "N";
            //Toast.makeText(getActivity(), "Do not show", Toast.LENGTH_LONG).show();
        } else {
            checkmtp = "Y";
            //Toast.makeText(getActivity(), "show MTP", Toast.LENGTH_LONG).show();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setMessage("Next month MTP is ready to view.");
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Global.whichmth = "NEXT";
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        intent.putExtra("ecode", Global.ecode);
                        intent.putExtra("date",Global.date);
                        intent.putExtra("dbprefix",Global.dbprefix);
                        intent.putExtra("openfrag","mtp");
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                AlertDialog dialog2 = builder.create();
                dialog2.show();*/
        }

        if (Global.emplevel.equalsIgnoreCase("1") && Global.misscallpopup!=1) {
            getMissCalls();
        }
    }

    private void showImgMsgDialog(){

        if(menuaccessItems.get(0).getImgMsg()!=null &&
                menuaccessItems.get(0).getImgMsg().equalsIgnoreCase("Y")) {
            progressDialoge.show();
            Call<DefaultResponse> call = RetrofitClient.getInstance()
                    .getApi().getImgMsgViewDet(Global.ecode, "NEOLAYR-e", Global.dbprefix);


            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse res = response.body();

                    if (res.getErrormsg() != null && (res.getErrormsg().equalsIgnoreCase("0")
                            || res.getErrormsg().equalsIgnoreCase("1"))) {

                        progressDialoge.dismiss();

                        final Dialog dialog = new Dialog(getActivity());
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.image_alert);

                        MaterialButton bPositive = dialog.findViewById(R.id.closebtn);
                        final LinearLayout llOuter = dialog.findViewById(R.id.llOuter);

                        bPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                    showAudioMsgDialog();
                            }
                        });


                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.show();
                        dialog.getWindow().setAttributes(lp);
                        Global.imgPopupShow = 1;
                        saveRecord();
                    } else if (res.getErrormsg() != null && res.getErrormsg().equalsIgnoreCase("2")) {
                        progressDialoge.dismiss();
                        Global.imgPopupShow = 2;
                            showAudioMsgDialog();
                    } else {
                        progressDialoge.dismiss();
                        Snackbar.make(menuoptions, "Some Problem Occurred While Getting Poster Viewed Details !", Snackbar.LENGTH_LONG).show();
                            showAudioMsgDialog();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    //Log.d("onFailure", "onFailure");
                    progressDialoge.dismiss();
                    Toast.makeText(getActivity(), "Failed to get Retailer Reachout Status!", Toast.LENGTH_SHORT).show();
                        showAudioMsgDialog();
                }
            });
        }else{
            showAudioMsgDialog();
        }
    }

    private void saveRecord(){
        progressDialoge.show();

        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().saveImgViewDetls(Global.ecode, Global.netid,Global.audioPopupShow,
                        "NEOLAYR-e",Global.dbprefix);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                if(res.isError()){
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                if (menuaccessItems.get(0).getAudioMsg()!=null &&
                        menuaccessItems.get(0).getAudioMsg().equalsIgnoreCase("Y")) {
                    showAudioMsgDialog();
                }
            }
        });

    }

}

