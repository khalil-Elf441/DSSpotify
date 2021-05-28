package com.example.dsspotify;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dsspotify.MorceauManager.Mp3filesManagerPrx;
import com.example.dsspotify.ui.Speech.Model.Message;
import com.example.dsspotify.ui.Speech.SpeechFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    //gestion des mediaplayes dans l'activite main
    public static MediaPlayer mediaPlayer = null;
    public static TextView txtview_titre = null;
    public static TextView txtview_artist = null;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().hide();

        //gestion de la manipulation entre les diffrentes pages de l'application
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_home , R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);

        //definition du resource Xml de chaque page
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.navigation_dashboard:
                        navController.navigate(R.id.navigation_dashboard);
                        break;
                    case R.id.navigation_notifications:
                        navController.navigate(R.id.navigation_home);
                        break;
                        //si l'utilisation click sur Speech la boite de dialoque Google Speech apparut
                    case R.id.navigation_home:
                        // test d'acces
                       // Log.wtf("hello","hello");
                        SpeechToText();
                        break;

                }
                return true;
            }
        });

        //definition des resources dans le fichier XML
         txtview_titre = findViewById(R.id.txtview_titre);
         txtview_artist = findViewById(R.id.txtview_artist);





        // la bar le lancement du morceau de musique
        SeekBar seekBar_mediaplayer = findViewById(R.id.seekBar_mediaplayer);

        //test local de media player
        //mediaPlayer = MediaPlayer.create(this, R.raw.maroon_5_memories);



        //source https://developer.android.com/guide/topics/media/mediaplayer
        //streaming a partir du serveur en se basant sur la documantation du Google Speech
        String url = "http://10.188.38.53:5555"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //mise a jour le la bar a chaque minute de l'avanemcent de la musique
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



        //boutton play
        ImageView imgbtn_main_play = findViewById(R.id.imgbtn_main_play);
        imgbtn_main_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        //button Stop
        ImageView imgbtn_main_stop = findViewById(R.id.imgbtn_main_stop);
        imgbtn_main_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });


        //button arreter et recommancer
        ImageView imgbtn_main_reset = findViewById(R.id.imgbtn_main_reset);
        imgbtn_main_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mediaPlayer.seekTo(0);
                mediaPlayer.start();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.s);
        //fragment.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    String text = result.get(0);
                    Log.wtf("Speech",text);


                    //lancement du transcription a partir su serveur
                    calltranscriptserver(text);

                    /*
                    //test local du transcriptions
                    SpeechFragment mapFragment = (SpeechFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_home);
                    Commande commande = TranscriptService.analyser(text);

                    //if(!commande.getDictionnaireWord().equals("nan") || !commande.getTargetMusic().equals("nan")){
                        //(mapFragment).adapter.addToStart(new Message("5",commande.getDictionnaireWord() +" "+commande.getTargetMusic(), new Date(),"khalil"),true);
                    //}else{

                      //  (mapFragment).adapter.addToStart(new Message("5","Oups, je vous ai pas bien entendu pouvez répéter s'il vous plaît ?", new Date(),"Server"),true);

                    //}
                     */

                }
                break;
            }
        }
    }
    //Solution 1 : google speech pour record des audios
    // source : https://developer.android.com/reference/android/speech/RecognizerIntent
    public void SpeechToText() {
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

    /*
    *
    *         map.put("DictionnaireWord",commande.getDictionnaireWord());
        map.put("targetMusic",commande.getTargetMusic());
    * */


    //fonction d'appel au service de transcription
    public void calltranscriptserver(String userinfo){

       // String url = "https://127.0.0.1:8080/api/users/transcript";
       // String url = "http://localhost:8080/api/users/transcript";
        String url = "http://10.188.38.53:8080/api/users/transcript";


        RequestQueue mQueue = Volley.newRequestQueue(this);

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userText", userinfo);

        JsonObjectRequest ObjectRequest = new JsonObjectRequest(
                 Request.Method.GET,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response du Api",response.toString());

                        try {
                            String target = response.getString("targetMusic");
                            String DictionnaireWord = response.getString("DictionnaireWord");

                            switch (DictionnaireWord){
                                case "jouer":
                                    StartStreaming(target);
                                    break;
                                case "stopper":
                                    StopStreaming(target);
                                    break;
                                case "continuer":
                                    ResumeStreaming(target);
                                    break;
                            }

                            //ajouter les message de discussion entre le serveur et le client
                            SpeechFragment mapFragment = (SpeechFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_home);
                            (mapFragment).adapter.addToStart(new Message("targetapi5",DictionnaireWord +" "+target, new Date(),"khalil"),true);
                            (mapFragment).adapter.addToStart(new Message("responseapi5"+target,"ok", new Date(),"Server"),true);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response du Api",error.toString());
                error.printStackTrace();
            }
        }
        );
        mQueue.add(ObjectRequest);

    }


    //gestion de la commucation entre l'application et Zeroc Ice
    public static class IceCommunication extends AsyncTask<String, Void, String> {

        String clip;
        String action;

        public IceCommunication(String clip, String action) {

            this.clip =  clip;
        }


        @Override
        protected String doInBackground(String... params) {

            String[] Iceparams = null;

            try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(Iceparams)) {

                String ipAdress = "10.188.38.53";
                communicator.getProperties().setProperty("Ice.Default.Package", "MorceauManager");
                Mp3filesManagerPrx mp3manager = Mp3filesManagerPrx.checkedCast(communicator.stringToProxy("Mp3filesManager:default -h "+ipAdress+" -p 10000"));


                switch (action){
                    case "jouer":
                        mp3manager.startStream(clip);
                        break;
                    case "stopper":
                        mp3manager.pauseStream();
                        break;
                    case "continuer":
                        mp3manager.resumeStream();
                        break;
                }
                // hello.AjouterUnFichier(txtvw_nom_music.getText().toString());
                // communicator.waitForShutdown();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // progressBar_ice.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            // progressBar_ice.setVisibility(View.VISIBLE);
        }
    }

    void StartStreaming(String music){
        String clip = music+".mp3";
        new MainActivity.IceCommunication(clip,"jouer").execute();
    }

    void StopStreaming(String music){
        String clip = music+".mp3";
        new MainActivity.IceCommunication(clip,"stopper").execute();
    }

    void ResumeStreaming(String music){
        String clip = music+".mp3";
        new MainActivity.IceCommunication(clip,"continuer").execute();
    }




}