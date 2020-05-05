package com.eis.dailycallregister.Activity;

import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.AreaJntWrkRes;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* abc_of_life_during_corona_parthapaul_palsonsderma_5thapr2020 -> abc_of_life_during_corona_5thapr2020
* msg_to_palsons_derma_family_5thmay2020
* */
public class PlayAudio extends AppCompatActivity {

    private ViewDialog progressDialoge;
    private MediaPlayer player;
    private SeekBar seekBar;
    private CardView btnPlay;
    private CardView btnPause;
    private Runnable runnable;
    private Handler handler;
    private TextView marquee;
    private ImageView imgPlay;
    private ImageView imgPause;
    private LinearLayout outerLinearLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_sir_audio_msg_corona);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Msg From Managing Director</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        progressDialoge = new ViewDialog(PlayAudio.this);

        imgPlay = findViewById(R.id.imgPlay);
        imgPause = findViewById(R.id.imgPause);
        seekBar = findViewById(R.id.seekBar);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        outerLinearLay = findViewById(R.id.outerLinearLay);
        marquee = findViewById(R.id.marquee);
        marquee.setSelected(true);

        handler = new Handler();

        if(player ==  null){
            player = MediaPlayer.create(this,R.raw.msg_to_palsons_derma_family_5thmay2020);
            ImageViewCompat.setImageTintList(imgPlay, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textcolorgray)));
            btnPlay.setEnabled(false);
        }

        if(player != null){
            saveRecordInDB();
            //add data to DB Global.audioPopupShow;
        }

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(player.getDuration());
                player.start();
                changeSeekBar();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player != null && fromUser){
                    player.seekTo(progress);
                }else if(player == null){
                    Toast.makeText(PlayAudio.this, "No Media is Being Played!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });

    }

    private void saveRecordInDB() {
        progressDialoge.show();

        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().saveAudioViewDetls(Global.ecode, Global.netid,
                        Global.audioPopupShow, Global.dbprefix, "msg_to_palsons_derma_family_5thmay2020");
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
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
                Snackbar snackbar = Snackbar.make(outerLinearLay, "Failed to save audio view details !", Snackbar.LENGTH_INDEFINITE)
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

    private void changeSeekBar() {

        if(player != null) {
            seekBar.setProgress(player.getCurrentPosition());
            if (player.isPlaying()) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekBar();
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        }
        else{
            Toast.makeText(this,"Media Player Has Stopped",Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View v){
if(player ==  null){
    player = MediaPlayer.create(this,R.raw.msg_to_palsons_derma_family_5thmay2020);
    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlayer();
        }
    });
}
player.start();
        ImageViewCompat.setImageTintList(imgPlay, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textcolorgray)));
        btnPlay.setEnabled(false);

        ImageViewCompat.setImageTintList(imgPause, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        btnPause.setEnabled(true);
        changeSeekBar();

    }

    public void pause(View v){
if(player !=null){
    player.pause();
    ImageViewCompat.setImageTintList(imgPause, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textcolorgray)));
    btnPause.setEnabled(false);

    ImageViewCompat.setImageTintList(imgPlay, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
    btnPlay.setEnabled(true);

}else{
    Toast.makeText(this,"No Audio is Being Played!",Toast.LENGTH_LONG).show();
}
    }

    public void stop(View v){
            stopPlayer();
    }


    private void stopPlayer(){
        if(player != null){
            player.release();
            player=null;

            ImageViewCompat.setImageTintList(imgPlay, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
            btnPlay.setEnabled(true);

            ImageViewCompat.setImageTintList(imgPause, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textcolorgray)));
            btnPause.setEnabled(false);

            Toast.makeText(this,"Media Player Released",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"No Audio is Being Played!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        stopPlayer();
        handler.removeCallbacks(runnable);
        if(player != null)
            player.seekTo(0);
        handler.postDelayed(runnable, 1000);
    }
    @Override
    public void onBackPressed() {
        finish();
        PlayAudio.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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
