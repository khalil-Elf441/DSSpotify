package com.example.dsspotify;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    public static MediaPlayer mediaPlayer = null;
    public static TextView txtview_titre = null;
    public static TextView txtview_artist = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().hide();

         txtview_titre = findViewById(R.id.txtview_titre);
         txtview_artist = findViewById(R.id.txtview_artist);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_home , R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);



        //
        SeekBar seekBar_mediaplayer = findViewById(R.id.seekBar_mediaplayer);

        //
        mediaPlayer = MediaPlayer.create(this, R.raw.lensko_titsepoken_2015);

        seekBar_mediaplayer.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar_mediaplayer.setProgress(mediaPlayer.getCurrentPosition());

            }
        }, 0, 1000);

        seekBar_mediaplayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
               // seekBar_mediaplayer.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        //
        ImageView imgbtn_main_play = findViewById(R.id.imgbtn_main_play);
        imgbtn_main_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });


        ImageView imgbtn_main_stop = findViewById(R.id.imgbtn_main_stop);
        imgbtn_main_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });

        ImageView imgbtn_main_reset = findViewById(R.id.imgbtn_main_reset);
        imgbtn_main_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mediaPlayer.seekTo(0);
                mediaPlayer.start();

            }
        });











    }



    public void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, 666);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // String text = result.get(0);
                   // txtOutput.setText(text);
                }
                break;
            }
        }
    }



}