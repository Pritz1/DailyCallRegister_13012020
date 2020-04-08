package com.eis.dailycallregister.Others;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.eis.dailycallregister.Pojo.MenuaccessItem;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static String ecode = null;
    public static String ename = null;
    public static String password = null;
    public static String date = null;
    public static String d1d2 = null;
    public static String dbprefix = null;
    public static String netid = null;
    public static String hname = null;
    public static String dcrdate = null;
    public static String dcrdateday = null;
    public static String dcrdatemonth = null;
    public static String dcrdateyear = null;
    public static String SampleGiftRecOrNot = null;
    public static String workingareaid = null;
    public static String tcpid = null;
    public static String wrktype = null;
    public static String dcrno = null;
    public static boolean dcrdatestatus;
    public static boolean executedcrchecks;
    public static boolean isfirst = true;
    public static String finyear = null;
    public static String emplevel = null;
    public static int misscallpopup = 0;
    public static String whichmth = null;
    public static String FinancialStartDate= null;			//added by aniket 14/09/2019
    public static String psr= null;			//added by Patanjali 09/03/2020
    public static String wrkwith= null;			//added by Patanjali 10/03/2020
    public static String level5= null;			//added by Patanjali 10/03/2020
    public static String level5Name= null;			//added by Patanjali 10/03/2020
    public static String level4= null;			//added by Patanjali 10/03/2020
    public static String level4Name= null;			//added by Patanjali 10/03/2020
    public static String level3= null;			//added by Patanjali 10/03/2020
    public static String level3Name= null;			//added by Patanjali 10/03/2020
    public static String level2= null;			//added by Patanjali 10/03/2020
    public static String level2Name= null;			//added by Patanjali 10/03/2020
    public static String level1= null;			//added by Patanjali 10/03/2020
    public static String level1Name= null;			//added by Patanjali 10/03/2020
    public static String townName= null;			//added by Patanjali 10/03/2020
    public static String townTownId= null;			//added by Patanjali 10/03/2020
    public static int audioPopupShow = 0; //added by prithvi to show audio popup omly once - 07/04/2020


    public static List<MenuaccessItem> menuaccessItemsGlobal = new ArrayList<>();			//added by aniket 30/11/2019


    public void clearGlobal(String mode) {
        if (mode.equalsIgnoreCase("All")) {
            ecode = null;
            ename = null;
            password = null;
            date = null;
            d1d2 = null;
            dbprefix = null;
            netid = null;
            hname = null;
            dcrdate = null;
            dcrdateday = null;
            dcrdatemonth = null;
            dcrdateyear = null;
            SampleGiftRecOrNot = null;
            workingareaid = null;
            tcpid = null;
            wrktype = null;
            dcrno = null;
            finyear = null;
            emplevel = null;
            misscallpopup = 0;
            whichmth = null;
            menuaccessItemsGlobal.clear();
            isfirst = true;
            psr = null;
            wrkwith = null;
            level5 = null;
            level4 = null;
            level3 = null;
            level2 = null;
            level1 = null;
            audioPopupShow = 0;
        } else if (mode.equalsIgnoreCase("DCR")) {
            dcrdate = null;
            dcrdateday = null;
            dcrdatemonth = null;
            dcrdateyear = null;
            SampleGiftRecOrNot = null;
            workingareaid = null;
            tcpid = null;
            wrktype = null;
            dcrno = null;
            finyear = null;
            whichmth = null;
        }
    }


    public static void successDilogue(final Context context, final String result) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dilogue);
        MaterialButton button = dialog.findViewById(R.id.btnsucces);
        AppCompatTextView textView = dialog.findViewById(R.id.successtext);
        textView.setText(result);
        button.setOnClickListener(new View.OnClickListener() {
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

    public void notAllowed(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Coming soon....");
        builder.setMessage("This feature is currently under construction !");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void afmNotAllowed(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage("Only PSR can access this feature !");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void psrNotAllowed(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);                  //added aniket 28/09/2019
        builder.setCancelable(true);
        builder.setMessage("Only AFM and RM can access this feature !");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void alert(final Context context,String msg,String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String getFinancialYr(String logMth, String logYr) {
      //  Log.d("startmonth",startmonth);
        String finStrtMth = FinancialStartDate.split("-")[01];// "04";   added by aniket 14/09/2019
       // Log.d("finStrtMth",finStrtMth);
        //String logMth = "01";
        //String logYr = "2019";

        int endMth = Integer.parseInt(finStrtMth) - 1;
        int strtYr = getFinStrtYr(Integer.parseInt(finStrtMth), Integer.parseInt(logMth), Integer.parseInt(logYr));
        int endYr = 0;

        if (endMth == 0) {
            endMth = 12;
            endYr = strtYr;
        } else {
            endYr = strtYr + 1;
        }
        //System.out.println("Financial Year is :"+finStrtMth+strtYr+" to "+endMth+""+endYr);

        //return strtYr+finStrtMth+"-"+endYr+""+((endMth+"").length()<2 ? "0"+endMth : endMth);
        String syr = Integer.toString(strtYr).substring(2);
        String eyr = Integer.toString(endYr).substring(2);
        return syr + "" + eyr;
    }

    public static String getFullFinancialYr(String logMth, String logYr) {
        String finStrtMth = "04";

        int endMth = Integer.parseInt(finStrtMth) - 1;
        int strtYr = getFinStrtYr(Integer.parseInt(finStrtMth), Integer.parseInt(logMth), Integer.parseInt(logYr));
        int endYr = 0;

        if (endMth == 0) {
            endMth = 12;
            endYr = strtYr;
        } else {
            endYr = strtYr + 1;
        }
        return strtYr + "-" + endYr;
    }


    public static int getFinStrtYr(int strtMth, int logMth, int logYr) {
        if (logMth < strtMth) { //login date=012017 -> (logmth)01 <= (endMth)0
            return (logYr - 1); //2017
        } else { //login date=042017 -> 04>03
            return logYr; //2016
        }
    }

    public static String getFieldName(int mth) {
        String field = "";

        switch (mth) {
            case 1:
                field = "JANCON";
                break;
            case 2:
                field = "FEBCON";
                break;
            case 3:
                field = "MARCON";
                break;
            case 4:
                field = "APRCON";
                break;
            case 5:
                field = "MAYCON";
                break;
            case 6:
                field = "JUNCON";
                break;
            case 7:
                field = "JULCON";
                break;
            case 8:
                field = "AUGCON";
                break;
            case 9:
                field = "SEPCON";
                break;
            case 10:
                field = "OCTCON";
                break;
            case 11:
                field = "NOVCON";
                break;
            case 12:
                field = "DECCON";
                break;
        }

        return field;
    }
}
