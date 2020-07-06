package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.eis.dailycallregister.Fragment.DCREntry;
import com.eis.dailycallregister.Fragment.Elearning;
import com.eis.dailycallregister.Fragment.HODCREntry;
import com.eis.dailycallregister.Fragment.HOMinutesOJTEntry;
import com.eis.dailycallregister.Fragment.Help;
import com.eis.dailycallregister.Fragment.MTPConfirmation;
import com.eis.dailycallregister.Fragment.PsrListUnderMgr;
import com.eis.dailycallregister.Fragment.Options;
import com.eis.dailycallregister.Fragment.ReportFragment;
import com.eis.dailycallregister.Fragment.UploadVisitingCard;
import com.eis.dailycallregister.Fragment.VisitPlanDocLst;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Pojo.MenuaccessItem;
import com.eis.dailycallregister.R;
import com.eis.dailycallregister.Others.ScheduleOJTNotification;

import java.util.ArrayList;
import java.util.List;

import static com.eis.dailycallregister.Others.Global.menuaccessItemsGlobal;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView ename, hqname, wdate;
    String whichmth = "";
    String getintentval="";
    ClipData.Item imgMsg;
   // ArrayList<String> empacc = new ArrayList<>();
    public static List<MenuaccessItem> menuaccessItems = new ArrayList<>();
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Patanjali 26/04/2020
        if (Integer.parseInt(Global.emplevel) >= 7) {
            new ScheduleOJTNotification(HomeActivity.this).scheduleAlarm();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ename = headerView.findViewById(R.id.name);
        hqname = headerView.findViewById(R.id.hqname);
        wdate = headerView.findViewById(R.id.wdate);

        ename.setText(Global.ename);
        hqname.setText("HQ : " + Global.hname);
        wdate.setText("WRK DATE : " + Global.date);

       /* empacc.clear();
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
        empacc.add("02712");
*/
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getintentval = getIntent().getStringExtra("openfrag");

    }

    @Override
    protected void onResume() {
        super.onResume();

        menuaccessItems = menuaccessItemsGlobal;
        int size= 0;
        if(menuaccessItems!=null)
            size = menuaccessItems.size();
if(size>0) {
    if ((menuaccessItems.get(0).getDcr().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.dcr, false);
    }

    if ((menuaccessItems.get(0).getMgrRcpa().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.mgronly, false);
    }
    if ((menuaccessItems.get(0).getVps().equalsIgnoreCase("N")
            || menuaccessItems.get(0).getReport().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.reports, false);
    }

    if ((menuaccessItems.get(0).getUploadVisitingCard().equalsIgnoreCase("N")
            || menuaccessItems.get(0).getElearning().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.essentials, false);
    }

    if ((menuaccessItems.get(0).getPatientProfile().equalsIgnoreCase("N")
            && menuaccessItems.get(0).getRetailReachOut().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.others, false);
    }

    if ((menuaccessItems.get(0).getAudioMsg().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.temp, false);
    }
    if ((menuaccessItems.get(0).getImgMsg().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.imgmsg, false);
    }
    if ((menuaccessItems.get(0).getSpclRep().equalsIgnoreCase("N"))) {
        navigationView.getMenu().setGroupVisible(R.id.spclRep, false);
    }
    if (menuaccessItems.get(0).getChemAddEdit() != null &&
            menuaccessItems.get(0).getChemAddEdit().equalsIgnoreCase("N")) {
        navigationView.getMenu().setGroupVisible(R.id.chmAddEdit, false);
    }
    if (menuaccessItems.get(0).getSodPhn() != null &&
            menuaccessItems.get(0).getSodPhn().equalsIgnoreCase("N")) {
        navigationView.getMenu().setGroupVisible(R.id.sodPhn, false);
    }
}


        if (getintentval.equalsIgnoreCase("dcr")) {
            displaySelectedScreen(R.id.nav_dcr);
        }else if (getintentval.equalsIgnoreCase("hodcr")) { //added by Patanjali 0103092019
            displaySelectedScreen(R.id.nav_hodcr);
        } else if (getintentval.equalsIgnoreCase("hoojt")) { //added by Patanjali 26042020
            displaySelectedScreen(R.id.nav_ho_ojt);
        } else if (getintentval.equalsIgnoreCase("visitingcard")) {
            displaySelectedScreen(R.id.nav_file_upload);
        } else if (getintentval.equalsIgnoreCase("home")) {
            displaySelectedScreen(R.id.nav_home);
        } else if (getintentval.equalsIgnoreCase("mtp")) {
            displaySelectedScreen(R.id.nav_mtp);
        } else if (getintentval.equalsIgnoreCase("visitplansum")) {
            displaySelectedScreen(R.id.nav_vps);
        } else if (getintentval.equalsIgnoreCase("elearn")) {
            displaySelectedScreen(R.id.nav_eln);
        } else if (getintentval.equalsIgnoreCase("report")) { //added by aniket 21092019
            displaySelectedScreen(R.id.nav_rep);
        }else if (getintentval.equalsIgnoreCase("mgrrcpa")){
            displaySelectedScreen(R.id.nav_mgrrcpa);                  //added by Aniket 26/09/2019
        }else if (getintentval.equalsIgnoreCase("patientpr")){
            displaySelectedScreen(R.id.nav_patientpr);                  //added by Aniket 13/11/2019
        }else if (getintentval.equalsIgnoreCase("chemistpr")){
            displaySelectedScreen(R.id.nav_chemistpr);                  //added by Aniket 14/11/2019
        }else if (getintentval.equalsIgnoreCase("homtp")){
            displaySelectedScreen(R.id.nav_hoMtp);                  //added by Prithvi 16/03/2020
        }else if (getintentval.equalsIgnoreCase("audioMsg")){
            displaySelectedScreen(R.id.nav_audioMsg);                  //added by Prithvi 08/04/2020
        }else if (getintentval.equalsIgnoreCase("imgMsg")){
            displaySelectedScreen(R.id.nav_imgMsg);                  //added by Prithvi 10/04/2020
        }else if (getintentval.equalsIgnoreCase("spclDcr")){
            displaySelectedScreen(R.id.nav_spclRep);                  //added by Prithvi 15/04/2020
        }else if (getintentval.equalsIgnoreCase("chemAddEdit")){
            displaySelectedScreen(R.id.nav_chem_addedit);                  //added by Prithvi 15/04/2020
        }else if (getintentval.equalsIgnoreCase("sodPhn")){
            displaySelectedScreen(R.id.nav_sodPhn);                  //added by Prithvi 15/04/2020
        }else if (getintentval.equalsIgnoreCase("otherCust")){
            displaySelectedScreen(R.id.nav_otherCust);                  //added by Prithvi 15/04/2020
        }else{
            displaySelectedScreen(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setCancelable(true);
            builder.setTitle("EXIT ?");
            builder.setMessage("Do you want to Exit ?");
            builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                    HomeActivity.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

                    //Patanjali 26/04/2020
                    if (Integer.parseInt(Global.emplevel) >= 7) {
                        new ScheduleOJTNotification(HomeActivity.this).scheduleAlarm();
                    }

                    //aniket 30112019
                    Global.password = null;
                    Intent intent = new Intent(HomeActivity.this, LoginScreen.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog2 = builder.create();
            dialog2.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mlogout) {
            logoutAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;


        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new Options();
                break;
            case R.id.nav_dcr:
                if (Global.emplevel.equalsIgnoreCase("1")) {
                    //if(empacc.contains(Global.ecode)) {
                        fragment = new DCREntry();
                    /*}else{
                        new Global().notAllowed(HomeActivity.this);
                    }*/
                } else {
                    new Global().afmNotAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_hodcr: //patanjali 26042020
                if (Integer.parseInt(Global.emplevel) >= 7) {
                    fragment = new HODCREntry();
                } else {
                    new Global().hoAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_ho_ojt:
                if (Integer.parseInt(Global.emplevel) >= 7) {
                    fragment = new HOMinutesOJTEntry();
                } else {
                    new Global().hoAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_file_upload:
                if(Global.emplevel.equalsIgnoreCase("1")) {
                    fragment = new UploadVisitingCard();
                }else{
                    new Global().afmNotAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_mtp:
                //new Global().notAllowed(HomeActivity.this);
                if (Global.emplevel.equalsIgnoreCase("1")) {
                   // if(empacc.contains(Global.ecode)) {
                        fragment = new MTPConfirmation();
                   /* }else{
                        new Global().notAllowed(HomeActivity.this);
                    }*/
                } else {
                    new Global().afmNotAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_vps:
                if(Global.emplevel.equalsIgnoreCase("1")) {
                    fragment = new VisitPlanDocLst();
                }else{
                    new Global().afmNotAllowed(HomeActivity.this);
                }
                break;
            case R.id.nav_eln:
                fragment = new Elearning();
                break;
            case R.id.nav_rep:  //added by aniket 21092019
                fragment=new ReportFragment();
                break;

            case R.id.nav_mgrrcpa:                              //added by aniket  26/09/2019
                if (Global.emplevel.equalsIgnoreCase("1") ) {

                    new Global().psrNotAllowed(HomeActivity.this);
                }else{
                    Bundle data = new Bundle();
                    data.putString("title","RCPA");
                    data.putString("menu","mgrRcpa");
                    fragment = new PsrListUnderMgr();
                    fragment.setArguments(data);
                }
                break;

            case R.id.nav_patientpr:
                if (Global.emplevel.equalsIgnoreCase("1") ) {
                    getintentval = "home";
                    Intent intent = new Intent(HomeActivity.this, PatientDrList.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                }else{
                    new Global().afmNotAllowed(HomeActivity.this);           //added by aniket  26/11/2019
                }
               // new Global().alert(HomeActivity.this,"Coming Soon...!","Access Denied");
                break;

            case R.id.nav_chemistpr:
                //new Global().alert(HomeActivity.this,"Coming Soon...!","Access Denied");
                //todo
                if (Global.emplevel.equalsIgnoreCase("1") ) {
                    getintentval = "home";
                    Intent intent = new Intent(HomeActivity.this, ChemistProfileList.class);
                    intent.putExtra("menu","chemistpr");
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                }else{
                    new Global().afmNotAllowed(HomeActivity.this);                 //added by aniket  26/11/2019
                }
                break;


            case R.id.nav_help:
                //new Global().notAllowed(HomeActivity.this);
                fragment = new Help();
                break;

            case R.id.nav_hoMtp:
                if (Global.emplevel!=null && Integer.parseInt(Global.emplevel)>=7  ) {
                    getintentval = "home";
                    Intent intent = new Intent(HomeActivity.this, HOMtpActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                }else{
                    new Global().alert(HomeActivity.this,"You Are Not Allowed To Access This","NO ACCESS");           //added by aniket  26/11/2019
                }
                break;

            case R.id.nav_audioMsg:
                    getintentval = "home";
                    Intent intent = new Intent(HomeActivity.this, PlayAudio.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                    startActivity(intent, bndlanimation);
                break;

            case R.id.nav_imgMsg:
                getintentval = "home";
                intent = new Intent(HomeActivity.this, ShowImage.class);
                bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                break;

           case R.id.nav_spclRep:
                getintentval = "home";
                intent = new Intent(HomeActivity.this, SpclDcrEntry.class);
                bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                break;

           case R.id.nav_chem_addedit:
                getintentval = "home";
                intent = new Intent(HomeActivity.this, ChemistProfileList.class);
                intent.putExtra("menu","chemAddEdit");
                bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
                break;

           case R.id.nav_sodPhn:
               if (Global.emplevel.equalsIgnoreCase("1") ) { //if lvl is 1 then show Drs List
                   getintentval = "home";
                   intent = new Intent(HomeActivity.this, PatientDrList.class);
                   intent.putExtra("menu","sodPhn");
                   bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                   startActivity(intent, bndlanimation);
               }else{ //if lvl is greater than 1 then show PSR list
                   Bundle data = new Bundle();
                   data.putString("title","Select HQ/PSR");
                   data.putString("menu","sodPhn");
                   fragment = new PsrListUnderMgr();
                   fragment.setArguments(data);
               }
               break;
               case R.id.nav_otherCust:
                   getintentval = "home";
                   intent = new Intent(HomeActivity.this, PatientDrList.class);
                   intent.putExtra("menu","sodPhn");
                   bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                   startActivity(intent, bndlanimation);
               break;

            case R.id.nav_logout:
                logoutAlert();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out);
            ft.replace(R.id.dcrentryscreen, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void logoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("LOGOUT ?");
        builder.setMessage("Are you sure you want to Logout ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Global.password = null;
                        Intent intent = new Intent(HomeActivity.this, LoginScreen.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
                        startActivity(intent, bndlanimation);
                        finish();
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

    public void mtpAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Alert ?");
        builder.setMessage("Which month's MTP you want to view ?");
        builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Global.whichmth = "NEXT";
                //fragment = new MTPConfirmation();
            }
        });
        builder.setNeutralButton("CURRENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //new Global().notAllowed(getActivity());
                Global.whichmth = "CURRENT";
                //fragment = new MTPConfirmation();
            }
        });
        AlertDialog dialog2 = builder.create();
        dialog2.show();
    }

}
